package com.mailcompany.core.workflow;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class,
immediate = true, property = "process.label=Birla soft Read Passed Value And SetIn Payload")
public class ReadPassedValueAndSetInPayload implements WorkflowProcess{

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		MetaDataMap generalMetaDataMap = workItem.getWorkflowData().getMetaDataMap();
		String businessVal = generalMetaDataMap.get("business", "");
		String payloadType = workItem.getWorkflowData().getPayloadType();
		if(null != payloadType && payloadType.equalsIgnoreCase("JCR_PATH")) {
			String payloadPath = workItem.getWorkflowData().getPayload().toString();
			if(null != payloadPath && !payloadPath.equalsIgnoreCase("")) {
				Session session= workflowSession.adaptTo(Session.class);
				if(null != session) {
					try {
						Node payLoadNode = (Node)session.getItem(payloadPath+"/jcr:content");
						if(null != payLoadNode) {
							if(null != businessVal) {
								payLoadNode.setProperty("business", businessVal);								
							}
							else {
								payLoadNode.setProperty("business", "");
							}
						}
					} catch (RepositoryException e) {
					}
				}
			}
		}
	}

}
