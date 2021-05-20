package com.qiangdong.reader.response.works_topic;

import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.novel.NovelDto;

import java.util.ArrayList;
import java.util.List;


public class ListWorksTopicWorksResponse {

    private WorksTopicDto topicDto = new WorksTopicDto();
    private List<NovelDto> novels = new ArrayList<>();
    private List<ComicDto> comics = new ArrayList<>();

    public WorksTopicDto getTopicDto() {
        return topicDto;
    }

    public void setTopicDto(WorksTopicDto topicDto) {
        this.topicDto = topicDto;
    }

    public List<NovelDto> getNovels() {
        return novels;
    }

    public void setNovels(List<NovelDto> novels) {
        this.novels = novels;
    }

    public List<ComicDto> getComics() {
        return comics;
    }

    public void setComics(List<ComicDto> comics) {
        this.comics = comics;
    }
}
