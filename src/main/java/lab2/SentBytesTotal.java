package lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SentBytesTotal {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;

    private static final String address = "64.242.88.10";

    public static class MapperImpl extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final Text client = new Text();
	private IntWritable bytes = new IntWritable();

        @Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split(" ");
            if (parts[0].equals(address)) {
                client.set(parts[0]);
                bytes.set(Integer.parseInt(parts[9]));
                context.write(client, bytes);
            }
        }
    }

    public static class ReducerImpl extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();

        @Override
	protected void reduce(Text client, Iterable<IntWritable> intOne, Context context) throws IOException, InterruptedException {
            int sum = 0;
            Iterator<IntWritable> itr = intOne.iterator();
        
            while (itr.hasNext()) {
                sum  += itr.next().get();
            }
            result.set(sum);
            context.write(client, result);
       }
    }
}
