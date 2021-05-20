package com.qiangdong.reader.search;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.common.ElasticSearchIndexConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = ElasticSearchIndexConstant.USER_INDEX_NAME, createIndex = false)
public class UserForSearch {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String username;
    private String avatar;
    private Integer fansUserCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFansUserCount() {
        return fansUserCount;
    }

    public void setFansUserCount(Integer fansUserCount) {
        this.fansUserCount = fansUserCount;
    }
}
