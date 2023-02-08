package lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DayBytes {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;

    public static class MapperImpl extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final IntWritable bytes = new IntWritable();
        private Text date = new Text();
        private SimpleDateFormat dateForm = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split(" ");
            try {
                Date rawDate = dateForm.parse(parts[3].substring(1));
                SimpleDateFormat dayForm = new SimpleDateFormat("dd");
                SimpleDateFormat monForm = new SimpleDateFormat("MM");
                SimpleDateFormat yearForm = new SimpleDateFormat("yyyy");
                String day = dayForm.format(rawDate);
                String month = monForm.format(rawDate);
                String year = yearForm.format(rawDate);
                date.set(year + "-" + month + "-" + day);
                bytes.set(Integer.parseInt(parts[9]));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            context.write(date, bytes);
        }
    }

    public static class ReducerImpl extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();

	protected void reduce(Text date, Iterable<IntWritable> intOne, Context context) throws IOException, InterruptedException {
            int sum = 0;
            Iterator<IntWritable> itr = intOne.iterator();
        
            while (itr.hasNext()) {
                sum  += itr.next().get();
            }
            result.set(sum);
            context.write(date, result);
       }
    }
}
