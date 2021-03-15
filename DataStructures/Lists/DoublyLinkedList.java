package DataStructures.Lists;

/**
 * 双向链表，包含节点和指向前后的两个指针
 */
public class DoublyLinkedList {

    //指向头结点的指针
    // （注意此处Link为节点类型，相当于其他类中的Node）
    private Link head;

    //指向尾结点的指针
    private Link tail;

    //链表中的元素数量
    private int size;

    /**
     * 默认构造函数
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 通过数组构造一个链表
     *
     * @param array the array whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public DoublyLinkedList(int[] array) {
        if (array == null) {
            throw new NullPointerException();
        }
        for (int i : array) {
            insertTail(i);
        }
        size = array.length;
    }

    /**
     * 在头部插入元素
     *
     * @param x Element to be inserted
     */
    public void insertHead(int x) {
        //创建一个新节点
        Link newLink = new Link(x);

        if (isEmpty()) {
            //空链表，尾结点指针指向待添加的节点
            tail = newLink;
        } else {
            //头结点的上一个节点指向待添加的节点
            head.previous = newLink;
        }
        //新节点的下一个节点指向原头结点
        newLink.next = head;
        //头结点指向添加后的新节点
        head = newLink;
        ++size;
    }

    /**
     * 在尾部插入一个元素
     *
     * @param x Element to be inserted
     */
    public void insertTail(int x) {
        Link newLink = new Link(x);
        newLink.next = null;
        if (isEmpty()) {
            tail = newLink;
            head = tail;
        } else {
            tail.next = newLink;
            newLink.previous = tail;
            tail = newLink;
        }
        ++size;
    }

    /**
     * 在指定索引处插入元素
     *
     * @param x     Element to be inserted
     * @param index Index(from start) at which the element x to be inserted
     */
    public void insertElementByIndex(int x, int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            //头部插入
            insertHead(x);
        } else {
            if (index == size) {
                //尾部插入
                insertTail(x);
            } else {
                //指定位置插入
                Link newLink = new Link(x);
                Link previousLink = head;
                //遍历找到指定索引上一个位置的指针
                for (int i = 1; i < index; i++) {
                    previousLink = previousLink.next;
                }

                previousLink.next.previous = newLink;
                newLink.next = previousLink.next;
                newLink.previous = previousLink;
                previousLink.next = newLink;
            }
        }
        ++size;
    }

    /**
     * 删除头部的元素
     *
     * @return The new head
     */
    public Link deleteHead() {
        Link temp = head;
        head = head.next;

        if (head == null) {
            tail = null;
        } else {
            head.previous = null;
        }
        --size;
        return temp;
    }

    /**
     * 删除尾部的元素
     *
     * @return The new tail
     */
    public Link deleteTail() {
        Link temp = tail;
        tail = tail.previous;

        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        --size;
        return temp;
    }

    /**
     * 从列表中删除元素x
     *
     * @param x element to be deleted
     * @return Link deleted
     */
    public void delete(int x) {
        Link current = head;

        while (current.value != x) {
            if (current != tail) {
                current = current.next;
            } else {
                throw new RuntimeException("The element to be deleted does not exist!");
            }
        }

        if (current == head) {
            deleteHead();
        } else if (current == tail) {
            deleteTail();
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous; // 1 <--> 3
        }
        --size;
    }

    /**
     * 插入元素并重新排序
     *
     * @param x Element to be added
     */
    public void insertOrdered(int x) {
        Link newLink = new Link(x);
        Link current = head;
        while (current != null && x > current.value) {

            current = current.next;
        }

        if (current == head) {
            insertHead(x);
        } else if (current == null) {
            insertTail(x);
        } else {
            newLink.previous = current.previous;
            current.previous.next = newLink;
            newLink.next = current;
            current.previous = newLink;
        }
        ++size;
    }

    /**
     * 从列表中删除指定节点
     *
     * @param z Element to be deleted
     */
    public void deleteNode(Link z) {
        if (z.next == null) {
            deleteTail();
        } else if (z == head) {
            deleteHead();
        } else {
            z.previous.next = z.next;
            z.next.previous = z.previous;
        }
        --size;
    }

    //删除重复项
    public static void removeDuplicates(DoublyLinkedList l) {
        Link linkOne = l.head;
        while (linkOne.next != null) {
            Link linkTwo = linkOne.next;
            while (linkTwo.next != null) {
                if (linkOne.value == linkTwo.value) {
                    l.delete(linkTwo.value);
                }
                linkTwo = linkTwo.next;
            }
            linkOne = linkOne.next;
        }
    }

    /**
     * 清空列表
     */
    public void clearList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 如果列表为空，则返回true
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * 打印列表内容
     */
    public void display() { // Prints contents of the list
        Link current = head;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println();
    }
}

/**
 * 实现链表的节点类型
 *
 * @author Unknown
 */
class Link {
    /**
     * 节点中存的值
     */
    public int value;

    /**
     * 指向上一节点的指针
     */
    public Link next;

    /**
     * 指向下一节点的指针
     */
    public Link previous;

    /**
     * 构造方法
     *
     * @param value Value of node
     */
    public Link(int value) {
        this.value = value;
    }

    /**
     * 显示节点值
     */
    public void displayLink() {
        System.out.print(value + " ");
    }

    /**
     * Main Method
     *
     * @param args Command line arguments
     */
    public static void main(String args[]) {
        DoublyLinkedList myList = new DoublyLinkedList();
        myList.insertHead(13);
        myList.insertHead(7);
        myList.insertHead(10);
        myList.display(); // <-- 10(head) <--> 7 <--> 13(tail) -->

        myList.insertTail(11);
        myList.display(); // <-- 10(head) <--> 7 <--> 13 <--> 11(tail) -->

        myList.deleteTail();
        myList.display(); // <-- 10(head) <--> 7 <--> 13(tail) -->

        myList.delete(7);
        myList.display(); // <-- 10(head) <--> 13(tail) -->

        myList.insertOrdered(23);
        myList.insertOrdered(67);
        myList.insertOrdered(3);
        myList.display(); // <-- 3(head) <--> 10 <--> 13 <--> 23 <--> 67(tail) -->
        myList.insertElementByIndex(5, 1);
        myList.display(); // <-- 3(head) <--> 5 <--> 10 <--> 13 <--> 23 <--> 67(tail) -->
        myList.clearList();
        myList.display();
        myList.insertHead(20);
        myList.display();
    }
}
