package com.helloworld;

public class Main {
	public final static String TOPIC = "hello-world";
	public final static String BOOTSTRAP_SERVERS = "localhost:9092";

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Start");
		
		KafkaHelloWorldConsumer consumer = new KafkaHelloWorldConsumer(BOOTSTRAP_SERVERS, TOPIC);
		consumer.start();
		
		KafkaHelloWorldProducer producer = new KafkaHelloWorldProducer(BOOTSTRAP_SERVERS, TOPIC);
		producer.produce(1L, "test message").close();
		
		Thread.sleep(1_000); // Buffer time for consumer to consume
		
		consumer.close();
		System.out.println("End");
		
	}
}
