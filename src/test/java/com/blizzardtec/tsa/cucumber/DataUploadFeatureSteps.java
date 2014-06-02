 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.controller.BaseController;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Barnaby Golden
 *
 */
public final class DataUploadFeatureSteps extends FeatureSteps {

    /**
     * Run before each scenario.
     */
    @Before("@data")
    public void beforeScenario() {
        // before
    }

    /**
     * A file containing n days worth of data.
     *
     * @param arg1 param
     * @throws Throwable thrown
     */
    @Given("^a file containing (\\d+) days worth of data$")
    public void aCsvFileContainingDaysWorthOfData(final int arg1)
            throws Throwable {

        final File file = new File(TestBase.DATASET_FILE);

        assertTrue("Test file: "
           + TestBase.DATASET_FILE + " not found", file.exists());
    }

    /**
     * The file is loaded.
     *
     * @throws Throwable thrown
     */
    @When("^the file is loaded$")
    public void theCsvFileIsLoaded() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.DATASET_UPLOAD_ACTION + ".go");

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.DATASET_NAME);
        fillField("filePath", TestBase.DATASET_FILE);
        fillField("dataType", TestBase.STD_DATA_TYPE);

        hitSubmit("datasetUploadSubmit");
    }

    /**
     * n days worth of data is stored.
     *
     * @param arg1 param
     * @throws Throwable thrown
     */
    @Then("^(\\d+) days worth of data is stored$")
    public void daysWorthOfDataIsStored(final int arg1)
            throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.LIST_ACTION + ".go?listType=DATASET");

        Thread.sleep(TestBase.DELAY);

        final WebElement element = getDriver().findElement(
                By.xpath("//*[contains(.,'"
                            + TestBase.DATASET_NAME + "')]"));

            assertNotNull("Dataset name not found on list page", element);
    }

    /**
     * Run after each scenario.
     * @throws InterruptedException thrown
     */
    @After("@data")
    public void afterScenario() throws InterruptedException {

        // clean up the dataset
        getDriver().get(TestBase.getUrl()
                + BaseController.DEL_DATASET_FORM_ACTION + ".go");

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.DATASET_NAME);

        hitSubmit("deleteSubmit");

        getDriver().quit();
    }
}
