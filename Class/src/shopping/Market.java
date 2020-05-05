package shopping;

public class Market {
    private Stack posIdStack;
    public Monitor monitor;
    public CommLink commodities;
    public Tree commodityTree;
    public TreeNode curNode;
    public int GID=-1;
    Market(){
        this.monitor = new Monitor(this);
        System.out.print("请输入商场名称：");
        CommLink link = new CommLink();
        this.GID=this.GID+1;
        Classification classification = new Classification(String.valueOf(this.GID),this.monitor.askForLine(),link);
        this.commodityTree = new Tree(classification,null,this);
        this.commodities = new CommLink();
        this.curNode = this.commodityTree.getRoot();
    }
    public void run(){
        System.out.println("");
        System.out.println("欢迎光临 "+this.commodityTree.getRoot().getData().name);
        System.out.println("");
        Boolean flag = true;
        while(flag){
            this.monitor.renderMessage(this.curNode.getMsg());
            char userOperate = this.monitor.askForOperation();
            flag = this.curNode.operate(userOperate);
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
        System.out.println(this.commodityTree.getRoot().getData().name+" is closed,Welcome for next time!");
    }
}
