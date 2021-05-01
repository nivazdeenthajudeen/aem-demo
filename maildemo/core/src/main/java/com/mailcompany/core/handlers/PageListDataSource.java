package com.mailcompany.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

public class PageListDataSource extends WCMUsePojo{
	

	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest request = getRequest();
		ResourceResolver resourceResolver = getResourceResolver();
		List<Resource> resourceList = new ArrayList<>();
		for(int i=1; i<=10; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("text", i);
			map.put("value", i);
			ValueMap vm = new ValueMapDecorator(map);
			Resource res = new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", vm);
			resourceList.add(res);			
		}
		DataSource ds = new SimpleDataSource(resourceList.iterator());
		request.setAttribute(DataSource.class.getName(), ds);
	}

}
