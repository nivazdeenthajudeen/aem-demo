package com.mailcompany.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class SonyProdSelectorModel {
	
	@SlingObject
	SlingHttpServletRequest request;

	@ValueMapValue
	private String pcParentTag;
	
	@ValueMapValue
	private String parentpagepath;
	
	QueryBuilder builder;
	
	@SlingObject
	ResourceResolver resolver;
	
	Session session;
	
	TagManager tagManager;
	
	List<Page> resultPages = new ArrayList<>();
	
	List<String> tags = new ArrayList<>();
	
	PageManager pm;
	
	@PostConstruct
	protected void init() {
		if(null != resolver) {
			tagManager = resolver.adaptTo(TagManager.class);	
			builder = resolver.adaptTo(QueryBuilder.class);
			session = resolver.adaptTo(Session.class);
			pm = resolver.adaptTo(PageManager.class);
		}
		setTags();
		setResultPages();
	}
	
	private void setTags() {
		if(null != pcParentTag && !pcParentTag.equalsIgnoreCase("")) {
			if(null != tagManager) {
				Tag sonyParentTag = tagManager.resolve(pcParentTag);
				if(null != sonyParentTag) {
					Iterator<Tag> sonyParentTagChildItr = sonyParentTag.listChildren();
					while(sonyParentTagChildItr.hasNext()) {
						Tag sonyParentChildTag = sonyParentTagChildItr.next();
						if(null != sonyParentChildTag) {
							tags.add(sonyParentChildTag.getName());							
						}
					}
				}
			}
		}
	}
	
	private void setResultPages() {
		try {
			if(null != resolver && null != session && null != builder && null != parentpagepath && !parentpagepath.equalsIgnoreCase("")) {
				List<String> totalTags = getTags();
				if(totalTags.size() > 0) {
					Map<String, String> map = new HashMap<>();
					 map.put("path", parentpagepath);
					 map.put("type", "cq:Page");
					 map.put("property", "jcr:content/cq:tags"); 
					 map.put("property.or", "true");
					 String categorySelector = getCategorySelector();
					 if(null != categorySelector && !categorySelector.equalsIgnoreCase("")) {
						 Tag tag = tagManager.resolve(pcParentTag+"/"+categorySelector);
						 if(null != tag) {
							 map.put("property.1_value", tag.getTagID());							 
						 }
					 }
					 else {
						 for(int i=0; i<totalTags.size(); i++) {
							 Tag tag = tagManager.resolve(pcParentTag+"/"+totalTags.get(i));
							 if(null != tag) {
								 map.put("property."+i+"_value", tag.getTagID());							 
							 }
						 }						 
					 }
					 Query query = builder.createQuery(PredicateGroup.create(map), session);	
					 SearchResult result = query.getResult();
					 List<Hit> hits = result.getHits();
					 for(Hit hit: hits) {
						 if(null != pm) {
							 if(null != hit) {
								 Page page = pm.getContainingPage(hit.getPath());
								 resultPages.add(page);	
							 }				 
						 }
					 }					
				}
			}			
		}
		catch(Exception e) {
			
		}
	}
	
	public List<Page> getResultPages(){
		return resultPages;
	}
	
	public List<String> getTags(){
		return tags;
	}
	
	private String getCategorySelector(){
		String categorySelector = "";
		String[] selectors = request.getRequestPathInfo().getSelectors();
		if(null != selectors && selectors.length > 0) {
			for(String selector: selectors) {
				if(selector.startsWith("category-")) {
					categorySelector = selector.replace("category-", "");
				}
			}
		}
		return categorySelector;
	}
	
}
