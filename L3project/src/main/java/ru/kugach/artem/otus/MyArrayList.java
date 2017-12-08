package ru.kugach.artem.otus;


import java.util.*;

public class MyArrayList <T> implements List<T> {

    private Object[] elementData;
    private int size;

    public MyArrayList() {

        this.elementData  = new Object[10];
    }

    public MyArrayList(int initialCapacity) {

        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException();
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size+1);
        elementData[size++] = t;
        return true;
    }

    private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE;

    private void ensureCapacity(int minCapacity){
        if (minCapacity>elementData.length) {
            increaseInternalArray(minCapacity);
        }
    }

    private void increaseInternalArray(int minCapacity){
        int newCapacity = elementData.length * 3 / 2;
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity>MAX_ARRAY_LENGTH){
            newCapacity=MAX_ARRAY_LENGTH;
        }
        Object[] oldElementData = elementData;
        elementData = new Object[newCapacity];
        System.arraycopy(oldElementData,0,elementData,0,size);
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
    }


    @Override
    public void clear() {
        throw new RuntimeException();
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Size of List: " + size + ", Index: " + index);
        }
        return (T) elementData[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Size of List: " + size + ", Index: " + index);
        }
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException();
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException();
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException();
    }

    public String toString() {
        MyListIterator it = new MyListIterator(0);
        if (! it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while(true) {
            T t = it.next();
            sb.append(t);
            if (! it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException();
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new RuntimeException();
    }

    private class MyListIterator implements ListIterator<T> {
        int cursor;
        int last = -1;

        MyListIterator(int index) {
            cursor = index;
        }

        public boolean hasNext() {
            return cursor < size;
        }

        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            cursor = i + 1;
            return (T) elementData[last = i];
        }

        public void remove() {
            throw new RuntimeException();
        }


        public boolean hasPrevious() {
            throw new RuntimeException();
        }

        public int nextIndex() {
            throw new RuntimeException();
        }

        public int previousIndex() {
            throw new RuntimeException();
        }

        public T previous() {

            throw new RuntimeException();
        }

        public void set(T t) {
            MyArrayList.this.set(last, t);
        }

        public void add(T t) {
            throw new RuntimeException();
        }
    }
}
