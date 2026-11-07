package entity;

import java.io.Serializable;

public class Request implements Serializable {
    private String request;
    private Product p;
    private static final long serialVersionUID = 1L;
    private Request(String request , Product p){
        this.request = request;
        this.p = p;
    }
    public static Request creat(String request , Product p){
        return new Request(request,p);
    }
    public String getRequest(){return this.request;}
    public Product getP(){return this.p;}
}