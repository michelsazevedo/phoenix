package com.catalyst.phoenix.spanner.mutations;

import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import com.google.cloud.spanner.Mutation;
import org.apache.beam.sdk.transforms.DoFn;

public class WorkflowTouchMutation extends DoFn<WorkflowTouch, Mutation> {
    private String destination;

    public WorkflowTouchMutation(String destination) {
        this.destination = destination;
    }

    @ProcessElement
    public void processElement(ProcessContext context) {
        WorkflowTouch opportunity =  context.element();

        Mutation opportunityMutation = Mutation.newInsertOrUpdateBuilder(this.destination)
                .set("TenantId").to(opportunity.getTenantId())
                .set("WorkflowId").to(opportunity.getWorkflowId())
                .set("EventDate").to(opportunity.getEventDate())
                .set("LeadId").to(opportunity.getLeadId())
                .set("Attribution").to(opportunity.getAttribution())
                .build();

        context.output(opportunityMutation);
    }
}
