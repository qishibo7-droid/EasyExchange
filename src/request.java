import java.io.Serializable;

public class request implements Serializable {
    private String request;
    private product p;
    private request(String request , product p){
        this.request = request;
        this.p = p;
    }
    public static request creat(String request , product p){
        return new request(request,p);
    }
    public String getRequest(){return this.request;}
    public product getP(){return this.p;}
}
