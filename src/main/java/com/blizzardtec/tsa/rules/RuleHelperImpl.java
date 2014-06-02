 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.dao.RuleXmlDao;
import com.blizzardtec.tsa.model.RuleXml;

/**
 * @author Barnaby Golden
 *
 */
public final class RuleHelperImpl implements RuleHelper {

    /**
     * RuleXml DAO.
     */
    @Autowired
    private transient RuleXmlDao ruleDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void persistRule(final RuleXml ruleXml) {

        ruleDao.create(ruleXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> listRuleNames() {

        return ruleDao.findAllRuleNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRule(final String name) {

        final RuleXml ruleXml = ruleDao.findByName(name);

        ruleDao.delete(ruleXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleXml getRule(final String name) {

        return ruleDao.findByName(name);
    }
}
