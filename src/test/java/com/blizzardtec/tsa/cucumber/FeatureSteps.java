 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.cucumber;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.blizzardtec.tsa.TestBase;

/**
 * @author Barnaby Golden
 *
 */
public class FeatureSteps {

    /**
     * Driver.
     */
    private WebDriver driver;

    /**
     * Format an action.
     *
     * @param action
     *            the action to format
     * @return the full action string
     */
    protected final String format(final String action) {
        return action + ".go";
    }

    /**
     * @return the driver
     */
    protected final WebDriver getDriver() {

        boolean useGrid = false;

        if (SystemUtils.IS_OS_WINDOWS) {
            System.setProperty(
              "webdriver.gecko.driver", "src/test/resources/wires.exe");
        } else {
            System.setProperty(
              "webdriver.gecko.driver", "src/test/resources/wires");
        }

        final DesiredCapabilities capabilities = DesiredCapabilities.firefox();

        capabilities.setCapability("marionette", true);

        if (driver == null) {

            if (useGrid) {
                DesiredCapabilities capability = DesiredCapabilities.firefox();

                try {
                    driver = new RemoteWebDriver(
                         new URL(TestBase.GRID_URL),
                         capability);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                driver = new FirefoxDriver(capabilities);
            }
        }

        return driver;
    }

    /**
     * @param driver the driver to set
     */
    protected final void setDriver(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Fill a field with a string.
     * @param id the id of the element
     * @param value the string to send to the element
     * @throws InterruptedException thrown
     */
    protected final void fillField(final String id, final String value)
                                        throws InterruptedException {

        final WebElement element = getDriver().findElement(By.id(id));

        element.sendKeys(value);

        Thread.sleep(TestBase.DELAY);
    }

    /**
     * Click on a submit button.
     * @param buttonName id of the submit button
     * @throws InterruptedException thrown
     */
    protected final void hitSubmit(final String buttonName)
                                        throws InterruptedException {

        final WebElement element =
                getDriver().findElement(By.id(buttonName));

        element.sendKeys(Keys.RETURN);

        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(final WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript(
                               "return document.readyState").equals("complete");
                    }
                };

        WebDriverWait wait = new WebDriverWait(driver, TestBase.TIMEOUT);

        wait.until(pageLoadCondition);
    }

    /**
     * Return the delay needed while a chart is loading.
     * @return delay in ms
     */
    protected final int getChartDelay() {

        final String profile = System.getProperty("profile");

        int timeout;

        if (profile.equalsIgnoreCase("local")) {
            timeout = TestBase.CHART_TIMEOUT_LOCAL;
        } else {
            timeout = TestBase.CHART_TIMEOUT_CI;
        }

        return timeout;
    }
}
