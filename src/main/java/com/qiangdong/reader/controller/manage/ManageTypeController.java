package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.request.type.AddOrUpdateTypeRequest;
import com.qiangdong.reader.request.type.DeleteTypeRequest;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/manage/type")
public class ManageTypeController {

    @Autowired
    private TypeServiceImpl typeService;

    @PostMapping("/list")
    public PageResponse<TypeDto> listType(@RequestBody ListTypeRequest request){
        return typeService.listTypeByBelong(request);
    }

    @PostMapping("/add-update")
    public Response<Type> addOrUpdateType(@RequestBody AddOrUpdateTypeRequest request){
        return typeService.addOrUpdateType(request);
    }

    @DeleteMapping("/{typeId}")
    public Response<String> deleteType(@RequestBody DeleteTypeRequest request){
        return typeService.deleteType(request, new Type());
    }


}
