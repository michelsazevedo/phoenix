package com.catalyst.phoenix.spanner.mutations;

import com.catalyst.phoenix.spanner.models.Opportunity;
import com.google.cloud.spanner.Mutation;
import org.apache.beam.sdk.transforms.DoFn;

public class OpportunityMutation extends DoFn<Opportunity, Mutation> {
    @ProcessElement
    public void processElement(ProcessContext context) {
        Opportunity opportunity =  context.element();

        Mutation opportunityMutation = Mutation.newInsertOrUpdateBuilder("Opportunities")
                .set("TenantId").to(opportunity.getTenantId())
                .set("WorkflowId").to(opportunity.getWorkflowId())
                .set("EventDate").to(opportunity.getEventDate())
                .set("LeadId").to(opportunity.getLeadId())
                .set("Attribution").to(opportunity.getAttribution())
                .build();

        context.output(opportunityMutation);
    }
}
