package com.catalyst.phoenix.spanner.models;

import java.util.Date;
import com.google.cloud.Timestamp;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class WorkflowTouch {
    private long TenantId;
    private String WorkflowId;
    private Date EventDate;
    private long LeadId;
    private String Attribution;

    public WorkflowTouch(long TenantId, String WorkflowId, Date EventDate, long LeadId, String Attribution) {
        this.TenantId = TenantId;
        this.WorkflowId = WorkflowId;
        this.EventDate = EventDate;
        this.LeadId = LeadId;
        this.Attribution = Attribution;
    }

    public long getTenantId() {
        return TenantId;
    }

    public void setTenantId(long TenantId) {
        this.TenantId = TenantId;
    }

    public String getWorkflowId() {
        return WorkflowId;
    }

    public void setWorkflowId(String WorkflowId) {
        this.WorkflowId = WorkflowId;
    }

    public Timestamp getEventDate() {
        return Timestamp.of(EventDate);
    }

    public void setEventDate(Date EventDate) {
        this.EventDate = EventDate;
    }

    public long getLeadId() {
        return LeadId;
    }

    public void setLeadId(long LeadId) {
        this.LeadId = LeadId;
    }

    public String getAttribution() {
        return Attribution;
    }

    public void setAttribution(String Attribution) {
        this.Attribution = Attribution;
    }
}