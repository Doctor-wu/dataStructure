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
            this.children.delete(node);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public TreeNode findNodeById(String id){
        if(this.Data.ID==id) return this;
        else if(this.children.length!=0){
            for (TreeNode item:this.children.getValue()) {
                if( item.findNodeById(id)!=null){
                    return item;
                }
            }
        }
        return null;
    }

    public TreeNode findNodeByName(String name){
        if(this.Data.name.equals(name)) return this;
        else if(this.children.length!=0){
            for (TreeNode item:this.children.getValue()) {
                if( item.findNodeByName(name)!=null){
                    return item;
                }
            }
        }
        return null;
    }

    public void listChildren(){
        if(this.children.length!=0){
            for (int i = 0; i <this.children.length ; i++) {
                System.out.print(" "+this.children.getValue()[i].getData().name);
            }
        }else{
            System.out.print("该分类暂无子分类");
        }
    }


    // 数据操作
    public Message getMsg(){
        if(this.isMarket){
            String[] strings = new String[8];
            strings[0] = "退出系统";
            strings[1] = "列出分类";
            strings[2] = "查找商品";
            strings[3] = "列出商品";
            strings[4] = "添加商品";
            strings[5] = "添加分类";
            strings[6] = "删除商品";
            strings[7] = "删除分类";
            Operation operation = new Operation(strings);
            return new Message(this.Data.name,"商城",operation);
        }
        return null;
    }
    public Boolean operate(char op){
        if(this.isMarket){
            // 商城接管
            switch (Character.toString(op)){
                case("0"):{
                    return false;
                }
                case ("1"):{
                    this.listChildren();
                    break;
                }
                case ("2"):{
                    System.out.print("请问要以何种方式搜索商品：【0】：ID 【1】：名称");
                    System.out.println("");
                    char ope = this.belongMarket.monitor.askForOperation();
                    if(Character.toString(ope).equals("0")){
                        System.out.print("请输入商品ID：");
                        Commodity comm = this.Data.commodity.findCommByID(this.belongMarket.monitor.askForLine());
                        if(comm!=null){
                            CommLink link = new CommLink();
                            link.add(comm);
                            link.printData();
                        }else{
                            System.out.println("未查找到商品");
                        }
                    }else if(Character.toString(ope).equals("1")){
                        System.out.print("请输入商品名称：");
                        CommLink link = this.Data.commodity.findCommByName(this.belongMarket.monitor.askForLine());
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
                    this.Data.commodity.printData();
                    break;
                }
                case ("4"):{
                    this.belongMarket.GID++;
                    System.out.print("请输入商品名称：");
                    String name = this.belongMarket.monitor.askForLine();
                    System.out.print("请输入商品价格：");
                    String price = this.belongMarket.monitor.askForLine();
                    System.out.print("请输入商品数量：");
                    String count = this.belongMarket.monitor.askForLine();
                    Commodity comm = new Commodity(String.valueOf(this.belongMarket.GID),name,Double.parseDouble(price),Integer.parseInt(count));
                    this.Data.addComm(comm);
                    break;
                }
                default:{
                    System.out.println("该目录不支持"+op+"操作");
                }
            }
        }else{
            // 委托给分类或商品
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
    public TreeNode delete(TreeNode node){
        TreeNode deleteItem = null;
        TreeNode[] spare = new TreeNode[this.size];
        int curry = 0;

        for (int i=0;i<this.length;i++) {
            if (this.value[i].toString().equals(node.toString())) {
                spare[i+curry] = this.value[i];
            }else{
                deleteItem = this.value[i];
                curry = -1;
            }
        }
        if(curry!=0){
            this.length--;
            System.out.println(String.format("已删除%d",deleteItem.getData()));
        }else{
            System.out.println(String.format("未删除%s",node.toString()));
        }
        return deleteItem;
    }
}
