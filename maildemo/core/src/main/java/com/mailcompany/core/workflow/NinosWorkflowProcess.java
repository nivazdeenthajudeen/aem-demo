package com.mailcompany.core.workflow;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(
		service = WorkflowProcess.class,
		property = "process.label=Ninos Workflow Process")
public class NinosWorkflowProcess implements WorkflowProcess{
	
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		String processArguments = metaDataMap.get("PROCESS_ARGS", "");
		String additionalField = metaDataMap.get("additionalField", "");
		LOGGER.info("additionalField "+additionalField);
		String businessVal = "";
		String[] processArgumentsStringArr = processArguments.split(",");
		for(String processArgument: processArgumentsStringArr) {
			if(processArgument.startsWith("business:")) {
				String[] processArgumentStringArr = processArgument.split(":");
				if(processArgumentStringArr != null && processArgumentStringArr.length > 1) {
					businessVal = processArgumentStringArr[1];
				}
			}
		}
		WorkflowData workflowData = workItem.getWorkflowData();
		MetaDataMap generalMetaDataMap = workflowData.getMetaDataMap();
		generalMetaDataMap.put("business", businessVal);
	}


}
