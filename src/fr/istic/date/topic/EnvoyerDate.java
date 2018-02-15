package fr.istic.date.topic;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.util.Date;

import com.rabbitmq.client.Channel;

public class EnvoyerDate {

  private static final String EXCHANGE_NAME = "date_topic";

  public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setUri("amqp://mri:64GbL3k7uc33QCtc@cours.4x.re:8082/mri");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

    String message = getDate();
    String messageGMT = getDateGMT();

    channel.basicPublish(EXCHANGE_NAME, "date.locale", null, message.getBytes("UTF-8"));
    channel.basicPublish(EXCHANGE_NAME, "date.gmt", null, messageGMT.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");
    System.out.println(" [x] Sent '" + messageGMT + "'");


    channel.close();
    connection.close();
  }

  private static String getDate() {
	    return (new Date()).toString();
	}

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
  
  @SuppressWarnings("deprecation")
private static String getDateGMT() {
	    return (new Date()).toGMTString();
	}
}

