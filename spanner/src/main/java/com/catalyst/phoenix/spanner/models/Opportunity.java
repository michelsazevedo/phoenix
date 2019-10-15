package com.catalyst.phoenix.spanner.models;

import java.time.LocalDateTime;
import java.util.Date;


import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class Opportunity {
    private String TenantId;
    private String WorkflowId;
    private String EventDate;
    private String LeadId;
    private String Attribution;

    public Opportunity(String TenantId, String WorkflowId, String EventDate, String LeadId, String Attribution) {
        this.TenantId = TenantId;
        this.WorkflowId = WorkflowId;
        this.EventDate = EventDate;
        this.LeadId = LeadId;
        this.Attribution = Attribution;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getWorkflowId() {
        return WorkflowId;
    }

    public void setWorkflowId(String WorkflowId) {
        this.WorkflowId = WorkflowId;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String EventDate) {
        this.EventDate = EventDate;
    }

    public String getLeadId() {
        return LeadId;
    }

    public void setLeadId(String LeadId) {
        this.LeadId = LeadId;
    }

    public String getAttribution() {
        return Attribution;
    }

    public void setAttribution(String Attribution) {
        this.Attribution = Attribution;
    }
}