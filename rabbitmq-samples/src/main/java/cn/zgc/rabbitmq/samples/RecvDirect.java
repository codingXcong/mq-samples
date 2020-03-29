package cn.zgc.rabbitmq.samples;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvDirect {
    private final static String QUEUE_NAME = "a1";

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
        channel.exchangeDeclare("direct_exchange","direct");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME,"direct_exchange","a");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("["+flag+"] Received '" + message + "'");

                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);



    }
}