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

    @Override
    public String toString() {
        return "\n商品ID：" + this.ID + "\n商品名：" + this.name + "\n商品价格：" + this.price + "\n商品数量：" + this.count;
    }
}