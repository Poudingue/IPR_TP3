package fr.istic.chat;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.util.Date;
import java.util.Scanner;

import com.rabbitmq.client.Channel;

public class EnvoyerChat {

	private static final String EXCHANGE_NAME = "chat_topic";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://mri:64GbL3k7uc33QCtc@cours.4x.re:8082/mri");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		Scanner sc = new Scanner(System.in);
		System.out.println("Message ?");
		String message = sc.nextLine();
		System.out.println("Topic ?");
		String topic = sc.nextLine();
		while (!message.equals("exit")) {
			if (!topic.equals("")) {
				channel.basicPublish(EXCHANGE_NAME, "chat." + topic, null, message.getBytes("UTF-8"));
				System.out.println(topic + "#" + argv[0] + ">" + message);

			} else {
				channel.basicPublish(EXCHANGE_NAME, "chat.general", null, message.getBytes("UTF-8"));
				System.out.println("general#" + argv[0] + ">" + message);

			}
			System.out.println("Message ?");
			message = sc.nextLine();
			System.out.println("Topic ?");
			topic = sc.nextLine();
		}

		channel.close();
		connection.close();
	}

	private static String getDate() {
		return (new Date()).toString();
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
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
