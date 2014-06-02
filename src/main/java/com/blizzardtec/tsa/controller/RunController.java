 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.form.AnalysisRunForm;
import com.blizzardtec.tsa.DelegateException;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class RunController extends BaseController {

    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Direct to a page that configures a strategy test run.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(value = CONFIGURE_RUN_ACTION,
                   method = RequestMethod.GET)
    public String configureRun(final ModelMap map) {

        populate(map);

        map.addAttribute("analysisRunForm", new AnalysisRunForm());

        return CONFIGURE_RUN_PAGE;
    }

    /**
     * Populate the map with attributes.
     * @param map map
     */
    private void populate(final ModelMap map) {

        final ConcurrentMap<String, String> datasetNameMap =
                new ConcurrentHashMap<String, String>();

        final ConcurrentMap<String, String> strategyNameMap =
                new ConcurrentHashMap<String, String>();

        for (final String datasetName
                : delegate.listNames(ObjectType.DATASET)) {

            datasetNameMap.put(datasetName, datasetName);
        }

        for (final String strategyName
                : delegate.listNames(ObjectType.STRATEGY)) {

            strategyNameMap.put(strategyName, strategyName);
        }

        map.addAttribute("datasetNames", datasetNameMap);
        map.addAttribute("strategyNames", strategyNameMap);
    }

    /**
     * Run a strategy test.
     * @param runForm analysis form
     * @param map map
     * @param result result
     * @return JSP mapping
     */
    @RequestMapping(value = ANALYSIS_RUN_ACTION,
                   method = RequestMethod.POST)
    public String analysisRun(
        @Valid @ModelAttribute("analysisRunForm") final AnalysisRunForm runForm,
        final BindingResult result, final ModelMap map) {

        String retVal = null;

        if (result.hasErrors()) {
            populate(map);
            retVal = CONFIGURE_RUN_PAGE;
        } else {
            try {

                // starting funds is in pounds but is stored in pence
                final String runName = delegate.runAnalysis(
                        runForm.getDatasetName(),
                        runForm.getStrategyName(),
                        runForm.getStartingFunds() * 100);

                final String paramStr = "runId=" + runName;

                retVal =
                     redirect(CONF_VISUALISE_ACTION, new String[] {paramStr});

            } catch (DelegateException de) {

                result.reject("RunError");
                retVal = CONFIGURE_RUN_PAGE;
            }
        }

        return retVal;
    }
}
