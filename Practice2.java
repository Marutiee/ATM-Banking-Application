class Cellphone{
    /*
    public void vibrate(){
        System.out.println("Phone is Vibrating");
    }
    public void ring(){
        System.out.println("Phone is Ringing");
    }
    public void end(){
        System.out.println("Call Ended");
    }
     */
}

class Square{
    int side;

    public int area(){
        return side*side;
    }
    public int perimenter(){
        return 4*side;
    }
}

public class Practice2 {
    public static void main(String[] args) {
        Square sq = new Square();
        sq.side=6;
        System.out.println("Area of Square "+sq.area());
        System.out.println("Perimeter of Square "+sq.perimenter());
    }
}