package base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消息的消费者，推模式
 */
public class PushConsumer {
    public static void main(String[] args) throws MQClientException {
        // 创建Consumer,并指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定nameserver地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅主题topic和tag
        consumer.subscribe("base-topic","*");
        // 指定消费模式：负载均衡|广播模式 。默认是负载均衡，消费者才会区分是负载均衡还是广播模式，生产者不做区分。
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置回调函数，处理消息
        consumer.setMessageListener(new MessageListenerConcurrently() {
            // 接收消息
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("consumerThread="+Thread.currentThread().getName()+", msg:"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动Consumer
        consumer.start();
    }
}
