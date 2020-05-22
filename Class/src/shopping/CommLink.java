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

    public CommNode headNode;
    public CommNode currNode;
    public CommNode tailNode;
    boolean isAll = false; //是否为全部商品的链表，若真则表示这条链表是包含全部商品的主链表。若假则表示这条链表是分类下的商品链表。
    Monitor monitor; //用于交互
    int length = 0;

    public CommLink() //链表的构造函数，构造链表时，自动创建一个首结点，该结点数据域为空，且后续不使用该结点。
    {
        this.isAll = false;
        headNode = new CommNode();
        tailNode = headNode;
        monitor = new Monitor(this);
    }

    public CommLink(boolean isAll) //链表的构造函数，构造链表时，自动创建一个首结点，该结点数据域为空，且后续不使用该结点。
    {
        this.isAll = isAll;
        headNode = new CommNode();
        tailNode = headNode;
        monitor = new Monitor(this);
    }

    public Commodity findCommByID(int ID) //通过商品ID在查找特定商品，返回该商品对象的引用，若未找到返回null。 
    {
        currNode = headNode;
        Commodity resComm = null; //返回值
        while (currNode != null) {
            if (currNode.commodity != null) //检测到当前结点数据域为空时不操作，一般只有首结点数据域为空。
            {
                if (currNode.commodity.ID == ID) {
                    resComm = currNode.commodity;
                    return resComm; //ID唯一，所以找到时立刻返回。
                }
            }
            currNode = currNode.nextNode;
        }
        return resComm;
    }

    CommNode findCommNodeByID(int ID) //通过商品ID在查找特定商品，返回该商品结点的引用，若未找到返回null。与findCommByID()不同的是，这里返回的是结点而非商品对象。
    {
        currNode = headNode;
        CommNode resCommNode = null; //返回值
        while (currNode != null) {
            if (currNode.commodity != null) //检测到当前结点数据域为空时不操作，一般只有首结点数据域为空。
            {
                if (currNode.commodity.ID == ID) {
                    resCommNode = currNode;
                    return resCommNode; //ID唯一，所以找到时立刻返回。
                }
            }
            currNode = currNode.nextNode;
        }
        return resCommNode;
    }

    public CommLink findCommByName(String name) //通过商品名在 商品链表 中查找商品，返回商品的引用的链表。（不同商品名称可能相同，因此返回链表。）
    {
        currNode = headNode;
        CommLink resComm = null;
        while (currNode != null) {
            if (currNode.commodity != null) {
                if (currNode.commodity.name.equals(name)) {
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

    public boolean deleteByID(int ID) //删除商品链表中的特定ID的商品。
    {
        currNode = headNode;
        CommNode lastNode = currNode; //记录当前结点的上一个结点,用于删除结点时重新连接链表。
        while (currNode != null) {
            if (currNode.commodity != null) {
                if (currNode.commodity.ID == ID) {
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
                this.monitor.renderDivider();
                currNode.printData();
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

    public CommLink createCommLinkByID(int... commID) //用户给定一组商品ID，在该链表中找到这些ID所指的商品引用串成一条链表返回。主要用于创建分类时，让分类包含这些ID的商品。
    {
        CommLink resCommLink = new CommLink();
        Commodity tempComm = null;

        for (int i : commID) {
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

    public Message getMsg() {
        String[] operations = new String[]{"返回上级", "通过ID查找商品", "列出全部商品", "新增商品", "删除商品"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message("商品操作菜单", "商品链表", operation);
        return resMsg;
    }

    public int operate(char op) {
        if (isAll = true) {
            switch (Character.toString(op)) {
                case ("0"): {
                    //返回主菜单
                    return 0;
                }
                case ("1"): {
                    //通过ID查找商品
                    char ope = '1'; //操作符，用于判断用户想干嘛。
                    do {
                        //this.monitor.clearPrint();
                        this.monitor.location.push("通过ID查找商品");
                        System.out.print("请输入你要查找的商品ID：");
                        int tempID = Integer.valueOf(monitor.askForLine());
                        CommNode tempCommNode = this.findCommNodeByID(tempID);
                        if (tempCommNode != null) {
                            this.monitor.renderDivider();
                            System.out.println("已找到商品，商品信息为：");
                            tempCommNode.printData();
                        } else {
                            System.out.println("未找到该商品！");
                        }
                        System.out.println("\n是否要继续查找？\n【0】：返回主菜单 \n【1】：继续查找\n【2】：返回上一级");
                        ope = this.monitor.askForOperation();
                    } while (ope == '1'); //1表示继续查找，则一再在这循环
                    switch (ope) {
                        case '0':
                            return 0;
                        case '2':
                            return 1; //向Market返回1则表示进入商品主链表操作菜单
                        default:
                            return 1;
                    }
                }
                case ("2"): {
                    //列出全部商品
                    char ope = '1'; //操作符，用于判断用户想干嘛。
                    this.monitor.clearPrint();
                    System.out.println("正在列出全部商品：");
                    this.printData();
                    this.monitor.renderDivider();
                    System.out.println("共有" + this.countNode() + "个商品。");
                    this.monitor.renderDivider();
                    System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                    ope = this.monitor.askForOperation();
                    switch (ope) {
                        case '0':
                            return 0;
                        case '1':
                            return 1; //向Market返回1则表示进入商品主链表操作菜单
                        default:
                            return 1;
                    }
                }
                case ("3"): {
                    //新增商品
                    char ope = '1'; //操作符，用于判断用户想干嘛。
                    //this.monitor.clearPrint();

                    System.out.println("\n请输入新增商品名：");
                    String tempCommName = this.monitor.askForLine();
                    System.out.println("请输入新增商品价格：");
                    Double tempCommPrice = Double.valueOf(this.monitor.askForLine());
                    System.out.println("请输入新增商品数量：");
                    int tempCommCount = Integer.valueOf(this.monitor.askForLine());

                    Commodity tempComm = new Commodity(tempCommName, tempCommPrice, tempCommCount);
                    Market.commLink.add(tempComm);
                    this.monitor.renderDivider();
                    System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                    ope = this.monitor.askForOperation();
                    switch (ope) {
                        case '0':
                            return 0;
                        case '1':
                            return 1; //向Market返回1则表示进入商品主链表操作菜单
                        default:
                            return 1;
                    }
                }
                case ("4"): {
                    //删除商品
                    char ope = '1'; //操作符，用于判断用户想干嘛。
                    //this.monitor.clearPrint();
                    System.out.print("\n请输入欲删除的商品ID：");
                    int tempCommID = Integer.valueOf(this.monitor.askForLine());

                    Commodity tempComm = Market.commLink.findCommByID(tempCommID);
                    if (tempComm != null) {
                        System.out.println("\n已找到商品：");
                        System.out.println(tempComm.toString());
                        this.monitor.renderDivider();
                        System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：确认删除\n【2】：返回上一级");
                        ope = this.monitor.askForOperation();
                        switch (ope) {
                            case '0':
                                return 0;
                            case '1':
                                Market.commLink.deleteByID(tempCommID);
                                return 1; //向Market返回1则表示进入商品主链表操作菜单
                            case '2':
                                return 1;
                            default:
                                return 1;
                        }
                    } else {
                        System.out.println("没有找到该商品。");
                        this.monitor.renderDivider();
                        System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                        ope = this.monitor.askForOperation();
                        switch (ope) {
                            case '0':
                                return 0;
                            case '1':
                                return 1; //向Market返回1则表示进入商品主链表操作菜单
                            default:
                                return 1;
                        }
                    }
                }
                default: {
                    System.out.println("该目录不支持" + op + "操作");

                }
            }
        } else {
            switch (Character.toString(op)) {
                case ("0"): {
                    //返回上一级
                }
                case ("1"): {
                    //通过ID查找商品
                    System.out.println("请输入你要查找的商品ID：");
                    int tempCommID = Integer.valueOf(this.monitor.askForLine());
                    Commodity tempCom = this.findCommByID(tempCommID);
                    if (tempCom != null) {

                    } else {
                        System.out.println("未找到该商品！");
                    }
                }
                case ("2"): {
                    //列出全部商品

                }
                default: {
                    System.out.println("该目录不支持" + op + "操作");
                }
            }
        }
        return 0;
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

    public void printData() {
        System.out.print(this);
    }

    public Message getMsg() {
        String[] operations = new String[]{"返回上级", "修改商品名", "修改商品价格", "修改商品数量", "将该商品加入到分类", "查看该商品属于哪些分类"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message(this.commodity.name, "商品", operation);
        return resMsg;
    }

    public Boolean operate(char op) {
        switch (Character.toString(op)) {
            case ("0"): {
                //返回上一级
            }
            case ("1"): {
                //修改商品名
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("请输入新的商品名：");
                Scanner in = new Scanner(System.in);
                String tempName = in.nextLine();
                this.commodity.changeName(tempName);
            }
            case ("2"): {
                //修改商品价格
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("当前商品价格为：" + this.commodity.price + "请输入新的商品价格：");
                Scanner in = new Scanner(System.in);
                double tempPrice = Double.valueOf(in.nextLine());
                this.commodity.changePrice(tempPrice);
            }
            case ("3"): {
                //修改商品数量
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("当前商品数量为：" + this.commodity.count + "请输入新的商品数量：");
                Scanner in = new Scanner(System.in);
                int tempCount = Integer.valueOf(in.nextLine());
                this.commodity.changeCount(tempCount);
            }
            case ("4"): {
                //将该商品加入到分类
                System.out.println("当前商品ID为：" + this.commodity.ID + " 当前商品名为：" + this.commodity.name);
                System.out.println("请输入欲加入的分类ID：");
                Scanner in = new Scanner(System.in);
                String tempID = in.nextLine();
                //这里要读取用户输入的分类ID，然后从书中找到这个分类结点，操作这个分类结点里的商品链表，将当前商品加入到分类的商品链表中。
                //tempClasNode = findNodeById(tempID);
            }
            case ("5"): {
                //查看该商品属于哪些分类
                //findCommByID(this.CommID)
            }
            default: {
                System.out.println("该目录不支持" + op + "操作");
            }
        }
        return true;
    }
}
