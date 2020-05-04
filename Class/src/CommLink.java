/*
 * 商品链表
 */
package shopping;

import java.util.ArrayList;

/**
 *
 * @author 69465
 *
 */
public class CommLink {

    CommNode headNode;
    CommNode tailNode;
    int length = 0;

    public CommLink() //链表的构造函数，构造链表时，自动创建一个首结点，该结点数据域为空，且后续不使用该结点。
    {
        headNode = new CommNode();
        tailNode = headNode;
    }

    public Commodity findCommByID(String ID) //通过商品ID在查找特定商品，返回该商品对象的引用，若未找到返回null。 
    {
        CommNode currNode = headNode;
        Commodity resComm = null;
        while (currNode != null) {
            if (currNode.commodity != null) //检测到当前结点数据域为空时不操作，一般只有首结点数据域为空。
            {
                if (currNode.commodity.ID.equals(ID)) {
                    resComm = currNode.commodity;
                    return resComm; //ID唯一，所以找到时立刻返回。
                }
            }
            currNode = currNode.nextNode;
        }
        return resComm;
    }

    public ArrayList<Commodity> findCommByName(String ID) //通过商品名在 商品链表 中查找商品，返回商品对象的引用的数组。（不同商品名称可能相同，因此返回数组。）
    {
        CommNode currNode = headNode;
        ArrayList<Commodity> resComm = null;
        while (currNode != null) {
            if (currNode.commodity != null) {
                if (currNode.commodity.ID.equals(ID)) {
                    resComm.add(currNode.commodity);
                }
            }
            currNode = currNode.nextNode;
        }
        return resComm;
    }

    public void add(Commodity commodity) { //添加结点到链表
        CommNode tempNode = new CommNode(commodity);
        tailNode.nextNode = tempNode;
        tailNode = tempNode;
        length++;
    }

    public boolean deleteByID(String ID) //删除商品链表中的特定ID的商品。
    {
        CommNode currNode = headNode;
        CommNode lastNode = currNode; //记录当前结点的上一个结点,用于删除结点时重新连接链表。
        while (currNode != null) {
            if (currNode.commodity != null) {
                if (currNode.commodity.ID.equals(ID)) {
                    lastNode.nextNode = currNode.nextNode; //找到该商品，将该结点从链表断开。
                    length--;
                    return true;
                }
            }
            lastNode = currNode;
            currNode = currNode.nextNode;
        }
        return false;
    }

    public void printData() { //输出链表全部数据，商品会详细输出所有数据。
        CommNode currNode = headNode;
        while (currNode != null) {
            if (currNode.commodity != null) {
                System.out.println(currNode);
            }
            currNode = currNode.nextNode;
        }
    }

    public void printName() { //输出所有商品的名称
        CommNode currNode = headNode;
        System.out.print("目前有以下商品：");
        while (currNode != null) {
            if (currNode.commodity != null) {
                currNode.printName();
                System.out.print(" ");
            }
            currNode = currNode.nextNode;
        }
    }

    //***************内部类 链表结点实现**************//
    class CommNode {

        Commodity commodity; //数据域
        CommNode nextNode;

        public CommNode(Commodity commodity) { //结点的构造函数
            this.commodity = commodity;
            this.nextNode = null;
        }

        public CommNode() {
            this.commodity = null;
            this.nextNode = null;
        }

        @Override
        public String toString() {
            return commodity.toString();
        }

        public void printName() {
            System.out.print(commodity.name);
        }
    }
    //***************内部类 链表实现**************//
}
