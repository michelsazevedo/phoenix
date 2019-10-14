package com.catalyst.phoenix.spanner;

import com.catalyst.phoenix.spanner.builders.OpportunityBuilder;
import com.catalyst.phoenix.spanner.mutations.OpportunityMutation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;

public class SpannerCsvToRow {
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
