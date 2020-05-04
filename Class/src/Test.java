public class Test {
    public static void main(String[] args){
        TreeNodeList list = new TreeNodeList(3);
        for(int i=0;i<20;i++){
            list.push(new TreeNode(i, null, null, i));
        }
        for(int i=0;i<list.length;i++){
            System.out.println(list.getValue()[i].getLevel());
        }
        list.delete(list.getValue()[0]);
        System.out.println(String.format("数组长度：%d",list.length));
    }
}
