package com.catalyst.phoenix.spanner.models;

import java.time.LocalDateTime;
import java.util.Date;


import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class Opportunity {
    private Integer TenantId;
    private String WorkflowId;
    private String EventDate;
    private Integer LeadId;
    private Integer Attribution;

    public Opportunity(Integer TenantId, String WorkflowId, String EventDate, Integer LeadId, Integer Attribution) {
        this.TenantId = TenantId;
        this.WorkflowId = WorkflowId;
        this.EventDate = EventDate;
        this.LeadId = LeadId;
        this.Attribution = Attribution;
    }

    public Integer getTenantId() {
        return TenantId;
    }

    public void setTenantId(int TenantId) {
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

    public Integer getLeadId() {
        return LeadId;
    }

    public void setLeadId(int LeadId) {
        this.LeadId = LeadId;
    }

    public Integer getAttribution() {
        return Attribution;
    }

    public void setAttribution(int Attribution) {
        this.Attribution = Attribution;
    }
}