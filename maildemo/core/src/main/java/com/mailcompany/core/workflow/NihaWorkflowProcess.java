package com.mailcompany.core.workflow;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
@Component(
		service = WorkflowProcess.class,
		property = "process.label=Niha Workflow Process")
public class NihaWorkflowProcess implements WorkflowProcess{
	
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		String businessVal = "";
		ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
		PageManager pm = resolver.adaptTo(PageManager.class);
		WorkflowData workflowData = workItem.getWorkflowData();
		MetaDataMap generalMetaDataMap = workflowData.getMetaDataMap();
		if(generalMetaDataMap.containsKey("business")) {
			businessVal = generalMetaDataMap.get("business", "");
			LOGGER.info("business "+businessVal);
		}
		String payloadType = workflowData.getPayloadType();
		if(payloadType.equalsIgnoreCase("JCR_PATH")) {
			String payloadPath = workflowData.getPayload().toString();
			LOGGER.info("PayLoad Path "+payloadPath);
			Page page = pm.getContainingPage(payloadPath);
			if(null != page) {
				String title = page.getTitle();
				LOGGER.info("Page Title "+ title);
			}
			Session session = workflowSession.adaptTo(Session.class);
			try {
				Node node = (Node)session.getItem(payloadPath+"/jcr:content");
				node.setProperty("business", businessVal);
			} catch (RepositoryException e) {
			}
		}

	}
}
