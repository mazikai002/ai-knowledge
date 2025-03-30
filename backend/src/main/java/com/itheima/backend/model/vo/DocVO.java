package com.itheima.backend.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocVO {

    private String docId;

    private String name;

    private String url;
}
