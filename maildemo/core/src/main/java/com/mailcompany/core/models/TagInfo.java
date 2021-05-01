package com.mailcompany.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Model(adaptables = Resource.class)
public class TagInfo {
	
	@Self 
	Resource resource;
	
	@SlingObject
	ResourceResolver resolver;
	
	TagManager tagManager;

	private String tagTitle;
	
	private String tagName;
	
	@PostConstruct
	protected void init(){
		if(null != resolver) {
			tagManager = resolver.adaptTo(TagManager.class);
			}
		if(null != resource && null != tagManager) {
			Tag tag = tagManager.resolve(resource.getPath());
			if(null != tag) {
				tagTitle = tag.getTitle();
				tagName = resource.getName();
			}
		}
	}
	
	public String getTagTitle() {
		return this.tagTitle;
	}
	
	public String getTagName() {
		return this.tagName;
	}
}
