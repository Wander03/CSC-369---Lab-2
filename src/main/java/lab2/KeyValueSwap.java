package lab2;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class KeyValueSwap {

    public static final Class OUTPUT_KEY_CLASS = IntWritable.class;
    public static final Class OUTPUT_VALUE_CLASS = Text.class;

    public static class MapperImpl extends Mapper<LongWritable, Text, IntWritable, Text> {
        private Text newVal = new Text();

        @Override
        protected void map(LongWritable oldKey, Text oldVal, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(oldKey.toString().split(" ")[0]);
            while (itr.hasMoreTokens()) {
                newVal.set(itr.nextToken());
                IntWritable newKey = new IntWritable(Integer.parseInt(oldKey.toString().split(" ")[1]));
                context.write(newKey, newVal);
            }
        }
    }

    public static class ReducerImpl extends Reducer<IntWritable, Text, IntWritable, Text> {
        protected void reduce(IntWritable key, Text val, Context context) throws IOException, InterruptedException {
            context.write(key, val);
        }
    }
}
