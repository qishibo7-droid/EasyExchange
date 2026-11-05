import java.util.HashMap;
import java.util.List;

public class WareHouse {
    public static HashMap<Integer , product> warehouse = new HashMap<>();//用id和对象存储商品
    public static HashMap<String , List<product>> reverse = new HashMap<>();
}
