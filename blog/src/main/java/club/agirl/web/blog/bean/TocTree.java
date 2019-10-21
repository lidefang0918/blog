package club.agirl.web.blog.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TocTree implements Serializable {
    private String name;
    private Integer bookId;
    private Integer docId;
    private Integer depth;
    List<TocTree> children;
}
