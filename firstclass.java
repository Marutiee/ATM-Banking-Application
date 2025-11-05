class College{
    String clgname;
    String sub;
    int std;

}

public class firstclass {
    public static void main(String[] args) {
        College clg=new College();

        clg.clgname="SSV";
        clg.sub="Computer Science";
        clg.std=6;

        System.out.println("My College name is "+clg.clgname);
        System.out.println("My Subject name is "+clg.sub);
        System.out.println("My Student present in class is "+clg.std);

    }
}
