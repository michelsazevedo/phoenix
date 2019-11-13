package com.catalyst.phoenix.spanner;

import com.catalyst.phoenix.spanner.builders.WorkflowTouchBuilder;
import com.catalyst.phoenix.spanner.mutations.WorkflowsTouchMutation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;

public class SpannerCsvToRow {
    public static void main(String[] args) {
        SpannerCsvToRowOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(SpannerCsvToRowOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        pipeline.apply("Read lines", TextIO.read().from(options.getInput()))
                .apply("Build entities", ParDo.of(new WorkflowTouchBuilder()))
                .apply("Mutate entities", new WorkflowsTouchMutation(options.getTable()))
                .apply("Write Spanner Row",
                        SpannerIO.write()
                            .withInstanceId(options.getInstanceId())
                            .withDatabaseId(options.getDatabaseId())
                            .grouped());
         
        pipeline.run();
    }
}
