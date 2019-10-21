package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserBean extends BaseBean {
    private String type; // 类型 [User - 用户, Group - 团队]
    private String login; // 用户个人路径
    private String name; // 昵称
    @JSONField(name = "avatar_url")
    private String avatarUrl; // 头像 URL
    @JSONField(name = "large_avatar_url")
    private String largeAvatarUrl;
    @JSONField(name = "medium_avatar_url")
    private String mediumAvatarUrl;
    @JSONField(name = "small_avatar_url")
    private String smallAvatarUrl;
    @JSONField(name = "books_count")
    private Integer booksCount; // 仓库数量
    @JSONField(name = "public_books_count")
    private Integer publicBooksCount; // 公开仓库数量
    @JSONField(name = "followers_count")
    private Integer followersCount; // 粉丝数量
    @JSONField(name = "following_count")
    private Integer followingCount; // 关注数量
    @JSONField(name = "space_id")
    private Integer spaceId; // 企业空间编号
    @JSONField(name = "account_id")
    private Integer accountId; // 用户账户编号
    @JSONField(name = "owner_id")
    private Integer ownerId; // 团队创建人，仅适用于 type - 'Group'
    @JSONField(name = "members_count")
    private Integer membersCount; // 团队成员数量
    private String description; // 介绍
}
