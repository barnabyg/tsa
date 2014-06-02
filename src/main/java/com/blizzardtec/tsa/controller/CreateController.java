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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.form.CreateRuleForm;
import com.blizzardtec.tsa.form.CreateStrategyForm;
import com.blizzardtec.tsa.indicator.Indicator;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.IndicatorDirectionRule;
import com.blizzardtec.tsa.rules.IndicatorValueRule;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.Rule.RuleType;
import com.blizzardtec.tsa.strategy.Strategy;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class CreateController extends BaseController {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(CreateController.class);

    /**
     * Create rule form name.
     */
    private static final String CREATE_RULE_FORM_NAME = "createRuleForm";
    /**
     * Create rule form name.
     */
    private static final String CREATE_STRGY_FORM_NAME
                                        = "createStrategyForm";

    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Show the page for creating a rule.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(
            value = CREATE_RULE_FORM_ACTION, method = RequestMethod.GET)
    public String createRuleForm(final ModelMap map) {

        populateRuleMap(map);

        map.addAttribute(CREATE_RULE_FORM_NAME, new CreateRuleForm());

        return CREATE_RULE_FORM_PAGE;
    }

    /**
     * Populate rule map.
     * @param map map
     */
    private void populateRuleMap(final ModelMap map) {

        // rule types
        final ConcurrentMap<String, String> ruleTypeMap =
                new ConcurrentHashMap<String, String>();

        for (final RuleType ruleType: RuleType.values()) {

            ruleTypeMap.put(ruleType.name(), ruleType.name());
        }

        map.addAttribute("ruleTypes", ruleTypeMap);

        // value indicators
        final ConcurrentMap<String, String> valueIndicatorsMap =
                new ConcurrentHashMap<String, String>();

        for (final IndicatorType indicatorType: Indicator.VALUE_INDICATORS) {
            valueIndicatorsMap.put(indicatorType.name(), indicatorType.name());
        }

        map.addAttribute("valueIndicatorNames", valueIndicatorsMap);

        // direction indicators
        final ConcurrentMap<String, String> directionIndicatorsMap =
                new ConcurrentHashMap<String, String>();

        for (final IndicatorType indicatorType
                            : Indicator.DIRECTION_INDICATORS) {

            directionIndicatorsMap.put(
                    indicatorType.name(), indicatorType.name());
        }

        map.addAttribute("directionIndicatorNames", directionIndicatorsMap);

        // value operations
        final ConcurrentMap<String, String> valueOperationsMap =
                new ConcurrentHashMap<String, String>();

        for (final OperationType operationType: Rule.VALUE_OPS) {

            valueOperationsMap.put(
                    operationType.name(), operationType.name());
        }

        map.addAttribute("valueOperations", valueOperationsMap);

        // direction operations
        final ConcurrentMap<String, String> directionOperationsMap =
                new ConcurrentHashMap<String, String>();

        for (final OperationType operationType: Rule.DIRECTION_OPS) {

            directionOperationsMap.put(
                    operationType.name(), operationType.name());
        }

        map.addAttribute("directionOperations", directionOperationsMap);

        // trade types
        final ConcurrentMap<String, String> tradeTypesMap =
                new ConcurrentHashMap<String, String>();

        for (final TradeType tradeType: TradeType.values()) {

            tradeTypesMap.put(tradeType.name(), tradeType.name());
        }

        map.addAttribute("tradeTypes", tradeTypesMap);
    }

    /**
     * Create a new rule.
     * @param createRuleForm create rule form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = CREATE_RULE_ACTION, method = RequestMethod.POST)
    public String createRule(
            @Valid @ModelAttribute(
            CREATE_RULE_FORM_NAME) final CreateRuleForm createRuleForm,
            final BindingResult result,
            final ModelMap map) {

        String retVal =
              redirect(LIST_ACTION, new String[] {"listType=RULE"});

        if (result.hasErrors()) {
            populateRuleMap(map);
            retVal = CREATE_RULE_FORM_PAGE;
        } else {
            final Trade trade = new Trade();

            trade.setType(TradeType.valueOf(createRuleForm.getTradeType()));

            trade.setInstrument(createRuleForm.getInstrument());
            trade.setQuantity(createRuleForm.getQuantity());

            final float value = createRuleForm.getValue();

            Rule rule = null;

            IndicatorType indicatorType = null;

            OperationType operationType = null;

            if (RuleType.valueOf(createRuleForm.getRuleType())
                                        == RuleType.INDICATOR_DIRECTION) {

                operationType = OperationType.valueOf(
                        createRuleForm.getDirectionOperation());

                indicatorType = IndicatorType.valueOf(
                        createRuleForm.getDirectionIndicatorName());

                rule = new IndicatorDirectionRule(
                            indicatorType, operationType, trade);

            }

            if (RuleType.valueOf(createRuleForm.getRuleType())
                    == RuleType.INDICATOR_VALUE) {

                operationType = OperationType.valueOf(
                        createRuleForm.getValueOperation());

                indicatorType = IndicatorType.valueOf(
                        createRuleForm.getValueIndicatorName());

                rule = new IndicatorValueRule(
                            indicatorType, operationType, value, trade);
            }

            rule.setName(createRuleForm.getName());

            try {
                delegate.createRule(rule);
            } catch (DelegateException e) {
                result.reject("CreateRuleError");
                retVal = CREATE_RULE_FORM_PAGE;
                LOG.error(e.getMessage());
            }
        }

        return retVal;
    }

    /**
     * Show the page for creating a strategy.
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(
            value = CREATE_STRGY_FORM_ACTION, method = RequestMethod.GET)
    public String createStrategyForm(final ModelMap map) {

        populateStrategyMap(map);

        map.addAttribute(CREATE_STRGY_FORM_NAME, new CreateStrategyForm());

        return CREATE_STRGY_FORM_PAGE;
    }

    /**
     * Populate strategy map.
     * @param map map
     */
    private void populateStrategyMap(final ModelMap map) {

        final ConcurrentMap<String, String> rulesMap =
                                new ConcurrentHashMap<String, String>();

        for (final String ruleName: delegate.listNames(ObjectType.RULE)) {
            rulesMap.put(ruleName, ruleName);
        }

        map.addAttribute("ruleNames", rulesMap);
    }

    /**
     * Create a new strategy.
     * @param createStrategyForm create strategy form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(
            value = CREATE_STRGY_ACTION, method = RequestMethod.POST)
    public String createStrategy(
            @Valid @ModelAttribute(
            CREATE_STRGY_FORM_NAME) final CreateStrategyForm createStrategyForm,
            final BindingResult result,
            final ModelMap map) {

        String retVal =
                redirect(LIST_ACTION, new String[] {"listType=STRATEGY"});

        if (result.hasErrors()) {
            populateStrategyMap(map);
            retVal = CREATE_STRGY_FORM_PAGE;
        } else {
            final Strategy strategy = new Strategy();
            strategy.setName(createStrategyForm.getName());

            if (LOG.isDebugEnabled()) {
                LOG.debug("Creating strategy " + strategy.getName());
            }

            final List<Rule> list = new ArrayList<Rule>();

            try {

                for (final String ruleName: createStrategyForm.getRules()) {

                    if (LOG.isDebugEnabled()) {
                        LOG.debug(
                                "Adding rule " + ruleName
                                    + " to " + strategy.getName());
                    }

                    list.add(delegate.getRule(ruleName));
                }

                strategy.setRules(list);

                delegate.createStrategy(strategy);

            } catch (DelegateException e) {
                result.reject("CreateStrategyError");
                retVal = CREATE_STRGY_FORM_PAGE;
                LOG.error(e.getMessage());
            }
        }

        return retVal;
    }
}
