package net.github.rtc.app.model.dto.user;

public class ProfileHeaderDto {
    private String name;
    private String imageId;
    private int unreadMessageCount;

    public ProfileHeaderDto() { }

    public ProfileHeaderDto(String name, String imageUrl) {
        this.name = name;
        this.imageId = imageUrl;
    }

    public ProfileHeaderDto(String name, String imageId, int unreadMessageCount) {
        this.name = name;
        this.imageId = imageId;
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }
}
