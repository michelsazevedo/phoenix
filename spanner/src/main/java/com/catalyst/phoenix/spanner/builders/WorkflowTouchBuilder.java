package com.catalyst.phoenix.spanner.builders;

import com.catalyst.phoenix.common.util.SplitToCollection;
import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WorkflowTouchBuilder extends DoFn<String, WorkflowTouch> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowTouchBuilder.class);

    @ProcessElement
    public void processElement(ProcessContext c) throws ParseException {
        String line = c.element();

        List<String> columns = new ArrayList<>(Arrays.asList("TenantId", "WorkflowId", "LeadId", "Attribution", "EventDate"));

        Map<?, ?> params =  toMap(line, columns);

        LOG.info("Row ~>" + line);

        try {
            WorkflowTouch workflowTouch = new WorkflowTouch(
                    Long.parseLong((String) params.get("TenantId")),
                    (String) params.get("WorkflowId"),
                    getWorkflowDate((String) params.get("EventDate")),
                    Long.parseLong((String) params.get("LeadId")),
                    getAttribution((String) params.get("Attribution"))
            );
            c.output(workflowTouch);
        } catch (Exception e) {
            LOG.info("Row ~> " + line + "with exception: " + e.getMessage());
        }
    }

    private Date getWorkflowDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.parse(date);
    }

    private String getAttribution(String attribution) {
        return Integer.parseInt(attribution) == 0 ? "first" : "last";
    }

    private  Map<?, ?> toMap(String line, List<String> keys) {
        List<String> row = new ArrayList<>(Arrays.asList(line.split(";")));
        List<List<?>> attributes = this.zip(row, keys);

        return attributes.stream()
                .collect(Collectors.toMap(el -> el.get(0), el -> el.get(1)));
    }

    private List<List<?>> zip(List<String> row, List<String> keys) {
        List<List<?>> els = new ArrayList<>();

        for(int i = 0; i < keys.size(); i++) {
            try {
                els.add(new ArrayList<>(Arrays.asList(keys.get(i), row.get(i))));
            } catch(Exception e) {
                LOG.info("Row ~> " + row + "with exception: " + e.getMessage());
            }
        }

        return els;
    }
}