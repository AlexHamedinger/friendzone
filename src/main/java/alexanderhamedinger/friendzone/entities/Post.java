package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;

@Entity
public class Post extends BasicEntity {

    private long poster;
    private String title;
    @Lob
    private byte[] postImage;

    //getter & setter
    public long getPoster() {
        return poster;
    }
    public void setPoster(long poster) {
        this.poster = poster;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public byte[] getPostImage() {
        return postImage;
    }
    public String getImageURL() {
        return "/postimages/" + this.getId();
    }
    public String getDetailURL() {
        return "/postDetail?id=" + this.getId();
    }
    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    @Override
    public String toString() {
        String y_n = "NO";
        if(postImage != null) {
            y_n = "YES";
        }
        return "\n#########################################################################\n" +
                "PostID: " + this.getId() + "\n" +
                "Posted by: " + poster + "\n" +
                "Title: " + title + "\n" +
                "Image (Y/N): " + y_n + "\n" +
                "Date: " + this.getCreationDateAsString() + "\n" +
                "#########################################################################\n";
    }
}
