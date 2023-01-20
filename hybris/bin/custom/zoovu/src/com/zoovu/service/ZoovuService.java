/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.zoovu.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;

import de.hybris.platform.impex.model.ImpExMediaModel;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.impex.constants.ImpExConstants;
import de.hybris.platform.servicelayer.impex.ImpExError;
import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImpExValueLineError;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.*;
import de.hybris.platform.util.CSVConstants;

public interface ZoovuService {
//Impex


    @Autowired
    private ImportService importService;

    public Boolean runImpexSetup() {
        final String data = "INSERT_UPDATE IntegrationObject; code[unique = true]; integrationType(code)\n" +
                "; ZoovuProduct; INBOUND\n" +
                "\n" +
                "INSERT_UPDATE IntegrationObjectItem; integrationObject(code)[unique = true]; code[unique = true]; type(code)\n" +
                "; ZoovuProduct  ; Catalog              ; Catalog\n" +
                "; ZoovuProduct  ; CatalogVersion       ; CatalogVersion\n" +
                "; ZoovuProduct  ; Product              ; Product\n" +
                "\n" +
                "INSERT_UPDATE IntegrationObjectItemAttribute; integrationObjectItem(integrationObject(code), code)[unique = true]; attributeName[unique = true]; attributeDescriptor(enclosingType(code), qualifier); returnIntegrationObjectItem(integrationObject(code), code); unique[default = false]\n" +
                "; ZoovuProduct:Catalog              ; id                      ; Catalog:id                      ;\n" +
                "; ZoovuProduct:CatalogVersion       ; catalog                 ; CatalogVersion:catalog          ; ZoovuProduct:Catalog\n" +
                "; ZoovuProduct:CatalogVersion       ; version                 ; CatalogVersion:version          ;\n" +
                "; ZoovuProduct:Product              ; code                    ; Product:code                    ;\n" +
                "; ZoovuProduct:Product              ; catalogVersion          ; Product:catalogVersion          ; ZoovuProduct:CatalogVersion\n" +
                "; ZoovuProduct:Product              ; name                    ; Product:name                    ;\n" +
                "\n" +
                "INSERT_UPDATE InboundChannelConfiguration; integrationObject(code)[unique = true]; authenticationType(code);\n" +
                "                                         ; ZoovuProduct                          ; OAUTH                   ;\n" +
                "\n" +
                "INSERT_UPDATE IntegrationClientCredentialsDetails; clientId[unique = true]; resourceIds; authorities                ; clientSecret       ; registeredRedirectUri                           ; user(uid)        ;\n" +
                "                                                 ; zoovuOauthClient       ; hybris     ; ROLE_INTEGRATIONADMINGROUP ; userpassword       ; https://localhost:9002/oauth2_implicit_callback ; zoovuOauthUser   ;\n" +
                "\n" +
                "INSERT_UPDATE OAuthClientDetails;clientId[unique=true]    ;resourceIds       ;scope        ;authorizedGrantTypes                                            ;authorities             ;clientSecret    ;registeredRedirectUri\n" +
                "						;zoovuClient           ;hybris            ;extended     ;authorization_code,refresh_token,password,client_credentials    ;ROLE_TRUSTED_CLIENT     ;1234;   ";

        final ImpExResource mediaRes = new StreamBasedImpExResource(new ByteArrayInputStream(data.getBytes()),
                CSVConstants.HYBRIS_ENCODING);
        final ImportConfig config = new ImportConfig();
        config.setScript(mediaRes);

        final ImportResult result = importService.importData(config);

        return result.isSuccessful();
    }
}
