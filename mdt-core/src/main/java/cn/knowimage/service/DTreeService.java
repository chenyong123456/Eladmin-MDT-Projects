package cn.knowimage.service;

import cn.knowimage.pojo.vo.TNM;
import cn.knowimage.pojo.instance.TreeNode;


import java.util.List;

public interface DTreeService {

    //根据头节点返回整个树
    TreeNode getTreeByTNM(TNM tnm);

    //根据头节点id拿到其子节点集合
    List<TreeNode> getTreeByHeadId(Integer head_id);

    //根据头节点id拿到其叶子节点集合
    List<TreeNode> getTreeLeafByHeadId(Integer head_id);

    //根据TNM拿到头节点
    TreeNode getHeadNode(TNM tnm);

    //根据id拿到节点
    TreeNode getNodeById(Integer id);

}
