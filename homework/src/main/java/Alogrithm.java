import java.util.*;

public class Alogrithm {
    public static class Heap<E>{
        private static final double RESIZE_FACTOR = 1.5;
        private static final int INITIAL_SIZE = 12;
        int size = 0;
        Object[] array;
        Comparator<E> comparator;
        public Heap(){
            this.array = new Object[INITIAL_SIZE];
            this.size = 0;
        }
        public Heap(E[] array){
            this(array,null);
        }
        public Heap(E[] array, Comparator<E> comparator){
            this.array = array;
            this.size = array.length;
            this.comparator = comparator;
            heapify();
        }
        public Heap(Comparator<E> comparator){
            this();
            this.comparator = comparator;
        }

        private void heapify(){
            for(int i = (size-2)/2; i >= 0; i--){
                perculateDown(i);
            }
        }
        private void perculateDown(int i){
            while(i < size / 2){
                int candidate = i * 2 + 1;
                if( i * 2 + 2 < size && compare(array[i * 2 + 2], array[candidate]) < 0 ){
                    candidate = i * 2 + 2;
                }
                if(compare(array[i], array[candidate]) < 0){
                    return;
                }
                swap(i, candidate);
                i = candidate;
            }
        }
        private void perculateUp(int i){
            while(i > 0){
                if(compare( array[(i - 1) / 2], array[i]) < 0) {
                    return;
                }
                swap( i, (i - 1) / 2 );
                i = (i - 1) / 2;
            }
        }
        public void offer(int value){
            if(size == array.length){
                resize();
            }
            array[size++] = value;
            perculateUp(size-1);
        }

        public E poll(){
            if(isEmpty()){
                throw new NoSuchElementException("Poll From Empty Heap");
            }
            E result = (E) array[0];
            swap(0, size - 1);
            size--;
            perculateDown(0);
            return result;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        private void resize(){
            int newLength = (int) (array.length * RESIZE_FACTOR + 1);
            Object[] newArray = new Object[newLength];
            for(int i = 0; i < array.length; i++){
                newArray[i] = array[i];
            }
            array = newArray;
        }

        private void swap(int i, int j){
            Object temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        private int compare(Object e1, Object e2){
            if(comparator != null){
                return comparator.compare((E)e1, (E)e2);
            }
            Comparable<E> element1 = (Comparable<E>) e1;
            return element1.compareTo((E)e2);
        }

    }


    public static void main(String[] args) {

    }
}
