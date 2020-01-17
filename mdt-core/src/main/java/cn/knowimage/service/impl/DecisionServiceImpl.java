package cn.knowimage.service.impl;

import cn.knowimage.mapper.DecisionMapper;
import cn.knowimage.mapper.UserMapper;
import cn.knowimage.pojo.instance.Decision;
import cn.knowimage.ims.vo.User;
import cn.knowimage.service.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DecisionServiceImpl implements DecisionService {

    @Autowired
    private DecisionMapper decisionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Decision insert(Decision decision) {
        decisionMapper.insertSelective(decision);
        Decision decision1 = decisionMapper.selectOne(decision);
        return decision1;
    }

    @Override
    public List<Decision> getAllByMDT(int mid) {

        Example example = new Example(Decision.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mdt_id",mid);

        List<Decision> decisions = decisionMapper.selectByExample(example);

        decisions.forEach(decision -> {
            User user = userMapper.selectByPrimaryKey(decision.getUser_id());
            decision.setUser(user);
        });

        return decisions;
    }
}
