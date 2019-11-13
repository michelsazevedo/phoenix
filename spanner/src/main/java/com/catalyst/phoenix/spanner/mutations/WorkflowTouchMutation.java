package com.catalyst.phoenix.spanner.mutations;

import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import com.google.cloud.spanner.Mutation;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowTouchMutation extends DoFn<WorkflowTouch, Mutation> {
    private String destination;
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowTouchMutation.class);

    public WorkflowTouchMutation(String destination) {
        this.destination = destination;
    }

    @ProcessElement
    public void processElement(ProcessContext context) {
        WorkflowTouch workflow = context.element();

        Mutation mutation = Mutation.newInsertOrUpdateBuilder(destination)
                .set("TenantId").to(workflow.getTenantId())
                .set("WorkflowId").to(workflow.getWorkflowId())
                .set("EventDate").to(workflow.getEventDate())
                .set("LeadId").to(workflow.getLeadId())
                .set("Attribution").to(workflow.getAttribution())
                .build();

        LOG.info("[Mutation] Outputs workflow <leadId: "+ workflow.getLeadId() + " attribution: " + workflow.getAttribution());
        context.output(mutation);
    }
}
