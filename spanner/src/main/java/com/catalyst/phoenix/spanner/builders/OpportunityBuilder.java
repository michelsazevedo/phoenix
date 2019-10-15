package com.catalyst.phoenix.spanner.builders;

import com.catalyst.phoenix.common.util.SplitToCollection;
import com.catalyst.phoenix.spanner.models.Opportunity;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OpportunityBuilder extends DoFn<String, Opportunity> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(OpportunityBuilder.class);

    @ProcessElement
    public void processElement(ProcessContext c) {
        String line = c.element();

        SplitToCollection row = new SplitToCollection((String) line);

        List<String> columns = new ArrayList<>(Arrays.asList("TenantId", "WorkflowId", "LeadId", "Attribution", "EventDate"));

        Map<?, ?> params =  row.toMap(columns);

        LOG.info("Row ~>" + row);

        c.output(new Opportunity(
                (String) params.get("TenantId"),
                (String) params.get("WorkflowId"),
                (String) params.get("EventDate"),
                (String) params.get("LeadId"),
                (String) params.get("Attribution")
        ));
    }
}