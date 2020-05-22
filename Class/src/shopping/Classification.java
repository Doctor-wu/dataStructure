/*
* 分类类
 */
package shopping;

/**
 *
 * @author 69465
 */
public class Classification { //分类类，缩写为Clas。

    String ID; //分类ID
    String name; //分类名
    CommLink commodity; //该分类所包含的商品引用的链表
    //boolean isCommodity = false;

    public Classification(String ID, String name, CommLink commodity) { //构造函数
        this.ID = ID;
        this.name = name;
        this.commodity = commodity;
    }

    public Classification(String ID, String name) { //构造函数，仅创建分类，不加入商品。
        this.ID = ID;
        this.name = name;
        this.commodity = new CommLink();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Commodity getCommByID(int CommID) //给定一个商品ID，返回该分类下这个商品的引用。
    {
        return commodity.findCommByID(CommID);
    }

    public void addComm(Commodity commodity) { //添加一个商品到该分类中
        this.commodity.add(commodity);
    }

    public boolean deleteCommByID(int CommID) //给定一个商品ID，删除该分类下该商品引用。仅删除引用（即包含关系），不会删除商品本身。
    {
        return this.commodity.deleteByID(CommID);
    }

    public int countComm() { //返回该分类下有多少商品。
        return this.commodity.countNode();
    }

    public void printData() {
        System.out.println("分类ID：" + this.ID);
        System.out.println("分类名：" + this.name);
        System.out.print("分类包含以下商品：");
        this.commodity.printName();
        System.out.println("");
    }

    public Message getMsg() {
        String[] operations = new String[]{"返回上级", "修改分类名", "列出该分类商品列表", "输出该分类商品详细信息", "加入商品到该分类", "移除分类中的商品", "统计该分类商品数量"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message(this.name, "分类", operation);
        return resMsg;
    }
}
