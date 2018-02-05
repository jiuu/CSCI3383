
import java.util.Random;
import java.util.Arrays;

public class MinHeap {

    private int capacity;
    private int size;
    private Thing[] heap;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Thing[capacity];
    }

    public Thing[] getArray() {
        return heap;
    }

    //starts at index 0

    public Thing peek() {
        if (size == 0) {
            return null;
        }
        return heap[0];
    }

    public Thing deleteMin() {
        if (size == 0) {
            return null;
        }
        Thing min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null; //set the last element in the array to be 0, since it no longer exists
        size--;
        heapifyDown();
        return min;
    }

    public boolean delete(Thing t) {
        boolean completed = false;
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < heap.length; i++) {
            if (heap[i].equals(t)) {
                Thing deleted = heap[i];
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                heapifyDown();
                completed = true;
                return completed;
            }
        }
        return completed;
    }

    public void insert(Thing thing) {
        int element = thing.priority;
        if (size == heap.length) {
            doubleHeapSize();
        }
        heap[size] = thing;
        size++;
        heapifyUp();
    }

    public void heapify(Thing heap[], int n, int i) {
        int largest = i;

        if (getLeftChildIndex(i) < n && leftChild(i) > heap[largest].priority)
            largest = getLeftChildIndex(i);

        if (getRightChildIndex(i) < n && rightChild(i) > heap[largest].priority)
            largest = getRightChildIndex(i);

        if (largest != i) {
            swap(i, largest);

            //recursively heapify the affected sub-tree
            heapify(heap, n, largest);
        }
    }

    //sorts the heap in decreasing order (largest to smallest)
    public Thing[] heapSort(){
        Thing[] array = new Thing[this.size];
        int count = 0;
        while((size > 0)){
            array[count] = deleteMin();
            count++;
        }
        return array;
    }

    private void heapifyUp() {
        int i = size - 1;

        while (hasParent(i) && parent(i) > heap[i].priority) {
            swap(getParentIndex(i), i);
            i = getParentIndex(i);
        }
    }

    private void heapifyDown() {
        int i = 0;

        //only check if there is a left child because if there cannot be a right child without a left
        while (hasLeftChild(i)) {
            int smallerChildIndex = getLeftChildIndex(i);

            if (hasRightChild(i) && rightChild(i) < leftChild(i)) {
                smallerChildIndex = getRightChildIndex(i);
            }

            if (heap[i].priority < heap[smallerChildIndex].priority) {
                break;
            } else {
                swap(i, smallerChildIndex);
            }

            i = smallerChildIndex;
        }
    }


    private void swap(int i, int j) {
        Thing temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void doubleHeapSize() {
        if (size == capacity) {
            heap = Arrays.copyOf(heap, capacity * 2);
            capacity *= 2;
        }
    }


    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private int leftChild(int index) {
        return heap[getLeftChildIndex(index)].priority;
    }

    private int rightChild(int index) {
        return heap[getRightChildIndex(index)].priority;
    }

    private int parent(int index) {
        return heap[getParentIndex(index)].priority;
    }

    private void printHeap(Thing[] heap) {
        System.out.println(Arrays.toString(heap));
    }

    public int[] genTest(int num) {
        Random random = new Random();
        int[] test = new int[num];
        for (int k = 0; k < num; k++) {
            int c = random.nextInt(2);
            test[k] = c;
        }
        return test;
    }


    public static void main(String[] args) {
        

        MinHeap min = new MinHeap(1);
        Random rand = new Random();

        long avgTime10000 = 0;
        long avgTime20000 = 0;
        long avgTime30000 = 0;
        int t1000 = 100000;

        for (int i = 0; i < 3; i++) {
            int[] test = min.genTest(t1000);
            long startTime = System.currentTimeMillis();
            MinHeap tester = new MinHeap(1);
            for(int o = 0; o < 10000; o++){
                Thing a = new Thing(rand.nextInt(100), rand.nextInt(100));
                tester.insert(a);
            }
            for (int j = 0; j < t1000; j++) {
          
                if (test[j] == 0) {
                    Thing a = new Thing(rand.nextInt(100), rand.nextInt(100));
                    tester.insert(a);
                } else {
                    tester.deleteMin();
                }
            }
            long finishTime = System.currentTimeMillis();
            long totalTime = finishTime - startTime;
            if (i == 0) {
                avgTime10000 = totalTime ;
            } else if (i == 1) {
                avgTime20000 = totalTime ;
            } else {
                avgTime30000 = totalTime ;
            }
            t1000 = t1000 + 100000;
            System.out.println("Size: " + tester.size);
        }

        System.out.println(avgTime10000);
        System.out.println(avgTime20000);
        System.out.println(avgTime30000);
        
        int h100 = 100000;
        long time1 = 0;
        long time2 = 0;
        long time3 = 0;

        for (int y = 0; y < 3; y++) {
            MinHeap tester2 = new MinHeap(1);
            for (int o = 0; o < h100; o++) {
                tester2.insert(new Thing(rand.nextInt(100), rand.nextInt(100)));
            }
            long startTime = System.currentTimeMillis();
            tester2.heapSort();
            long finishTime = System.currentTimeMillis();
            //System.out.println(finishTime - startTime);
            h100 = h100 + 100000;
            if (y == 0) {
                time1 = finishTime - startTime;
            } else if (y == 1) {
                time2 = finishTime - startTime;
            } else {
                time3 = finishTime - startTime;
            }
        }
        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
    }
}

