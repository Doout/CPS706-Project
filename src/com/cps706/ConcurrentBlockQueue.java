package com.cps706;

/**
 * Created by Baheer.
 */
public class ConcurrentBlockQueue<T> {

    private volatile Object[] items;
    private volatile boolean upSize;
    private volatile int index;
    private volatile int size;
    private volatile int maxSize;

    public ConcurrentBlockQueue(int initSize) {
        initSize = initSize + 1;
        if (initSize < 5)
            initSize = 5;
        this.maxSize = initSize - 1;
        index = 0;
        size = 0;
        upSize = true;
        items = new Object[initSize];
    }

    public static void main(String... arg) {
        ConcurrentBlockQueue<String> list = new ConcurrentBlockQueue<>(5);
        list.allowReSize(false);
        for (int i = 0; i < 100; i++)
            list.push(" " + i);
        while (!list.isEmpty())
            System.out.println(list.pop());

    }

    private void upSize() {
        Object[] neitems = new Object[(int) (Math.ceil(items.length * 1.5))];
        int i;
        for (i = 0; !isEmpty(); i++) {
            neitems[i] = pop();
        }
        this.size = i;
        this.maxSize = neitems.length - 1;
        this.index = 0;
        this.items = neitems;
    }

    public synchronized T pop() {
        if (isEmpty())
            return null;
        if (index >= maxSize)
            index %= maxSize;
        synchronized (items) {
            size--;
            return (T) items[index++];
        }
    }

    public synchronized void push(T item) {
        synchronized (items) {
            if (size == maxSize) {
                if (upSize) upSize();
                else return;
            }
            items[(index + size++) % maxSize] = item;
        }
    }

    public void allowReSize(boolean resizeAllow) {
        this.upSize = resizeAllow;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

}
