import java.util.Scanner;
public class test {
    public static int findnum() {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        return num;
    }
    public static void main(String[] args) {
        int num = findnum();
        System.out.println(num);
    }
}  
