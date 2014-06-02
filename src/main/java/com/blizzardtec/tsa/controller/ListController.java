 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class ListController extends BaseController {

    /**
     * List field.
     */
    private static final String LIST_MSG = "listMessage";
    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Request handler for home page.
     * @param listType the type of list we should show
     * @param map param
     * @param request servlet request
     * @param response servlet response
     * @return JSP mapping
     */
    @RequestMapping(value = LIST_ACTION, method = RequestMethod.GET)
    public String homeRequest(
            @RequestParam("listType") final String listType,
            final ModelMap map,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        List<String> list = null;

        switch (listType) {
        case "DATASET":
            list = delegate.listNames(ObjectType.DATASET);
            if (list.isEmpty()) {
                map.addAttribute(LIST_MSG, "There are no datasets");
            } else {
                map.addAttribute("datasetNames", list);
            }
            break;
        case "STRATEGY":
            list = delegate.listNames(ObjectType.STRATEGY);
            if (list.isEmpty()) {
                map.addAttribute(LIST_MSG, "There are no strategies");
            } else {
                map.addAttribute("strategyNames", list);
            }
            break;
        case "INDICATOR":
            list = new ArrayList<String>();
            for (final IndicatorType indicatorType: IndicatorType.values()) {
                list.add(indicatorType.name());
            }
            if (list.isEmpty()) {
                map.addAttribute(LIST_MSG, "There are no indicators");
            } else {
                map.addAttribute("indicatorNames", list);
            }
            break;
        case "RULE":
            list = delegate.listNames(ObjectType.RULE);
            if (list.isEmpty()) {
                map.addAttribute(LIST_MSG, "There are no rules");
            } else {
                map.addAttribute("ruleNames", list);
            }
            break;
        case "RESULTSET":
            list = delegate.listNames(ObjectType.RESULTSET);
            if (list.isEmpty()) {
                map.addAttribute(LIST_MSG, "There are no result sets");
            } else {
                map.addAttribute("resultsetNames", list);
            }
            break;
        default:
            map.addAttribute(LIST_MSG, "Unknwon list type");
            break;
        }

        return LIST_PAGE;
    }
}
