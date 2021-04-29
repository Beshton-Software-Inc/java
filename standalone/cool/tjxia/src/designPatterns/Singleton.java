package designPatterns;

public class Singleton {
    static class SingletonLazy{
        private static SingletonLazy instance = null;
        private SingletonLazy(){}
        public synchronized Object getInstance(){
            if(instance == null){
                instance = new SingletonLazy();
            }
            return instance;
        }
    }

    static class SingletonHungry{
        private static SingletonHungry instance = new SingletonHungry();
        private SingletonHungry(){}
        public synchronized SingletonHungry getInstance(){
            return instance;
        }
    }

    static class DoubleLocked{
        private static volatile DoubleLocked instance = null;
        private DoubleLocked(){}
        public DoubleLocked getInstance(){
            if(instance == null){
                synchronized (DoubleLocked.class){
                    if (instance == null) {
                        instance = new DoubleLocked();
                    }
                }
            }
            return instance;
        }
    }

    static class StaticInnerClass{
        private static class Instance {
            public static StaticInnerClass instance = new StaticInnerClass();
        }
        private StaticInnerClass(){}
        public StaticInnerClass getInstance(){
            return Instance.instance;
        }
    }

    
    static class EnumSingleton{
        private EnumSingleton(){};
    }
    enum Enum {
        INSTANCE;
        private EnumSingleton instance;
        private Enum(){
            instance = new EnumSingleton();
        }
        public EnumSingleton getInstance(){
            return instance;
        }
    }

}
