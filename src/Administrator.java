import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Administrator {
    private String name;
    private String password;
    private Administrator(String name , String password){this.name = name ; this.password = password;}
    public static Administrator creat(String name , String password){return new Administrator(name , password);}
    public void address(){
        for (request re : request_lite.request_lite){
            if (re.getRequest().equals("buy")) this.buy(re);
            else this.put(re);
        }
    }
    public void put(request request){
        WareHouse.warehouse.put(request.getP().getId() , request.getP());
        if (!WareHouse.reverse.containsKey(request.getP().getName())|| WareHouse.reverse.get(request.getP().getName()).isEmpty()){
            WareHouse.reverse.put(request.getP().getName() , new ArrayList<>(Arrays.asList(request.getP())));
        }else WareHouse.reverse.compute(request.getP().getName() , (k ,v) -> {v.add(request.getP());return v;});
        request_lite.request_lite.remove(request);
    }
    public void buy(request request){
        if (WareHouse.warehouse.containsKey(request.getP().getId())){WareHouse.warehouse.remove(request.getP().getId());}
        else {System.out.println("没有找到该商品");}
    }
}
