package people;

import entity.Product;
import entity.Request;
import house.WareHouse;
import house.request_lite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Administrator implements adress , Serializable {
    private String name;
    private String password;
    private Administrator(String name , String password){this.name = name ; this.password = password;}
    public static Administrator creat(String name , String password){return new Administrator(name , password);}
    public void address(){
        for (Request re : request_lite.request_lite){
            if (re.getRequest().equals("people.buy")) this.buy(re);
            else this.put(re);
        }
        request_lite.request_lite.clear();
    }


    public void put(Request request){
        Product p = request.getP();
        WareHouse.warehouse.put(p.getId() , p);
        if (!WareHouse.reverse.containsKey(p.getName())|| WareHouse.reverse.get(p.getName()).isEmpty()){
            WareHouse.reverse.put(p.getName() , new ArrayList<>(Arrays.asList(p)));
        }else WareHouse.reverse.compute(p.getName() , (k , v) -> {v.add(p);return v;});
    }


    public void buy(Request request){
        Product p = request.getP();
        if (WareHouse.warehouse.containsKey(p.getId())){
            WareHouse.warehouse.remove(p.getId());
            Iterator<Product> iterator = WareHouse.reverse.get(p.getName()).iterator();
            while (iterator.hasNext()){
                Product data = iterator.next();
                if (data.getId() == p.getId()) iterator.remove();
            }
            System.out.println(p.getName()+"被成功拍下");
        }
        else {System.out.println("没有找到该商品或者该商品已经被他人拍走。");}
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}