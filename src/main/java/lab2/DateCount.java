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
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class DateCount {

    public static final Class OUTPUT_KEY_CLASS = MonYr.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;

    public static class MonYr implements WritableComparable<MonYr> {
        private static HashMap<String, Integer> monMap = new HashMap<>();
        {
            monMap.put("Jan", 1);
            monMap.put("Feb", 2);
            monMap.put("Mar", 3);
            monMap.put("Apr", 4);
            monMap.put("May", 5);
            monMap.put("Jun", 6);
            monMap.put("Jul", 7);
            monMap.put("Aug", 8);
            monMap.put("Sep", 9);
            monMap.put("Oct", 10);
            monMap.put("Nov", 11);
            monMap.put("Dec", 12);
        }
        private int month;
        private int year;

        private String monthStr;

        public MonYr(String month, int year) {
            this.month = monMap.get(month);
            this.year = year;
            this.monthStr = month;
        }
        @Override
        public String toString() {
            return monthStr + "/" + year;
        }

        @Override
        public int compareTo(MonYr other) {
            if (this.year != other.year) {
                return this.year - other.year;
            }
            return this.month - other.month;
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeInt(month);
            out.writeInt(year);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            month = in.readInt();
            year = in.readInt();
        }
    }


    public static class MapperImpl extends Mapper<LongWritable, Text, MonYr, IntWritable> {
	private final IntWritable one = new IntWritable(1);

        @Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split(" ");
            String[] date = parts[3].split("/");
            context.write(new MonYr(date[1], Integer.parseInt(date[2].split(":")[0])), one);
        }
    }

    public static class ReducerImpl extends Reducer<MonYr, IntWritable, MonYr, IntWritable> {
	private IntWritable result = new IntWritable();

	protected void reduce(MonYr date, Iterable<IntWritable> intOne, Context context) throws IOException, InterruptedException {
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
