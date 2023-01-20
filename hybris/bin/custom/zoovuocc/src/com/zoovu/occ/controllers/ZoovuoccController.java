/*********************************************************************************************************
 *           Zoovu - SAP Commerce Cloud Extension
 *  On system init or update hybris/bin/custom/zoovuocc/resources/impex/essentialdataOne.impex
 *  will perform the following operations:
 *
 *  1) Create an Integration Object based on the Product template with attribute mapping which can be tailored to Semantic Studio's needs.
 *  2) Create an Oauth Client to use with both the ZoovuProduct Integration Object and the /occ/v2/{baseSiteID}/zoovu endpoint. This Client has the necessary role
 *     (Trusted Client) needed to run the queries.
 *  3) Create an Inbound Channel configuration to allow for the ZoovuProduct Integration Object to be accessed using the
 *     Oauth CLient from above. This will now create endpoint {ccv2.services.backoffice.url.0}/odata2webservices/ZoovuProduct/
 *
 * This Controller can contain as many functions and routes as needed.
 *********************************************************************************************************/


package com.zoovu.occ.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;


import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.*;


@Controller("zoovuoccController")
@RequestMapping(value = "/{baseSiteId}")
public class ZoovuoccController {

    //Routes
    @Secured("ROLE_TRUSTED_CLIENT")
    @RequestMapping(value = "/zoovu", method = RequestMethod.GET)
    @ResponseBody
    public String getNewResource() {
        String values = enumValuesFromPK("2053266", "name", "en");
        return "Zoovu SAP CC Connector " + values;
    }


    //Flexible search

    private FlexibleSearchService flexibleSearchService;

    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    public String enumValuesFromPK(String pk, String attributeName, String locale) {
        String queryString = "SELECT * FROM {Product AS p} WHERE {p:pk} = '" + pk + "' AND {p:" + attributeName + "[" + locale + "]} IS NOT NULL";
        System.out.println(queryString);
        FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
        SearchResult<Object> results = flexibleSearchService.search(flexibleSearchQuery);
        List<Object> resultsList = results.getResult();
        System.out.println(resultsList);
        String jsonString = "";
        for (Object result : resultsList) {
            System.out.println(result);
            jsonString += "\n" + result;
        }
        System.out.println(jsonString);
        return jsonString;
    }
}


