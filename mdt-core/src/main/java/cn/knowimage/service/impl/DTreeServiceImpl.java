package cn.knowimage.service.impl;

import cn.knowimage.pojo.vo.TNM;
import cn.knowimage.pojo.instance.TreeNode;
import cn.knowimage.mapper.TNMMapper;
import cn.knowimage.mapper.TreeMapper;
import cn.knowimage.service.DTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTreeServiceImpl implements DTreeService {

    @Autowired
    private TreeMapper treeMapper;

    @Autowired
    private TNMMapper tnmMapper;

    @Override
    public TreeNode getTreeByTNM(TNM tnm) {
        Integer treeId = tnmMapper.findHeadId(tnm.getTnm_key(),tnm.getDisease());
        TreeNode treeNode = treeMapper.selectByPrimaryKey(treeId);
        DeepReadTree(treeNode);
        return treeNode;
    }

    @Override
    public List<TreeNode>  getTreeByHeadId(Integer head_id) {

        Example example = new Example(TreeNode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("head_id",head_id);

        List<TreeNode> treeNodes = treeMapper.selectByExample(example);
        return treeNodes;
    }

    @Override
    public List<TreeNode> getTreeLeafByHeadId(Integer head_id) {

        return null;
    }

    @Override
    public TreeNode getHeadNode(TNM tnm) {
        Integer treeId = tnmMapper.findHeadId(tnm.getTnm_key(),tnm.getDisease());
        TreeNode treeNode = treeMapper.selectByPrimaryKey(treeId);
        treeNode.setTreeNodes(getTreeByHeadId(treeNode.getId()));
        return treeNode;
    }

    @Override
    public TreeNode getNodeById(Integer id) {
        return treeMapper.selectByPrimaryKey(id);
    }


    private TreeNode DeepReadTree(TreeNode treeNode){
        List<TreeNode> treeNodes = getTreeByHeadId(treeNode.getId());
        List<TreeNode> list = new ArrayList<>();
        treeNodes.forEach(treeNode1 -> {
            list.add(treeNode1);
            DeepReadTree(treeNode1);
        });
        treeNode.setTreeNodes(list);
        return treeNode;
    }
}
