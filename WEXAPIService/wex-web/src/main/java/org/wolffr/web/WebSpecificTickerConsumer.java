package org.wolffr.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Singleton
@Startup
public class WebSpecificTickerConsumer {
	
	@EJB
	private SimpleTickerDataStore simpleTickerDataStore;
	

	@PostConstruct
	public void init() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare("specificTickerExchange", "fanout");
		String queue= channel.queueDeclare().getQueue();
		channel.queueBind(queue, "specificTickerExchange", "");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				SpecificTicker ticker = deSerialize(body);
				simpleTickerDataStore.processTicker(ticker);
			}
		};
		channel.basicConsume(queue, true, consumer);
	}
	
	
	private static SpecificTicker deSerialize(byte[] object) {
		ByteArrayInputStream input = new ByteArrayInputStream(object);
		try {
			ObjectInputStream in = new ObjectInputStream(input);
			SpecificTicker ticker = (SpecificTicker) in.readObject();
			input.close();
			in.close();
			return ticker;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
	}
}
