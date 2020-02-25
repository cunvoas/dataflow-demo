package com.github.cunvoas;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;

public class Bq2DsAll {
    

    /** command line options interface */
    public interface Options extends DataflowPipelineOptions {

        @Description("projectId")
        ValueProvider<String> getProjectId();
        void setProjectId(ValueProvider<String> projectId);
    }
    
    public static Options getOptions(String[] args) {
        PipelineOptionsFactory.register(DataflowPipelineOptions.class);
        Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        options.setStreaming(true);
        return options;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        DataflowPipelineOptions options = getOptions(args);
        String subscriptionName = "projects/dpsp-arbolpim/subscriptions/gcs2bq_job_status_sub";
        
        Pipeline pipeline = Pipeline.create(options);
        
        pipeline
            // read message from pubsub
            .apply("read PubSub from subscription",
                    PubsubIO.readMessages().fromSubscription(subscriptionName));

        // business code here
        
        pipeline.run();
    }
    
}
