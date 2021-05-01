package com.mailcompany.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.jackrabbit.oak.commons.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.tagging.Tag;



@Model(adaptables = {SlingHttpServletRequest.class})
public class ProductSelector {

	@SlingObject
	Resource resource;
	
	@SlingObject
	ResourceResolver resolver;
	
	@ValueMapValue
	private String tagbasepath;
	
	List<TagInfo> tags = new ArrayList<>();
	
	@PostConstruct
	protected void init() {
		setTags();
	}
	
	private void setTags(){
		if(null != tagbasepath && !tagbasepath.equalsIgnoreCase("") ) {
			Resource tagBaseResource = resolver.getResource(tagbasepath);
			if(null != tagBaseResource) {
				Tag categoryBaseTag = tagBaseResource.adaptTo(Tag.class);
				if(null != categoryBaseTag) {
					Iterator<Tag> categoryBaseTagChildItr = categoryBaseTag.listChildren();
					while(categoryBaseTagChildItr.hasNext()) {
						Tag categoryBaseTagChild = categoryBaseTagChildItr.next();
						TagInfo tagInfo = categoryBaseTagChild.adaptTo(Resource.class).adaptTo(TagInfo.class);
						tags.add(tagInfo);
					}
				}
			}
		}
	}
	
	public List<TagInfo> getTags(){
		return tags;
	}
}
