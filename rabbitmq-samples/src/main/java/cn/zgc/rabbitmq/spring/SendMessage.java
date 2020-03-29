package cn.zgc.rabbitmq.spring;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class SendMessage {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(new com.rabbitmq.client.ConnectionFactory());
        String username = connectionFactory.getUsername();
        String host = connectionFactory.getHost();
        System.out.println("host="+host+",username="+username);
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("myqueue"));

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("myqueue", "foo");

        //String foo = (String) template.receiveAndConvert("myqueue");
        //System.out.println("revice:"+foo);
    }
}
