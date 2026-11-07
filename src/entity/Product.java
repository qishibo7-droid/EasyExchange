package entity;

import java.io.*;

public class Product implements Serializable {
    static String path = "D:\\EasyExchange\\src\\int.txt";
    private String name;
    private int price;
    private int id;
    private static int data = 0;
    private static final long serialVersionUID = 1L;

    private Product(String name , int price){
        this.name = name;
        this.price = price;
        this.id = Product.data;
        Product.data = Product.data + 1;
    }
    public static Product creat(String name , int price){
        return new Product(name , price);
    }
    public String getName(){return this.name;}
    public int getPrice(){return this.price;}
    public int getId(){return this.id;}

}
