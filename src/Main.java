import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Mayın Tarlası Oyunu\nSatır sayısını girin: ");
        int rows = scanner.nextInt();
        System.out.print("Sütun sayısını girin: ");
        int cols = scanner.nextInt();

        MineSweeper game = new MineSweeper(rows, cols);
    }
}