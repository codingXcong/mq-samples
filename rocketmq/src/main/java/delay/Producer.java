package delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i=0; i<10; i++) {
            Message message = new Message("delay-topic","tag1",("hello rocketmq"+i).getBytes());
            message.setDelayTimeLevel(5);
            SendResult sendResult = producer.send(message);
            System.out.println("sendResult:"+sendResult);
        }

        producer.shutdown();
    }
}
