 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.form.VisualiseForm;
import com.blizzardtec.tsa.visualisation.VisualisationHelper.ChartType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class VisualiseController extends BaseController {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(VisualiseController.class);

    /**
     * Image type.
     */
    private static final String IMAGE_TYPE = "image/png";
    /**
     * Width.
     */
    private static final int WIDTH = 800;
    /**
     * Height.
     */
    private static final int HEIGHT = 400;

    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Configure the visualisation of results page.
     *
     * @param runId optional parameter to set the run id
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(value = CONF_VISUALISE_ACTION,
                   method = RequestMethod.GET)
    public String visualiseConfRequest(
           @RequestParam(value = "runId", defaultValue = "") final String runId,
           final ModelMap map) {

        final ConcurrentMap<String, String> runsMap =
                new ConcurrentHashMap<String, String>();

        final List<String> resultsetNames =
                delegate.listNames(ObjectType.RESULTSET);

        for (final String resultset: resultsetNames) {
            runsMap.put(resultset, resultset);
        }

        map.addAttribute("allRuns", runsMap);

        final ConcurrentMap<String, String> chartTypesMap =
                new ConcurrentHashMap<String, String>();

        for (final ChartType chartType: ChartType.values()) {
            chartTypesMap.put(chartType.toString(), chartType.toString());
        }

        map.addAttribute("chartTypes", chartTypesMap);

        final VisualiseForm visualiseForm = new VisualiseForm();

        if (runId.isEmpty()) {
            if (!resultsetNames.isEmpty()) {
                final String runName = resultsetNames.get(0);
                visualiseForm.setRunId(runName);
                map.addAttribute("resultset", delegate.getResultset(runName));
            }
        } else {
            visualiseForm.setRunId(runId);
            map.addAttribute("resultset", delegate.getResultset(runId));
        }

        map.addAttribute("visualiseForm", visualiseForm);

        return CONF_VISUALISE_PAGE;
    }

    /**
     * Chart image generation.
     * @param runId run name
     * @param chartType chart type
     * @param map map
     * @param request servlet request
     * @param response servlet response
     */
    @RequestMapping(value = CHART_ACTION,
                   method = RequestMethod.GET)
    public void chartRequest(
            @RequestParam("runId") final String runId,
            @RequestParam("chartType") final String chartType,
            final ModelMap map,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        response.setContentType(IMAGE_TYPE);

        JFreeChart chart = null;

        try {

            chart = delegate.showChart(ChartType.valueOf(chartType), runId);

        } catch (DelegateException de) {
            LOG.error("Delegate error when generating chart \""
                            + runId + "\" error: " + de.getMessage());
        }

        try {

            ChartUtilities.writeChartAsPNG(
                    response.getOutputStream(), chart, WIDTH, HEIGHT);

        } catch (IOException ioe) {
            LOG.error("IO error when generating chart with name \""
                    + runId + "\" error: " + ioe.getMessage());
        }

        try {

            response.getOutputStream().close();

        } catch (IOException ioe) {
            LOG.error("IO close error when generating chart with name \""
                    + runId + "\" error: " + ioe.getMessage());
        }
    }
}
