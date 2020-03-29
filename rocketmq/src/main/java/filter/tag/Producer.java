package filter.tag;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("product-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i=0; i<10; i++) {
            Message message = new Message("tag-topic","tag1",("hello rocketmq"+i).getBytes());
            SendResult send = producer.send(message);
            System.out.println("sendResult="+send);
        }

        producer.shutdown();
    }

}
