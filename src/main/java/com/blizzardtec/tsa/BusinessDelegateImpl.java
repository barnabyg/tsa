/**
 *
 */
package com.blizzardtec.tsa;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.analysis.ResultsetHelper;
import com.blizzardtec.tsa.data.DatasetHelper;
import com.blizzardtec.tsa.data.DatasetHelper.DataType;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.StrategyXml;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.RuleException;
import com.blizzardtec.tsa.rules.RuleHelper;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;
import com.blizzardtec.tsa.strategy.StrategyHelper;
import com.blizzardtec.tsa.visualisation.VisualisationHelper;
import com.blizzardtec.tsa.visualisation.VisualisationHelper.ChartType;

/**
 * @author Barnaby Golden
 *
 */
public final class BusinessDelegateImpl implements BusinessDelegate {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(BusinessDelegateImpl.class);

    /**
     * Format of date-time to use on analysis run labels.
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd-HH:mm:ss";

    /**
     * Adapter to deal with a <code>MultipartFile</code>.
     */
    @Autowired
    private transient StreamAdapter streamAdapter;
    /**
     * Helper for dealing with datasets.
     */
    @Autowired
    private transient DatasetHelper datasetHelper;
    /**
     * Helper for dealing with trading strategies.
     */
    @Autowired
    private transient StrategyHelper strategyHelper;
    /**
     * Trading analyser.
     */
    @Autowired
    private transient ResultsetHelper analyserHelper;
    /**
     * Visualisation helper.
     */
    @Autowired
    private transient VisualisationHelper visualisationHelper;
    /**
     * Rule helper.
     */
    @Autowired
    private transient RuleHelper ruleHelper;

    /**
     * {@inheritDoc}
     */
    public void saveDataSet(
         final InputStream stream, final String name, final DataType type)
            throws DelegateException {

        // sanity check
        // see if the dataset name has already been used
        if (datasetHelper.datasetExists(name)) {
            throw new DelegateException("Dataset already exists");
        }

        final List<String> list = streamAdapter.splitFile(stream);

        if (list.isEmpty()) {
            throw new DelegateException("Empty dataset file");
        }

        datasetHelper.persistDataset(datasetHelper.parseData(list, name, type));
    }

    /**
     * {@inheritDoc}
     */
    public void saveStrategy(final InputStream stream, final String name)
            throws DelegateException {

        final String fileContents = streamAdapter.extractFile(stream);

        if (fileContents.isEmpty()) {
            throw new DelegateException("Empty strategy file");
        }

        try {
            if (!strategyHelper.validateStrategyXml(fileContents)) {
                throw new DelegateException("Invalid strategy XML");
            }
        } catch (StrategyException e) {
            throw new DelegateException(e);
        }

        final StrategyXml strategyXml = new StrategyXml();
        strategyXml.setName(name);
        strategyXml.setXml(fileContents);

        strategyHelper.persistStrategy(strategyXml);
    }

    /**
     * {@inheritDoc}
     */
    public String runAnalysis(
            final String dataSetName,
            final String strategyName,
            final float startingFunds)
                 throws DelegateException {

        final StrategyXml strategy = strategyHelper.getStrategy(strategyName);

        if (strategy == null) {
            throw new DelegateException("Did not retrieve strategy");
        }

        final DateFormat dateFormat =
                new SimpleDateFormat(DATETIME_FORMAT, Locale.UK);

        final Calendar cal = Calendar.getInstance();

        final String name =
             dataSetName + " "
           + strategyName + " "
           + dateFormat.format(cal.getTime());

        try {
            analyserHelper.runAnalysis(
                    name,
                    datasetHelper.getDataset(dataSetName),
                    strategy,
                    startingFunds);
        } catch (StrategyException e) {
            throw new DelegateException(e);
        }

        return name;
    }

    /**
     * {@inheritDoc}
     */
    public byte[] balanceChart(final int runId)
            throws DelegateException {

        return new byte[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String name, final ObjectType type) {

        switch (type) {
        case DATASET:
            datasetHelper.deleteDataset(name);
            break;
        case STRATEGY:
            strategyHelper.deleteStrategy(name);
            break;
        case RESULTSET:
            analyserHelper.deleteResultset(name);
            break;
        case RULE:
            ruleHelper.deleteRule(name);
            break;
        default:
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resultset getResultset(final String runName) {

        return analyserHelper.getResultset(runName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JFreeChart showChart(
            final ChartType type,
            final String runId)
                throws DelegateException {

        JFreeChart chart = null;

        try {
            chart = visualisationHelper.showChart(type, runId);
        } catch (IOException ioe) {
            throw new DelegateException(ioe);
        }

        return chart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> listNames(final ObjectType type) {

        List<String> list = null;

        switch (type) {
        case DATASET:
            list = datasetHelper.listDatasetNames();
            break;
        case STRATEGY:
            list = strategyHelper.listStrategyNames();
            break;
        case RESULTSET:
            list = analyserHelper.listResultsets();
            break;
        case RULE:
            list = ruleHelper.listRuleNames();
        default:
            break;
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createRule(final Rule rule) throws DelegateException {

        try {
            ruleHelper.persistRule(rule.toXml());
        } catch (RuleException e) {
            throw new DelegateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rule getRule(final String ruleName) throws DelegateException {

        Rule rule = null;

        try {
            rule = ruleHelper.getRule(ruleName).toRule();
        } catch (RuleException e) {
            throw new DelegateException(e);
        }

        return rule;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createStrategy(final Strategy strategy)
                                        throws DelegateException {

        final StrategyXml strategyXml = new StrategyXml();

        strategyXml.setName(strategy.getName());

        try {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Strategy XML: " + strategy.toXml());
            }

            strategyXml.setXml(strategy.toXml());

        } catch (StrategyException e) {
            throw new DelegateException(e);
        }

        strategyHelper.persistStrategy(strategyXml);
    }
}
