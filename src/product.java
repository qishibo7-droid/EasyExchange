import java.io.Serializable;

public class product implements Serializable {
    private String name;
    private int price;
    private int id;
    private static int data = 0;
    private static final long serialVersionUID = 1L;
    private product(String name , int price){
        this.name = name;
        this.price = price;
        this.id = product.data;
        product.data = product.data + 1;
    }
    public static product creat(String name , int price){
        return new product(name , price);
    }
    public String getName(){return this.name;}
    public int getPrice(){return this.price;}
    public int getId(){return this.id;}
}
