/*
 * 商品链表
 */
package shopping;

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
        CommNode currNode = headNode;
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

    public int printData() { //输出链表全部结点 ，商品会详细输出所有数据。
        int count = 0;
        CommNode currNode = headNode;
        while (currNode != null) {
            if (currNode.commodity != null) {
                count++;
                System.out.println(currNode);
            }
            currNode = currNode.nextNode;
        }
        return count;
    }

    public int printName() { //输出所有商品的名称，返回商品数量。
        int count = 0;
        CommNode currNode = headNode;
        while (currNode != null) {
            if (currNode.commodity != null) {
                count++;
                currNode.printName();
                System.out.print(" ");
            }
            currNode = currNode.nextNode;
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
        CommNode currNode = headNode;
        int res = 0;
        while (currNode != null) {
            if (currNode.commodity != null) {
                res++;
            }
            currNode = currNode.nextNode;
        }
        return res;
    }
//    public Boolean operate(char op){
//        if(this.is){
//            // 商城接管
//            switch (Character.toString(op)){
//                case("0"):{
//
//                }
//                case ("1"):{
//      
//                }
//                case ("2"):{
//      
//                }
//                case ("3"):{
//
//                }
//                case ("4"):{
//
//                }
//                default:{
//                    System.out.println("该目录不支持"+op+"操作");
//                }
//            }
//        }
//        return true;
//    }
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
