import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
public class MineSweeper {
    private int rows;
    private int cols;
    private int[][] mineField;
    private boolean[][] revealed;
    private int remainingCells;

    public MineSweeper(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mineField = new int[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.remainingCells = rows * cols;

        // Mayınları yerleştir
        placeMines(rows * cols / 4);

        // Oyunu başlat
        startGame();
    }

    private void placeMines(int mineCount) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mineCount) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (mineField[row][col] != -1) {
                mineField[row][col] = -1; // Mayını işaretle
                minesPlaced++;
            }
        }
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (remainingCells > 0) {
            printField();
            int row, col;

            do {
                System.out.print("Satır seçin (0-" + (rows - 1) + "): ");
                row = scanner.nextInt();
                System.out.print("Sütun seçin (0-" + (cols - 1) + "): ");
                col = scanner.nextInt();
            } while (!isValidMove(row, col));

            if (mineField[row][col] == -1) {
                revealField();
                System.out.println("Mayına bastınız! Oyun bitti!");
                printField();
                return;
            } else {
                revealCell(row, col);
            }
        }

        System.out.println("Tebrikler! Tüm mayınları temizlediniz! Oyunu kazandınız!");
        printField();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && !revealed[row][col];
    }

    private void revealCell(int row, int col) {
        if (!isValidMove(row, col)) {
            return;
        }

        if (mineField[row][col] != -1) {
            int minesAround = countMinesAround(row, col);
            mineField[row][col] = minesAround;
            revealed[row][col] = true;
            remainingCells--;

            if (minesAround == 0) {
                // Etrafındaki tüm boş hücreleri aç
                revealCell(row - 1, col);
                revealCell(row + 1, col);
                revealCell(row, col - 1);
                revealCell(row, col + 1);
                revealCell(row - 1, col - 1);
                revealCell(row - 1, col + 1);
                revealCell(row + 1, col - 1);
                revealCell(row + 1, col + 1);
            }
        }
    }

    private int countMinesAround(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && mineField[i][j] == -1) {
                    count++;
                }
            }
        }

        return count;
    }

    private void revealField() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                revealed[i][j] = true;
            }
        }
    }

    private void printField() {
        System.out.println("Mayın Tarlası Durumu:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (revealed[i][j]) {
                    if (mineField[i][j] == -1) {
                        System.out.print("* ");
                    } else {
                        System.out.print(mineField[i][j] + " ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}