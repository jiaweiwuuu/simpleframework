package demo.reflect;

import java.sql.Ref;

public class ReflectTarget {
    public String name;
    protected int index;
    char type;
    private int targetInfo;


    public void show1(String a){
        System.out.println("public show1"+a);
    }

    protected void show2(String a){
        System.out.println("protected show2"+a);
    }

    private void show3(String a){
        System.out.println("protected show3"+a);
    }

    void show4(String a){
        System.out.println("package show4"+a);
    }


    ReflectTarget(String str){
        System.out.println("default");
    }

    public ReflectTarget(){
        System.out.println("public nonargument");
    }
    public ReflectTarget(char name){
        System.out.println("one argument");
    }
    public ReflectTarget(String name, int index){
        System.out.println("2 argument"+name + index);
    }
    protected ReflectTarget(boolean n){
        System.out.println("protected method"+n);
    }

    private ReflectTarget(int a){
        System.out.println("private method "+a);
    }
    public static void main(String[] args) throws ClassNotFoundException {
        // first method to get class
        ReflectTarget reflectTarget = new ReflectTarget();
        Class reflectTargetClass1 = reflectTarget.getClass();
        System.out.println("first : "+reflectTargetClass1.getName());


        Class reflectTargetClass2 = ReflectTarget.class;
        System.out.println("second : "+reflectTargetClass2.getName());

        System.out.println(reflectTargetClass1 == reflectTargetClass2);

        //forname

        Class reflectTargetClass3 = Class.forName("demo.reflect.ReflectTarget");
        System.out.println("third : "+reflectTargetClass3.getName());
        System.out.println(reflectTargetClass3 == reflectTargetClass2);

    }

    @Override
    public String toString() {
        return "ReflectTarget{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", type=" + type +
                ", targetInfo='" + targetInfo + '\'' +
                '}';
    }
}
