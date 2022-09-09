package sample;

public class Post {
    private String post;
    private int oklad;

    public Post(String post, int oklad) {
        this.post = post;
        this.oklad = oklad;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setOklad(int oklad) {
        this.oklad = oklad;
    }

    public String getPost() {
        return post;
    }

    public int getOklad() {
        return oklad;
    }
}
