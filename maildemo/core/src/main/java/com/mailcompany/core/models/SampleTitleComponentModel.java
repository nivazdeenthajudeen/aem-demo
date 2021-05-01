package com.mailcompany.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleTitleComponentModel {
	
	
	@ChildResource
	List<HeaderLinkModel> headernavlinks;
	
	

	public List<HeaderLinkModel> getHeaderLinks(){
		return headernavlinks;
	}
}
