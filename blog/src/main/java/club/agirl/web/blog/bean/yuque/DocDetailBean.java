package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class DocDetailBean extends DocBean {

    @JSONField(name = "book_id")
    private Integer bookId;
    private String body; // 正文 Markdown 源代码
    @JSONField(name = "body_draft")
    private String bodyDraft; // 草稿 Markdown 源代码
    @JSONField(name = "body_html")
    private String bodyHtml; // 转换过后的正文 HTML
    @JSONField(name = "body_lake")
    private String bodyLake; // 语雀 lake 格式的文档内容
    @JSONField(name = "published_at")
    private Date publishedAt;
    @JSONField(name = "first_published_at")
    private Date firstPublishedAt;
    @JSONField(name = "word_count")
    private Integer wordCount;
    @JSONField(name = "_serializer")
    private String serializer;
    @JSONField(name = "action_type")
    private String actionType; // 操作类型：publish - 发布、 update - 更新、 delete - 删除
    private boolean publish; // 文档是否为第一次发布，第一次发布时为 true
    private String path; // 文档的完整访问路径（不包括域名）

}