package cn.knowimage.mdt.patienthistory.mapper;


import cn.knowimage.mdt.patienthistory.pojo.HistoryAndType;
import cn.knowimage.mdt.patienthistory.pojo.OperationHistory;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface OperationHistoryMapper extends Mapper<OperationHistory> {

    @Select("SELECT h.*, t.* FROM OPERATION_HISTORY h, OPERATION_TYPE t WHERE h.id = t.operation_history_id AND h.mdt_id = ${mdtId}")
    public List<HistoryAndType> getOperationHistory(int mdtId);


}
