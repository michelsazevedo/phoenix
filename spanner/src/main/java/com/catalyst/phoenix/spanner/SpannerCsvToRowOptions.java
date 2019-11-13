package com.catalyst.phoenix.spanner;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface SpannerCsvToRowOptions extends DataflowPipelineOptions {
    @Description("Input Origin")
    @Validation.Required
    String getInput();
    void setInput(String value);

    @Description("Spanner Instance")
    @Validation.Required
    String getInstanceId();
    void setInstanceId(String value);

    @Description("Spanner Table")
    @Validation.Required
    String getTable();
    void setTable(String value);

    @Description("Spanner Database")
    @Validation.Required
    String getDatabaseId();
    void setDatabaseId(String value);
}
