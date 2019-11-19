package com.catalyst.phoenix.spanner;


import com.google.api.services.dataflow.Dataflow;
import com.google.api.services.dataflow.model.Job;
import com.google.common.collect.ImmutableList;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileSystems;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerWriteResult;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.util.GcsUtil;
import org.apache.beam.sdk.util.gcsfs.GcsPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static com.catalyst.phoenix.spanner.SpannerCsvToRow.main;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SpannerIO.class, SpannerCsvToRow.class})
@PowerMockIgnore({"javax.net.ssl.*"})
public class SpannerCsvToRowTest {
    private GcsUtil mockGcsUtil;

    @Before
    public void setUp() throws Exception {
        SpannerCsvToRow mock = mock(SpannerCsvToRow.class);
        whenNew(SpannerCsvToRow.class).withNoArguments().thenReturn(mock);

        SpannerIO.Write mockSpannerWrite = mock(SpannerIO.Write.class);
        SpannerWriteResult spannerWriteResult = mock(SpannerWriteResult.class);

        mockStatic(SpannerIO.class);
        when(SpannerIO.write()).thenReturn(mockSpannerWrite);
        when(mockSpannerWrite.withDatabaseId(anyString())).thenReturn(mockSpannerWrite);
        when(mockSpannerWrite.withInstanceId(anyString())).thenReturn(mockSpannerWrite);

    }

    @Test
    public void helloTest() throws Exception {
        SpannerCsvToRow spannerCsvToRow = new SpannerCsvToRow();
        when(spannerCsvToRow.hello()).thenReturn("Hello Baeldung!");

        String welcome = spannerCsvToRow.hello();
        Mockito.verify(spannerCsvToRow).hello();

        assertEquals("Hello Baeldung!", welcome);
    }

    @Test
    public void mainTest() throws IOException {
        String[] args = new String[]{
            "--input=gs://rdsm-analytics-development/test-mushin-spanner/workflow-opportunities/opportunities/workflow-touch-0aa.txt",
            "--project=rdsm-analytics-development",
            "--instanceId=mushin-analytics",
            "--databaseId=performance",
            "--table=Opportunities",
            "--jobName=kuala"
        };

        SpannerCsvToRow.main(args);
    }
}