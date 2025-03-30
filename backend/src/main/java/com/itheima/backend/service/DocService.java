package com.itheima.backend.service;

import com.itheima.backend.model.vo.DocVO;

import java.util.List;

public interface DocService {

    void loadData();

    List<DocVO> list();

}
