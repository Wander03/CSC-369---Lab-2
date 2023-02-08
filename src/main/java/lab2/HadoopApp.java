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
	    job.setReducerClass(URLCount1.ReducerImpl.class);
	    job.setMapperClass(URLCount1.MapperImpl.class);
	    job.setOutputKeyClass(URLCount1.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(URLCount1.OUTPUT_VALUE_CLASS);
	} else if ("KeyValueSwap".equalsIgnoreCase(otherArgs[0])) {
		job.setReducerClass(KeyValueSwap.ReducerImpl.class);
		job.setMapperClass(KeyValueSwap.MapperImpl.class);
		job.setOutputKeyClass(KeyValueSwap.OUTPUT_KEY_CLASS);
		job.setOutputValueClass(KeyValueSwap.OUTPUT_VALUE_CLASS);
        } else if ("AccessLog2".equalsIgnoreCase(otherArgs[0])) {
	    job.setReducerClass(AccessLog2.ReducerImpl.class);
	    job.setMapperClass(AccessLog2.MapperImpl.class);
	    job.setOutputKeyClass(AccessLog2.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(AccessLog2.OUTPUT_VALUE_CLASS);
	} else {
	    System.out.println("Unrecognized job: " + otherArgs[0]);
	    System.exit(-1);
	}

        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }

}
