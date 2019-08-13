package cn.zgc.rabbitmq.samples;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello.august";

    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QUEUE_NAME,"fanout");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME,QUEUE_NAME,"");
        String message = "hello 333";
        channel.basicPublish(QUEUE_NAME, QUEUE_NAME, null, message.getBytes());
        System.out.println("send message: " + message);

        channel.close();
        connection.close();
    }
}
