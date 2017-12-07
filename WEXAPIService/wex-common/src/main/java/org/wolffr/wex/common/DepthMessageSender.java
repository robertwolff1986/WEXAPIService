package org.wolffr.wex.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.wolffr.wex.common.mongo.depth.SpecificDepth;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DepthMessageSender {

	public static void send(SpecificDepth depth) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {
			Channel channel = connection.createChannel();
			channel.exchangeDeclare("specificDepthExchange", "fanout");
			byte[] serializedObject = serialize(depth);
			channel.basicPublish("specificDepthExchange", "", null, serializedObject);
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[] serialize(SpecificDepth depth) {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(depth);
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
