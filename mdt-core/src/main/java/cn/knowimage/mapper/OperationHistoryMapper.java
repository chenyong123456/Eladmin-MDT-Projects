package cn.knowimage.mapper;



import cn.knowimage.pojo.instance.HistoryAndType;
import cn.knowimage.pojo.instance.OperationHistory;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OperationHistoryMapper extends Mapper<OperationHistory> {

    @Select("SELECT h.*, t.* FROM OPERATION_HISTORY h, OPERATION_TYPE t WHERE h.id = t.operation_history_id AND h.mdt_id = ${mdtId}")
    public List<HistoryAndType> getOperationHistory(int mdtId);


}
