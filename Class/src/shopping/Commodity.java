/*
商品类
 */
package shopping;

/**
 *
 * @author 69465
 */
public class Commodity {   //商品类，缩写为Comm。

    String ID; //主键
    String name = "未定义";
    double price = 0.0;
    int count = 0;

    public Commodity(String ID, String name, double price, int count) //创建一个商品
    {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(double price) {
        this.price = price;
    }

    public void changeCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "商品ID：" + this.ID + "\n商品名：" + this.name + "\n商品价格：" + this.price + "\n商品数量：" + this.count + "\n";
    }

    public Message getMsg() {
        String[] operations = new String[]{"修改商品名", "修改商品价格", "修改商品数量","将该商品加入到分类","查看该商品属于哪些分类"};
        Operation operation = new Operation(operations);
        Message resMsg = new Message(this.name, "商品", operation);
        return resMsg;
    }
}
