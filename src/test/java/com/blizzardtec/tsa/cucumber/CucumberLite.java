/**
 * Cut-down set of Cucumber integration tests to run.
 */
package com.blizzardtec.tsa.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {
        "pretty",
        "junit:target/cucumber-junit-report/allcukes.xml",
        "html:target/cucumber",
        "json:target/cucumber.json" },
        tags = "@addstrategy",
        monochrome = true)
public final class CucumberLite {

}
