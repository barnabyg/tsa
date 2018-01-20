 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.blizzardtec.tsa.dao.StrategyXmlDao;
import com.blizzardtec.tsa.model.StrategyXml;

/**
 * @author Barnaby Golden
 *
 */
public final class StrategyHelperImpl implements StrategyHelper {

    /**
     * Schema file location.
     */
    private static final String SCHEMA_FILE = "strategy.xsd";

    /**
     * Injected strategy DAO.
     */
    @Autowired
    private transient StrategyXmlDao strategyDao;

    /**
     * {@inheritDoc}
     */
    public void persistStrategy(final StrategyXml strategyXml) {

        strategyDao.create(strategyXml);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> listStrategyNames() {

        return strategyDao.findAllStrategyNames();
    }

    /**
     * {@inheritDoc}
     */
    public void deleteStrategy(final String name) {

        final StrategyXml strategy = strategyDao.findByName(name);

        if (strategy != null) {
            strategyDao.delete(strategy);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StrategyXml getStrategy(final String name) {

        return strategyDao.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    public boolean validateStrategyXml(final String xmlStrategy)
                                            throws StrategyException {

        final SchemaFactory sf =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = null;

        try {
            schema = sf.newSchema(findResource(SCHEMA_FILE));
        } catch (SAXException e1) {
            throw new StrategyException(e1);
        }


        final DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();

        dbFactory.setSchema(schema);
        dbFactory.setNamespaceAware(true);

        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new StrategyException(e);
        }

        final Validator handler = new Validator();
        dBuilder.setErrorHandler(handler);

        Document doc;

        boolean flag = true;

        try {

            doc = dBuilder.parse(
                    new ByteArrayInputStream(xmlStrategy.getBytes("UTF-8")));

            if (handler.hasErrors()) {
                flag = false;
            }

            doc.getClass();

        } catch (UnsupportedEncodingException e) {
            throw new StrategyException(e);
        } catch (SAXException e) {
            throw new StrategyException(e);
        } catch (IOException e) {
            throw new StrategyException(e);
        }

        return flag;
    }

    /**
     * Find a resource.
     * @param name the name of the resource to find
     * @return a file handle to the resource or null if not found
     * @throws StrategyException thrown
     */
    private File findResource(final String name) throws StrategyException {

        final URL url =
           Thread.currentThread().getContextClassLoader().getResource(name);

        if (url == null) {
            throw new StrategyException("Unable to find resource: " + name);
        }

        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new StrategyException(e);
        }

        return file;
    }
}
