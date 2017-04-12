import java.util.Scanner;

/**
 * Created by bespa on 11.04.2017.
 */
public class suka {
    public static void main(String[] args){
        System.out.println("Vlad pidr\n Y/N?");
        Scanner sc = new Scanner(System.in);
        String s=sc.nextLine();
        if(s.equals("Y")) System.out.println("Ya znau"); else if(s.equals("N"))System.out.println("Ne pizdi"); else
            System.out.println("Vvedi norm otvet");
    }
}
