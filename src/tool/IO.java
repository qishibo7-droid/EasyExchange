package tool;

import java.util.Scanner;

/**
 * 全局统一的输入工具类。
 * 整个程序只允许存在这一个 Scanner，避免多个 Scanner 各自缓冲同一份 System.in。
 */
public class IO {
    public static final Scanner IN = new Scanner(System.in);
}
