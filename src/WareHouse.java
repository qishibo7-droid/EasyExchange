import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WareHouse {
    public static HashMap<Integer , product> warehouse = new HashMap<>();//用id和对象存储商品
    public static HashMap<String , List<product>> reverse = new HashMap<>();
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
