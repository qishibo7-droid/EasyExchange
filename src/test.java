public class test {
    public static void main(String[] args) {
        User user = User.creat("小明" , "12345678QQQ");
        Administrator administrator = Administrator.creat("管理员" , "33258111");
        user.put("手机" , 3999);
        user.put("手机" , 2999);
        user.put("电脑" , 12999);
        user.put("小红帽" , 2999);
        user.buy(2);
        user.buy(1);
        user.buy(2);
        request_lite.request_lite.forEach(a -> System.out.println(a.getRequest()+a.getP().getId()+a.getP().getName()+a.getP().getPrice()));
        administrator.address();
        user.buy(2);
        user.buy(1);
        user.buy(2);
        administrator.address();
    }
}
