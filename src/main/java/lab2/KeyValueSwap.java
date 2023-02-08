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
        private Text newValue = new Text();
        private IntWritable newKey = new IntWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split("\t");
            newValue.set(parts[0]);
            newKey.set(Integer.parseInt(parts[1]));
            context.write(newKey, newValue);
            }
        }

    public static class ReducerImpl extends Reducer<IntWritable, Text, IntWritable, Text> {
        private IntWritable result = new IntWritable();

        protected void reduce(IntWritable count, Text path, Context context) throws IOException, InterruptedException {
            context.write(count, path);
        }
    }
}

