
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import java.util.Date;

//不要往pom里import jackson，会替换掉默认的jackson，导致其他代码出错，比如以下代码
//当引入了        <dependency>
//            <groupId>com.fasterxml.jackson.core</groupId>
//            <artifactId>jackson-core</artifactId>
//            <version>2.1.4</version>
//        </dependency>
//        <dependency>
//            <groupId>com.fasterxml.jackson.core</groupId>
//            <artifactId>jackson-databind</artifactId>
//            <version>2.1.4</version>
//        </dependency> 时候
//以下代码报错：java.long.noSuchFeildError ALLOW_FINAL_FIELDS_AS_MUTATORS
public class SQS {
    public static void main(String[] args) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        CreateQueueRequest create_request = new CreateQueueRequest("queue1-test-muyizhu")
                .addAttributesEntry("DelaySeconds", "60")
                .addAttributesEntry("MessageRetentionPeriod", "86400");

        try {
            sqs.createQueue(create_request);
            String queue_url = sqs.getQueueUrl("queue1-test-muyizhu").getQueueUrl();
            System.out.println("queue1-test-muyizhu url is "+queue_url);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
    }
}
