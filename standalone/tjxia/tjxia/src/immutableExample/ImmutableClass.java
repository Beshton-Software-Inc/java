package immutableExample;

public final class ImmutableClass {
    final int i;
    final MutableClass mutableClass;

    public ImmutableClass(int i, MutableClass mutableClass) {
        this.i = i;
        this.mutableClass = new MutableClass();
        this.mutableClass.setNum(mutableClass.getNum());
        //this.mutableClass = mutableClass;
    }

    public static void main(String[] args) {
        MutableClass mutableClass = new MutableClass(1);
        ImmutableClass immutableClass = new ImmutableClass(1, mutableClass);
        System.out.println(immutableClass.i + " " + immutableClass.mutableClass.num);
        mutableClass.setNum(2);
        System.out.println(immutableClass.i + " " + immutableClass.mutableClass.num);
    }
}

class MutableClass{
    int num;

    MutableClass(){}

    MutableClass(int i){
        this.num = i;
    }

    int getNum(){
        return num;
    }

    void setNum(int i){
        this.num = i;
    }
}
