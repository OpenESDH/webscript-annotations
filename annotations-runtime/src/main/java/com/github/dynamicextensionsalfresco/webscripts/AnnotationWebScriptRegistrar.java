package com.github.dynamicextensionsalfresco.webscripts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.WebScript;

public class AnnotationWebScriptRegistrar implements ApplicationContextAware {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

	/* Dependencies */

	private ApplicationContext applicationContext;

	private AnnotationWebScriptBuilder annotationBasedWebScriptBuilder;

	private WebScriptUriRegistry webScriptUriRegistry;
	
	/* State */

	private final List<WebScript> webScripts = new ArrayList<WebScript>();

	/* Main operations */

	public void registerWebScripts() {
	    logger.info("Registering annotation based web scripts");
		for (final String beanName : applicationContext.getBeanDefinitionNames()) {
		    try{
    			for (final WebScript webScript : annotationBasedWebScriptBuilder.createWebScripts(beanName)) {
    				webScriptUriRegistry.registerWebScript(webScript);
    				webScripts.add(webScript);
    			}
		    }catch(NoClassDefFoundError error){
		        NoClassDefFoundError er = new NoClassDefFoundError("\n\nERROR: while registering webscript for the bean: " + beanName);
		        er.addSuppressed(error);
		        throw er;
		    }
		}
		logger.info("Registered annotation based web scripts: " + webScripts.size());
	}

	public void unregisterWebScripts() {
		for (final Iterator<WebScript> it = webScripts.iterator(); it.hasNext();) {
			final WebScript webScript = it.next();
			webScriptUriRegistry.unregisterWebScript(webScript);
			it.remove();
		}

	}

	/* State */

	public List<WebScript> getWebScripts() {
		return webScripts;
	}

	/* Dependencies */

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setAnnotationBasedWebScriptBuilder(final AnnotationWebScriptBuilder annotationBasedWebScriptBuilder) {
		this.annotationBasedWebScriptBuilder = annotationBasedWebScriptBuilder;
	}

	public void setWebScriptUriRegistry(final WebScriptUriRegistry webScriptUriRegistry) {
		this.webScriptUriRegistry = webScriptUriRegistry;
	}

}
