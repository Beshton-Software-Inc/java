package messageQueue;

public class Consumer {
    MessageQueue mq = null;
    Consumer(MessageQueue mq){
        this.mq = mq;
    }

    public void consume(){
        mq.consume();
    }
}
