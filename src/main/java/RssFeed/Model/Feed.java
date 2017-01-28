package RssFeed.Model;

/**
 * Created by martreux on 27/01/2017.
 */
public class Feed {

    private String id;
    private String owner;
    private String url;
    private String title;
    private String link;
    private String description;
    private String image;
    private String pubDate;
    private FeedItem[] items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeedItem[] getItems() {
        return items;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setItems(FeedItem[] items) {
        this.items = items;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

