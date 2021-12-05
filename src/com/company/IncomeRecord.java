package com.company;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.company.Input.inputFloat;
import static com.company.Input.inputString;
import static com.company.StringUtils.*;

interface Income {

    //1. Add a new Income Record to the file
    public static boolean addIncomeRecord() { return false; }

    //2. Used to read all the income records from the file
    public static List<IncomeRecord> readRecords() { return null; }

    //3. Used to write new income record into the file
    public static boolean writeRecords(List<IncomeRecord> records) { return false; }

    //4. Used to Display all Income Records
    public static void displayIncomeRecords() {}

    //5. Used to Display sum of all Income Records
    public static void sumIncomeRecords() {}

    //6. Used to display sum of all the incomes for the current month
    public static void sumMonthlyIncome() {}

    //7. This is a function used to sort income records based on category, date and month
    public static void sortIncomeRecord() throws Exception {}
}

public class IncomeRecord implements Income,Serializable {
    String incomeType;
    Category category;
    Date date;
    float amount;
    String description;
    final String cashFlow = "income";

    @Override
    public String toString() {
        return "   Type='" + incomeType + '\'' +
                ", Category='" + category.categoryName + '\'' +
                ", Amount=" + amount +
                ", Description='" + description + '\'' +
                ", CashFlow='" + cashFlow + '\'' +
                ", Date and Time='" + date + '\'';
    }

    static final String filepath =
            "C:\\Users\\minis\\Desktop\\THE HUB\\PROGRAMMING\\JAVA\\EXPENSE TRACKER\\IncomeRecords";

    //Add a new Income Record to the file
    public static boolean addIncomeRecord() {

        try {
            IncomeRecord newRecord = new IncomeRecord();
            String categoryInput;
            Date currentDate = new Date();
            currentDate = StringUtils.removeTime(currentDate);

            System.out.println("Enter Description: ");
            newRecord.description = inputString();

            System.out.println("Enter the Amount: ");
            newRecord.amount = inputFloat();

            newRecord.date = currentDate;

            System.out.println("Payment Type ( Cash, Card, Online Payment, Mobile Payment): ");
            newRecord.incomeType = inputString();

            if (Main.checkPaymentType(newRecord.incomeType)) {
                System.out.println("Wrong Payment Type");
                return false;
            }

            System.out.println("Enter Category: ");
            Category.displayCategories();
            categoryInput = inputString();

            newRecord.category = Category.isCategoryTrue(categoryInput);

            List<IncomeRecord> allRecords = readRecords();

            assert allRecords != null;
            allRecords.add(newRecord);

            return writeRecords(allRecords);
        }
        catch (Exception ex) {
            return false;
        }

    }

    //Used to read all the income records from the file
    public static List<IncomeRecord> readRecords() {

        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<IncomeRecord> records;

            records = (List<IncomeRecord>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            return records;

        } catch (Exception ex) {
            return null;
        }
    }

    //Used to write new income record into the file
    public static boolean writeRecords(List<IncomeRecord> records) {

        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(records);

            objectOut.close();
            fileOut.close();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Used to Display all Income Records
    public static void displayIncomeRecords() {
        List<IncomeRecord> allRecords = readRecords();
        assert allRecords != null;
        for (IncomeRecord record : allRecords) {
            System.out.println(record);
        }
    }

    //Used to Display sum of all Income Records
    public static void sumIncomeRecords() {
        List<IncomeRecord> allRecords = readRecords();
        assert allRecords != null;
        float sumIncomes = 0.0f;
        for (IncomeRecord record : allRecords) {
            sumIncomes += record.amount;
        }
        System.out.println("Total Income:" + sumIncomes);
    }

    //Used to display sum of all the incomes for the current month
    public static void sumMonthlyIncome() {
        int yearNumber;
        int monthNumber;
        float sumIncome = 0.0f;

        // Used to get the current month and year
        Calendar calendar = Calendar.getInstance();
        int inputMonth = calendar.get(Calendar.MONTH);
        int inputYear = calendar.get(Calendar.YEAR);

        //allRecords contain all the income records stored in the file
        List<IncomeRecord> allRecords = readRecords();

        assert allRecords != null;

        for (IncomeRecord records : allRecords) {
            Calendar recordCalendar = Calendar.getInstance();
            recordCalendar.setTime(records.date);

            //Extracts the month and year of the current record
            monthNumber = recordCalendar.get(Calendar.MONTH);
            yearNumber = recordCalendar.get(Calendar.YEAR);

            //Checks if the current month and year matches the records month and year
            if (inputMonth == monthNumber && inputYear == yearNumber) {
                sumIncome += records.amount;
            }

        }
        System.out.println("Total Monthly Income: " + sumIncome);
    }

    //This is a function used to sort income records based on category, date and month
    public static void sortIncomeRecord() throws Exception {

        IncomeRecord newRecord = new IncomeRecord();
        String categoryInput;
        String answer;
        String date;
        int monthNumber;
        int yearNumber;

        Date newDate;

        System.out.println("Sort by \n1. Category \n2. Date \n3. Month");
        answer = inputString();

        switch (answer) {

            //Sort by Category
            case "1" -> {
                System.out.println("Enter Category (Enter full name of Category): ");
                categoryInput = inputString();
                Category.displayCategories();


                newRecord.category = Category.isCategoryTrue(categoryInput);

                List<IncomeRecord> allRecords = readRecords();

                assert allRecords != null;
                for (IncomeRecord records : allRecords) {
                    if (categoryInput.compareToIgnoreCase(records.category.categoryName) == 0) {
                        System.out.println(records);
                    }
                }
            }
            //Sort by Date
            case "2" -> {
                System.out.println("Enter Date (Format:dd/mm/yyyy):");
                date = inputString();

                newDate = convert(date);

                List<IncomeRecord> allRecords = readRecords();

                assert allRecords != null;

                for (IncomeRecord records : allRecords) {
                    if (records.date.compareTo(newDate) == 0) {
                        System.out.println(records);
                    }
                }
            }
            //Sort by Month
            case "3" -> {
                System.out.println("Enter Month (Example: For June, Enter 06):");
                int inputMonth = Integer.parseInt(inputString());

                System.out.println("Enter Year (Format: YYYY:");
                int inputYear = Integer.parseInt(inputString());

                List<IncomeRecord> allRecords = readRecords();

                assert allRecords != null;

                for (IncomeRecord records : allRecords) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(records.date);
                    monthNumber = calendar.get(Calendar.MONTH);
                    monthNumber++;
                    yearNumber = calendar.get(Calendar.YEAR);

                    if (inputMonth == monthNumber && inputYear == yearNumber) {
                        System.out.println(records);
                    }
                }

            }
        }
    }
}


