package DataStructures.Lists;

import java.util.StringJoiner;

/**
 * 单链表
 * https://en.wikipedia.org/wiki/Linked_list
 */
public class SinglyLinkedList {

    //头结点
    private Node head;


    //链表中元素数量
    private int size;

    //空参构造——初始化
    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * 全参构造
     *
     * @param head the head node of list
     * @param size the size of list
     */
    public SinglyLinkedList(Node head, int size) {
        this.head = head;
        this.size = size;
    }

    /**
     * 在头部插入元素
     *
     * @param x element to be added
     */
    public void insertHead(int x) {
        insertNth(x, 0);
    }

    /**
     * 在链表尾部插入元素
     *
     * @param data element to be added
     */
    public void insert(int data) {
        insertNth(data, size);
    }

    /**
     * 在指定位置插入元素
     *
     * @param data     data to be stored in a new node
     * @param position position at which a new node is to be inserted
     */
    public void insertNth(int data, int position) {

        checkBounds(position, 0, size);

        Node newNode = new Node(data);
        if (head == null) {
            /* the list is empty */
            head = newNode;
            size++;
            return;
        } else if (position == 0) {
            /* insert at the head of the list */
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }

        Node cur = head;
        //找到指定位置的上一个节点
        for (int i = 0; i < position - 1; ++i) {
            cur = cur.next;
        }
        //先连
        newNode.next = cur.next;
        //后断
        cur.next = newNode;
        size++;
    }

    /**
     * 删除头部元素
     */
    public void deleteHead() {
        deleteNth(0);
    }

    /**
     * 删除尾部元素
     */
    public void delete() {
        deleteNth(size - 1);
    }

    /**
     * 删除指定位置元素
     */
    public void deleteNth(int position) {
        checkBounds(position, 0, size - 1);
        if (position == 0) {
            Node destroy = head;
            head = head.next;
            destroy = null; /* clear to let GC do its work */
            size--;
            return;
        }
        //找到指定位置的上一个节点
        Node cur = head;
        for (int i = 0; i < position - 1; ++i) {
            cur = cur.next;
        }

        Node destroy = cur.next;
        //越过要删除的节点
        cur.next = cur.next.next;
        destroy = null; // clear to let GC do its work

        size--;
    }

    /**
     * @param position to check position
     * @param low      low index
     * @param high     high index
     * @throws IndexOutOfBoundsException if {@code position} not in range {@code low} to {@code high}
     */
    public void checkBounds(int position, int low, int high) {
        if (position > high || position < low) {
            throw new IndexOutOfBoundsException(position + "");
        }
    }

    /**
     * 清空链表
     */
    public void clear() {
        Node cur = head;
        while (cur != null) {
            Node prev = cur;
            cur = cur.next;
            prev = null; // clear to let GC do its work
        }
        head = null;
        size = 0;
    }

    /**
     * 检查链表是否为空
     *
     * @return {@code true} if list is empty, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回链表中元素的数量
     *
     * @return the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * 获取头结点
     *
     * @return head of the list.
     */
    public Node getHead() {
        return head;
    }

    /**
     * 计算链表中的节点数量
     *
     * @return count of the list
     */
    public int count() {
        int count = 0;
        Node cur = head;
        while (cur != null) {
            cur = cur.next;
            count++;
        }
        return count;
    }

    /**
     * 判断某个值是否存在于链表中
     *
     * @param key the value to be searched.
     * @return {@code true} if key is present in the list, otherwise {@code false}.
     */
    public boolean search(int key) {
        Node cur = head;
        while (cur != null) {
            if (cur.value == key) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * 获取指定索引位置的元素
     *
     * @param index given index of element
     * @return element at special index.
     */
    public int getNth(int index) {
        checkBounds(index, 0, size - 1);
        Node cur = head;
        for (int i = 0; i < index; ++i) {
            cur = cur.next;
        }
        return cur.value;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("->");
        Node cur = head;
        while (cur != null) {
            joiner.add(cur.value + "");
            cur = cur.next;
        }
        return joiner.toString();
    }

    /**
     * 测试类
     */
    public static void main(String[] arg) {
        SinglyLinkedList list = new SinglyLinkedList();
        assert list.isEmpty();
        assert list.size() == 0 && list.count() == 0;
        assert list.toString().equals("");

        /* Test insert function */
        list.insertHead(5);
        list.insertHead(7);
        list.insertHead(10);
        list.insert(3);
        list.insertNth(1, 4);
        assert list.toString().equals("10->7->5->3->1");

        /* Test search function */
        assert list.search(10) && list.search(5) && list.search(1) && !list.search(100);

        /* Test get function */
        assert list.getNth(0) == 10 && list.getNth(2) == 5 && list.getNth(4) == 1;

        /* Test delete function */
        list.deleteHead();
        list.deleteNth(1);
        list.delete();
        assert list.toString().equals("7->3");

        assert list.size == 2 && list.size() == list.count();

        list.clear();
        assert list.isEmpty();

        try {
            list.delete();
            assert false; /* this should not happen */
        } catch (Exception e) {
            assert true; /* this should happen */
        }
    }
}

/**
 * 链表中的节点类型.
 * 包含一个值和指向下一节点的指针
 */
class Node {

    //节点中存储的值,此处指定为int类型，实际可用泛型
    int value;

    //指向下一个节点的指针(引用)
    Node next;

    Node() {
    }

    /**
     * Constructor
     *
     * @param value Value to be put in the node
     */
    Node(int value) {
        this(value, null);
    }

    /**
     * Constructor
     *
     * @param value Value to be put in the node
     * @param next  Reference to the next node
     */
    Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
