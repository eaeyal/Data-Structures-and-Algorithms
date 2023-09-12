import java.util.LinkedList;
import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Edan Eyal
 * @version 1.0
 * @userid eeyal3
 * @GTID 903754556
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Please enter a non-null array to sort!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Please provide a non-null comparator to sort the array with!");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            T temp = arr[maxIndex];
            arr[maxIndex] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Please enter a non-null array to sort!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Please provide a non-null comparator to sort the array with!");
        }
        boolean swapsMade = true;
        int start = 0;
        int end = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int aEnd = end;
            for (int i = start; i < aEnd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    end = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                int aStart = start;
                for (int j = end; j > aStart; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        swapsMade = true;
                        start = j;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Please enter a non-null array to sort!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Please provide a non-null comparator to sort the array with!");
        }
        mergeHelper(arr, comparator);
    }

    /**
     * a private helper method for the merge sort method
     * @param arr the array to sort
     * @param comparator a Comparator object to compare objects to sort
     * @param <T> the type of Object to sort
     */
    private static <T> void mergeHelper(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        int rightArrayLength = midIndex;
        if (arr.length % 2 != 0) {
            rightArrayLength++;
        }
        T[] right = (T[]) new Object[rightArrayLength];
        int c = 0;
        for (int i = midIndex; i < arr.length; i++) {
            right[c] = arr[i];
            c++;
        }
        mergeHelper(left, comparator);
        mergeHelper(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Please enter a non-null array to sort!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Please provide a non-null comparator to sort the array with!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Please provide a non-null random object to use in the sort!");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Please enter a k value within the bounds of the array!");
        }
        int start = 0;
        int end = arr.length - 1;
        return kHelper(arr, start, end, k, comparator, rand);
    }

    /**
     * a private helper method for the kth Select method
     * @param arr the array to perform the search on
     * @param start the start index of the array
     * @param end the end index of the array
     * @param k the index to retrieve data from + 1 if the array was sorted
     * @param comparator a comparator object to compare objects to search/sort
     * @param rand a Random object used to select pivots
     * @return the kth smallest element
     * @param <T> the type of Object to search for
     */
    private static <T> T kHelper(T[] arr, int start, int end, int k, Comparator<T> comparator, Random rand) {
        if ((end - start) < 1) {
            return arr[start];
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];
        T temp = arr[start];
        arr[start] = arr[pivotIndex];
        arr[pivotIndex] = temp;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotValue) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotValue) >= 0) {
                j--;
            }
            if (i <= j) {
                T newTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = newTemp;
                i++;
                j--;
            }
        }
        temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;
        if (j == k - 1) {
            return arr[j];
        }
        if (j > k - 1) {
            return kHelper(arr, start, j - 1, k, comparator, rand);
        } else {
            return kHelper(arr, j + 1, end, k, comparator, rand);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The provided array was null!");
        }
        int max = arr[0];
        for (int integer : arr) {
            if (Math.abs(integer) > max) {
                max = Math.abs(integer);
            }
        }
        int k = 0;
        while (max != 0) {
            max /= 10;
            k++;
        }
        int power = 10;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int digit = arr[j] % (power);
                if (i > 0) {
                    digit /= (power / 10);
                }
                if (buckets[digit + 9] == null) {
                    buckets[digit + 9] = new LinkedList<Integer>();
                }
                buckets[digit + 9].add(arr[j]);

            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    while (!bucket.isEmpty()) {
                        arr[index++] = bucket.remove();
                    }
                }
            }
            power *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Please enter non-null data to sort!");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = queue.remove();
        }
        return sorted;
    }
}
