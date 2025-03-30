package com.itheima.backend.controller.v1;

import com.itheima.backend.common.RestResult;
import com.itheima.backend.model.vo.DocVO;
import com.itheima.backend.service.DocService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/doc")
public class Doc {

    @Autowired
    private DocService docService;

    @GetMapping("/list")
    public RestResult<List<DocVO>> list() {
        return RestResult.buildSuccessResult(docService.list());
    }

    @GetMapping("/load")
    public RestResult<String> load() {
        docService.loadData();
        return RestResult.buildSuccessResult();
    }
}
