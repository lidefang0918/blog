package club.agirl.web.blog.bean;

import club.agirl.web.blog.bean.yuque.DocDetailBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocDetail implements Serializable {

    private Integer id; // 文档编号
    private String title; // 标题
    private Integer bookId; // 仓库编号，就是 repo_id
    private String bodyHtml; // 转换过后的正文 HTML
    private Integer visibility; // 公开级别 [0 - 私密, 1 - 公开]
    private Integer status; // 状态 [0 - 草稿, 1 - 发布]
    private Integer wordCount; // 文章字数
    private Date publishedAt; // 发布时间
    private Date firstPublishedAt; // 首次发布时间

    //    private String slug; // 文档路径
    //    private Integer userId; // 用户/团队编号
    //    private String format; // 描述了正文的格式 [lake , markdown]
    //    private String body; // 正文 Markdown 源代码
    //    private String bodyDraft; // 草稿 Markdown 源代码
    //    private String bodyLake; // 语雀 lake 格式的文档内容
    //    private Integer creatorId; // 文档创建人 User Id
    //    private String likesCount; // 赞数量
    //    private String commentsCount; // 评论数量
//    private Date contentUpdatedAt; // 文档内容更新时间
//    private Date deletedAt; // 删除时间，未删除为 null
//    private Date createdAt; // 创建时间
//    private Date updatedAt; // 更新时间

    public static DocDetail of(DocDetailBean docDetailBean){
        return DocDetail.builder()
                .id(docDetailBean.getId())
                .title(docDetailBean.getTitle())
                .bookId(docDetailBean.getBookId())
                .bodyHtml(docDetailBean.getBodyHtml())
                .visibility(docDetailBean.getVisibility())
                .status(docDetailBean.getStatus())
                .wordCount(docDetailBean.getWordCount())
                .publishedAt(docDetailBean.getPublishedAt())
                .firstPublishedAt(docDetailBean.getFirstPublishedAt())
                .build();
    }
}
