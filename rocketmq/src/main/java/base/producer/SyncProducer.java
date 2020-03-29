package base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 发送同步消息，send方法会阻塞，直到broker响应才继续往下执行，或者抛出异常
 *
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定nameserver地址(可以指定多个地址，如：192.168.2.101:9876;192.168.2.102:9876)
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        // 创建消息对象
        Message msg = new Message("base-topic","tag1","hello rocketmq".getBytes());
        // 发送消息
        System.out.println("will send msg");
        SendResult sendResult = producer.send(msg);
        // 获取发送状态
        System.out.println("send result:"+sendResult);
        // 关闭producer
        producer.shutdown();
    }
}
