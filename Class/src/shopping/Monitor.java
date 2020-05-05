package shopping;

public class Monitor {
    private Stack location;
    // 当前控制对象
    private Object curController;
    // 构造函数
    Monitor(Object controller){
        this.curController = controller;
        this.location = new Stack(5);
        this.location.push("主菜单");
    }
    // 获取当前控制对象
    public Object getCurController(){
        return this.curController;
    };
    private void renderLocation(){
        String result = "";
        for (String item:this.location.getData()) {
            if(item =="#" || item == null) continue;
            result += item+">";
        }
        result = result.substring(0,result.length()-1);
        System.out.print(result);
    }
    // 打印方法
    public void print(String str){
        this.renderDivider();
        this.renderLocation();
        this.renderDivider();
    };
    private void renderDivider(){
        System.out.println("");
        for(int i=0;i<50;i++) {
            System.out.print("*");
        }
        System.out.println("");
    };
    public void renderMessage(Message message){
        System.out.println("");
        System.out.println("名称："+message.name);
        System.out.println("类别："+message.type);
        this.renderDivider();
        System.out.println("可执行操作：");
        for (int i = 0; i <message.operation.operations.length ; i++) {
            System.out.println("【"+i+"】: "+message.operation.operations[i]);
        }
    }
}


class Operation{
    public String[] operations;
    Operation(String[] operations){
        this.operations = operations;
    }
}

class Message{
    public String name;
    public String type;
    public Operation operation;
    Message(String name,String type,Operation operation){
        this.name = name;
        this.type = type;
        this.operation = operation;
    }
}
