package com.mailcompany.core.workflow;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service=WorkflowProcess.class,
immediate=true, property="process.label=Birlasoft ReadDialogParticipantValuesProcess")
public class ReadDialogParticipantValuesProcess implements WorkflowProcess{

	Logger logger = LoggerFactory.getLogger(getClass());	
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		try {
			String payloadType = workItem.getWorkflowData().getPayloadType();
			if(null != payloadType && payloadType.equalsIgnoreCase("JCR_PATH")) {
				String payLoadPath = workItem.getWorkflowData().getPayload().toString();
				ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
				Resource resource = resolver.getResource(payLoadPath+"/jcr:content");
				ValueMap properties = resource.adaptTo(ValueMap.class);
				String participantDialogProp = properties.get("participant_dialog_prop","");
				logger.info("participantDialogProp "+participantDialogProp);
			}			
		}
		catch(Exception e) {
			
		}
	}

}
