package people;

public enum buy_put {
    A("people.buy") , B("people.put");
    String action;
    buy_put(String action){this.action = action;}
}
