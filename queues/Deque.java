/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev; // point to previous node
        Node next; // point to next node
    }

    private Node head;  // point to first node
    private Node tail;  // point to last  node
    private int size;   // number of nodes

    /** construct an empty deque */
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    /** is the deque empty? */
    public boolean isEmpty() {
        return head == null;
    }

    /** return the number of items on the deque */
    public int size() {
        return  size;
    }

    /** add the item to the front. */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cann't add null");
        }

        // same as stack
        Node newFirst = new Node();
        newFirst.item = item;
        if (isEmpty()) {
            head = newFirst;
            tail = newFirst;
        } else {
            head.prev = newFirst;
            tail.next = newFirst;
        }
        newFirst.next = head;
        newFirst.prev = tail;

        head = newFirst;  // update head
        size += 1;  // update size
    }

    /** add the item to the back */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cann't add null");
        }

        if (isEmpty()) {  // same as add First when is empty
            addFirst(item);
            return;
        }
        // same as queue
        Node newTail = new Node();
        newTail.item = item;
        tail.next = newTail;
        head.prev = newTail;
        newTail.prev = tail;
        newTail.next = head;

        tail = newTail;  // update tail
        size += 1;  // update size
    }

    /** remove and return the item from the front */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cann't remove when deque is empty");
        }

        Item item = head.item;
        if (head == tail) {  // only contain one node
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }

        size -= 1;  // update size
        return item;
    }

    /** remove and return the item from the back */
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cann't remove when deque is empty");
        }

        Item item = tail.item;
        if (head == tail) {  // only contain one node
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            head.prev = tail;
            tail.next = head;
        }

        size -= 1;  // update size
        return item;
    }

    /** return an iterator over items in order from front to back */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node curr;
        private int count;  // count number of nodes iterated

        public DequeIterator() {
            curr = head;
            count = 0;
        }

        public boolean hasNext() {
            return count < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Stop iteration");
            }

            Item item = curr.item;
            curr = curr.next;  // update curr
            count += 1;  // update count
            return item;
        }
    }

    /** unit testing (required) */
    public static void main(String[] args) {
        Deque<String> testDeque1 = new Deque<>();
        Deque<String> testDeque2 = new Deque<>();
        String word;

        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            testDeque1.addFirst(word);
            testDeque2.addLast(word);
        }

        StdOut.println("number: " + testDeque1.size());
        for (String item: testDeque1) {
            StdOut.print(item + " ");
        }
        StdOut.print("\nhead: " + testDeque1.removeFirst());
        StdOut.println("\ttail: " + testDeque1.removeLast());

        StdOut.println("number: " + testDeque2.size());
        for (String item: testDeque2) {
            StdOut.print(item + " ");
        }
        StdOut.print("\nhead: " + testDeque2.removeFirst());
        StdOut.println("\ttail: " + testDeque2.removeLast());
    }
}
