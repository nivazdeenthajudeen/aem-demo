package com.mailcompany.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, immediate = true, property = {
		"sling.servlet.paths=/query/fetchpages",
		"sling.servlet.methods=GET"
})
public class NinosQueryServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1642164129225300298L;
	
	private Logger logger;
	
	@Reference
	ResourceResolverFactory resolverFactory;

		
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		logger = LoggerFactory.getLogger(getClass());
		ResourceResolver serviceResourceResolver = null;
		Map<String, Object> serviceMap = new HashMap<>();
		serviceMap.put(ResourceResolverFactory.SUBSERVICE, "penasirsubservice");
		try {
			serviceResourceResolver = resolverFactory.getServiceResourceResolver(serviceMap);
			Resource breadCrumbResource = serviceResourceResolver.getResource("/apps/maildemo/components/content/breadcrumb");
			Resource contentResource = serviceResourceResolver.getResource("/content/we-retail/us/en/men");
			logger.debug("bread Res"+breadCrumbResource);
			logger.debug("contentResource Res"+contentResource);
		} catch (LoginException e) {
			
		}
		finally {
			if(null != serviceResourceResolver && serviceResourceResolver.isLive()) {
				serviceResourceResolver.close();
			}
		}
	}

}
