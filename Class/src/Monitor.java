import java.util.function.Function;

public class Monitor {
    // 当前控制对象
    private Object curController;
    // 构造函数
    Monitor(Object controller){
        this.curController = controller;
    }
    // 获取当前控制对象
    public Object getCurController(){
        return this.curController;
    };
    // 打印方法
    public void print(String str){
        System.out.print(str);
    };
}
