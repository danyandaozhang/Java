package DataStructures.Bags;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 只允许添加和遍历，而不允许删除的集合
 * <p>
 * 本质是一个只能在<b>头部<b/>添加的单链表
 *
 * @param <Element> - the generic type of an element in this bag
 */
public class Bag<Element> implements Iterable<Element> {

    // 包中的第一个节点
    private Node<Element> firstElement;

    // 包中元素的数量
    private int size;

    /**
     * 节点类型
     * <p>
     * 包含节点上的元素和指向下一个节点的索引
     */
    private static class Node<Element> {
        private Element content;
        private Node<Element> nextElement;
    }

    /**
     * 空参构造，初始化参数
     */
    public Bag() {
        firstElement = null;
        size = 0;
    }

    /**
     * @return true if this bag is empty, false otherwise
     */
    public boolean isEmpty() {
        return firstElement == null;
    }

    /**
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * 在链表的头部添加元素
     * <p>
     * 添加后firstElement始终指向头部的最新节点
     *
     * @param element - the element to add
     */
    public void add(Element element) {
        Node<Element> oldfirst = firstElement;
        firstElement = new Node<>();
        firstElement.content = element;
        firstElement.nextElement = oldfirst;
        size++;
    }

    /**
     * 通过遍历，判断包中是否包含某个元素
     *
     * @param element which you want to look for
     * @return true if bag contains element, otherwise false
     */
    public boolean contains(Element element) {
        Iterator<Element> iterator = this.iterator();
        //依次遍历，发现元素即停止
        while (iterator.hasNext()) {
            if (iterator.next().equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回迭代器
     * <p>
     * 最后添加的元素先遍历出
     *
     * @return an iterator that iterates over the elements in this bag in arbitrary order
     */
    public Iterator<Element> iterator() {
        return new ListIterator<>(firstElement);
    }

    /**
     * 迭代器类型
     */
    @SuppressWarnings("hiding")
    private class ListIterator<Element> implements Iterator<Element> {
        private Node<Element> currentElement;

        public ListIterator(Node<Element> firstElement) {
            currentElement = firstElement;
        }

        public boolean hasNext() {
            return currentElement != null;
        }

        /**
         * remove is not allowed in a bag
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Element next() {
            if (!hasNext()) throw new NoSuchElementException();
            Element element = currentElement.content;
            currentElement = currentElement.nextElement;
            return element;
        }
    }


    /**
     * 用以测试的main方法
     */
    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();

        bag.add("1");
        bag.add("1");
        bag.add("2");

        System.out.println("size of bag = " + bag.size());
        for (String s : bag) {
            System.out.println(s);
        }

        System.out.println(bag.contains(null));
        System.out.println(bag.contains("1"));
        System.out.println(bag.contains("3"));
    }
}
