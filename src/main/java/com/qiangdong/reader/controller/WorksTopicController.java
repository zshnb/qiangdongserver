package com.qiangdong.reader.controller;

import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.request.works_topic.ListTopicWorksRequest;
import com.qiangdong.reader.request.works_topic.ListWorksTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.works_topic.ListWorksTopicWorksResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/works-topic")
public class WorksTopicController {

    private final WorksTopicServiceImpl worksTopicService;

    public WorksTopicController(WorksTopicServiceImpl worksTopicService) {
        this.worksTopicService = worksTopicService;
    }

    @PostMapping("/list")
    public PageResponse<WorksTopicDto> listTopic(@RequestBody ListWorksTopicRequest request){
        return worksTopicService.listWorksTopic(request);
    }

    @PostMapping("/list-works")
    public ListWorksTopicWorksResponse listTopicWorks(@RequestBody ListTopicWorksRequest request){
        return worksTopicService.listTopicWorks(request, new WorksTopic());
    }
}
