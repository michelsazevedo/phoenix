package com.catalyst.phoenix.spanner.builders;

import com.catalyst.phoenix.common.util.SplitToCollection;
import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkflowTouchBuilder extends DoFn<String, WorkflowTouch> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowTouchBuilder.class);

    @ProcessElement
    public void processElement(ProcessContext c) throws ParseException {
        String line = c.element();

        SplitToCollection row = new SplitToCollection((String) line);

        List<String> columns = new ArrayList<>(Arrays.asList("TenantId", "WorkflowId", "LeadId", "Attribution", "EventDate"));

        Map<?, ?> params =  row.toMap(columns);

        LOG.info("Row ~>" + row);

        c.output(new WorkflowTouch(
                Long.parseLong((String) params.get("TenantId")),
                (String) params.get("WorkflowId"),
                getWorkflowDate((String) params.get("EventDate")),
                Long.parseLong((String) params.get("LeadId")),
                getAttribution((String) params.get("Attribution"))
        ));
    }

    private Date getWorkflowDate(String workflowDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = workflowDate.substring(0, 10);
        String time = workflowDate.substring(11, 23);

        return format.parse(date + " " + time + " UTC");
    }

    private String getAttribution(String attribution) {
        return Integer.parseInt(attribution) == 0 ? "first" : "last";
    }
}