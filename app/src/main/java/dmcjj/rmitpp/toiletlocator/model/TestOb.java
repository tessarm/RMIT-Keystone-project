package dmcjj.rmitpp.toiletlocator.model;

/**
 * Created by A on 30/08/2017.
 */

public class TestOb
{
    private String name;
    private String body;
    private int value;
    private long date;

    public TestOb(String name, String body, int value, long date){
        this.name = name;
        this.body = body;
        this.value = value;
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }




}
