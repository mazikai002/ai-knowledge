package com.itheima.backend.service.impl;

import lombok.RequiredArgsConstructor;

import com.itheima.backend.model.vo.DocVO;
import com.itheima.backend.service.DocService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class DocServiceImpl implements DocService {
    @Override
    public void loadData() {

    }

    @Override
    public List<DocVO> list() {
        return List.of();
    }
}
