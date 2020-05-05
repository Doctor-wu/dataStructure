package shopping;

public class Stack {
    private String data[];
    public int op;
    public int size;
    Stack(int size){
        this.size = size;
        this.data = new String[size];
        this.data[0] = "#";
        this.op = 0;
    }
    public int push(String str){
        this.op++;
        this.data[this.op] = str;
        this.realloc();
        return this.op;
    }
    public String pop(){
        if(this.op>0){
            this.op--;
            return this.data[this.op+1];
        }else{
            return null;
        }
    }
    private void realloc(){
        if(3*this.op> this.size*2){
            String[] spare = new String[2*this.size];
            for(int i=0;i<=this.op;i++){
                spare[i] = this.data[i];
            }
            this.data = spare;
            this.size = 2*this.size;
            System.out.println(String.format("Stack已重新分配内存,目前大小:%d",this.size));
        }
    }
    public String[] getData(){
        return this.data;
    }
}
