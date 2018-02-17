package service;

import com.google.common.io.Resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Gps;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class KafkaService {

  KafkaProducer<String, String> producer;

  public KafkaService() {
    try (InputStream props = Resources.getResource("producer.props").openStream()) {
      Properties properties = new Properties();
      properties.load(props);
      producer = new KafkaProducer<>(properties);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds gps into Kafka
   */
  public void addElement(Gps gps) {

    gps.setTimestamp(new Date().getTime());
    try {
      // send message
      producer
          .send(new ProducerRecord<String, String>("gps-event", String.format(pojoToJson(gps))));
    } catch (Throwable throwable) {
      System.out.printf("%s", throwable.getStackTrace());
    }
  }

  /**
   * Convert a pojo to Json
   */
  public String pojoToJson(Object pojo) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    String json = null;
    try {
      json = mapper.writeValueAsString(pojo);
    } catch (JsonProcessingException e) {
      throw e;
    }
    return json;
  }

}
