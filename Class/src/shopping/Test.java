package shopping;

public class Test {
    public static void main(String[] args){
//        TreeNodeList list = new TreeNodeList(3);
//        for(int i=0;i<20;i++){
//            list.push(new TreeNode(i, null, null, i));
//        }
//        for(int i=0;i<list.length;i++){
//            System.out.println(list.getValue()[i].getLevel());
//        }
//        list.delete(list.getValue()[0]);
//        System.out.println(String.format("数组长度：%d",list.length));
        CommLink commLink = new CommLink();
        commLink.add(new Commodity("001", "可乐", 3.0, 10));
        commLink.add(new Commodity("002", "雪碧", 3.0, 10));
        commLink.add(new Commodity("003", "橙汁", 3.0, 10));
        commLink.printData();
        new Tree<TreeNode>(new TreeNode(commLink,null,null,0),null);
    }
}
