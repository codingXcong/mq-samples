package cn.zgc.rabbitmq.samples;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

public class SendDirect {
    private final static String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //channel.queueBind(EXCHANGE_NAME,EXCHANGE_NAME,"");
        String message = "hello direct222";
        channel.basicPublish(EXCHANGE_NAME, "a", null, message.getBytes());
        System.out.println("send message: " + message);

        channel.close();
        connection.close();
    }
}
