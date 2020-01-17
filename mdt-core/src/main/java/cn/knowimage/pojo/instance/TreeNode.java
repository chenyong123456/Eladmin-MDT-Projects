package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "tree")
public class TreeNode implements Serializable {
    @Id
    private Integer id;
    private String name;
    @Transient
    private List<TreeNode> treeNodes;
    private Integer head_id;
    @Transient
    private boolean check;
    @Transient
    private boolean isLeaf;
}
