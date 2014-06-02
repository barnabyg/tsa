 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.model.RuleXml;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class RuleXmlDaoTest extends TestBase {

    /**
    *
    */
   private static final String RULE_NAME2 = "rule name2";
   /**
    *
    */
   private static final String RULE_XML = "<somexml></somexml>";
   /**
    *
    */
   private static final String RULE_NAME = "rule name";
   /**
    * Injected DAO.
    */
   @Autowired
   private transient RuleXmlDao dao;

   /**
    * Test create, update, retrieve, delete for Rule.
    */
   @Test
   public void crudRuleXmlTest() {

       final RuleXml ruleXml = new RuleXml();

       ruleXml.setName(RULE_NAME);
       ruleXml.setXml(RULE_XML);

       final long id = dao.create(ruleXml);

       final RuleXml ruleXml2 = dao.retrieve(id);

       assertNotNull("Retrieve failed to return rule", ruleXml2);

       assertEquals(
          "Rule name did not match",
                  RULE_NAME, ruleXml2.getName());
       assertEquals(
          "Rule xml did not match",
                  RULE_XML, ruleXml2.getXml());

       ruleXml2.setName(RULE_NAME2);

       dao.update(ruleXml2);

       final RuleXml ruleXml3 = dao.retrieve(id);

       assertEquals(
           "Rule name did not match",
                   RULE_NAME2, ruleXml3.getName());

       assertEquals(
           "Rule xml did not match",
                   RULE_XML, ruleXml3.getXml());

       dao.delete(ruleXml3);

       final RuleXml ruleXml4 = dao.retrieve(id);

       assertNull("Delete failed to remove rule", ruleXml4);
   }

   /**
    * Find by name test.
    */
   @Test
   public void findByNameTest() {

       final RuleXml rule = new RuleXml();

       rule.setName(RULE_NAME);
       rule.setXml(RULE_XML);

       final long id = dao.create(rule);

       final RuleXml rule2 = dao.findByName(RULE_NAME);

       assertNotNull("Find by name did not find rule", rule2);

       final RuleXml rule3 = dao.retrieve(id);
       dao.delete(rule3);
   }
}
