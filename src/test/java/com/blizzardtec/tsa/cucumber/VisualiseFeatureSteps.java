 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import static org.junit.Assert.fail;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.controller.BaseController;
import com.blizzardtec.tsa.visualisation.VisualisationHelper.ChartType;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Barnaby Golden
 *
 */
public final class VisualiseFeatureSteps
                    extends FeatureSteps {

    /**
     * Run id.
     */
    private transient String runName;

    /**
     * Run before each scenario.
     */
    @Before("@visualise")
    public void beforeScenario() {
        // before
    }

    /**
     * I upload a dataset.
     *
     * @throws Throwable
     *             thrown
     */
    @Given("^I upload a standard dataset$")
    public void uploadADataset() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DATASET_UPLOAD_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.DATASET_NAME);
        fillField("filePath", TestBase.DATASET_FILE);
        fillField("dataType", TestBase.STD_DATA_TYPE);

        hitSubmit("datasetUploadSubmit");
    }

    /**
     * A trading strategy has been loaded.
     *
     * @throws Throwable thrown
     */
    @Given("^I upload a trading strategy$")
    public void tradingStrategyHasBeenLoaded()
            throws Throwable {

        // confirm the strategy file exists
        final File file = new File(TestBase.STRATEGY_FILE);
        if (!file.exists()) {
            fail("Strategy file does not exist");
        }

        getDriver().get(TestBase.getUrl()
                + format(BaseController.STRATEGY_UPLOAD_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.STRATEGY_NAME);
        fillField("filePath", TestBase.STRATEGY_FILE);

        hitSubmit("strategyUploadSubmit");
    }

    /**
     * The trading strategy has been processed against the data.
     *
     * @throws Throwable thrown
     */
    @Given("^the trading strategy has been processed against the data$")
    public void theTradingStrageyHasBeenProcessedAgainsData()
            throws Throwable {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.CONFIGURE_RUN_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("strategyName", TestBase.STRATEGY_NAME);
        fillField("datasetName", TestBase.DATASET_NAME);
        fillField("startingFunds", Float.toString(TestBase.STARTING_FUNDS));

        hitSubmit("analysisSubmit");

        final WebElement element =
                getDriver().findElement(By.id("runId"));

        this.runName = element.getText();

        Thread.sleep(TestBase.DELAY);
    }

    /**
     * Visualise results is selected.
     *
     * @throws Throwable thrown
     */
    @When("^visualise results is selected$")
    public void visualiseResultsIsSelected() throws Throwable {
        // no step required
    }

    /**
     * A chart of the results will be shown.
     *
     * @throws Throwable thrown
     */
    @Then("^a chart of the results will be shown$")
    public void aChartOfTheResultsWillBeShown() throws Throwable {

        showChart(ChartType.CASH_BALANCE_XY);
        showChart(ChartType.NORMALISED_XY);
        showChart(ChartType.SHARE_HOLDING_XY);
    }

    /**
     * Show a chart.
     * @param type the chart type to show
     * @throws InterruptedException thrown
     */
    private void showChart(final ChartType type)
                            throws InterruptedException {

        getDriver().get(TestBase.getUrl()
                + format(BaseController.CONF_VISUALISE_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("runId", this.runName);
        fillField("chartType", type.toString());

        Thread.sleep(TestBase.DELAY * 4);
    }

    /**
     * Run after each scenario.
     *
     * @throws InterruptedException
     *             will never be thrown
     */
    @After("@visualise")
    public void afterScenario() throws InterruptedException {

        // clean up the strategy

        getDriver().get(TestBase.BASE_URL
                + format(BaseController.DEL_STRATEGY_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.STRATEGY_NAME);

        hitSubmit("deleteSubmit");

        // clean up the dataset

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_DATASET_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.DATASET_NAME);

        hitSubmit("deleteSubmit");

        // clean up the result set

        getDriver().get(TestBase.getUrl()
                + format(BaseController.DEL_RESULTSET_FORM_ACTION));

        Thread.sleep(TestBase.DELAY);

        fillField("name", this.runName);

        hitSubmit("deleteSubmit");

        getDriver().quit();
    }
}
