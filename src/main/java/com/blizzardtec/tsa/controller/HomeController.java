/**
 *
 */
package com.blizzardtec.tsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class HomeController extends BaseController {

    /**
     * Request handler for home page.
     *
     * @return JSP mapping
     */
    @RequestMapping(value = HOME_ACTION,
                   method = RequestMethod.GET)
    public String home() {

        return HOME_PAGE;
    }

    /**
     * Request handler for home page.
     *
     * @return JSP mapping
     */
    @RequestMapping(value = HELP_ACTION,
                   method = RequestMethod.GET)
    public String help() {

        return HELP_PAGE;
    }

    /**
     * Request handler for home page.
     *
     * @return JSP mapping
     */
    @RequestMapping(value = CONTACT_ACTION,
                   method = RequestMethod.GET)
    public String contact() {

        return CONTACT_PAGE;
    }
}
