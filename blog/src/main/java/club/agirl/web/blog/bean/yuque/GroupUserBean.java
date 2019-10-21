package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupUserBean extends BaseBean {
    @JSONField(name = "user_id")
    private Integer groupId; // 团队编号
    private UserBean group; // 团队信息 <UserSerializer>
    @JSONField(name = "user_id")
    private Integer userId; // 用户编号
    private UserBean user; // 用户信息 <UserSerializer>
    private Integer role; // 角色 [0 - Owner, 1 - Member]
}
