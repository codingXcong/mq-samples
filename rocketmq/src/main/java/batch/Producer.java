package batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("product-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        List<Message> msgs = new ArrayList<>();


        //4.创建消息对象，指定主题Topic、Tag和消息体
        /**
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        Message msg1 = new Message("batch-topic", "Tag1", ("Hello World" + 1).getBytes());
        Message msg2 = new Message("batch-topic", "Tag1", ("Hello World" + 2).getBytes());
        Message msg3 = new Message("batch-topic", "Tag1", ("Hello World" + 3).getBytes());

        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);

        //5.发送消息
        SendResult result = producer.send(msgs);

        //当批量消息比较大时，大于4M，先把大的消息分裂成若干个小的消息再进行发送
        /*ListSplitter splitter = new ListSplitter(msgs);
        while (splitter.hasNext()) {
            try {
                List<Message>  listItem = splitter.next();
                producer.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }
        }*/

        System.out.println("发送结果:" + result);


        producer.shutdown();
    }

}
