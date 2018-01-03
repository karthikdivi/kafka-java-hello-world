package com.helloworld;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class KafkaHelloWorldProducer {
	private Producer<Long, String> producer = null;
	public final String topic;
	public final String bootstrapServers;

	public KafkaHelloWorldProducer(String bootstrapServers, String topic) {
		this.bootstrapServers = bootstrapServers;
		this.topic = topic; 
		producer = createProducer();
	}

	private Producer<Long, String> createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

	public KafkaHelloWorldProducer produce(long key, String value) {
		try {
			final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, key, value);
			RecordMetadata metadata = producer.send(record).get();
			System.out.printf("sent record(key=%s value=%s) " + "metadata(partition=%d, offset=%d)\n", record.key(), record.value(), metadata.partition(), metadata.offset());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public void close() {
		if (producer != null) {
			try {
				producer.flush();
				producer.close();
			} catch (Exception e) {
				// Ignore
			}
		}
	}

}
