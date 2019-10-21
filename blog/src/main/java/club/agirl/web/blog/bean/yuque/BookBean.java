package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookBean extends BaseBean {
    private String type; // 类型 [Book - 文档]
    private String slug; // 仓库路径
    private String name; // 名称
    private String namespace; // 仓库完整路径 user.login/book.slug
    @JSONField(name = "user_id")
    private Integer userId; // 所属的团队/用户编号
    private UserBean user;
    private String description; // 介绍
    @JSONField(name = "creator_id")
    private Integer creatorId; // 创建人 User Id
    @JSONField(name = "public")
    private Integer visibility; // 公开状态 [1 - 公开, 0 - 私密]
    @JSONField(name = "toc_yml")
    private String tocYml; // 目录原文
    @JSONField(name = "items_count")
    private Integer itemsCount; // 文档数量
    @JSONField(name = "likes_count")
    private Integer likesCount; // 喜欢数量
    @JSONField(name = "watches_count")
    private Integer watchesCount; // 订阅数量


}
