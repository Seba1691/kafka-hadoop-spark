package consumer;

import com.google.common.io.Resources;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import kafka.serializer.StringDecoder;

public class SparkConsumer {

  static JavaSparkContext sc = null;

  static JavaStreamingContext jsc = null;

  public static void main(String[] args) {

    SparkConf conf = new SparkConf().setAppName("Simple Application");

    System.setProperty("SPARK_YARN_MODE", "true");

    jsc = new JavaStreamingContext(conf, new Duration(1000));

    InputStream props = null;
    Properties properties = new Properties();
    HashMap<String, String> kafkaConf = new HashMap<String, String>();
    try {
      props = Resources.getResource("consumer.props").openStream();
      properties.load(props);
      for (Object key : properties.keySet()) {
        kafkaConf.put((String) key, properties.getProperty((String) key));
      }

    } catch (IOException e1) {
      e1.printStackTrace();
    }

    HashSet<String> topics = new HashSet<String>();
    topics.add("gps-event");


    JavaPairInputDStream<String, String> messages =
        KafkaUtils.createDirectStream(jsc, String.class, String.class, StringDecoder.class,
            StringDecoder.class, kafkaConf, topics);

    messages.foreachRDD(new VoidFunction<JavaPairRDD<String, String>>(){

      public void call(JavaPairRDD<String, String> rdd) throws Exception {
        if (!rdd.isEmpty()) {
          rdd.saveAsTextFile("hdfs://master:9000/gps/" + new Date().getTime());
        }
      }});
    
    jsc.start();
    jsc.awaitTermination();
  }

}
