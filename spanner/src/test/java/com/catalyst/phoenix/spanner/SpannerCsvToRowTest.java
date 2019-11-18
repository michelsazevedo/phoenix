package com.catalyst.phoenix.spanner;

import jdk.tools.jaotc.Main;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.catalyst.phoenix.*")
class SpannerCsvToRowTest {

    @BeforeEach
    void setUp() {
        SpannerIO spannerMock = PowerMockito.mock(SpannerIO.class);
    }

    @Test
    void main() {
        String[] args = {
            "--input=gs://rdsm-analytics-development/test-mushin-spanner/workflow-opportunities/opportunities/*.txt",
            "--instanceId=mushin-analytics",
            "--databaseId=performance",
            "--table=Opportunities",
            "--jobName=kuala",
            "--project=rdsm-analytics-development"
        };
        SpannerCsvToRow.main(args);
    }
}