package org.example;

import java.util.Scanner;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class Main {
    public static void main(String[] args) {

        // input the dice
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Which dice will be used?: ");
        int dice = scanner.nextInt();
        // input the rows v
        System.out.printf("How many rows do you want?: ");
        int rows = scanner.nextInt();
        // input the range 00-05
        int range = GetSection(scanner, dice);

        // Validate Entries:
        System.out.println("Dice: 1d" + dice);
        System.out.println("Rows: " + rows);
        System.out.println("Sections: " + range);

        String[] tableFull = new String[(dice / range)];
        String seperator1= "| ------- |";
        String headlines = " | Headline 1";
        String space = "    ";

        if (dice < 10){
            space = "    ";
        } else if (dice < 100) {
            space = "   ";
        } else if (dice == 100) {
            space = "  ";
        }


        String seperatorHeadLine = " ---------- |";
        String tableBase = "| 1d" + dice + space + headlines.repeat(rows) + " |" + "\n" + seperator1 + seperatorHeadLine.repeat(rows) + "\n";
        String table = CreateTable(tableBase, tableFull, range, dice, seperatorHeadLine, rows);

        System.out.println(table);

        StringSelection stringSelection = new StringSelection(table);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        System.out.println("Your table was copied into your clipboard!");
        scanner.close();
    }

    public static int GetSection(Scanner scanner, int dice) {
        System.out.printf("how big shall 1 section be?: ");
        int section = scanner.nextInt();
        int validSection;
        while (true) {
            if (dice % section != 0) {
                System.out.println(dice + " is not dividable by " + section + ", please enter another number: ");
                section = scanner.nextInt();
            } else {
                validSection = section;
                break;
            }
        }
        return validSection;
    }
    // 8(dice) / 2(range) = 4(lines) --> 1-2; 3-4; 5-6; 7-8
    public static String CreateTable(String tableBase, String[] tableFull, int range, int dice, String seperatorHeadline, int rows){
        String table = "";
        String space = "";


        int currentNumber = 1;
        for (int i = tableFull.length - 1; i >= 0; i--) {
            int nextNumber = currentNumber+(range-1);

            if (currentNumber < 10 && nextNumber < 10){
                space = "     ";
            } else if (currentNumber < 10 && nextNumber == 10) {
                space = "    ";
            } else if (currentNumber >= 10 && nextNumber < 100) {
                space = "   ";
            } else if (nextNumber >= 100) {
                space = "  ";
            }
            tableFull[i] = "| " + currentNumber + "-" + nextNumber + space + "|" + seperatorHeadline.repeat(rows) + "\n";
            currentNumber += range;
        }
        table = tableBase;
        for (int k = tableFull.length-1; k >= 0; k--) {
            table = table + tableFull[k];
        }
        return table;
    }
}