package DataStructures.DynamicArray;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 动态数组
 *
 * @param <E> the type that each index of the array will hold
 */
public class DynamicArray<E> implements Iterable<E> {

    //数组容量
    private int capacity;
    //实际存储的元素数量
    private int size;
    //数组类型
    private Object[] elements;

    /**
     * 有参构造，指定容量
     *
     * @param capacity the starting length of the desired array
     */
    public DynamicArray(final int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.elements = new Object[this.capacity];
    }

    /**
     * 无参构造，初始容量为10
     */
    public DynamicArray() {
        this.size = 0;
        this.capacity = 10;
        this.elements = new Object[this.capacity];
    }

    /**
     * 容量扩大至原来的2倍
     *
     * @return int the new capacity of the array
     */
    public int newCapacity() {
        this.capacity *= 2;
        // changed from this.capacity <<= 1; now much easier to understand
        return this.capacity;
    }

    /**
     * 向数组中添加元素，
     * 若数组已满，则创建一个两倍大小于原数组的新数组，并将元素复制到新数组
     *
     * @param element the element of type <E> to be added to the array
     */
    public void add(final E element) {
        if (this.size == this.elements.length) {
            //使用jdk原生工具复制
            this.elements = Arrays.copyOf(this.elements, newCapacity());
        }

        this.elements[this.size] = element;
        size++;
    }

    /**
     * 在指定位置(索引)添加元素
     *
     * @param index   the index for the element to be placed
     * @param element the element to be inserted
     */
    public void put(final int index, E element) {
        this.elements[index] = element;
    }

    /**
     * 获取指定位置元素
     * 若为空则返回null
     *
     * @param index the desired index of the element
     * @return <E> the element at the specified index
     */
    public E get(final int index) {
        return getElement(index);
    }

    /**
     * 从数组中移除元素
     *
     * @param index the index of the element to be removed
     * @return <E> the element removed
     */
    public E remove(final int index) {
        final E oldElement = getElement(index);
        fastRemove(this.elements, index);

        return oldElement;
    }

    /**
     * 获取数组中的元素数量
     *
     * @return int size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * 判断数组是否为空
     *
     * @return boolean true if the array contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    //流操作相关
    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 删除数组中指定位置的元素
     *
     * <b>注意，数组元素数量-1，但容量没变，且删除后最后一个位置的元素变为null<b/>
     *
     * @param elements 原数组
     * @param index    待删除的位置
     */
    private void fastRemove(final Object[] elements, final int index) {
        //删除后的数量为原数量-1
        final int newSize = this.size - 1;

        //删除的元素不是最后一个
        if (newSize > index) {
            //在同一个数组中截取复制达到删除的目的，但是数组容量没变，最后一个位置元素变为null
            System.arraycopy(elements, index + 1, elements, index, newSize - index);
        }

        elements[this.size = newSize] = null;
    }

    //获取指定位置元素
    private E getElement(final int index) {
        return (E) this.elements[index];
    }

    /**
     * returns a String representation of this object
     *
     * @return String a String representing the array
     */
    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(this.elements).filter(Objects::nonNull).toArray());
    }

    /**
     * 返回特定的迭代器
     *
     * @return Iterator a Dynamic Array Iterator
     */
    @Override
    public Iterator iterator() {
        return new DynamicArrayIterator();
    }

    //指定的迭代器类型
    private class DynamicArrayIterator implements Iterator<E> {

        private int cursor;

        @Override
        public boolean hasNext() {
            return this.cursor != size;
        }

        @Override
        public E next() {
            if (this.cursor > DynamicArray.this.size) throw new NoSuchElementException();

            if (this.cursor > DynamicArray.this.elements.length)
                throw new ConcurrentModificationException();

            final E element = DynamicArray.this.getElement(this.cursor);
            this.cursor++;

            return element;
        }

        @Override
        public void remove() {
            if (this.cursor < 0) throw new IllegalStateException();

            DynamicArray.this.remove(this.cursor);
            this.cursor--;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);

            for (int i = 0; i < DynamicArray.this.size; i++) {
                action.accept(DynamicArray.this.getElement(i));
            }
        }
    }

    /**
     * This class is the driver for the DynamicArray<E> class it tests a variety of methods and prints
     * the output
     */
    public static void main(String[] args) {
        DynamicArray<String> names = new DynamicArray<>();
        names.add("Peubes");
        names.add("Marley");

        for (String name : names) {
            System.out.println(name);
        }

        names.stream().forEach(System.out::println);

        System.out.println(names);

        System.out.println(names.getSize());

        names.remove(0);

        for (String name : names) {
            System.out.println(name);
        }
    }
}
