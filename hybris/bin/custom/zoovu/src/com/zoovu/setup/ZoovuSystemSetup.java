/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.zoovu.setup;

import static com.zoovu.constants.ZoovuConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.zoovu.constants.ZoovuConstants;
import com.zoovu.service.ZoovuService;


@SystemSetup(extension = ZoovuConstants.EXTENSIONNAME)
public class ZoovuSystemSetup
{
	private final ZoovuService zoovuService;

	public ZoovuSystemSetup(final ZoovuService zoovuService)
	{
		this.zoovuService = zoovuService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		zoovuService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return ZoovuSystemSetup.class.getResourceAsStream("/zoovu/sap-hybris-platform.png");
	}
}
