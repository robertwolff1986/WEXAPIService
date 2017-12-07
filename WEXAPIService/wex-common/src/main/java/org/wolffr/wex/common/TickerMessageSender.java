package org.wolffr.wex.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TickerMessageSender {

	public static void send(SpecificTicker ticker) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {
			Channel channel = connection.createChannel();
			channel.exchangeDeclare("specificTickerExchange", "fanout");
			//channel.queueDeclare("specificTickerQueue", false, false, false, null);
			byte[] serializedObject = serialize(ticker);
			channel.basicPublish("specificTickerExchange", "", null, serializedObject);
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[] serialize(SpecificTicker ticker) {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(ticker);
			oos.flush();
			oos.reset();
			bytes = baos.toByteArray();
			oos.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

}
