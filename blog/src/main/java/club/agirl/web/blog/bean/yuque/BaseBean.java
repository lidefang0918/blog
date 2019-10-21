package club.agirl.web.blog.bean.yuque;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseBean implements Serializable {
    protected Integer id;
    @JSONField(name = "updated_at")
    protected String updatedAt;
    @JSONField(name = "created_at")
    protected String createdAt;
}
