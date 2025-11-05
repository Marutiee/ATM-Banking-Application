import java.util.*;
import java.util.Scanner;

public class firstProgram {

    static int factorial(int n){
        if(n==0 || n==1){
            return 1;
        }else{
            return n * factorial(n-1);
        }
    }
    static void fibbonacci(int n){
        System.out.print("0 ");
        System.out.print("1 ");
        int a=0,b=1;
        for(int i=0;i<n;i++){
            int next = a+b;
            System.out.print(next+" ");
            a=b;
            b=next;
        }
    }

    static int fib(int n){
        if(n==0){
            return 0;
        }
        else if(n==1){
            return 1;
        }
        return fib(n-1)+fib(n-2);
    }
    public static void main(String[] args) {
        //Scanner sc=new Scanner(System.in);
        //System.out.print("Enter a number to find a factorial : ");
        //int num=sc.nextInt();
        //int x=factorial(num);
        //System.out.println("Factorial is :"+x);

        //fibbonacci(5);
        int n=5;
        for(int i=0;i<n;i++){
            System.out.print(fib(i)+" ");
        }
    }
}
