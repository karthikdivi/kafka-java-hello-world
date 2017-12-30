package com.helloworld;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class KafkaHelloWorldConsumer {
	private Consumer<Long, String> consumer;
	public final String topic;
	public final String bootstrapServers;

	public KafkaHelloWorldConsumer(String bootstrapServers, String topic) {
		this.bootstrapServers = bootstrapServers;
		this.topic = topic;
		consumer = createConsumer();
	}

	private Consumer<Long, String> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(topic));
		return consumer;
	}

	public void start() {
		CompletableFuture.runAsync(() -> {
			try {
				while (true) {
					final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
					consumerRecords.forEach(record -> {
						System.out.printf("Consumed Record:(%d, %s, %d, %d)\n", record.key(), record.value(), record.partition(), record.offset());
					});
					consumer.commitAsync();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Ignore
			}
		});

	}

	public void close() {
		if (consumer != null) {
			try {
				consumer.close();
			} catch (Exception e) {
				// Ignore
			}
		}
	}
}
