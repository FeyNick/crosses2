package geekbrains.homework;


import java.util.Random;
import java.util.Scanner;

/*
     1) я проверяю, не могу ли я выиграть на следующем ходу
     2) я проверяю, не могу ли я проиграть на слежующем ходу
     3) я делаю случайный(!) ход
     4) я хорошо работаю, есл размер поля и количество точек для победы совпадает (например 5х5 поле, 5 знаков в ряд)
        */

// умник работает только на 3х3 !!! (могу доделать для размера n но нужно еще время)
public class Main {
    public static char[][] map;
    public static int SIZE = 3;
    public static int DOTS_TO_WIN = 3;
    public static int turn = 1;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static int mod = 2;
    public static Random rand = new Random();

    public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
       System.out.println("Choose map size: ");
       SIZE = scanner.nextInt();
       System.out.println("Choose dots amount yo win: ");
       DOTS_TO_WIN= scanner.nextInt();   // для различных размеров карты
       System.out.println("Choose version (1 for easy one, 2 for HARD one): ");
       mod = scanner.nextInt(); // все что выше можно заккоментировать для поля 3х3, умного режима
        initMap();
        while (true) {
            if (turn == ((SIZE * SIZE)/2 + 2)) {
                printMap();
                System.out.println("DRAW");
                break;
            }
            System.out.println("TURN: " + turn);
            printMap();
            int res;
            res = hasWon();
            if (res == 1) {
                System.out.println("YOU WON!!!");
                break;
            }   // победа человека
            if (res == 2) {
                System.out.println("YOU LOST(");
                break;
            }   // победа компьютера
            if (res == 0) {
                humanTurn();
                aiMod();
                turn++;
            }

            // aiMod();
        }
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }

    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Scanner scan = new Scanner(System.in);

    public static boolean isValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return false;
        return map[x][y] == DOT_EMPTY;
    }

    public static void humanTurn() {
        int x, y;
        do {
            y = scan.nextInt() - 1;
            x = scan.nextInt() - 1;
        } while (!isValid(x, y));
        map[x][y] = DOT_X;
    }

    public static void aiMod() {
        if (turn == 1)
            aiTurn();
        else {
            if (mod == 1) {
                aiTurn();
            } else
                Spy();
        }
    }

    public static void aiTurn() {
        int x, y;
        if (turn == ((SIZE * SIZE)/2 + 1))
            return;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isValid(x, y));
        map[x][y] = DOT_O;
    }

    public static int hasWon() {
        int res = 0;
        for (int x = 0; x < SIZE; x++) {
            res = yChecker(x);
            if (res != 0)
                return res;
        }      // столбцы
        for (int y = 0; y < SIZE; y++) {
            res = xChecker(y);
            if (res != 0)
                return res;

        }      // строчки
        if (res == 0) {
            res = d1Checker();
            if (res != 0)
                return res;
        }
        if (res == 0) {
            res = d2checker();
        }      // диагонали
        return res;
    }

    public static int yChecker(int x) {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;// нет победы
        for (int y = 0; y < SIZE; y++) {
            if (map[x][y] == DOT_X)
                xCounter += 1;
            if (map[x][y] == DOT_O)
                oCounter += 1;
        }
        if (xCounter == DOTS_TO_WIN) {
            res = 1;// победа человека
            return res;
        }
        if (oCounter == DOTS_TO_WIN)
            res = 2; // победа компюьтера
        return res;
    }

    public static int xChecker(int y) {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;// нет победы
        for (int x = 0; x < SIZE; x++) {
            if (map[x][y] == DOT_X)
                xCounter += 1;
            if (map[x][y] == DOT_O)
                oCounter += 1;
        }
        if (xCounter == DOTS_TO_WIN) {
            res = 1;// победа человека
            return res;
        }
        if (oCounter == DOTS_TO_WIN)
            res = 2; // победа компюьтера
        return res;
    }

    public static int d1Checker() {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;// нет победы
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == DOT_X)
                xCounter += 1;
            if (map[i][i] == DOT_O)
                oCounter += 1;
        }
        if (xCounter == DOTS_TO_WIN) {
            res = 1;// победа человека
            return res;
        }
        if (oCounter == DOTS_TO_WIN)
            res = 2; // победа компюьтера
        return res;
    }

    public static int d2checker() {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;
        int y = 0;
        for (int x = (SIZE - 1); x >= 0; x--) {
            if (map[x][y] == DOT_O)
                oCounter += 1;
            if (oCounter == DOTS_TO_WIN) {
                res = 2;
                return res;
            }
            y++;
        }
        y = 0;
        for (int x = (SIZE - 1); x >= 0; x--) {
            if (map[x][y] == DOT_X)
                xCounter += 1;
            if (xCounter == DOTS_TO_WIN) {
                res = 1;
            }
            y++;
        }
        return res;
    }

    public static void Spy() {
        int res = 0;
        for (int x = 0; x < SIZE; x++) {
            res = ySpy(x);
            if (res != 0) {
                xStringFiller(x);
                return;
            }
        }
        if (res == 0) {
            for (int y = 0; y < SIZE; y++) {
                res = xSpy(y);
                if (res != 0) {
                    yStringFiller(y);
                    return;
                }
            }
        }
        if (res == 0) {
            res = d1Spy();
            if (res != 0) {
                d1Filler();
                return;
            }
        }
        if (res == 0) {
            res = d2Spy();
            if (res != 0) {
                d2Filler();
                return;
            }
        }
        if (res == 0) {
            aiTurn();
        }
    }

    public static int d1Spy() {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;
        int resx =0;
        int reso =0;
        int trigger =0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == DOT_O) {
                oCounter += 1;
                if (oCounter == DOTS_TO_WIN - 1)
                    reso = i;
                    trigger = 2;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == DOT_X) {
                xCounter += 1;
                if (xCounter == DOTS_TO_WIN - 1)
                    resx = i;
                    trigger = 1;
            }
        }
        if(trigger == 0) {
            return res;
        }
        if(trigger == 2 && xCounter == 0){
            res = reso;
            return res;
        }
        if (trigger == 1 && oCounter == 0){
            res = resx;
            return res;
        }
        return res;
    }

    public static int ySpy(int x) {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;
        for (int y = 0; y < SIZE; y++) {
            if (map[x][y] == DOT_O) {
                oCounter += 1;
                if (oCounter == DOTS_TO_WIN - 1) {
                    res = 2;
                }
            }
            if (map[x][y] == DOT_X) {
                xCounter += 1;
                if (xCounter == DOTS_TO_WIN - 1) {
                    res = 1;
                }
            }
        }
        if(res == 2 && xCounter == 0){
            return res;
        }
        if (res == 1 && oCounter == 0){
            return res;
        }
        res = 0;
        return res;
    }

    public static int xSpy(int y) {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;
        for (int x = 0; x < SIZE; x++) {
            if (map[x][y] == DOT_O) {
                oCounter += 1;
                if (oCounter == DOTS_TO_WIN - 1)
                    res = 2;
            }
            if (map[x][y] == DOT_X) {
                xCounter += 1;
                if (xCounter == DOTS_TO_WIN - 1)
                    res = 1;
            }
        }
        if(res == 2 && xCounter == 0){
            return res;
        }
        if (res == 1 && oCounter == 0){
            return res;
        }

        res = 0;
        return res;
    }

    public static int d2Spy() {
        int xCounter = 0;
        int oCounter = 0;
        int res = 0;
        int y =0;
        for (int x = (SIZE - 1); x >= 0; x--) {
            if (map[x][y] == DOT_O)
                oCounter += 1;
            if (oCounter == DOTS_TO_WIN -1) {
                res = 2;
            }
            y++;
        }
        y = 0;
        for (int x = (SIZE - 1); x >= 0; x--) {
            if (map[x][y] == DOT_X)
                xCounter += 1;
            if (xCounter == DOTS_TO_WIN -1) {
                res = 1;
            }
            y++;
        }
        if(res == 2 && xCounter == 0){
            return res;
        }
        if (res == 1 && oCounter == 0){
            return res;
        }
        res = 0;
        return res;
    }
    public static void yStringFiller(int y) {
        for (int i = 0; i < SIZE; i++) {
            if (map[i][y] == DOT_EMPTY) {
                map[i][y] = DOT_O;
                return;
            }
        }
    }

    public static void xStringFiller(int x) {
        for (int i = 0; i < SIZE; i++) {
            if (map[x][i] == DOT_EMPTY) {
                map[x][i] = DOT_O;
                return;
            }
        }
    }

    public static void d1Filler() {
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == DOT_EMPTY) {
                map[i][i] = DOT_O;
                return;
            }
        }
    }


    public static void d2Filler() {
        int y = 0;
        for (int x = (SIZE - 1); x >= 0; x--) {
                if (map[x][y] == DOT_EMPTY) {
                    map[x][y] = DOT_O;
                    return;
                }
            y++;
        }
    }
}

