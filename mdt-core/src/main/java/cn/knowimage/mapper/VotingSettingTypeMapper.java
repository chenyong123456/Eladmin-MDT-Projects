package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.VotingSettingType;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface VotingSettingTypeMapper extends Mapper<VotingSettingType> {
  /**
   * 修改投票机制
   * @param votingMechanism 投票机制
   * @return
   */
  @Update("update VOTING_SETTING_TYPE set voting_mechanism=#{voting_mechanism}")
  int updateVotingMechanism(int votingMechanism);
}
