package com.mailcompany.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL )
public class HeaderLinkModel {

	@ValueMapValue
	private String label;
	
	@ValueMapValue
	private String path;
	
	public String getLabel() {
		return this.label;
	}
	
	public String getPath() {
		return this.path;
	}
}
