
package com.entrepidea.imgurdnloadr.dnldrtask.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("datetime")
    @Expose
    private Integer datetime;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("cover_width")
    @Expose
    private Integer coverWidth;
    @SerializedName("cover_height")
    @Expose
    private Integer coverHeight;
    @SerializedName("account_url")
    @Expose
    private String accountUrl;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("layout")
    @Expose
    private String layout;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("ups")
    @Expose
    private Integer ups;
    @SerializedName("downs")
    @Expose
    private Integer downs;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("is_album")
    @Expose
    private Boolean isAlbum;
    @SerializedName("vote")
    @Expose
    private Object vote;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;
    @SerializedName("nsfw")
    @Expose
    private Boolean nsfw;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("topic_id")
    @Expose
    private Integer topicId;
    @SerializedName("images_count")
    @Expose
    private Integer imagesCount;
    @SerializedName("in_gallery")
    @Expose
    private Boolean inGallery;
    @SerializedName("is_ad")
    @Expose
    private Boolean isAd;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("ad_type")
    @Expose
    private Integer adType;
    @SerializedName("ad_url")
    @Expose
    private String adUrl;
    @SerializedName("in_most_viral")
    @Expose
    private Boolean inMostViral;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("animated")
    @Expose
    private Boolean animated;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("bandwidth")
    @Expose
    private Long bandwidth;
    @SerializedName("mp4")
    @Expose
    private String mp4;
    @SerializedName("gifv")
    @Expose
    private String gifv;
    @SerializedName("mp4_size")
    @Expose
    private Integer mp4Size;
    @SerializedName("looping")
    @Expose
    private Boolean looping;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getDatetime() {
        return datetime;
    }

    public void setDatetime(Integer datetime) {
        this.datetime = datetime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(Integer coverWidth) {
        this.coverWidth = coverWidth;
    }

    public Integer getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(Integer coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getIsAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public Object getVote() {
        return vote;
    }

    public void setVote(Object vote) {
        this.vote = vote;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(Integer imagesCount) {
        this.imagesCount = imagesCount;
    }

    public Boolean getInGallery() {
        return inGallery;
    }

    public void setInGallery(Boolean inGallery) {
        this.inGallery = inGallery;
    }

    public Boolean getIsAd() {
        return isAd;
    }

    public void setIsAd(Boolean isAd) {
        this.isAd = isAd;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Boolean getInMostViral() {
        return inMostViral;
    }

    public void setInMostViral(Boolean inMostViral) {
        this.inMostViral = inMostViral;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAnimated() {
        return animated;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getGifv() {
        return gifv;
    }

    public void setGifv(String gifv) {
        this.gifv = gifv;
    }

    public Integer getMp4Size() {
        return mp4Size;
    }

    public void setMp4Size(Integer mp4Size) {
        this.mp4Size = mp4Size;
    }

    public Boolean getLooping() {
        return looping;
    }

    public void setLooping(Boolean looping) {
        this.looping = looping;
    }

    public static class Tag {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("followers")
        @Expose
        private Integer followers;
        @SerializedName("total_items")
        @Expose
        private Integer totalItems;
        @SerializedName("following")
        @Expose
        private Boolean following;
        @SerializedName("background_hash")
        @Expose
        private String backgroundHash;
        @SerializedName("background_is_animated")
        @Expose
        private Boolean backgroundIsAnimated;
        @SerializedName("is_promoted")
        @Expose
        private Boolean isPromoted;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("logo_hash")
        @Expose
        private Object logoHash;
        @SerializedName("logo_destination_url")
        @Expose
        private Object logoDestinationUrl;
        @SerializedName("description_annotations")
        @Expose
        private DescriptionAnnotations descriptionAnnotations;
        @SerializedName("accent")
        @Expose
        private Object accent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Integer getFollowers() {
            return followers;
        }

        public void setFollowers(Integer followers) {
            this.followers = followers;
        }

        public Integer getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Integer totalItems) {
            this.totalItems = totalItems;
        }

        public Boolean getFollowing() {
            return following;
        }

        public void setFollowing(Boolean following) {
            this.following = following;
        }

        public String getBackgroundHash() {
            return backgroundHash;
        }

        public void setBackgroundHash(String backgroundHash) {
            this.backgroundHash = backgroundHash;
        }

        public Boolean getBackgroundIsAnimated() {
            return backgroundIsAnimated;
        }

        public void setBackgroundIsAnimated(Boolean backgroundIsAnimated) {
            this.backgroundIsAnimated = backgroundIsAnimated;
        }

        public Boolean getIsPromoted() {
            return isPromoted;
        }

        public void setIsPromoted(Boolean isPromoted) {
            this.isPromoted = isPromoted;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getLogoHash() {
            return logoHash;
        }

        public void setLogoHash(Object logoHash) {
            this.logoHash = logoHash;
        }

        public Object getLogoDestinationUrl() {
            return logoDestinationUrl;
        }

        public void setLogoDestinationUrl(Object logoDestinationUrl) {
            this.logoDestinationUrl = logoDestinationUrl;
        }

        public DescriptionAnnotations getDescriptionAnnotations() {
            return descriptionAnnotations;
        }

        public void setDescriptionAnnotations(DescriptionAnnotations descriptionAnnotations) {
            this.descriptionAnnotations = descriptionAnnotations;
        }

        public Object getAccent() {
            return accent;
        }

        public void setAccent(Object accent) {
            this.accent = accent;
        }

        /**
         * Created by jonat on 6/29/2017.
         */

        public static class DescriptionAnnotations {
        }
    }
}

