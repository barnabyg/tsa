 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.controller.BaseController;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.rules.Rule.RuleType;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Barnaby Golden
 *
 */
public final class AddStrategyFeatureSteps extends FeatureSteps {

    /**
     * Name field ID.
     */
    private static final String NAME_ID = "name";
    /**
     * Action extension.
     */
    private static final String ACTION_EXT = ".go";

    /**
     * Run before each scenario.
     */
    @Before("@addstrategy")
    public void beforeScenario() {
        // before
    }

    /**
     * Given I enter several rules.
     * @throws Throwable thrown
     */
    @Given("^I enter several rules$")
    public void enterSeveralRules() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.CREATE_RULE_FORM_ACTION + ACTION_EXT);

        Thread.sleep(TestBase.DELAY);

        fillField(NAME_ID, TestBase.RULE_NAME);
        fillField("ruleType", RuleType.INDICATOR_VALUE.name());
        fillField("valueIndicatorName", IndicatorType.FIVE_DAY_AVERAGE.name());
        fillField("valueOperation", OperationType.GREATER.name());
        fillField("value", Float.toString(TestBase.RULE_VALUE));
        fillField("tradeType", TradeType.BUY.name());
        fillField("instrument", TestBase.INSTRUMENT);
        fillField("quantity", Integer.toString(TestBase.QUANTITY));

        hitSubmit("createRuleSubmit");
    }

    /**
     * When I save the new strategy.
     * @throws Throwable thrown
     */
    @When("^I save the new strategy$")
    public void saveNewStrategy() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.CREATE_STRGY_FORM_ACTION + ACTION_EXT);

        Thread.sleep(TestBase.DELAY);

        fillField(NAME_ID, TestBase.STRATEGY_NAME);
        fillField("rules0", TestBase.RULE_NAME);

        hitSubmit("createStrategySubmit");
    }

    /**
     * Then I can analyse the new strategy.
     * @throws Throwable thrown
     */
    @Then("^I can analyse the new strategy$")
    public void canAnalyseNewStrategy() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DATASET_UPLOAD_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.DATASET_NAME);
        fillField("filePath", TestBase.DATASET_FILE);
        fillField("dataType", TestBase.STD_DATA_TYPE);

        hitSubmit("datasetUploadSubmit");

        getDriver().get(TestBase.getUrl()
                + format(BaseController.CONFIGURE_RUN_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("strategyName", TestBase.STRATEGY_NAME);
        fillField("datasetName", TestBase.DATASET_NAME);
        fillField("startingFunds", Float.toString(TestBase.STARTING_FUNDS));

        hitSubmit("analysisSubmit");

        Thread.sleep(getChartDelay());
    }

    /**
     * Run after each scenario.
     *
     * @throws InterruptedException
     *             will never be thrown
     */
    @After("@addstrategy")
    public void afterScenario() throws InterruptedException {

        // clean up rule
        getDriver().get(TestBase.getUrl()
                + BaseController.DEL_RULE_FORM_ACTION + ACTION_EXT);

        Thread.sleep(TestBase.DELAY);

        fillField(NAME_ID, TestBase.RULE_NAME);

        hitSubmit("deleteSubmit");

        // clean up strategy
        getDriver().get(TestBase.getUrl()
                + BaseController.DEL_STRATEGY_FORM_ACTION + ACTION_EXT);

        Thread.sleep(TestBase.DELAY);

        fillField(NAME_ID, TestBase.STRATEGY_NAME);

        hitSubmit("deleteSubmit");

        // clean up the dataset

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_DATASET_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField(NAME_ID, TestBase.DATASET_NAME);

        hitSubmit("deleteSubmit");

        getDriver().quit();
    }
}
