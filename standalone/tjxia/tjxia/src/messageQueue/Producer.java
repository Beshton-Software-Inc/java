package messageQueue;

public class Producer {
    MessageQueue mq = null;
    Producer(MessageQueue mq){
        this.mq = mq;
    }

    public void produce(Object o){
        mq.produce(o);
    }
}
