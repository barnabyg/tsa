/**
 *
 */
package com.blizzardtec.tsa;

import java.io.InputStream;
import java.util.List;

import org.jfree.chart.JFreeChart;

import com.blizzardtec.tsa.data.DatasetHelper.DataType;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.visualisation.VisualisationHelper.ChartType;

/**
 * @author Barnaby Golden
 *
 */
public interface BusinessDelegate {

    /**
     * List of the object types dealt with
     * to help with generic methods.
     * @author Barnaby Golden
     *
     */
    enum ObjectType { STRATEGY, DATASET, RESULTSET, RULE }

    /**
     * Load a CSV file.
     *
     * @param stream input stream
     * @param name the unique name for the data set
     * @param type the type of data
     * @throws DelegateException thrown
     */
    void saveDataSet(InputStream stream, String name, DataType type)
                                                throws DelegateException;

    /**
     * Generic delete object method.
     * @param name name of the object to delete
     * @param type type of the object to delete
     */
    void delete(String name, ObjectType type);

    /**
     * Load a trading strategy file.
     *
     * @param stream input stream
     * @param name the unique name for the strategy
     * @throws DelegateException thrown
     */
    void saveStrategy(InputStream stream, String name)
                                        throws DelegateException;

    /**
     * Generic list names method.
     * @param type object type to get list for
     * @return list of names for given objects
     */
    List<String> listNames(ObjectType type);

    /**
     * Run analysis for a given strategy and data set.
     * @param dataSetName unique name of the data set to use
     * @param strategyName unique name of the strategy to use
     * @param startingFunds the amount of cash available
     * @return runId unique run id
     * @throws DelegateException thrown
     */
    String runAnalysis(
            String dataSetName, String strategyName, float startingFunds)
                                        throws DelegateException;

    /**
     * Find the result set for a given name.
     * @param runName result set name
     * @return result set
     */
    Resultset getResultset(String runName);

    /**
     * Produce a chart image byte array for the account balance of a given run.
     * @param runId unique run id
     * @return image
     * @throws DelegateException thrown
     */
    byte[] balanceChart(int runId) throws DelegateException;

    /**
     * Display a chart.
     * @param type chart type
     * @param runId run id
     * @return chart
     * @throws DelegateException thrown
     */
    JFreeChart showChart(
            ChartType type,
            String runId)
                    throws DelegateException;

    /**
     * Create a new rule.
     * @param rule the rule to create
     * @throws DelegateException thrown
     */
    void createRule(Rule rule) throws DelegateException;

    /**
     * Get a rule with the specified name.
     * @param ruleName rule name
     * @return rule or null if not found
     * @throws DelegateException thrown
     */
    Rule getRule(String ruleName) throws DelegateException;

    /**
     * Create a new strategy.
     * @param strategy the strategy to create
     * @throws DelegateException thrown
     */
    void createStrategy(Strategy strategy) throws DelegateException;
}
