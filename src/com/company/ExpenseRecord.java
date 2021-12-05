package com.company;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.company.Input.inputFloat;
import static com.company.Input.inputString;
import static com.company.StringUtils.*;

interface Expense {

    //1. Add a new ExpenseRecord to the file
    public static boolean addExpenseRecord() { return false; }

    //2. Used to read all the expense records from the file
    public static List<ExpenseRecord> readRecords() { return null; }

    //3. Used to write new expense record into the file
    public static boolean writeRecords(List<ExpenseRecord> records) { return false; }

    //4. Used to Display all expense Records
    public static void displayExpenseRecords() {}

    //5. Used to Display sum of all Expense Records
    public static void sumExpenseRecords() {}

    //6. Used to display sum of all the expenses for the current month
    public static void sumExpenseIncome() {}

    //7. This is a function used to sort expense records based on category, date and month
    public static void sortExpenseRecord() throws Exception {}
}

public class ExpenseRecord implements Expense, Serializable {
    String paymentType;
    Category category;
    Date date;
    float amount;
    String description;
    final String cashFlow = "expense";

    @Override
    public String toString() {
        return "   Type='" + paymentType + '\'' +
                ", Category='" + category.categoryName + '\'' +
                ", Amount=" + amount +
                ", Description='" + description + '\'' +
                ", CashFlow='" + cashFlow + '\'' +
                ", Date and Time='" + date + '\'';
    }

    static final String filepath =
            "C:\\Users\\minis\\Desktop\\THE HUB\\PROGRAMMING\\JAVA\\EXPENSE TRACKER\\ExpenseRecords";



    //Add a new Expense Record to the file
    public static boolean addExpenseRecord() {

        try {
            ExpenseRecord newRecord = new ExpenseRecord();
            String categoryInput;
            Date currentDate = new Date();
            currentDate = StringUtils.removeTime(currentDate);

            System.out.println("Enter Description: ");
            newRecord.description = inputString();

            System.out.println("Enter the Amount: ");
            newRecord.amount = inputFloat();

            newRecord.date = currentDate;

            System.out.println("Payment Type ( Cash, Card, Online Payment, Mobile Payment): ");
            newRecord.paymentType = inputString();

            if (Main.checkPaymentType(newRecord.paymentType)){
                System.out.println("Wrong Payment Type");
                return false;
            }

            System.out.println("Enter Category: (Enter Full Name of Category)");
            Category.displayCategories();

            categoryInput = inputString();

            Category tempCategory;
            tempCategory = Category.isCategoryTrue(categoryInput);

            if (tempCategory == null) return false;

            newRecord.category = tempCategory;

            List<ExpenseRecord> allRecords = readRecords();

            assert allRecords != null;
            allRecords.add(newRecord);
            return writeRecords(allRecords);
        }
        catch (Exception Ex) {
            return false;
        }
    }


    //Used to read all the expense records from the file
    public static List<ExpenseRecord> readRecords() {

        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<ExpenseRecord> records;

            records = (List<ExpenseRecord>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            return records;

        } catch (Exception ex) {
            return null;
        }
    }


    //Used to write a new expense record into the file
    public static boolean writeRecords(List<ExpenseRecord> records) {

        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(records);

            objectOut.close();
            fileOut.close();

            return true;

        } catch (Exception ex) {
            return false;
        }
    }


    //Used to Display all Expense Records
    public static void displayExpenseRecords() {
        List<ExpenseRecord> allRecords= readRecords();
        assert allRecords != null;
        for(ExpenseRecord record: allRecords) {
            System.out.println(record);
        }
    }


    //Used to Display sum of all Expense Records
    public static void sumExpenseRecords() {
        List<ExpenseRecord> allRecords = readRecords();
        assert allRecords != null;
        float sumExpense = 0.0f;
        for (ExpenseRecord record : allRecords) {
            sumExpense += record.amount;
        }
        System.out.println("Total Expense:" + sumExpense);
    }


    //Used to display sum of all the expenses for the current month
    public static void sumMonthlyExpense() {
        int yearNumber;
        int monthNumber;
        float sumExpense = 0.0f;

        // Used to get the current month and year
        Calendar calendar = Calendar.getInstance();
        int inputMonth = calendar.get(Calendar.MONTH);
        int inputYear = calendar.get(Calendar.YEAR);

        //allRecords contain all the expense records stored in the file
        List<ExpenseRecord> allRecords = readRecords();

        assert allRecords != null;

        for (ExpenseRecord records : allRecords) {
            Calendar recordCalendar = Calendar.getInstance();
            recordCalendar.setTime(records.date);

            //Extracts the month and year of the current record
            monthNumber = recordCalendar.get(Calendar.MONTH);
            yearNumber = recordCalendar.get(Calendar.YEAR);

            //Checks if the current month and year matches the records month and year
            if (inputMonth == monthNumber && inputYear == yearNumber) {
                sumExpense += records.amount;
            }


        }
        System.out.println("Total Monthly Expense: " + sumExpense);
    }

    //This is a function used to sort expense records based on category, date and month
    public static void sortExpenseRecord() throws Exception {

        ExpenseRecord newRecord = new ExpenseRecord();
        String categoryInput;
        String answer;
        String date;
        Date newDate;
        int yearNumber;
        int monthNumber;

        System.out.println("Sort by \n1. Category \n2. Date \n3. Month");
        answer = inputString();

        switch (answer) {

            //Sort by Category
            case "1" -> {
                System.out.println("Enter Category (Enter full name of Category): ");
                categoryInput = inputString();
                Category.displayCategories();


                newRecord.category = Category.isCategoryTrue(categoryInput);


                List<ExpenseRecord> allRecords = readRecords();

                assert allRecords != null;
                for (ExpenseRecord records : allRecords) {
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

                List<ExpenseRecord> allRecords = readRecords();

                assert allRecords != null;

                for (ExpenseRecord records : allRecords) {
                    if (records.date.compareTo(newDate) == 0) {
                        System.out.println(records);
                    }

                }

            }
            //Sort by Month
            case "3" -> {
                System.out.println("Enter Month (Example: For June, Enter 06):");
                int inputMonth = Integer.parseInt(inputString());

                System.out.println("Enter Year (Format: YYYY):");
                int inputYear = Integer.parseInt(inputString());


                List<ExpenseRecord> allRecords = readRecords();

                assert allRecords != null;

                for (ExpenseRecord records : allRecords) {
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
