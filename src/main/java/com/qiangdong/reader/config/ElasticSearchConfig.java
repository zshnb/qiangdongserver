package com.qiangdong.reader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("es")
public class ElasticSearchConfig {
    private boolean indexNovel = false;
    private boolean indexComic = false;
    private boolean indexUserActivity = false;
    private boolean indexUser = false;
    private boolean indexTopic = false;

    public boolean isIndexNovel() {
        return indexNovel;
    }

    public void setIndexNovel(boolean indexNovel) {
        this.indexNovel = indexNovel;
    }

    public boolean isIndexComic() {
        return indexComic;
    }

    public void setIndexComic(boolean indexComic) {
        this.indexComic = indexComic;
    }

    public boolean isIndexUserActivity() {
        return indexUserActivity;
    }

    public void setIndexUserActivity(boolean indexUserActivity) {
        this.indexUserActivity = indexUserActivity;
    }

    public boolean isIndexUser() {
        return indexUser;
    }

    public void setIndexUser(boolean indexUser) {
        this.indexUser = indexUser;
    }

    public boolean isIndexTopic() {
        return indexTopic;
    }

    public void setIndexTopic(boolean indexTopic) {
        this.indexTopic = indexTopic;
    }
}
