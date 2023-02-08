package lab2;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class HadoopApp {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Lab 2");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

	if (otherArgs.length < 3) {
	    System.out.println("Expected parameters: <job class> <input dir> <output dir>");
	    System.exit(-1);
	} else if ("URLCount".equalsIgnoreCase(otherArgs[0])) {
	    job.setReducerClass(URLCount.ReducerImpl.class);
	    job.setMapperClass(URLCount.MapperImpl.class);
	    job.setOutputKeyClass(URLCount.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(URLCount.OUTPUT_VALUE_CLASS);
	} else if ("KeyValueSwap".equalsIgnoreCase(otherArgs[0])) {
		job.setReducerClass(KeyValueSwap.ReducerImpl.class);
		job.setMapperClass(KeyValueSwap.MapperImpl.class);
		job.setOutputKeyClass(KeyValueSwap.OUTPUT_KEY_CLASS);
		job.setOutputValueClass(KeyValueSwap.OUTPUT_VALUE_CLASS);
	} else if ("HTTPCount".equalsIgnoreCase(otherArgs[0])) {
	    job.setReducerClass(HTTPCount.ReducerImpl.class);
	    job.setMapperClass(HTTPCount.MapperImpl.class);
	    job.setOutputKeyClass(HTTPCount.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(HTTPCount.OUTPUT_VALUE_CLASS);
	} else if ("SentBytesTotal".equalsIgnoreCase(otherArgs[0])) {
		job.setReducerClass(SentBytesTotal.ReducerImpl.class);
		job.setMapperClass(SentBytesTotal.MapperImpl.class);
		job.setOutputKeyClass(SentBytesTotal.OUTPUT_KEY_CLASS);
		job.setOutputValueClass(SentBytesTotal.OUTPUT_VALUE_CLASS);
	} else if ("ClientCount".equalsIgnoreCase(otherArgs[0])) {
		job.setReducerClass(ClientCount.ReducerImpl.class);
		job.setMapperClass(ClientCount.MapperImpl.class);
		job.setOutputKeyClass(ClientCount.OUTPUT_KEY_CLASS);
		job.setOutputValueClass(ClientCount.OUTPUT_VALUE_CLASS);
	} else if ("DateCount".equalsIgnoreCase(otherArgs[0])) {
		job.setReducerClass(DateCount.ReducerImpl.class);
		job.setMapperClass(DateCount.MapperImpl.class);
		job.setOutputKeyClass(DateCount.OUTPUT_KEY_CLASS);
		job.setOutputValueClass(DateCount.OUTPUT_VALUE_CLASS);
	} else {
	    System.out.println("Unrecognized job: " + otherArgs[0]);
	    System.exit(-1);
	}

        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }

}
