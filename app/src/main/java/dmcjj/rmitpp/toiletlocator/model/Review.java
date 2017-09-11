package dmcjj.rmitpp.toiletlocator.model;

/**
 * Created by A on 2/09/2017.
 */

public class Review
{
    private String owner;
    private String text;
    private int rating;

    public Review(){

    }

    public Review(String uid, String text, int rating){
        this.owner = uid;
        this.text = text;
    }

    public String getUid() {
        return owner;
    }

    public void setUid(String uid) {
        this.owner = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




}
