package club.agirl.web.blog.bean.yuque;

import lombok.Data;

import java.io.Serializable;

@Data
public class TocBean implements Serializable {
    private String title; // 标题
    private String slug; // 文档路径
    private Integer depth; // 目录层次
}
