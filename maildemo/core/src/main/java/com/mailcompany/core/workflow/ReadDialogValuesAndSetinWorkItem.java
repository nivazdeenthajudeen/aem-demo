package com.mailcompany.core.workflow;

import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(
		service=WorkflowProcess.class, immediate=true, property = "process.label=Birlasoft Read Dialog Values and set in Workitem")
public class ReadDialogValuesAndSetinWorkItem implements WorkflowProcess{

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		String businessVal = "";
		String processArguments = metaDataMap.get("PROCESS_ARGS", "");
		if(processArguments != null && processArguments.length() > 0) {
			String[] processArgumentsStrArr = processArguments.split(",");
			if(null != processArgumentsStrArr && processArgumentsStrArr.length >0) {
				for(String processArgument:processArgumentsStrArr) {
					if(null != processArgument && processArgument.length() > 0) {
						String[] processArgumentArr = processArgument.split(":");
						if(null != processArgumentArr && processArgumentArr.length > 0) {
							if(processArgumentArr[0].equalsIgnoreCase("business")) {
								businessVal = processArgumentArr[1];
							}
						}
					}
				}
			}
		}
		if(null != businessVal && businessVal.length()>0) {
			WorkflowData workflowData = workItem.getWorkflowData();
			MetaDataMap generalMetaDataMap = workflowData.getMetaDataMap();
			generalMetaDataMap.put("business", businessVal);
		}
	}
}
