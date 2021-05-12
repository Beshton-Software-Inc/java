public class Singleton {
    private volatile static Singleton singletonObj;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singletonObj == null) {
            synchronized (Singleton.class) {
                if (singletonObj == null) {
                    singletonObj = new Singleton();
                }
            }
        }
        return singletonObj;
    }
}