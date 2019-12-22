package com.catalyst.phoenix.spanner.builders;

import com.catalyst.phoenix.spanner.models.WorkflowTouch;
import org.apache.beam.sdk.transforms.DoFn;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class WorkflowTouchBuilder extends DoFn<String, Map<String, WorkflowTouch>> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowTouchBuilder.class);

    @ProcessElement
    public void processElement(ProcessContext context) throws ParseException {
        String line = context.element();

        JSONObject jsonObj = new JSONObject(line);
        Map<String, WorkflowTouch> workflows = new Hashtable<>();

        workflows.put("first", new WorkflowTouch(
            jsonObj.getLong("tenant_id"),
            getWorkflowUuid(jsonObj.getJSONObject("first_touch")),
            getWorkflowDate(jsonObj.getString("action_event_timestamp")),
            jsonObj.getLong("lead_id"),
            "first"
        ));

        workflows.put("last", new WorkflowTouch(
            jsonObj.getLong("tenant_id"),
            getWorkflowUuid(jsonObj.getJSONObject("last_touch")),
            getWorkflowDate(jsonObj.getString("action_event_timestamp")),
            jsonObj.getLong("lead_id"),
            "last"
        ));

        context.output(workflows);
    }

    private Date getWorkflowDate(String date) throws ParseException {
        Date eventDate;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            eventDate = format.parse(date);
        } catch (Exception e) {
            LOG.info("[Date Error] ~> " + e.getMessage());

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            int milliseconds = (int)(Math.random() * 15) + 1;
            eventDate = addMilliseconds(dateFormatter.parse(date), milliseconds);
        }
        return eventDate;
    }

    private String getWorkflowUuid(JSONObject attribution) {
        return attribution.getString("workflow_started_event_id");
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
}
