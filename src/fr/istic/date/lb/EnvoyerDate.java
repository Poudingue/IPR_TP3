package fr.istic.date.lb;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.util.Date;

import com.rabbitmq.client.Channel;

public class EnvoyerDate {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setUri("amqp://mri:64GbL3k7uc33QCtc@cours.4x.re:8082/mri");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

    String message = getDate()+" by Jammal and Cusson";

    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

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
}
