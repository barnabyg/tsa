/*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import java.io.File;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.controller.BaseController;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.fail;

/**
 * @author Barnaby Golden
 *
 */
public final class StrategyAnalyserFeatureSteps
                            extends FeatureSteps {

    /**
     * What strategy type are we testing.
     */
    private transient StrategyType strategyType;

    /**
     * Strategy type.
     * @author Barnaby Golden
     *
     */
    private enum StrategyType { RISING, FALLING }

    /**
     * Run before each scenario.
     */
    @Before("@analysis")
    public void beforeScenario() {
        // before
    }

    /**
     * Upload a strategy that [buys/sells] when the [indicator] is [pos/neg].
     * @param strategyType strategy type
     * @param tradeType buys or sells
     * @param indicator indicator
     * @param marketDirection positive or negative
     * @throws Throwable thrown
     */
    @Given("^I upload a \"([^\"]*)\" strategy "
            + "that \"([^\"]*)\" when the \"([^\"]*)\" is \"([^\"]*)\"$")
    public void uploadStrategy(final String strategyType,
            final String tradeType, final String indicator,
            final String marketDirection) throws Throwable {

        if (strategyType.equalsIgnoreCase("rising")) {

            this.strategyType = StrategyType.RISING;
        }

        if (strategyType.equalsIgnoreCase("falling")) {

            this.strategyType = StrategyType.FALLING;
        }

        if (this.strategyType == StrategyType.RISING) {

            // confirm the strategy file exists
            final File file = new File(TestBase.RISING_STRATEGY_FILE);

            if (!file.exists()) {
                fail("Rising strategy file does not exist");
            }

            getDriver().get(TestBase.getUrl()
                    + format(BaseController.STRATEGY_UPLOAD_ACTION));

            Thread.sleep(TestBase.DELAY);

            fillField("names0", TestBase.RISING_STRATEGY_NAME);
            fillField("filePath", TestBase.RISING_STRATEGY_FILE);

            hitSubmit("strategyUploadSubmit");
        }

        if (this.strategyType == StrategyType.FALLING) {
            // confirm the strategy file exists
            final File file = new File(TestBase.FALLING_STRATEGY_FILE);
            if (!file.exists()) {
                fail("Falling strategy file does not exist");
            }

            getDriver().get(TestBase.getUrl()
                    + format(BaseController.STRATEGY_UPLOAD_ACTION));

            Thread.sleep(TestBase.DELAY);

            fillField("names0", TestBase.FALLING_STRATEGY_NAME);
            fillField("filePath", TestBase.FALLING_STRATEGY_FILE);

            hitSubmit("strategyUploadSubmit");
        }
    }

    /**
     * I upload a dataset.
     *
     * @throws Throwable
     *             thrown
     */
    @Given("^I upload a dataset$")
    public void uploadADataset() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DATASET_UPLOAD_ACTION));
        Thread.sleep(TestBase.DELAY);

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.DATASET_NAME);
        fillField("filePath", TestBase.DATASET_FILE);
        fillField("dataType", TestBase.STD_DATA_TYPE);

        hitSubmit("datasetUploadSubmit");
    }

    /**
     * The strategy is evaluated.
     *
     * @throws Throwable
     *             thrown
     */
    @When("^the strategy is evaluated$")
    public void strategyEvaluatedUsingRisingData() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.CONFIGURE_RUN_ACTION));

        Thread.sleep(TestBase.DELAY);

        if (this.strategyType == StrategyType.RISING) {
            fillField("strategyName", TestBase.RISING_STRATEGY_NAME);
        } else {
            fillField("strategyName", TestBase.FALLING_STRATEGY_NAME);
        }

        fillField("datasetName", TestBase.DATASET_NAME);
        fillField("startingFunds", Float.toString(TestBase.STARTING_FUNDS));

        hitSubmit("analysisSubmit");
    }

    /**
     * Only buy events will occur.
     *
     * @throws Throwable
     *             thrown
     */
    @Then("^only buy events will occur$")
    public void onlyBuyEventsOccur() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    /**
     * No buy events will occur.
     *
     * @throws Throwable
     *             thrown
     */
    @Then("^no buy events will occur$")
    public void noBuyEventsWillOccur() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    /**
     * Run after each scenario.
     *
     * @throws InterruptedException
     *             will never be thrown
     */
    @After("@analysis")
    public void afterScenario() throws InterruptedException {

        // clean up the strategies

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_STRATEGY_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.RISING_STRATEGY_NAME);

        hitSubmit("deleteSubmit");

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_STRATEGY_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.FALLING_STRATEGY_NAME);

        hitSubmit("deleteSubmit");

        // clean up the dataset

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_DATASET_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.DATASET_NAME);

        hitSubmit("deleteSubmit");

        getDriver().quit();
    }
}
