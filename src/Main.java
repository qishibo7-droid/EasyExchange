import SignIN.SignIN;
import creat.Creat_People;
import entity.Product;
import house.WareHouse;
import people.Administrator;
import people.User;
import house.people_house;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tool.IO;

public class Main {
    boolean flag_administrator = false;
    boolean flag_User = false;
    public static void main(String[] args) {
       Main main = new Main();
        Administrator administrator = null;
        User user = null;
        String data = IO.IN.nextLine();
        HashMap<String , String> map = new HashMap<>();
        List<String> lite1 = new ArrayList<>(List.of("break","创建管理员","创建用户","管理员登录","用户登录","上传商品","购买商品","搜索商品","处理请求","查看仓库"));
        List<String> lite2 = new ArrayList<>(List.of(
                "退出程序，结束本次操作并保存数据",
                "注册新的管理员账号并设置登录密码",
                "注册新的普通用户账号并设置登录密码",
                "管理员登录系统，享有审批权限",
                "用户登录系统，可上传和购买商品",
                "将商品名称与价格提交入库，待管理员审批",
                "按商品编号下单购买，从仓库中移除",
                "按商品名称查询仓库中的库存与价格",
                "管理员逐条审批未处理的上传和购买请求",
                "列出仓库中全部商品（编号、名称、价格）"
        ));
        for (int i = 0 ; i<lite1.size() ;i++) map.put(lite1.get(i) , lite2.get(i));
        while (!data.equals("break")) {
            if (!lite1.contains(data) && !data.equals("help")) {      // ← 新增这一条，放最前面
                System.out.println("无效语句");
            }
            else if (data.equals("创建管理员")) {Creat_People.creatAdministrator();}
            else if (data.equals("help")) System.out.println(map);
            else if (data.equals("创建用户")) Creat_People.creatUser();

            else if (data.equals("管理员登录")) {
                if (main.flag_administrator) System.out.println("管理员已经登录");
                else administrator = main.Administratoi_sign();
            }
            else if (!main.flag_administrator) System.out.println("管理员未登录，系统未启动");


            else if (data.equals("用户登录")) {
                if (main.flag_User) System.out.println("用户已经登录");
                else user = main.User_sign();
            }
            else if (!main.flag_User) System.out.println("用户未登录");

            else if (data.equals("上传商品")) {
                System.out.println("输入商品名称");
                String name = IO.IN.nextLine();
                System.out.println("输入价格");
                int price = IO.IN.nextInt();
                IO.IN.nextLine();
                user.put(name, price);
            }
            else if (data.equals("购买商品")) {
                System.out.println("输入购买商品id");
                int id = IO.IN.nextInt();
                IO.IN.nextLine();
                user.buy(id);
            }
            else if (data.equals("搜索商品")) {
                System.out.println("输入搜索商品名称");
                String name = IO.IN.nextLine();
                user.check(name);
            }
            else if (data.equals("处理请求")) {
                administrator.address();
            }
            else if (data.equals("查看仓库")) WareHouse.print_warehouse();
            else System.out.println("无效语句");
            data = IO.IN.nextLine();
        }
        System.out.println("系统已经关闭");
        WareHouse.save();
        people_house.save();
    }
    public Administrator Administratoi_sign(){
        Administrator administrator =  SignIN.signAdministrator();
        System.out.println("管理员："+administrator.getName()+"已经登录");
        System.out.println("系统以启动");
        this.flag_administrator = true;
        return administrator;
    }
    public User User_sign(){
        User user = SignIN.signUser();
        System.out.println("用户："+user.getName()+"已经登录");
        this.flag_User = true;
        return user;
    }
}
