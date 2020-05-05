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
//        CommLink commLink = new CommLink();
//        commLink.add(new Commodity("001", "可乐", 3.0, 10));
//        commLink.add(new Commodity("002", "雪碧", 3.0, 10));
//        commLink.add(new Commodity("003", "橙汁", 3.0, 10));
//        commLink.printData();
//        new Tree<TreeNode>(new TreeNode(commLink,null,null,0),null);
//        Monitor monitor = new Monitor(new Object());
//        monitor.print("1");
//        String[] strings = new String[2];
//        strings[0] = "测试1";
//        strings[1] = "测试2";
//        monitor.renderMessage(new Message("测试名称","测试类别",new Operation(strings)));
//        System.out.println(monitor.askForLine());
        new Market().run();
    }
}
