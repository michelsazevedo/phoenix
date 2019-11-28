package com.catalyst.phoenix.spanner;

import com.catalyst.phoenix.spanner.builders.WorkflowTouchBuilder;
import com.catalyst.phoenix.spanner.mutations.WorkflowTouchMutation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpannerCsvToRow {
    private static final Logger LOG = LoggerFactory.getLogger(SpannerCsvToRow.class);

    public static void main(String[] args) {
        SpannerCsvToRowOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(SpannerCsvToRowOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        pipeline.apply("read lines", TextIO.read().from(options.getInput()))
                .apply("load entities", ParDo.of(new WorkflowTouchBuilder()))
                .apply("mutation entities", ParDo.of(new WorkflowTouchMutation(options.getTable())))
                .apply("write rows", SpannerIO.write()
                        .withInstanceId(options.getInstanceId())
                        .withDatabaseId(options.getDatabaseId())
                );

        pipeline.run();
    }
}
