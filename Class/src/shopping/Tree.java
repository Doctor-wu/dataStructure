package shopping;

public class Tree<T> {
    private TreeNode root;

    Tree(T data, TreeNode parent) {
        this.root = new TreeNode(data, parent, new TreeNodeList(10), 0);
    }
}

class TreeNode<T> {
    private T Data;
    private int level;
    private Monitor monitor;
    private TreeNode root;
    private TreeNodeList siblings;
    private TreeNodeList children;
    private TreeNode parent;
    TreeNode(T data,TreeNode parent, TreeNodeList siblings,int level){
        this.root = this;
        this.Data = data;
        this.parent = parent;
        this.siblings = siblings;
        this.level = level;
        this.monitor = new Monitor(this);
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
//    public TreeNode findNode(String id){
//        if(this.Data.getID()==id) return this;
//        else if(this.children.length!=0){
//            for (TreeNode item:this.children.getValue()) {
//                item.findNode(id);
//            }
//        }
//        return null;
//    }


    // 基础方法
    public T getData() {
        return Data;
    }

    public Number getLevel() {
        return level;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNodeList getChildren() {
        return children;
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
