 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Barnaby Golden
 *
 */
public class BaseController {

    /**
     * Home page.
     */
    public static final String HOME_PAGE = "home";
    /**
     * Corresponding action for home page.
     */
    public static final String HOME_ACTION = "/home";

    /**
     * Help page.
     */
    public static final String HELP_PAGE = "help";
    /**
     * Corresponding action for help page.
     */
    public static final String HELP_ACTION = "/help";

    /**
     * Contacts page.
     */
    public static final String CONTACT_PAGE = "contact";
    /**
     * Corresponding action for contact page.
     */
    public static final String CONTACT_ACTION = "/contact";

    /**
     * Strategy upload page.
     */
    public static final String STRATEGY_UPLOAD_PAGE = "strategy_upload_form";
    /**
     * Corresponding action for strategy upload page.
     */
    public static final String STRATEGY_UPLOAD_ACTION = "/strategyupload";

    /**
     * Dataset upload page.
     */
    public static final String DATASET_UPLOAD_PAGE = "dataset_upload_form";
    /**
     * Corresponding action for dataset upload page.
     */
    public static final String DATASET_UPLOAD_ACTION = "/datasetupload";

    /**
     * List page.
     */
    public static final String LIST_PAGE = "list";
    /**
     * Corresponding action for list page.
     */
    public static final String LIST_ACTION = "/list";

    /**
     * Corresponding action for deletion of a strategy.
     */
    public static final String DEL_STRATEGY_FORM_ACTION
                                        = "/deletestrategyform";
    /**
     * Page to request deletion of a strategy.
     */
    public static final String DELETE_STRATEGY_PAGE = "delete_strategy";
    /**
     * Corresponding action for request deletion of a strategy page.
     */
    public static final String DELETE_STRATEGY_ACTION = "deletestrategy";

    /**
     * Corresponding action for deletion of a dataset.
     */
    public static final String DEL_DATASET_FORM_ACTION
                                        = "/deletedatasetform";
    /**
     * Page to request deletion of a dataset.
     */
    public static final String DELETE_DATASET_PAGE = "delete_dataset";
    /**
     * Corresponding action for request deletion of a dataset page.
     */
    public static final String DELETE_DATASET_ACTION = "deletedataset";

    /**
     * Corresponding action for deletion of a result set.
     */
    public static final String DEL_RESULTSET_FORM_ACTION
                                        = "/deleteresultsetform";
    /**
     * Page to request deletion of a result set.
     */
    public static final String DELETE_RESULTSET_PAGE = "delete_resultset";
    /**
     * Corresponding action for request deletion of a result set page.
     */
    public static final String DELETE_RESULTSET_ACTION = "deleteresultset";

    /**
     * Corresponding action for deletion of a rule.
     */
    public static final String DEL_RULE_FORM_ACTION
                                        = "/deleteruleform";
    /**
     * Page to request deletion of a rule.
     */
    public static final String DELETE_RULE_PAGE = "delete_rule";
    /**
     * Corresponding action for request deletion of a rule page.
     */
    public static final String DELETE_RULE_ACTION = "deleterule";

    /**
     * Corresponding action for dataset save.
     */
    public static final String DATASET_SAVE_ACTION = "/datasetsave";

    /**
     * Corresponding action for strategy save.
     */
    public static final String STRATEGY_SAVE_ACTION = "/strategysave";

    /**
     * Configure test page.
     */
    public static final String CONFIGURE_RUN_PAGE = "run_configure";
    /**
     * Corresponding action for configure analysis run page.
     */
    public static final String CONFIGURE_RUN_ACTION = "configurerun";

    /**
     * Corresponding action for analysis run.
     */
    public static final String ANALYSIS_RUN_ACTION = "/analysisrun";

    /**
     * Run results page.
     */
    public static final String RUN_RESULTS_PAGE = "run_results";
    /**
     * Corresponding action for run results.
     */
    public static final String RUN_RESULTS_ACTION = "/runresults";

    /**
     * Configure visualisation page.
     */
    public static final String CONF_VISUALISE_PAGE = "visualise";
    /**
     * Corresponding action for configure visualisation.
     */
    public static final String CONF_VISUALISE_ACTION = "/visualise";

    /**
     * Corresponding action for chart image generation.
     */
    public static final String CHART_ACTION = "/chart";

    /**
     * Form for creating rule page.
     */
    public static final String CREATE_RULE_FORM_PAGE = "create_rule";
    /**
     * Corresponding action for creation of a rule form.
     */
    public static final String CREATE_RULE_FORM_ACTION
                                        = "/createruleform";
    /**
     * Corresponding action for rule creation.
     */
    public static final String CREATE_RULE_ACTION = "/createrule";

    /**
     * Form for creating strategy page.
     */
    public static final String CREATE_STRGY_FORM_PAGE = "create_strategy";
    /**
     * Corresponding action for creation of a strategy form.
     */
    public static final String CREATE_STRGY_FORM_ACTION
                                        = "/createstrategyform";
    /**
     * Corresponding action for strategy creation.
     */
    public static final String CREATE_STRGY_ACTION = "/createstrategy";

    /**
     * Format a forward response for a given action.
     * @param action action to forward to
     * @return formated forward string
     */
    protected final String forward(final String action) {

        return "forward:" + action + ".go";
    }

    /**
     * Format a redirect response including query string parameters.
     * @param action action to forward to
     * @param params parameters in the form "x=y"
     * @return forward string
     */
    protected final String redirect(
                final String action, final String[] params) {

        final StringBuffer buffer = new StringBuffer();

        buffer.append("redirect:" + action + ".go?");

        for (final String param: params) {
            buffer.append(param);
        }

        return buffer.toString();
    }

    /**
     * Add a binder to handle empty number fields.
     * Otherwise there will be a conversion error.
     * @param binder param
     */
    @InitBinder
    public final void initBinder(final WebDataBinder binder) {

       binder.registerCustomEditor(
               Float.class, new MyCustomNumberEditor(Float.class, true));

       binder.registerCustomEditor(
               Integer.class, new MyCustomNumberEditor(Integer.class, true));
    }
}
