package creat;
import people.Administrator;
import people.User;
import house.people_house;

import tool.IO;


public class Creat_People {
   public static User creatUser(){
        System.out.println("请输入用户名");
        String name = IO.IN.nextLine();
        if (people_house.User_house.containsKey(name)){
            System.out.println("该用户名已被占用，请重新输入用户名");
            return Creat_People.creatUser();
        }
        System.out.println("请设置密码");
        String password = IO.IN.nextLine();
        System.out.println("重新输入密码以校验");
        String password1 = IO.IN.nextLine();
        while (!password1.equals(password)){
            System.out.println("两次密码不同，请重新输入");
            password = IO.IN.nextLine();
            System.out.println("重新输入密码以校验");
            password1 = IO.IN.nextLine();
        }
        System.out.println("创建成功");
        User data = User.creat(name , password);
        people_house.User_house.put(name , data);
        return data;
    }
    public static Administrator creatAdministrator(){
        System.out.println("请输入用户名");
        String name = IO.IN.nextLine();
        if (people_house.Administrator_house.containsKey(name)){
            System.out.println("该用户名已被占用，请重新输入用户名");
            return Creat_People.creatAdministrator();
        }
        System.out.println("请设置密码");
        String password = IO.IN.nextLine();
        System.out.println("重新输入密码以校验");
        String password1 = IO.IN.nextLine();
        while (!password1.equals(password)){
            System.out.println("两次密码不同，请重新输入");
            password = IO.IN.nextLine();
            System.out.println("重新输入密码以校验");
            password1 = IO.IN.nextLine();
        }
        System.out.println("创建成功");
        Administrator data = Administrator.creat(name , password);
        people_house.Administrator_house.put(name , data);
        return data;
    }
}
