/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.zoovu.controller;

import static com.zoovu.constants.ZoovuConstants.PLATFORM_LOGO_CODE;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.zoovu.service.ZoovuService;


@Controller
public class ZoovuHelloController
{
	@Autowired
	private ZoovuService zoovuService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome()
	{
		return "welcome";
	}

	@RequestMapping(value="/init", method = RequestMethod.GET)
	public Boolean impexSetup(){
		return zoovuService.runImpexSetup();
	}
}
