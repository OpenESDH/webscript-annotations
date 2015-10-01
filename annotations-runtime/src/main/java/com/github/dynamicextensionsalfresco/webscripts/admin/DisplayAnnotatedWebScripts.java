package com.github.dynamicextensionsalfresco.webscripts.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dynamicextensionsalfresco.webscripts.WebScriptUriRegistry;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.UriVariable;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript(families={"Admin tools"}, description="Displays annotations based Web Scripts (all and by family)")
public class DisplayAnnotatedWebScripts{

    @Autowired
    private WebScriptUriRegistry webScriptUriRegistry;

    @Authentication(value=AuthenticationType.ADMIN)
    @Uri(method = HttpMethod.GET, value = "/annotatedws/family/{family}", defaultFormat = "html")
    public Map<String, Object> getWebScriptsByFamily(@UriVariable final String family){
        final Map<String, Object> model = new HashMap<String, Object>();
        
        List<org.springframework.extensions.webscripts.WebScript> familyWebScripts = webScriptUriRegistry.getWebScripts()
                .stream()
                .filter(webScript -> webScript.getDescription().getFamilys().contains(family))
                .collect(Collectors.toList());
        
        model.put("family", family);
        model.put("webScripts", familyWebScripts);
        return model;
    }
    
    @Authentication(value=AuthenticationType.ADMIN)
    @Uri(method = HttpMethod.GET, value = "/annotatedws/all", defaultFormat = "html")
    public Map<String, Object> getAllWebScripts(){
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("webScripts", webScriptUriRegistry.getWebScripts());
        return model;
    }

}
