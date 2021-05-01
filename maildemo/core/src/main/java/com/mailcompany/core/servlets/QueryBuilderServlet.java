package com.mailcompany.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service = Servlet.class,
		immediate = true,
		property = {
				"sling.servlet.paths=/custom/querybug",
				"sling.servlet.methods=GET"
		})
public class QueryBuilderServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = -5641423769196777448L;
	
	private static final Logger log = LoggerFactory.getLogger(QueryBuilderServlet.class);
	
	private ResourceResolver resourceResolver;
	
	private Session session;
	
	@Reference
	private QueryBuilder builder;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		resourceResolver = request.getResourceResolver();
		session = resourceResolver.adaptTo(Session.class);
		Map<String, String> predicate = new HashMap<>();
		predicate.put("path", "/content/we-retail");
		predicate.put("type", "cq:Page");
		predicate.put("fulltext", "men");
		predicate.put("p.limit", "-1");
		Query query = builder.createQuery(PredicateGroup.create(predicate), session);	
		SearchResult result = query.getResult();
		List<Hit> hits = result.getHits();
		for(Hit hit: hits) {
			try {
				sb.append("Title" +hit.getTitle()+"<br/>");
				sb.append("Path" +hit.getPath()+"<br/>");
			} catch (RepositoryException e) {
			}
		}
		response.getWriter().write(sb.toString());
		response.setContentType("text/html");
	}

	
}
