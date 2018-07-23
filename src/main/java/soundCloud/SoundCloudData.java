package soundCloud;

import java.io.Serializable;

public class SoundCloudData implements Serializable{

    int id;
    String title;
    String permalink_url;
    User user;

    public SoundCloudData() {
    }

    public SoundCloudData(int id, String title, String permalink_url, User user) {
        this.id = id;
        this.title = title;
        this.permalink_url = permalink_url;
        this.user = user;
    }

    @Override
    public String toString() {
        return "SoundCloudData{" +
                "id=" + id +
                ", Title='" + title + '\'' +
                ", PermalinkURL='" + permalink_url + '\'' +
                ", User='" + user.toString() + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    public void setPermalink_url(String permalinkURL) {
        permalink_url = permalinkURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

