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
import com.blizzardtec.tsa.form.DeleteForm;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class DeleteController extends BaseController {

    /**
     * Delete form name.
     */
    private static final String DELETE_FORM_NAME = "deleteForm";
    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Show the page for deleting a strategy.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(
            value = DEL_STRATEGY_FORM_ACTION, method = RequestMethod.GET)
    public String deleteStrategyRequest(final ModelMap map) {

        populateStrategyMap(map);

        map.addAttribute(DELETE_FORM_NAME, new DeleteForm());

        return DELETE_STRATEGY_PAGE;
    }

    /**
     * Populate strategy map.
     * @param map map
     */
    private void populateStrategyMap(final ModelMap map) {

        final ConcurrentMap<String, String> strategyNameMap =
                new ConcurrentHashMap<String, String>();

        for (final String strategyName
                : delegate.listNames(ObjectType.STRATEGY)) {

            strategyNameMap.put(strategyName, strategyName);

        }

        map.addAttribute("strategyNames", strategyNameMap);
    }

    /**
     * Show the page for deleting a dataset.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(
            value = DEL_DATASET_FORM_ACTION, method = RequestMethod.GET)
    public String deleteDatasetRequest(final ModelMap map) {

        populateDatasetMap(map);

        map.addAttribute(DELETE_FORM_NAME, new DeleteForm());

        return DELETE_DATASET_PAGE;
    }

    /**
     * Populate dataset map.
     * @param map map
     */
    private void populateDatasetMap(final ModelMap map) {

        final ConcurrentMap<String, String> datasetNameMap =
                new ConcurrentHashMap<String, String>();

        for (final String datasetName
                : delegate.listNames(ObjectType.DATASET)) {
            datasetNameMap.put(datasetName, datasetName);
        }

        map.addAttribute("datasetNames", datasetNameMap);
    }

    /**
     * Show the page for deleting a result set.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(value = DEL_RESULTSET_FORM_ACTION,
                   method = RequestMethod.GET)
    public String deleteResultsetRequest(final ModelMap map) {

        populateResultsetMap(map);

        map.addAttribute(DELETE_FORM_NAME, new DeleteForm());

        return DELETE_RESULTSET_PAGE;
    }

    /**
     * Populate result set map.
     * @param map map
     */
    private void populateResultsetMap(final ModelMap map) {

        final ConcurrentMap<String, String> resultsetNameMap
                    = new ConcurrentHashMap<String, String>();

        for (final String datasetName : delegate
                .listNames(ObjectType.RESULTSET)) {
            resultsetNameMap.put(datasetName, datasetName);
        }

        map.addAttribute("resultsetNames", resultsetNameMap);
    }

    /**
     * Show the page for deleting a rule.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(value = DEL_RULE_FORM_ACTION,
                   method = RequestMethod.GET)
    public String deleteRuleRequest(final ModelMap map) {

        populateRuleMap(map);

        map.addAttribute(DELETE_FORM_NAME, new DeleteForm());

        return DELETE_RULE_PAGE;
    }

    /**
     * Populate rule map.
     * @param map map
     */
    private void populateRuleMap(final ModelMap map) {

        final ConcurrentMap<String, String> ruleNameMap
                    = new ConcurrentHashMap<String, String>();

        for (final String ruleName : delegate.listNames(ObjectType.RULE)) {
            ruleNameMap.put(ruleName, ruleName);
        }

        map.addAttribute("ruleNames", ruleNameMap);
    }

    /**
     * Delete a strategy.
     * @param deleteForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = DELETE_STRATEGY_ACTION, method = RequestMethod.POST)
    public String deleteStrategy(
           @Valid @ModelAttribute(DELETE_FORM_NAME) final DeleteForm deleteForm,
           final BindingResult result, final ModelMap map) {

        String retVal =
                redirect(LIST_ACTION, new String[] {"listType=STRATEGY"});

        if (result.hasErrors()) {
            populateStrategyMap(map);
            retVal = DELETE_STRATEGY_PAGE;
        } else {
            this.delegate.delete(deleteForm.getName(), ObjectType.STRATEGY);
        }

        return retVal;
    }

    /**
     * Delete a dataset.
     * @param deleteForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = DELETE_DATASET_ACTION, method = RequestMethod.POST)
    public String deleteDataset(
           @Valid @ModelAttribute(DELETE_FORM_NAME) final DeleteForm deleteForm,
           final BindingResult result, final ModelMap map) {

        String retVal =
                redirect(LIST_ACTION, new String[] {"listType=DATASET"});

        if (result.hasErrors()) {
            populateDatasetMap(map);
            retVal = DELETE_DATASET_PAGE;
        } else {
            this.delegate.delete(deleteForm.getName(), ObjectType.DATASET);
        }

        return retVal;
    }

    /**
     * Delete a result set.
     * @param deleteForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = DELETE_RESULTSET_ACTION, method = RequestMethod.POST)
    public String deleteResultset(
           @Valid @ModelAttribute(DELETE_FORM_NAME) final DeleteForm deleteForm,
           final BindingResult result, final ModelMap map) {

        String retVal =
                redirect(LIST_ACTION, new String[] {"listType=RESULTSET"});

        if (result.hasErrors()) {
            populateResultsetMap(map);
            retVal = DELETE_RESULTSET_PAGE;
        } else {
            this.delegate.delete(deleteForm.getName(), ObjectType.RESULTSET);
        }

        return retVal;
    }

    /**
     * Delete a rule.
     * @param deleteForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = DELETE_RULE_ACTION, method = RequestMethod.POST)
    public String deleteRule(
           @Valid @ModelAttribute(DELETE_FORM_NAME) final DeleteForm deleteForm,
           final BindingResult result, final ModelMap map) {

        String retVal =
                redirect(LIST_ACTION, new String[] {"listType=RULE"});

        if (result.hasErrors()) {
            populateRuleMap(map);
            retVal = DELETE_RULE_PAGE;
        } else {
            this.delegate.delete(deleteForm.getName(), ObjectType.RULE);
        }

        return retVal;
    }
}
