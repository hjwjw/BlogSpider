package io.github.hjwjw.entity;

/**
 * The Article class.
 *
 * @author HJW
 * @date 2018/10/13
 */

public class Article {

    private String title;

    private String createDate;

    private String markdownContent;

    private String privateStatus;

    private String tags;

    private String categories;

    private String type;

    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent;
    }

    public String getPrivateStatus() {
        return privateStatus;
    }

    public void setPrivateStatus(String privateStatus) {
        this.privateStatus = privateStatus;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
