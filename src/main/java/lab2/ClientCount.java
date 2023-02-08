package lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ClientCount {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;

    private static final String url = "/robots.txt";

    public static class MapperImpl extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final Text client = new Text();
        private final IntWritable one = new IntWritable(1);

        @Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split(" ");
            if (parts[6].equals(url)) {
                client.set(parts[0]);
                context.write(client, one);
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
