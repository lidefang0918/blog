package club.agirl.web.blog.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Doc implements Serializable {
    private String id; // 文档编号
    private String slug; // 文档路径
    private String title; // 标题
    private String userId; // 文档创建人 user_id
    private String format; // 描述了正文的格式 [asl, markdown]
    private String visibility; // 是否公开 [1 - 公开, 0 - 私密]
    private String status; // 状态 [1 - 正常, 0 - 草稿]
    private String likesCount; // 喜欢数量
    private String commentsCount; // 评论数量
    private String contentUpdatedAt; // 文档内容更新时间
    private String book; // <BookSerializer> 所属知识库
    private String user; // <UserSerializer> 所属团队（个人）
    private String lastEditor; // <UserSerializer> 最后修改人
    private String createdAt; // 创建时间
    private String updatedAt; // 更新时间
}
