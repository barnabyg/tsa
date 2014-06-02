 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

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

/**
 * @author Barnaby Golden
 *
 */
public final class StrategyUploadFeatureSteps extends FeatureSteps {

    /**
     * Run before each scenario.
     */
    @Before("@strategy")
    public void beforeScenario() {

        // before
    }

    /**
     * A strategy file.
     * @throws Throwable thrown
     */
    @Given("^a strategy file$")
    public void aStrategyFile() throws Throwable {

        final File file = new File(TestBase.STRATEGY_FILE);

        assertTrue("Test file: "
          + TestBase.STRATEGY_FILE + " not found", file.exists());
    }

    /**
     * The strategy file is loaded.
     * @throws Throwable thrown
     */
    @When("^the strategy file is loaded$")
    public void strategyFileIsLoaded() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.STRATEGY_UPLOAD_ACTION + ".go");

        Thread.sleep(TestBase.DELAY);

        fillField("names0", TestBase.STRATEGY_NAME);
        fillField("filePath", TestBase.STRATEGY_FILE);

        hitSubmit("strategyUploadSubmit");
    }

    /**
     * The strategy is stored.
     * @throws Throwable thrown
     */
    @Then("^the strategy is stored$")
    public void strategyIsStored() throws Throwable {

        getDriver().get(TestBase.getUrl()
                + BaseController.LIST_ACTION + ".go?listType=STRATEGY");

        final WebElement element = getDriver().findElement(
            By.xpath("//*[contains(.,'"
                        + TestBase.STRATEGY_NAME + "')]"));

        assertNotNull("Strategy name not found on list page", element);

        Thread.sleep(TestBase.DELAY);

    }

    /**
     * Run after each scenario.
     * @throws InterruptedException thrown
     */
    @After("@strategy")
    public void afterScenario() throws InterruptedException  {

        // clean up the strategy
        getDriver().get(TestBase.getUrl()
                + BaseController.DEL_STRATEGY_FORM_ACTION + ".go");

        Thread.sleep(TestBase.DELAY);

        fillField("name", TestBase.STRATEGY_NAME);

        hitSubmit("deleteSubmit");

        getDriver().quit();
    }
}
