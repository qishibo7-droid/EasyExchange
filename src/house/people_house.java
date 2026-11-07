package house;

import people.Administrator;
import people.User;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class people_house {
    static String path = "D:\\EasyExchange\\src\\people.txt";
    public static HashMap<String , User> User_house = new HashMap<>();//用用户名和对象存储用户
    public static HashMap<String , Administrator> Administrator_house = new HashMap<>();//用用户名和对象存储管理员
    static {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            people_house.User_house = (HashMap<String, User>) in.readObject();
            people_house.Administrator_house = (HashMap<String, Administrator>) in.readObject();
        } catch (FileNotFoundException | EOFException e) {
            //第一次运行还没有数据文件，保持空仓库
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void save(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(people_house.User_house);
            out.writeObject(people_house.Administrator_house);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
