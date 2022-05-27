/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
    private int size;

    /** construct an empty randomized queue */
    public RandomizedQueue() {
        data = (Item[]) new Object[4];
        size = 0;
    }

    /** is the randomized queue empty? */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the number of items on the randomized queue */
    public int size() {
        return size;
    }

    //** add the item */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cann't add null");
        }

        if (size == data.length) { // when array is full
            resize(2 * data.length);
        }
        data[size++] = item;
    }

    /** remove and return a random item */
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cann't dequeue from empty queue");
        }

        // get a sample, then swap this sample and tail item
        int index = StdRandom.uniform(0, size);
        size -= 1;
        swap(index, size);
        Item item = data[size];
        data[size] = null;
        if (size > 4 && size == data.length/4) { // when array is 1/4 filled
            resize(data.length / 2);
        }
        return item;
    }

    /** resize array to be given capacity. */
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) { // only first size items
            temp[i] = data[i];
        }
        data = temp;
    }

    /** swap items at index i and j */
    private void swap(int i, int j) {
        Item temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /** return a random item (but do not remove it) */
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cann't sample from empty queue");
        }

        int index = StdRandom.uniform(0, size); // random index
        return data[index];
    }

    /** return an independent iterator over items in random order */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indices; // array stored indices to be sample
        private int curr;

        public RandomizedQueueIterator() {
            indices = new int[size];
            curr = 0;
            for (int i = 0; i < indices.length; i++) { // original store i
                indices[i] = i;
            }
            StdRandom.shuffle(indices); // shuffle indices
        }

        public boolean hasNext() {
            return curr < indices.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Stop iteration");
            }

            int index  = indices[curr++]; // sample
            return data[index];
        }
    }

    /** unit testing (required) */
    public static void main(String[] args) {
        RandomizedQueue<String> testQueue1 = new RandomizedQueue<>();
        RandomizedQueue<String> testQueue2 = new RandomizedQueue<>();
        String word;

        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            testQueue1.enqueue(word);
            testQueue2.enqueue(word);
        }

        StdOut.println("number: " + testQueue1.size());
        for (String item: testQueue1) {
            StdOut.print(item + " ");
        }
        StdOut.print("\nsample: " + testQueue1.sample());
        StdOut.println("\tdequeue: " + testQueue1.dequeue());
        StdOut.println("current number: " + testQueue1.size() + "\n");

        StdOut.println("number: " + testQueue2.size());
        for (String item: testQueue2) {
            StdOut.print(item + " ");
        }
        StdOut.print("\nsample: " + testQueue2.sample());
        StdOut.println("\tdequeue: " + testQueue2.dequeue());
        StdOut.println("current number: " + testQueue2.size());
    }
}
