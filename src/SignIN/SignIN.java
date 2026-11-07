package SignIN;

import people.Administrator;
import people.User;
import house.people_house;
import tool.IO;

public class SignIN {
   public static User signUser(){
        System.out.println("输入用户名");
        String name = IO.IN.nextLine();
        System.out.println("请输入密码");
        String password = IO.IN.nextLine();
        if (!people_house.User_house.containsKey(name)) {
            System.out.println("用户名不存在");
            return SignIN.signUser();
        }
        User data = people_house.User_house.get(name);
        if (data.getPassword().equals(password)){
            System.out.println("登录成功用户："+name);
            return data;
        }else{
            System.out.println("密码不正确，请重新输入账户和密码");
            return SignIN.signUser();
        }
    }
    public static Administrator signAdministrator(){
        System.out.println("输入用户名");
        String name = IO.IN.nextLine();
        System.out.println("请输入密码");
        String password = IO.IN.nextLine();
        if (!people_house.Administrator_house.containsKey(name)) {
            System.out.println("用户名不存在");
            return SignIN.signAdministrator();
        }
        Administrator data = people_house.Administrator_house.get(name);
        if (data.getPassword().equals(password)){
            System.out.println("登录成功用户："+name);
            return data;
        }else{
            System.out.println("密码不正确，请重新输入账户和密码");
            return SignIN.signAdministrator();
        }
    }
}
