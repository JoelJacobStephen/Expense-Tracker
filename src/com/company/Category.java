package com.company;

import java.io.*;
import java.util.List;

import static com.company.Input.inputString;

public class Category implements Serializable {

    String categoryName;

    /*
    public Category(String name, Category[] subCategories) {
        this.name = name;
        this.subCategories = subCategories;
    }
    */

    static final String filepath =
            "C:\\Users\\minis\\Desktop\\THE HUB\\PROGRAMMING\\JAVA\\EXPENSE TRACKER\\Category Records";

    public static Category isCategoryTrue(String input) {

        List<Category> categories = readCategories();
        int value = 0;
        assert categories != null;
        for(Category category: categories) {
            if(input.compareToIgnoreCase(category.categoryName)==0) {
                value = 1;
                return category;
            }
        }
        System.out.println("Category doesn't exist");

        return null;
    }

    public static void addCategories() {

        Category newCategory = new Category();

        System.out.println("Enter the new category to be added to the category database: ");
        newCategory.categoryName = inputString();


        List<Category> allCategories = readCategories();

        assert allCategories != null;
        allCategories.add(newCategory);

        writeCategories(allCategories);

    }

    public static List<Category> readCategories() {

        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<Category> categories;

            categories = (List<Category>) objectIn.readObject();

            objectIn.close();
            fileIn.close();


            return categories;

        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean writeCategories(List<Category> records) {

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


    public static void displayCategories() {
        int count = 0;
        List<Category> allCategories= readCategories();
        assert allCategories != null;
        for(Category record: allCategories) {
            count++;
            System.out.println( count + "." + record.categoryName);
        }
    }
}
