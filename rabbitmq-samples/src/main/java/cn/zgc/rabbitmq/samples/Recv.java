package cn.zgc.rabbitmq.samples;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello.august";

    public static void main(String[] args) {
        try {
            consumer("consumer1");
            //consumer("consumer2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consumer(final String flag) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(QUEUE_NAME,"fanout");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME,QUEUE_NAME,"");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("["+flag+"] Received '" + message + "'");

                channel.basicReject(envelope.getDeliveryTag(),true);
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);


        //channel.basicReject(1L,true);
    }
}