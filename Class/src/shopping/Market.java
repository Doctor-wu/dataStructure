package shopping;

import java.io.BufferedReader;

public class Market {

    private Stack posIdStack;
    public Monitor monitor;
    static public CommLink commLink; //全部商品串成的链表
    static public Tree clasTree;
    public TreeNode currTreeNode;
    public int GID = -1;

    Market() {
        this.monitor = new Monitor(this);
        System.out.print("用户名：");
        this.commLink = new CommLink(true); //全部商品串成的链表
        CommLink link = new CommLink();
        this.GID = this.GID + 1;
        Classification classification = new Classification(String.valueOf(this.GID), this.monitor.askForLine(), link);
        this.clasTree = new Tree(classification, null, this);
        this.currTreeNode = this.clasTree.getRoot();
    }

    public void run() {
        this.loadComm(); //从文件读取商品数据，建立商品链表。
        this.loadClas();

        System.out.println("");
        System.out.println("欢迎光临 " + this.clasTree.getRoot().getData().name);
        System.out.println("");
        int currMenu = 0;
        //Boolean flag = true;
        do {
            switch (currMenu) {
                //0主菜单，1主商品链表操作菜单，2分类操作菜单，
                case 0: {
                    this.monitor.clearPrint();
                    this.monitor.renderMessage(this.getMsg());
                    char userOperate = this.monitor.askForOperation();
                    currMenu = this.operate(userOperate);
                    break;
                }
                case 1: {
                    this.monitor.clearPrint();
                    this.monitor.renderMessage(this.commLink.getMsg());
                    char userOperate = this.monitor.askForOperation();
                    currMenu = this.commLink.operate(userOperate);
                    break;
                }
                case 2: {
                    this.monitor.clearPrint();
                    this.monitor.renderMessage(this.clasTree.getMsg());
                    if (clasTree.currTreeNode == clasTree.getRoot()) {
                        System.out.println("\n· 当前所处分类：根分类");
                        System.out.print("· 当前分类包含的子分类：");
                        clasTree.currTreeNode.children.printChildName();
                        System.out.println("");
                    } else {
                        System.out.println("\n· 当前所处分类：" + clasTree.currTreeNode.Data.name);
                        System.out.print("· 当前分类包含的子分类：");
                        clasTree.currTreeNode.children.printChildName();
                        System.out.println("");
                    }

                    char userOperate = this.monitor.askForOperation();
                    currMenu = this.clasTree.operate(userOperate);
                    break;
                }
            }

        } while (currMenu != -1); //-1表示退出系统

//        while (flag) {
//            this.monitor.renderMessage(this.getMsg());
//            char userOperate = this.monitor.askForOperation();
//
//            flag = this.operate(userOperate);
//
//            System.out.println("");
//        }
        System.out.println(this.clasTree.getRoot().getData().name + " 已退出，欢迎下次使用！");
        savaComm(); //将商品链表保存到文件中。

    }

    public Message getMsg() {

        String[] strings = new String[3];
        strings[0] = "退出系统";
        strings[1] = "商品操作";
        strings[2] = "分类操作";
        Operation operation = new Operation(strings);
        return new Message("主菜单", "商城", operation);
    }

    public int operate(char userOperate) {
        switch (Character.toString(userOperate)) {
            case ("0"): {
                return -1; //返回-1，退出系统。
            }
            case ("1"): {
                return 1;
                //return this.commLink.operate(userOperate);
            }
            case ("2"): {
                return 2;
                //return this.currTreeNode.operate(userOperate);
            }
            default: {
                System.out.println("该目录不支持" + userOperate + "操作");
            }
        }
        return 0;//返回0，到主菜单。
    }

    public boolean loadComm() {
        //从CommDATA文件读取数据并建立商品加入链表中。
        //成功读取返回true，若返回false表示文件不存在或者数据为空。

        FileIO sava = new FileIO();
        commLink.currNode = commLink.headNode;
        String loadStr = new String();
        loadStr = sava.getDatafromFile(sava.CommDATA); //从文件读入字符串

        String[] lineStr = loadStr.split("\n");
        //将loadStr按换行符分隔，分隔后得到一个数组，一个元素为一行记录。

        if (lineStr != null && lineStr.length != 0) {
            for (String tempStr : lineStr) {
                String[] data = tempStr.split(" "); //遍历数组，对每个数组用空格进行分隔，分隔后每个元素为相应数据。

                //读出数据，准备建立商品。
                //data.[0]为ID，data.[1]为商品名，data.[2]为商品价格，data.[3]为商品数量。
                int tempID = Integer.valueOf(data[0]);
                if (tempID > Commodity.maxCommID) {
                    Commodity.maxCommID = tempID;
                }
                String tempName = data[1];
                double tempPrice = Double.valueOf(data[2]);
                int tempCount = Integer.valueOf(data[3]);

                Commodity tempComm = new Commodity(tempID, tempName, tempPrice, tempCount); //新建商品加入链表中。
                commLink.add(tempComm);
            }
            return true;
        } else {
            return false;
        }
    }

    public void loadClas() {

        Classification clasShiPin = new Classification("1", "食品");
        Classification clasShouJi = new Classification("2", "手机");
        Classification clasYinLiao = new Classification("3", "饮料");
        Classification clasShuiGuo = new Classification("4", "水果");
        TreeNode.GID = 4;

        clasYinLiao.addComm(this.commLink.findCommByID(2));
        clasYinLiao.addComm(this.commLink.findCommByID(3));
        clasYinLiao.addComm(this.commLink.findCommByID(4));

        clasShuiGuo.addComm(this.commLink.findCommByID(5));
        clasShuiGuo.addComm(this.commLink.findCommByID(6));

        clasShouJi.addComm(this.commLink.findCommByID(7));
        clasShouJi.addComm(this.commLink.findCommByID(8));

        TreeNode treeNodeShiPin = new TreeNode(clasShiPin, clasTree.root, null, 1);
        TreeNode treeNodeShouJi = new TreeNode(clasShouJi, clasTree.root, null, 1);
        TreeNode treeNodeYinLiao = new TreeNode(clasYinLiao, treeNodeShiPin, null, 2);
        TreeNode treeNodeShuiGuo = new TreeNode(clasShuiGuo, treeNodeShiPin, null, 2);

        TreeNodeList rootClid = new TreeNodeList(5);
        rootClid.push(treeNodeShiPin);
        rootClid.push(treeNodeShouJi);
        clasTree.root.children = rootClid;

        TreeNodeList ShiPinClid = new TreeNodeList(5);
        ShiPinClid.push(treeNodeYinLiao);
        ShiPinClid.push(treeNodeShuiGuo);
        treeNodeShiPin.children = ShiPinClid;

    }

    public boolean savaComm() {
        //从商品链表获取数据，写入CommDATA文件中。一行为一条数据，一行内信息用空格分隔。
        //成功保存返回true。

        //data.[0]为ID，data.[1]为商品名，data.[2]为商品价格，data.[3]为商品数量。
        FileIO sava = new FileIO();
        commLink.currNode = commLink.headNode; //currNode表示当前结点，可理解为迭代器。
        String savaStr = new String();

        while (commLink.currNode != null) { //遍历商品链表
            if (commLink.currNode.commodity != null) {
                savaStr += commLink.currNode.toStringNoTitle(); //从商品结点读出数据，构成字符串。
            }
            commLink.currNode = commLink.currNode.nextNode;
        }
        return sava.saveDataToFile(sava.CommDATA, savaStr);  //将字符串保存到文件。
    }
}
