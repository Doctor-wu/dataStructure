package shopping;

public class Tree {

    public TreeNode root;
    public TreeNode currTreeNode;
    private Monitor monitor;

    Tree(Classification data, TreeNode parent, Market belongMarket) {
        this.root = new TreeNode(data, parent, new TreeNodeList(10), 0, true);
        currTreeNode = root;
        monitor = new Monitor(this);
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNodeList collectNodeListById(int id) {
        TreeNodeList list = new TreeNodeList(5);
        this.root.collectNodeByCommId(list, id);
        return list;
    }

    public void showCurrClasNode() {
        if (currTreeNode == root) {
            System.out.println("当前在根目录");
        } else {
            System.out.println("当前所处分类信息：");
            currTreeNode.printData();
        }

    }

    public Message getMsg() {
        String[] operations = new String[]{"返回主菜单", "列出子分类", "切换到一个子分类", "切换到父分类", "新增子分类", "删除一个子分类", "列出该分类下所有商品", "加入商品到该分类", "移除分类中的商品"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message("分类操作菜单", "分类树结点", operation);
        return resMsg;
    }

    public int operate(char op) {
        switch (Character.toString(op)) {
            case ("0"): {
                //返回主菜单
                return 0;
            }
            case ("1"): {
                //列出子分类
                System.out.println("");
                this.showCurrClasNode();
                System.out.println("\n当前分类包含以下子分类：");
                this.monitor.renderDivider();
                currTreeNode.listChildren();

                char ope = '1'; //操作符，用于判断用户想干嘛。
                System.out.println("\n请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                ope = this.monitor.askForOperation();
                switch (ope) {
                    case '0':
                        return 0;
                    case '1':
                        return 2;
                    default:
                        return 2;
                }
            }
            case ("2"): {
                //切换到一个子分类
                System.out.println("");
                this.showCurrClasNode();

                System.out.println("\n当前分类下包含以下子分类：");
                this.monitor.renderDivider();

                boolean flag = currTreeNode.listChildren();
                if (flag == true) { //flag用于判断当前分类下有没有子分类。若没有子分类，提示没有子分类并返回到上一级。

                    System.out.print("\n请输入子分类ID：");
                    String tempClasID = this.monitor.askForLine();
                    TreeNode tempClasNode = currTreeNode.findClasNodeByClasId(tempClasID);
                    if (tempClasNode != null) { //判断用户输入的子分类是否存在，若存在则切换到该子分类，若不存在则提示并返回上一级。
                        System.out.println("已切换到子分类 " + tempClasNode.Data.name);
                        this.currTreeNode = tempClasNode;
                        return 2;
                    } else {
                        System.out.println("没有找到该子分类！");
                        return 2;
                    }
                } else {
                    return 2;
                }
            }
            case ("3"): {
                //切换到父分类
                System.out.println("");
                this.showCurrClasNode();
                if (currTreeNode != root) {
                    currTreeNode = currTreeNode.parent;
                    System.out.println("已切换到父分类！");
                    return 2;
                } else {
                    System.out.println("当前已经是根分类！");
                    return 2; //返回2即表示进入分类操作界面
                }
            }
            case ("4"): {
                //新增子分类
                CommLink link = new CommLink();
                System.out.print("请输入分类名称：");
                String tempClasName = this.monitor.askForLine();
                TreeNode.GID++;

                Classification classification = new Classification(String.valueOf(TreeNode.GID), tempClasName, link);
                TreeNode node = new TreeNode(classification, currTreeNode, currTreeNode.getChildren(), currTreeNode.level + 1);
                this.currTreeNode.children.push(node);

                System.out.print("添加成功!");

                char ope = '1'; //操作符，用于判断用户想干嘛。
                System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                ope = this.monitor.askForOperation();
                switch (ope) {
                    case '0':
                        return 0;
                    case '1':
                        return 2;
                    default:
                        return 2;
                }
            }
            case ("5"): {
                //删除一个子分类
                System.out.println("\n当前分类下包含以下子分类：");
                this.monitor.renderDivider();

                boolean flag = currTreeNode.listChildren();
                if (flag == true) { //flag用于判断当前分类下有没有子分类。若没有子分类，提示没有子分类并返回到上一级。

                    System.out.print("\n请输入想要删除的子分类ID：");
                    String tempClasID = this.monitor.askForLine();
                    TreeNode tempDelClasNode = currTreeNode.findClasNodeByClasId(tempClasID); //想要删除的分类

                    if (tempDelClasNode != null) { //判断用户输入的子分类是否存在，若存在则切换到该子分类，若不存在则提示并返回上一级。

                        char ope = '1';
                        System.out.println("\n已找到子分类：");
                        tempDelClasNode.printData();
                        this.monitor.renderDivider();
                        System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：确认删除\n【2】：返回上一级");
                        ope = this.monitor.askForOperation();
                        switch (ope) {
                            case '0':
                                return 0; //返回主菜单
                            case '1': //确认删除
                                TreeNode tempDelParentClasNode = tempDelClasNode.parent; //想要删除的分类的父分类

                                for (int i = 0; i < tempDelClasNode.children.length; i++) {
                                    //将要删除的分类的子分类移到父分类下.
                                    tempDelClasNode.children.value[i].parent = tempDelParentClasNode;
                                    tempDelParentClasNode.children.push(tempDelClasNode.children.value[i]);
                                }

                                tempDelParentClasNode.children.deleteById(tempDelClasNode.Data.ID); //将要删除的分类从它的父分类中移除。
                                tempDelClasNode.parent = null;

                                System.out.println(tempDelClasNode.Data.name + " 删除成功!");

                                ope = '1'; //操作符，用于判断用户想干嘛。
                                System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                                ope = this.monitor.askForOperation();
                                switch (ope) {
                                    case '0':
                                        return 0;
                                    case '1':
                                        return 2;
                                    default:
                                        return 2;
                                }
                            case '2': //返回上一级
                                return 2;
                            default:
                                return 2;
                        }
                    } else {
                        System.out.println("没有找到该子分类！");
                        return 2;
                    }
                } else {
                    return 2;
                }
            }
            case ("6"): {
                //列出该分类下所有商品
                System.out.println("\n当前分类包含以下商品：");
                currTreeNode.printComm();
                System.out.println("");
                this.monitor.renderDivider();
                char ope = '1'; //操作符，用于判断用户想干嘛。
                System.out.println("\n请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                ope = this.monitor.askForOperation();
                switch (ope) {
                    case '0':
                        return 0;
                    case '1':
                        return 2;
                    default:
                        return 2;
                }

            }
            case ("7"): {
                //加入商品到该分类
                System.out.print("请输入商品ID：");
                String tempCommID = this.monitor.askForLine();
                Commodity tempComm = Market.commLink.findCommByID(Integer.parseInt(tempCommID));
                if (tempComm != null) { //在商品链表中找到该商品，说明商品已经创建。
                    if (this.currTreeNode.Data.getCommByID(Integer.parseInt(tempCommID)) == null) { //查询该商品是否已经在该分类中。
                        this.currTreeNode.Data.addComm(tempComm);
                        System.out.println("\n被加入的商品信息：");
                        System.out.println(tempComm.toString());
                        System.out.println("\n商品添加成功！");
                    } else {
                        System.out.println("该商品已在该分类中！");
                    }

                } else {//在商品链表中没找到该商品，提示用户创建商品后再加入分类。
                    System.out.println("商品列表中没有找到该商品，请先在商品操作中添加该商品再加入分类！");
                }

                char ope = '1'; //操作符，用于判断用户想干嘛。
                System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                ope = this.monitor.askForOperation();
                switch (ope) {
                    case '0':
                        return 0;
                    case '1':
                        return 2;
                    default:
                        return 2;
                }
            }
            case ("8"): {
                //移除分类中的商品
                System.out.println("\n当前分类包含以下商品：");
                currTreeNode.printComm();
                this.monitor.renderDivider();
                System.out.print("请输入想要移除的商品ID：");
                String tempCommID = this.monitor.askForLine();
                Commodity tempComm = this.currTreeNode.Data.getCommByID(Integer.parseInt(tempCommID));
                if (tempComm != null) {
                    System.out.println(tempComm.name + " 移除成功！");
                    this.currTreeNode.Data.deleteCommByID(Integer.parseInt(tempCommID));

                } else {
                    System.out.println("在当前分类下没有找到该商品！");
                }

                char ope = '1'; //操作符，用于判断用户想干嘛。
                System.out.println("请执行操作：\n【0】：返回主菜单 \n【1】：返回上一级");
                ope = this.monitor.askForOperation();
                switch (ope) {
                    case '0':
                        return 0;
                    case '1':
                        return 2;
                }
            }
        }
        return 0;
    }
}

class TreeNode {

    static int GID;
    public Classification Data;
    public int level;
    public TreeNode root;
    public TreeNode parent;
    public TreeNodeList siblings;
    public TreeNodeList children;
    public Monitor monitor;
    private Boolean isMarket;

    //private Market belongMarket;
    TreeNode(Classification data, TreeNode parent, TreeNodeList siblings, int level) {
        this.root = this;
        this.Data = data;
        this.parent = parent;
        this.siblings = siblings;
        this.level = level;
        this.children = new TreeNodeList(5);
        this.isMarket = false;
        monitor = new Monitor(this);
    }

    ;

    TreeNode(Classification data, TreeNode parent, TreeNodeList siblings, int level, Boolean isMarket) {
        this.root = this;
        this.Data = data;
        this.parent = parent;
        this.siblings = siblings;
        this.level = level;
        this.children = new TreeNodeList(5);
        this.isMarket = isMarket;
        monitor = new Monitor(this);
    }

    ;
    // 树操作
    public Boolean insert(TreeNode node) {// 插入子节点
        try {
            this.children.push(node);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean delete(TreeNode node) {// 删除子节点
        try {
            this.children.deleteById(node.getData().ID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void printComm() { //打印该节点所有包含商品的详细信息
        this.Data.commodity.printData();
    }

    public void printName() {
        System.out.print(this.Data.name);
    }

    public TreeNode findClasNodeByClasId(String id) {
        if (this.Data.ID.equals(id)) {
            return this;
        } else if (this.children.length != 0) {
            for (int i = 0; i < this.children.length; i++) {
                TreeNode node = this.children.getValue()[i].findClasNodeByClasId(id);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }

    public TreeNode findClasNodeByName(String name) {
        if (this.Data.name.equals(name)) {
            return this;
        } else if (this.children.length != 0) {
            for (int i = 0; i < this.children.length; i++) {
                if (this.children.getValue()[i].findClasNodeByName(name) != null) {
                    return this.children.getValue()[i];
                }
            }
        }
        return null;
    }

    public void collectNodeByCommId(TreeNodeList list, int CommID) {
        if (this.Data.getCommByID(CommID) != null) {
            list.push(this);
        }
        if (this.children.length != 0) {
            for (int i = 0; i < this.children.length; i++) {
                this.children.getValue()[i].collectNodeByCommId(list, CommID);
            }
        }
    }

    public Boolean listChildren() {
        if (this.children.length != 0) {
            for (int i = 0; i < this.children.length; i++) {
                this.children.getValue()[i].getData().printData();

                this.monitor.renderDivider();
            }
            return true;
        } else {
            System.out.println("该分类暂无子分类");
            return false;
        }
    }

    public void printData() {
        this.monitor.renderDivider();
        Data.printData();
        System.out.print("分类包含以下子分类：");
        this.children.printChildName();
        System.out.println("");
    }

    public Message getMsg() {
        String[] operations = new String[]{"返回上一级", "列出子分类", "新增子分类", "删除一个子分类", "列出该分类下所有商品", "加入商品到该分类", "移除分类中的商品"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message("分类操作菜单", "分类树结点", operation);
        return resMsg;
    }

//    public int operate(char op) {
//        switch (Character.toString(op)) {
//            case ("0"): {
//                //返回上一级
//                return 0;
//            }
//            case ("1"): {
//                //列出子分类
//                this.children.printData();
//                break;
//            }
//            case ("2"): {
//                //新增子分类
//                CommLink link = new CommLink();
//                System.out.print("请输入分类名称：");
//                String tempClasName = this.monitor.askForLine();
//                TreeNode.GID++;
//
//                Classification classification = new Classification(String.valueOf(TreeNode.GID), tempClasName, link);
//                TreeNode node = new TreeNode(classification, this, parent.getChildren(), this.level + 1);
//                this.children.push(node);
//
//                System.out.print("添加成功!");
//                break;
//            }
//            case ("3"): {
//                //删除一个子分类
//
//            }
//            case ("4"): {
//                //列出该分类下所有商品
//
//            }
//            case ("5"): {
//                //新增子分类
//
//            }
//        }
//        return 0;
//    }

    // 数据操作
//    public Message getMsg(){
//        if(this.isMarket){
//            String[] strings = new String[6];
//            strings[0] = "退出系统";
//            strings[1] = "列出分类";
//            strings[2] = "查找商品";
//            strings[3] = "列出商品";
//            strings[4] = "添加分类";
//            strings[5] = "删除分类";
//            Operation operation = new Operation(strings);
//            return new Message(this.Data.name,"商城",operation);
//        }
//        return this.Data.getMsg();
//    }
//    public Boolean operate(char op){
//        if(this.isMarket){
//            // 商城接管
//            switch (Character.toString(op)){
//                case("0"):{
//                    return false;
//                }
//                case ("1"):{
//                    if(this.listChildren()){
//                        System.out.println("");
//                        System.out.print("输入分类ID跳转，输入0返回菜单：");
//                        String ope = this.monitor.askForLine();
//                        if(!ope.equals("0")){
//                            TreeNode node = this.findNodeById(ope);
//                            if(node!=null){
//                                this.belongMarket.curNode = node;
//                                this.belongMarket.monitor.location.push(this.belongMarket.curNode.getData().name);
//                            }else{
//                                System.out.print(String.format("未找到ID为%s的分类",ope));
//                            }
//                        }
//                    }
//                    break;
//                }
//                case ("2"):{
//                    System.out.print("请问要以何种方式搜索商品：【0】：ID 【1】：名称");
//                    System.out.println("");
//                    char ope = this.belongMarket.monitor.askForOperation();
//                    if(Character.toString(ope).equals("0")){
//                        System.out.print("请输入商品ID：");
//                        Commodity comm = this.belongMarket.commLink.findCommByID(this.belongMarket.monitor.askForLine());
//                        if(comm!=null){
//                            CommLink link = new CommLink();
//                            link.add(comm);
//                            link.printData();
//                        }else{
//                            System.out.println("未查找到商品");
//                        }
//                    }else if(Character.toString(ope).equals("1")){
//                        System.out.print("请输入商品名称：");
//                        CommLink link = this.belongMarket.commLink.findCommByName(this.belongMarket.monitor.askForLine());
//                        if(link!=null){
//                            link.printData();
//                        }else{
//                            System.out.println("未查找到商品");
//                        }
//                    }else{
//                        System.out.println("查找失败，未知命令: "+ope);
//                    }
//                    break;
//                }
//                case ("3"):{
//                    if(this.belongMarket.commLink.printData() == 0){
//                        System.out.println("该商城下无商品");
//                    }
//                    
//                    break;
//                }
//                case ("4"):{
//                    CommLink link  = new CommLink();
//                    System.out.print("请输入分类名称：");
//                    this.belongMarket.GID++;
//                    Classification classification = new Classification(String.valueOf(this.belongMarket.GID),this.belongMarket.monitor.askForLine(),link);
//                    TreeNode node = new TreeNode(classification,this,this.getChildren(),this.level+1,this.belongMarket);
//                    this.children.push(node);
//                    this.belongMarket.monitor.location.push(node.getData().name);
//                    this.belongMarket.curNode = node;
//                    System.out.print("添加成功!");
//                    break;
//                }
//                case ("5"):{
//                    System.out.print("请输入分类ID：");
//                    String id = this.belongMarket.monitor.askForLine();
////                    if(node!=null){
////                        this.children.deleteById(id);
////                    }else{
////                        System.out.println("删除失败，未查找到id为"+id+"的子分类");
////                    }
//                    this.children.deleteById(id);
//                    break;
//                }
//                default:{
//                    System.out.println("该目录不支持"+op+"操作");
//                }
//            }
//        }else if(this.belongMarket.curNode.getMsg().type.equals("分类")){
//            // 委托给分类
//            switch (Character.toString(op)){
//                case "0":{
//                    this.belongMarket.curNode = this.parent;
//                    this.belongMarket.monitor.location.pop();
//                    break;
//                }
//                case "1":{
//                    System.out.print("请输入分类名：");
//                    this.getData().changeName(this.belongMarket.monitor.askForLine());
//                    this.belongMarket.monitor.location.pop();
//                    this.belongMarket.monitor.location.push(this.getData().name);
//                    break;
//                }
//                case "2":{
//                    this.getData().commodity.printName();
//                    break;
//                }
//                case "3":{
//                    if(this.getData().commodity.printData() == 0){
//                        System.out.println("该分类下无商品");
//                    }
//                    break;
//                }
//                case "4":{
//                    System.out.print("请输入商品名：");
//                    String name = this.belongMarket.monitor.askForLine();
//                    System.out.print("请输入商品价格：");
//                    String price = this.belongMarket.monitor.askForLine();
//                    System.out.print("请输入商品数量：");
//                    String count = this.belongMarket.monitor.askForLine();
//                    Commodity comm = new Commodity(String.valueOf(++this.belongMarket.GID),name,Double.parseDouble(price),Integer.parseInt(count));
//                    this.belongMarket.commLink.add(comm);
//                    this.getData().addComm(comm);
//                    System.out.println("添加成功");
//                    break;
//                }
//                case "5":{
//                    System.out.print("请输入想删除商品的ID：");
//                    this.getData().deleteCommByID(this.belongMarket.monitor.askForLine());
//                }
//                case "6":{
//                    System.out.println(String.format("该分类下共有%d种商品",this.getData().countComm()));
//                    break;
//                }
//                default:{
//                    System.out.println("该目录不支持"+op+"操作");
//                }
//            }
//        }
//        return true;
//    }
    // 基础方法
    public Classification getData() {
        return Data;
    }

    public int getLevel() {
        return level;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNodeList getChildren() {
        return this.children;
    }

    public TreeNodeList getSiblings() {
        return siblings;
    }

    public void setChildren(TreeNodeList children) {
        this.children = children;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void setSiblings(TreeNodeList siblings) {
        this.siblings = siblings;
    }

    @Override
    public String toString() {
        return this.Data.toString();
    }
}

class TreeNodeList { //动态节点数组，当实际长度接近容量时，自动扩容。

    public TreeNode[] value;
    public int length; //实际长度
    public int size; //容量

    TreeNodeList(int size) {
        this.value = new TreeNode[size];
        this.length = 0;
        this.size = size;
    }

    public TreeNode[] getValue() {
        return value;
    }

    public int push(TreeNode node) {
        this.value[length] = node;
        this.length++;
        this.realloc();
        return this.length;
    }

    private void realloc() {
        if (3 * this.length > this.size * 2) {
            TreeNode[] spare = new TreeNode[2 * this.size];
            for (int i = 0; i < this.length; i++) {
                spare[i] = this.value[i];
            }
            this.value = spare;
            this.size = 2 * this.size;
            System.out.println(String.format("树节点数组已重新分配内存,目前大小:%d", this.size));
        }
    }

    public TreeNode deleteById(String id) {
        TreeNode deleteItem = null;
        TreeNode[] spare = new TreeNode[this.size];
        int curry = 0;

        for (int i = 0; i < this.length; i++) {
            if (!this.value[i].getData().ID.equals(id)) {
                spare[i + curry] = this.value[i];
            } else {
                deleteItem = this.value[i];
                curry = -1;
            }
        }
        this.value = spare;
        if (curry != 0) {
            this.length--;
            System.out.println(String.format("已删除ID为%s的子分类", deleteItem.getData().ID));
        } else {
            System.out.println(String.format("删除失败，未找到id为%s的子分类", id));
        }
        return deleteItem;
    }

    public void printChildName() {
        if (this.length != 0) {
            for (int i = 0; i < this.length; i++) {
                value[i].printName();
                System.out.print(" ");
            }
        } else {
            System.out.println("当前分类没有子分类！");
        }
    }

    public void printData() {
        if (this.length != 0) {
            for (int i = 0; i < this.length; i++) {
                value[i].printData();
                this.printChildName();
            }
        } else {
            System.out.println("当前数组没有数据！");
        }
    }
}
