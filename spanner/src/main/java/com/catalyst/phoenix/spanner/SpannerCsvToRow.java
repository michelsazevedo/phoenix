package com.catalyst.phoenix.spanner;

import com.catalyst.phoenix.spanner.builders.OpportunityBuilder;
import com.catalyst.phoenix.spanner.mutations.OpportunityMutation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SpannerCsvToRow {
    private static final Logger LOG = LoggerFactory.getLogger(SpannerCsvToRow.class);

    public static void main(String[] args) {
        SpannerCsvToRowOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(SpannerCsvToRowOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        pipeline.apply("read lines", TextIO.read().from(options.getInput()))
                .apply("load entities", ParDo.of(new OpportunityBuilder()))
                .apply("mutation entities", ParDo.of(new OpportunityMutation()))
                .apply("write rows", SpannerIO.write()
                        .withInstanceId(options.getInstanceId())
                        .withDatabaseId(options.getDatabaseId())
                );
        pipeline.run().waitUntilFinish();
    }
}
