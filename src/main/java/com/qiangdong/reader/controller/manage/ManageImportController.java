package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.ImportServiceImpl;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/import")
public class ManageImportController {
    private final ImportServiceImpl importService;

    public ManageImportController(ImportServiceImpl importService) {
        this.importService = importService;
    }

    @RequestMapping("/siwei/novel")
    @RequireAdmin
    public Response<String> importNovelFromSiWei(@RequestBody BaseRequest baseRequest) throws IOException {
        return importService.importNovelFromSiWei(baseRequest);
    }
}
