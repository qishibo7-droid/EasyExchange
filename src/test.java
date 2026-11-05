import java.util.stream.Collectors;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        User user = User.creat("小明" , "12345678QQQ");
        Administrator administrator = Administrator.creat("管理员" , "33258111");
        user.put("手机" , 3999);
        user.put("手机" , 2999);
        request_lite.request_lite.forEach(a -> System.out.println(a.getRequest()+a.getP().getId()+a.getP().getName()+a.getP().getPrice()));
        administrator.address();
        administrator.address();
        WareHouse.warehouse.forEach((k,v) -> System.out.println("id:"+k+",商品名为："+v.getName()+",价格为："+v.getPrice()));
        WareHouse.reverse.forEach((name, list) -> System.out.println(
                "商品名为" + name + "的有：" +
                        list.stream()
                                .map(p -> "(" + p.getId() + ", "+p.getName()+", "+ p.getPrice() + ")")
                                .collect(Collectors.joining(", "))));
        user.check("手机");
        user.check("电脑");
    }
}
