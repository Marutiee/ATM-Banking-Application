
class Employee{
    int id ;
    String name;
    void printDetails(){
        System.out.println("My name is "+name);
        System.out.println("My id is "+id);
    }
}

public class classess {
    public static void main(String[] args) {
        Employee emp=new Employee();
        emp.id=6;
        emp.name="maruti";
        emp.printDetails();
    }
}
