package shopping;

import java.util.Scanner;

public class Tree {
    private TreeNode root;

    Tree(Classification data, TreeNode parent, Market belongMarket) {
        this.root = new TreeNode(data, parent, new TreeNodeList(10), 0,true,belongMarket);
    }

    public TreeNode getRoot() {
        return root;
    }
    public TreeNodeList collectNodeListById(String id){
        TreeNodeList list = new TreeNodeList(5);
        this.root.collectNodeById(list, id);
        return list;
    }
}

class TreeNode {
    private Classification Data;
    private int level;
    private TreeNode root;
    private TreeNodeList siblings;
    private TreeNodeList children;
    private TreeNode parent;
    private Boolean isMarket;
    private Market belongMarket;
    TreeNode(Classification data,TreeNode parent, TreeNodeList siblings,int level, Market belongMarket){
        this.root = this;
        this.Data = data;
        this.parent = parent;
        this.siblings = siblings;
        this.level = level;
        this.children = new TreeNodeList(5);
        this.isMarket = false;
        this.belongMarket = belongMarket;
    };

    TreeNode(Classification data,TreeNode parent, TreeNodeList siblings,int level, Boolean isMarket, Market belongMarket){
        this.root = this;
        this.Data = data;
        this.parent = parent;
        this.siblings = siblings;
        this.level = level;
        this.children = new TreeNodeList(5);
        this.isMarket = isMarket;
        this.belongMarket = belongMarket;
    };
    // 树操作
    public Boolean insert(TreeNode node){// 插入子节点
        try {
            this.children.push(node);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public Boolean delete(TreeNode node){// 删除子节点
        try{
            this.children.deleteById(node.getData().ID);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public TreeNode findNodeById(String id){
        if(this.Data.ID.equals(id)) return this;
        else if(this.children.length!=0){
            for (int i=0;i<this.children.length;i++) {
                TreeNode node = this.children.getValue()[i].findNodeById(id);
                if( node!=null){
                    return node;
                }
            }
        }
        return null;
    }

    public TreeNode findNodeByName(String name){
        if(this.Data.name.equals(name)) return this;
        else if(this.children.length!=0){
            for (int i=0;i<this.children.length;i++) {
                if( this.children.getValue()[i].findNodeByName(name)!=null){
                    return this.children.getValue()[i];
                }
            }
        }
        return null;
    }


    public void collectNodeById(TreeNodeList list,String id){
        if(this.Data.getCommByID(id)!=null) list.push(this);
        if(this.children.length!=0){
            for (int i=0;i<this.children.length;i++) {
                this.children.getValue()[i].collectNodeById(list, id);
            }
        }
    }

    public Boolean listChildren(){
        if(this.children.length!=0){
            for (int i = 0; i <this.children.length ; i++) {
                this.children.getValue()[i].getData().printData();
                System.out.println("");
                System.out.println("");
            }
            return true;
        }else{
            System.out.println("该分类暂无子分类");
            return false;
        }
    }


    // 数据操作
    public Message getMsg(){
        if(this.isMarket){
            String[] strings = new String[6];
            strings[0] = "退出系统";
            strings[1] = "列出分类";
            strings[2] = "查找商品";
            strings[3] = "列出商品";
            strings[4] = "添加分类";
            strings[5] = "删除分类";
            Operation operation = new Operation(strings);
            return new Message(this.Data.name,"商城",operation);
        }
        return this.Data.getMsg();
    }
    public Boolean operate(char op){
        if(this.isMarket){
            // 商城接管
            switch (Character.toString(op)){
                case("0"):{
                    return false;
                }
                case ("1"):{
                    if(this.listChildren()){
                        System.out.println("");
                        System.out.print("输入分类ID跳转，输入0返回菜单：");
                        String ope = this.belongMarket.monitor.askForLine();
                        if(!ope.equals("0")){
                            TreeNode node = this.findNodeById(ope);
                            if(node!=null){
                                this.belongMarket.curNode = node;
                                this.belongMarket.monitor.location.push(this.belongMarket.curNode.getData().name);
                            }else{
                                System.out.print(String.format("未找到ID为%s的分类",ope));
                            }
                        }
                    }
                    break;
                }
                case ("2"):{
                    System.out.print("请问要以何种方式搜索商品：【0】：ID 【1】：名称");
                    System.out.println("");
                    char ope = this.belongMarket.monitor.askForOperation();
                    if(Character.toString(ope).equals("0")){
                        System.out.print("请输入商品ID：");
                        Commodity comm = this.belongMarket.commodities.findCommByID(this.belongMarket.monitor.askForLine());
                        if(comm!=null){
                            CommLink link = new CommLink();
                            link.add(comm);
                            link.printData();
                        }else{
                            System.out.println("未查找到商品");
                        }
                    }else if(Character.toString(ope).equals("1")){
                        System.out.print("请输入商品名称：");
                        CommLink link = this.belongMarket.commodities.findCommByName(this.belongMarket.monitor.askForLine());
                        if(link!=null){
                            link.printData();
                        }else{
                            System.out.println("未查找到商品");
                        }
                    }else{
                        System.out.println("查找失败，未知命令: "+ope);
                    }
                    break;
                }
                case ("3"):{
                    if(this.belongMarket.commodities.printData() == 0){
                        System.out.println("该商城下无商品");
                    }
                    
                    break;
                }
                case ("4"):{
                    CommLink link  = new CommLink();
                    System.out.print("请输入分类名称：");
                    this.belongMarket.GID++;
                    Classification classification = new Classification(String.valueOf(this.belongMarket.GID),this.belongMarket.monitor.askForLine(),link);
                    TreeNode node = new TreeNode(classification,this,this.getChildren(),this.level+1,this.belongMarket);
                    this.children.push(node);
                    this.belongMarket.monitor.location.push(node.getData().name);
                    this.belongMarket.curNode = node;
                    System.out.print("添加成功!");
                    break;
                }
                case ("5"):{
                    System.out.print("请输入分类ID：");
                    String id = this.belongMarket.monitor.askForLine();
//                    if(node!=null){
//                        this.children.deleteById(id);
//                    }else{
//                        System.out.println("删除失败，未查找到id为"+id+"的子分类");
//                    }
                    this.children.deleteById(id);
                    break;
                }
                default:{
                    System.out.println("该目录不支持"+op+"操作");
                }
            }
        }else if(this.belongMarket.curNode.getMsg().type.equals("分类")){
            // 委托给分类
            switch (Character.toString(op)){
                case "0":{
                    this.belongMarket.curNode = this.parent;
                    this.belongMarket.monitor.location.pop();
                    break;
                }
                case "1":{
                    System.out.print("请输入分类名：");
                    this.getData().changeName(this.belongMarket.monitor.askForLine());
                    this.belongMarket.monitor.location.pop();
                    this.belongMarket.monitor.location.push(this.getData().name);
                    break;
                }
                case "2":{
                    this.getData().commodity.printName();
                    break;
                }
                case "3":{
                    if(this.getData().commodity.printData() == 0){
                        System.out.println("该分类下无商品");
                    }
                    break;
                }
                case "4":{
                    System.out.print("请输入商品名：");
                    String name = this.belongMarket.monitor.askForLine();
                    System.out.print("请输入商品价格：");
                    String price = this.belongMarket.monitor.askForLine();
                    System.out.print("请输入商品数量：");
                    String count = this.belongMarket.monitor.askForLine();
                    Commodity comm = new Commodity(String.valueOf(++this.belongMarket.GID),name,Double.parseDouble(price),Integer.parseInt(count));
                    this.belongMarket.commodities.add(comm);
                    this.getData().addComm(comm);
                    System.out.println("添加成功");
                    break;
                }
                case "5":{
                    System.out.print("请输入像删除商品的ID：");
                    this.getData().deleteCommByID(this.belongMarket.monitor.askForLine());
                }
                case "6":{
                    System.out.println(String.format("该分类下共有%d种商品",this.getData().countComm()));
                    break;
                }
                default:{
                    System.out.println("该目录不支持"+op+"操作");
                }
            }
        }
        return true;
    }



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
    public String toString(){
        return this.Data.toString();
    }
}

class TreeNodeList{
    private TreeNode[] value;
    public int length;
    public int size;
    TreeNodeList(int size){
        this.value = new TreeNode[size];
        this.length = 0;
        this.size = size;
    }

    public TreeNode[] getValue() {
        return value;
    }

    public int push(TreeNode node){
        this.value[length]=node;
        this.length++;
        this.realloc();
        return this.length;
    }
    private void realloc(){
        if(3*this.length> this.size*2){
            TreeNode[] spare = new TreeNode[2*this.size];
            for(int i=0;i<this.length;i++){
                spare[i] = this.value[i];
            }
            this.value = spare;
            this.size = 2*this.size;
            System.out.println(String.format("树节点数组已重新分配内存,目前大小:%d",this.size));
        }
    }
    public TreeNode deleteById(String id){
        TreeNode deleteItem = null;
        TreeNode[] spare = new TreeNode[this.size];
        int curry = 0;

        for (int i=0;i<this.length;i++) {
            if (!this.value[i].getData().ID.equals(id)) {
                spare[i+curry] = this.value[i];
            }else{
                deleteItem = this.value[i];
                curry = -1;
            }
        }
        this.value = spare;
        if(curry!=0){
            this.length--;
            System.out.println(String.format("已删除ID为%s的子分类",deleteItem.getData().ID));
        }else{
            System.out.println(String.format("删除失败，未找到id为%s的子分类",id));
        }
        return deleteItem;
    }
}
