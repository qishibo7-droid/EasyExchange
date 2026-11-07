package house;

import entity.Product;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WareHouse {
    static String path = "D:\\EasyExchange\\src\\House.txt";
    public static HashMap<Integer , Product> warehouse = new HashMap<>();//用id和对象存储商品
    public static HashMap<String , List<Product>> reverse = new HashMap<>();
    static {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            WareHouse.warehouse = (HashMap<Integer, Product>) in.readObject();
            WareHouse.reverse = (HashMap<String, List<Product>>) in.readObject();
        } catch (FileNotFoundException | EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void save(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(WareHouse.warehouse);
            out.writeObject(WareHouse.reverse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void print_warehouse(){
        WareHouse.warehouse.forEach((k,v) -> System.out.println("id:"+k+",商品名为："+v.getName()+",价格为："+v.getPrice()));
    }
    public static void print_reverse(){
        WareHouse.reverse.forEach((name, list) -> System.out.println(
                "商品名为" + name + "的有：" +
                        list.stream()
                                .map(p -> "(" + p.getId() + ", "+p.getName()+", "+ p.getPrice() + ")")
                                .collect(Collectors.joining(", "))));
    }
}
