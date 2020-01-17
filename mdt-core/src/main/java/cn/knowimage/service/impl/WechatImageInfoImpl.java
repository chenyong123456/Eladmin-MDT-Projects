package cn.knowimage.service.impl;


import cn.knowimage.pojo.instance.ImageInfo;
import cn.knowimage.pojo.vo.ImageWechart;
import cn.knowimage.mapper.ImageMapper;
import cn.knowimage.mapper.ImageTypeMapper;
import cn.knowimage.mapper.WechatImageMapper;
import cn.knowimage.utils.ClincialResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class WechatImageInfoImpl {
    @Autowired
    WechatImageMapper wechatImageMapper;
    @Autowired
    ImageTypeMapper imageTypeMapper;
    @Autowired
    ImageMapper imageMapper;

    public ClincialResult wechatImageinformation(Integer mdtId) {
        List<ImageInfo> infotype=imageMapper.selectTypeIdByMDTId(mdtId);
        System.out.println("查询出来的类型id----》"+infotype);
        //创建出一个大集合（最外层集合）
        List<List<ImageWechart>> wechattype=new ArrayList<>();
       // 遍历查询出来的类型
        for (ImageInfo tp:infotype) {
            System.out.println(tp);
            //查询出Id
            Integer typeId=tp.getI_type_Id();
            System.out.println("id=====>"+typeId);
            //根据前台传过来的mdtId和类型Id查询出图片信息，封装成集合放入大集合中
            List list =wechatImageMapper.selectWechat(mdtId, typeId);
            System.out.println("查询出来的信息----》"+list);
            wechattype.add(list);
        }
        return ClincialResult.build(200, "信息返回成功", wechattype);
    }
}
