public class Post {
    String post;
    int oklad;

    public void setPost(String post) {
        this.post = post;
    }

    public void setOklad(int oklad) {
        this.oklad = oklad;
    }

    public Post(String post, int oklad) {
        this.post = post;
        this.oklad = oklad;
    }

    public String getPost() {
        return post;
    }

    public int getOklad() {
        return oklad;
    }
}
