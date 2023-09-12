//import java.util.NoSuchElementException;
//
///**
// * Your implementation of an ArrayQueue.
// *
// * @author Edan Eyal
// * @version 1.0
// * @userid eeyal3
// * @GTID 903754556
// *
// * Collaborators: N/A
// *
// * Resources: N/A
// */
//public class ArrayQueue<T> {
//
//    /*
//     * The initial capacity of the ArrayQueue.
//     *
//     * DO NOT MODIFY THIS VARIABLE.
//     */
//    public static final int INITIAL_CAPACITY = 9;
//
//    /*
//     * Do not add new instance variables or modify existing ones.
//     */
//    private T[] backingArray;
//    private int front;
//    private int size;
//
//    /**
//     * Constructs a new ArrayQueue.
//     */
//    public ArrayQueue() {
//        backingArray = (T[]) new Object[INITIAL_CAPACITY];
//        front = 0;
//        size = 0;
//    }
//
//    /**
//     * Adds the data to the back of the queue.
//     *
//     * If sufficient space is not available in the backing array, resize it to
//     * double the current length. When resizing, copy elements to the
//     * beginning of the new array and reset front to 0.
//     *
//     * Must be amortized O(1).
//     *
//     * @param data the data to add to the back of the queue
//     * @throws java.lang.IllegalArgumentException if data is null
//     */
//    public void enqueue(T data) {
//        // null check
//        if (data == null) {
//            throw new IllegalArgumentException("The data entered was null!");
//        }
//        // resize
//        if (size == backingArray.length) {
//            T[] newArray = (T[]) new Object[backingArray.length * 2];
//            int i = 0;
//            int j = front;
//            for (T element:backingArray) {
//                if (element != null) {
//                    newArray[i] = backingArray[j % backingArray.length];
//                    i++;
//                }
//                j++;
//            }
//            backingArray = newArray;
//            backingArray[size] = data;
//            size++;
//            front = 0;
//            return;
//        }
//        // addition
//        if (backingArray[backingArray.length - 1] != null) {
//            backingArray[((size + front + 1) % (backingArray.length + 1))] = data;
//        } else {
//            backingArray[((size + front + 1) % (backingArray.length + 1)) - 1] = data;
//        }
//        size++;
//    }
//
//    /**
//     * Removes and returns the data from the front of the queue.
//     *
//     * Do not shrink the backing array.
//     *
//     * Replace any spots that you dequeue from with null.
//     *
//     * If the queue becomes empty as a result of this call, do not reset
//     * front to 0.
//     *
//     * Must be O(1).
//     *
//     * @return the data formerly located at the front of the queue
//     * @throws java.util.NoSuchElementException if the queue is empty
//     */
//    public T dequeue() {
//        if (size == 0) {
//            throw new NoSuchElementException("The queue is empty!");
//        }
//        T returnData = backingArray[front];
//        backingArray[front] = null;
//        front++;
//        front = (front % backingArray.length);
//        size--;
//        return returnData;
//    }
//
//    /**
//     * Returns the data from the front of the queue without removing it.
//     *
//     * Must be O(1).
//     *
//     * @return the data located at the front of the queue
//     * @throws java.util.NoSuchElementException if the queue is empty
//     */
//    public T peek() {
//        if (size == 0) {
//            throw new NoSuchElementException("The queue is empty!");
//        }
//        return backingArray[front];
//    }
//
//    /**
//     * Returns the backing array of the queue.
//     *
//     * For grading purposes only. You shouldn't need to use this method since
//     * you have direct access to the variable.
//     *
//     * @return the backing array of the queue
//     */
//    public T[] getBackingArray() {
//        // DO NOT MODIFY THIS METHOD!
//        return backingArray;
//    }
//
//    /**
//     * Returns the front index of the queue.
//     *
//     * For grading purposes only. You shouldn't need to use this method since
//     * you have direct access to the variable.
//     *
//     * @return the front index of the queue
//     */
//    public int getFront() {
//        // DO NOT MODIFY THIS METHOD!
//        return front;
//    }
//
//    /**
//     * Returns the size of the queue.
//     *
//     * For grading purposes only. You shouldn't need to use this method since
//     * you have direct access to the variable.
//     *
//     * @return the size of the queue
//     */
//    public int size() {
//        // DO NOT MODIFY THIS METHOD!
//        return size;
//    }
//}
