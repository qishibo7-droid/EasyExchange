import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String name;
    private String password;
    private User(String name , String password){this.name = name ; this.password = password;}
    public static User creat(String name , String password){return new User(name , password);}
    public void put(String name , int price){
        //产生一个商品对象并序列号存储到指定位置等待管理员批准
        product p = product.creat(name , price);
        request Re = request.creat(buy_put.B.action , p);
        request_lite.request_lite.add(Re);
    }
    public void buy(int id){
        if (!WareHouse.warehouse.containsKey(id)) {
            System.out.println("该商品不存在");return;}
        product p = WareHouse.warehouse.get(id);
        request Re = request.creat(buy_put.A.action , p);
        request_lite.request_lite.add(Re);
}
    public void check(String name){
        if (WareHouse.reverse.containsKey(name)){
            List<product> lite = WareHouse.reverse.get(name);
            if (lite.isEmpty()){System.out.println("没有相关商品");return;}
            String print = lite.stream()
                    .map(p -> "(" + p.getId() + ", "+p.getName()+", "+ p.getPrice() + ")")
                    .collect(Collectors.joining(", "));
            System.out.println(print);
        }
        else System.out.println("没有相关商品");
    }
}
