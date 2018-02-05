public class Thing {
    int priority;
    Object data;


    public Thing(int p, Object d){
        this.priority = p;
        data = d;
    }

    public String toString(){
        return "Prior: " + priority + " Data: " + data;
    }
}
