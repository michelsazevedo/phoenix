package com.catalyst.phoenix.spanner.mutations;

import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import com.google.cloud.spanner.Mutation;
import org.apache.beam.sdk.io.gcp.spanner.MutationGroup;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

import java.util.Hashtable;
import java.util.Map;

public class WorkflowsTouchMutation extends PTransform<PCollection<Map<String, WorkflowTouch>>, PCollection<MutationGroup>> {
    private String destination;

    public WorkflowsTouchMutation(String destination) {
        this.destination = destination;
    }

    @Override
    public PCollection<MutationGroup> expand(PCollection<Map<String, WorkflowTouch>> input) {
        return input.apply(MapElements.via(new SimpleFunction<Map<String, WorkflowTouch>, MutationGroup>() {
            @Override
            public MutationGroup apply(Map<String, WorkflowTouch> workflows) {
                Map<String, Mutation> mutations = new Hashtable<>();

                workflows.forEach((attribution, workflow) -> {
                    mutations.put(
                        attribution, Mutation.newInsertOrUpdateBuilder(destination)
                            .set("TenantId").to(workflow.getTenantId())
                            .set("WorkflowId").to(workflow.getWorkflowId())
                            .set("EventDate").to(workflow.getEventDate())
                            .set("LeadId").to(workflow.getLeadId())
                            .set("Attribution").to(workflow.getAttribution())
                            .build());
                });
                return MutationGroup.create(mutations.get("last"), mutations.get("first"));
            }
        }));
    }
}
