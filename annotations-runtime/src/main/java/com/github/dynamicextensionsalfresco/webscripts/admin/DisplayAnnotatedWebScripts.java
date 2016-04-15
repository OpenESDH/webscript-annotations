package com.github.dynamicextensionsalfresco.webscripts.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.Description;
import org.springframework.stereotype.Component;

import com.github.dynamicextensionsalfresco.webscripts.WebScriptUriRegistry;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.UriVariable;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript(families = {"Admin tools"}, description = "Displays annotations based Web Scripts (all and by family)")
public class DisplayAnnotatedWebScripts {

    private final Logger logger = LoggerFactory.getLogger(DisplayAnnotatedWebScripts.class);

    @Autowired
    private WebScriptUriRegistry webScriptUriRegistry;

    @Authentication(value = AuthenticationType.ADMIN)
    @Uri(method = HttpMethod.GET, value = "/annotatedws/family/{family}", defaultFormat = "html")
    public Map<String, Object> getWebScriptsByFamily(@UriVariable final String family) {
        final Map<String, Object> model = new HashMap<>();

        List<org.springframework.extensions.webscripts.WebScript> familyWebScripts = webScriptUriRegistry.getWebScripts()
                .stream()
                .filter(webScript -> {
                    Description description = webScript.getDescription();
                    if (description == null) {
                        logger.warn("Webscript description is not defined for: {}" + webScript.toString());
                        return false;
                    }
                    Set<String> familys = description.getFamilys();
                    if (familys == null) {
                        logger.warn("Webscript familly is not defined for: {}" + description.getScriptPath());
                        return false;
                    }
                    return familys.contains(family);
                })
                .collect(Collectors.toList());

        model.put("family", family);
        model.put("webScripts", familyWebScripts);
        return model;
    }

    @Authentication(value = AuthenticationType.ADMIN)
    @Uri(method = HttpMethod.GET, value = "/annotatedws/all", defaultFormat = "html")
    public Map<String, Object> getAllWebScripts() {
        final Map<String, Object> model = new HashMap<>();
        model.put("webScripts", webScriptUriRegistry.getWebScripts());
        return model;
    }

}
