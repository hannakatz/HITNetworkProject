package concurrentpatterns;

public class SomeObject {
    String name;
    double amount;

    public SomeObject(){

    }

    public SomeObject(String name, double amount){
        this.name = name;
        this.amount = amount;
    }

    public static void main(String[] args) {
        SomeObject object = new SomeObject("A name", 30.5);
        /*
        1. Java allocates memory for the object. It has an address
        2. Data members are initialized to default vale
        3. Constructor invocation
        4. new expression get its value (address)
        5. assignment
         */

    }


}
