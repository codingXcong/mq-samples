package order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("product-group1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        List<OrderStep> orderSteps = OrderStep.buildOrders();
        for (int i=0; i<orderSteps.size(); i++) {
            String body = orderSteps.get(i) + "";
            Message message = new Message("order-topic",body.getBytes());
            SendResult send = producer.send(message, new MessageQueueSelector() {
                /**
                 * @param list：队列集合
                 * @param message：消息对象
                 * @param o：业务标识的参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int orderId = (int) o;
                    int index = orderId % list.size();
                    return list.get(index);
                }
            }, orderSteps.get(i).getOrderId());
            System.out.println("send result"+ send);
        }

        producer.shutdown();
    }
}
