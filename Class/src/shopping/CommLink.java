/*
 * 商品链表
 */
package shopping;

import java.util.*;

/**
 *
 * @author 69465
 *
 */
public class CommLink {

    CommNode headNode;
    CommNode currNode;
    CommNode tailNode;
    int length = 0;

    public CommLink() //链表的构造函数，构造链表时，自动创建一个首结点，该结点数据域为空，且后续不使用该结点。
    {
        headNode = new CommNode();
        tailNode = headNode;
    }

    public Commodity findCommByID(String ID) //通过商品ID在查找特定商品，返回该商品对象的引用，若未找到返回null。 
    {
        currNode = headNode;
        Commodity resComm = null; //返回值
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

//    public CommNode findCommNodeByID(String ID) 
//    //通过商品ID在查找特定商品，返回该商品结点的引用，若未找到返回null。与findCommByID()不同的是，这里返回的是结点而非商品对象。
//    {
//        CommNode currNode = headNode;
//        CommNode resCommNode = null; //返回值
//        while (currNode != null) {
//            if (currNode.commodity != null) //检测到当前结点数据域为空时不操作，一般只有首结点数据域为空。
//            {
//                if (currNode.commodity.ID.equals(ID)) {
//                    resCommNode = currNode;
//                    return resCommNode; //ID唯一，所以找到时立刻返回。
//                }
//            }
//            currNode = currNode.nextNode;
//        }
//        return resCommNode;
//    }
    public CommLink findCommByName(String name) //通过商品名在 商品链表 中查找商品，返回商品的引用的链表。（不同商品名称可能相同，因此返回链表。）
    {
        currNode = headNode;
        CommLink resComm = null;
        while (currNode != null) {
            if (currNode.commodity != null) {
                if (currNode.commodity.ID.equals(name)) {
                    resComm.add(currNode.commodity);
                }
            }
            currNode = currNode.nextNode;
        }
        return resComm;
    }

    public void add(Commodity commodity) { //添加结点到链表

        CommNode tempNode = new CommNode(commodity);
        commodity.toString();
        tailNode.nextNode = tempNode;
        tailNode = tempNode;
        length++;
    }

    public boolean deleteByID(String ID) //删除商品链表中的特定ID的商品。
    {
        currNode = headNode;
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

    public int printData() { //输出链表全部结点 ，商品会详细输出所有数据。
        int count = 0;
        currNode = headNode;
        while (currNode != null) {
            if (currNode.commodity != null) {
                count++;
                System.out.println(currNode);
            }
            currNode = currNode.nextNode;
        }
        if (count == 0) {
            System.out.print("当前没有商品！");
        }
        return count;
    }

    public int printName() { //输出所有商品的名称，返回商品数量。
        int count = 0;
        currNode = headNode;
        while (currNode != null) {
            if (currNode.commodity != null) {
                count++;
                currNode.printName();
                System.out.print(" ");
            }
            currNode = currNode.nextNode;
        }
        if (count == 0) {
            System.out.print("当前没有商品！");
        }
        return count;
    }

    public CommLink createCommLinkByID(String... commID) //用户给定一组商品ID，在该链表中找到这些ID所指的商品引用串成一条链表返回。主要用于创建分类时，分类包含这些ID的商品。
    {
        CommLink resCommLink = new CommLink();
        Commodity tempComm = null;

        for (String i : commID) {
            tempComm = findCommByID(i);
            if (tempComm != null) {
                resCommLink.add(new Commodity(tempComm.ID, tempComm.name, tempComm.price, tempComm.count));
            }
        }
        return resCommLink;
    }

    public int countNode() { //统计链表有多少个结点
        currNode = headNode;
        int res = 0;
        while (currNode != null) {
            if (currNode.commodity != null) {
                res++;
            }
            currNode = currNode.nextNode;
        }
        return res;
    }
}

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

    public String toStringNoTitle() {
        return commodity.toStringNoTitle();
    }

    public void printName() {
        System.out.print(commodity.name);
    }

    public Message getMsg() {
        String[] operations = new String[]{"修改商品名", "修改商品价格", "修改商品数量", "将该商品加入到分类", "查看该商品属于哪些分类", "返回商城"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message(this.commodity.name, "商品", operation);
        return resMsg;
    }

    public Boolean operate(char op) {
        switch (Character.toString(op)) {
            case ("0"): {
                //修改商品名
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("请输入新的商品名：");
                Scanner in = new Scanner(System.in);
                String tempName = in.nextLine();
                this.commodity.changeName(tempName);
            }
            case ("1"): {
                //修改商品价格
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("当前商品价格为：" + this.commodity.price + "请输入新的商品价格：");
                Scanner in = new Scanner(System.in);
                double tempPrice = Double.valueOf(in.nextLine());
                this.commodity.changePrice(tempPrice);
            }
            case ("2"): {
                //修改商品数量
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("当前商品数量为：" + this.commodity.count + "请输入新的商品数量：");
                Scanner in = new Scanner(System.in);
                int tempCount = Integer.valueOf(in.nextLine());
                this.commodity.changeCount(tempCount);
            }
            case ("3"): {
                //将该商品加入到分类
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("请输入欲加入的分类ID：");
                Scanner in = new Scanner(System.in);
                String tempID = in.nextLine();
                //这里要读取用户输入的分类ID，然后从书中找到这个分类结点，操作这个分类结点里的商品链表，将当前商品加入到分类的商品链表中。
                //tempClasNode = findNodeById(tempID);
            }
            case ("4"): {
                //查看该商品属于哪些分类
            }
            case ("5"): {
                //返回上一级
            }
            default: {
                System.out.println("该目录不支持" + op + "操作");
            }
        }
        return true;
    }
}
