package base.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 异步发送消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        // 创建producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定nameserver地址(可以指定多个地址，如：192.168.2.101:9876;192.168.2.102:9876)
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        // 创建消息对象
        Message message = new Message("base-topic","Tag2","hello rocketmq".getBytes());
        System.out.println("msg will send");
        // 发送异步消息
        producer.send(message, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功，result:"+sendResult);
            }

            public void onException(Throwable throwable) {
                System.out.println("消息发送失败，exception:"+throwable);
            }
        });
        // 异步发送的时候，这段代码要注释掉，不然等broker回调来了，却发现关闭了Producer，就会报错。
        //producer.shutdown();
    }
}
