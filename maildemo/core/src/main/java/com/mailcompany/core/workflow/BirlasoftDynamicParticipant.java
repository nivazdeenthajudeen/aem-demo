package com.mailcompany.core.workflow;

import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service=ParticipantStepChooser.class, immediate = true, property="chooser.label=Birla soft Sample Dynamic Participant")
public class BirlasoftDynamicParticipant implements ParticipantStepChooser{

	@Override
	public String getParticipant(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2) throws WorkflowException {
		return "birlasoft-content-user";
	}

}
