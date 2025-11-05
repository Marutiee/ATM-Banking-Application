class Employee1{
    int salary;
    String name;

    int getSalary(){
        return salary;
    }

    String getName(){
        return name;
    }

    void setName(String n){
        name = n;

    }
}

public class practice1 {
    public static void main(String[] args) {
        Employee1 emp = new Employee1();
        emp.name="maruti";
        emp.salary=15000;

        System.out.println("Name is : "+emp.getName());
        System.out.println("Salary is : "+emp.getSalary());
        emp.setName("MAYUR");
        System.out.println("New Name is : "+emp.getName());

    }
}
