package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocBean extends BaseBean {
    protected String slug; // 文档路径
    protected String title; // 标题
    @JSONField(name = "user_id")
    protected Integer userId; // 文档创建人 user_id
    protected String format; // 描述了正文的格式 [asl, markdown]
    @JSONField(name = "public")
    protected Integer visibility; // 公开级别 [0 - 私密, 1 - 公开]
    protected Integer status; // 状态 [0 - 草稿, 1 - 发布]
    @JSONField(name = "likes_count")
    protected Integer likesCount; // 喜欢数量
    @JSONField(name = "comments_count")
    protected Integer commentsCount; // 评论数量
    @JSONField(name = "content_updated_at")
    protected Date contentUpdatedAt; // 文档内容更新时间
    protected BookBean book;
    ; // <BookSerializer> 所属知识库
    protected UserBean user; // <UserSerializer> 所属团队（个人）
    @JSONField(name = "last_editor")
    protected UserBean lastEditor; // <UserSerializer> 最后修改人
}
