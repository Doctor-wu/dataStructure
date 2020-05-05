package shopping;

import java.io.BufferedReader;

public class Market {

    private Stack posIdStack;
    public Monitor monitor;
    public CommLink commodities; //全部商品串成的链表
    public Tree commodityTree;
    public TreeNode curNode;
    public int GID = -1;

    Market() {
        this.monitor = new Monitor(this);
        System.out.print("请输入商场名称：");
        this.commodities = new CommLink(); //全部商品串成的链表
        CommLink link = new CommLink();
        this.GID = this.GID + 1;
        Classification classification = new Classification(String.valueOf(this.GID), this.monitor.askForLine(), link);
        this.commodityTree = new Tree(classification, null, this);
        this.curNode = this.commodityTree.getRoot();
    }

    public void run() {
//        loadComm(); //从文件读取商品数据，建立商品链表。
        System.out.println("");
        System.out.println("欢迎光临 " + this.commodityTree.getRoot().getData().name);
        System.out.println("");
        Boolean flag = true;
        while (flag) {
            this.monitor.renderMessage(this.curNode.getMsg());
            char userOperate = this.monitor.askForOperation();
            flag = this.curNode.operate(userOperate);
            System.out.println("");
        }
        System.out.println(this.commodityTree.getRoot().getData().name + " is closed,Welcome for next time!");
//        savaComm(); //将商品链表保存到文件中。

    }

    public boolean loadComm() {
        //从CommDATA文件读取数据并建立商品加入链表中。
        //成功读取返回true，若返回false表示文件不存在或者数据为空。

        FileIO sava = new FileIO();
        commodities.currNode = commodities.headNode;
        String loadStr = new String();
        loadStr = sava.getDatafromFile(sava.CommDATA); //从文件读入字符串

        String[] lineStr = loadStr.split("\n");
        //将loadStr按换行符分隔，分隔后得到一个数组，一个元素为一行记录。

        if (lineStr != null && lineStr.length != 0) {
            for (String tempStr : lineStr) {
                String[] data = tempStr.split(" "); //遍历数组，对每个数组用空格进行分隔，分隔后每个元素为相应数据。

                //读出数据，准备建立商品。
                //data.[0]为ID，data.[1]为商品名，data.[2]为商品价格，data.[3]为商品数量。
                String tempID = data[0];
                String tempName = data[1];
                double tempPrice = Double.valueOf(data[2]);
                int tempCount = Integer.valueOf(data[3]);

                Commodity tempComm = new Commodity(tempID, tempName, tempPrice, tempCount); //新建商品加入链表中。
                commodities.add(tempComm);
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean savaComm() {
        //从商品链表获取数据，写入CommDATA文件中。一行为一条数据，一行内信息用空格分隔。
        //成功保存返回true。

        //data.[0]为ID，data.[1]为商品名，data.[2]为商品价格，data.[3]为商品数量。
        FileIO sava = new FileIO();
        commodities.currNode = commodities.headNode; //currNode表示当前结点，可理解为迭代器。
        String savaStr = new String();

        while (commodities.currNode != null) { //遍历商品链表
            if (commodities.currNode.commodity != null) {
                savaStr += commodities.currNode.toStringNoTitle(); //从商品结点读出数据，构成字符串。
            }
            commodities.currNode = commodities.currNode.nextNode;
        }
        return sava.saveDataToFile(sava.CommDATA, savaStr);  //将字符串保存到文件。
    }
}
