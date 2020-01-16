package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.domain.TumorType;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.DepartmentWeightSettingRepository;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.TumorTypeRepository;
import me.zhengjie.modules.system.service.dto.JobQueryCriteria;
import me.zhengjie.modules.system.service.mapper.DepartmentWeightSettingMapper;
import me.zhengjie.modules.system.service.mapper.TumorTypeMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.system.repository.JobRepository;
import me.zhengjie.modules.system.service.JobService;
import me.zhengjie.modules.system.service.dto.JobDTO;
import me.zhengjie.modules.system.service.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2019-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl implements JobService {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobMapper jobMapper;

  @Autowired
  private DeptRepository deptRepository;

  @Autowired
  private DepartmentWeightSettingRepository departmentWeightSettingRepository;

  @Autowired
  private DepartmentWeightSettingMapper departmentWeightSettingMapper;

  @Autowired
  private TumorTypeMapper tumorTypeMapper;

  @Autowired
  private TumorTypeRepository tumorTypeRepository;

  @Override
  public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
    Page<Job> page = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
    List<JobDTO> jobs = new ArrayList<>();
    for (Job job : page.getContent()) {

      jobs.add(jobMapper.toDto(job, deptRepository.findNameById(job.getDept().getPid())));
    }
    for (int i = 0; i < jobs.size(); i++) {
    }
    return PageUtil.toPage(jobs, page.getTotalElements());
  }

  @Override
  public JobDTO findById(Long id) {
    Optional<Job> job = jobRepository.findById(id);
    ValidationUtil.isNull(job, "Job", "id", id);
    return jobMapper.toDto(job.get());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public JobDTO create(Job resources) {
    Job job = jobRepository.findByJobName(resources.getName());
    if (job != null) {
      //该科室已存在
      throw new EntityExistException(Job.class, "jobname", resources.getName());
    }
    //如果该科室没有被禁用
    if (resources.getEnabled()) {
      //新增该科室对应的权重
      List<TumorType> list = tumorTypeRepository.findAll();
      for (TumorType t : list
      ) {
        DepartmentWeightSetting departmentWeightSetting = new DepartmentWeightSetting();
        departmentWeightSetting.setWeightings(Double.parseDouble("1"));
        departmentWeightSetting.setDeptId(jobRepository.save(resources).getId());
        departmentWeightSetting.setTumorId(t.getId());
        departmentWeightSettingMapper.toDto(departmentWeightSettingRepository.save(departmentWeightSetting));
      }
    }
    return jobMapper.toDto(jobRepository.save(resources));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Job resources) {
    Optional<Job> optionalJob = jobRepository.findById(resources.getId());


    ValidationUtil.isNull(optionalJob, "Job", "id", resources.getId());
    //根据id查询岗位
    Job job = optionalJob.get();
    if (!job.getName().equals(resources.getName())) {
      Job job1 = jobRepository.findByJobName(resources.getName());
      if (job1 != null) {
        //该科室已存在
        throw new EntityExistException(Job.class, "jobname", resources.getName());
      }
    }
    //如果该科室是启动的
    if (resources.getEnabled()) {
      //根据科室id查询科室权重表的数据
      List<DepartmentWeightSetting> list = departmentWeightSettingRepository.findByPid(job.getId());
      if (list.size() == 0) {
        //原来没有数据，新增
        //新增该科室对应的权重
        List<TumorType> tumorList = tumorTypeRepository.findAll();
        for (TumorType t : tumorList
        ) {
          DepartmentWeightSetting departmentWeightSetting = new DepartmentWeightSetting();
          departmentWeightSetting.setTumorId(t.getId());
          departmentWeightSetting.setWeightings(Double.parseDouble("1"));
          departmentWeightSetting.setDeptId(resources.getId());
          departmentWeightSettingMapper.toDto(departmentWeightSettingRepository.save(departmentWeightSetting));
        }
      }
    }
    //该科室是禁用的
    else {
      //删除该科室对应的权重数据
      departmentWeightSettingRepository.deleteDepartmentWeightSettingByDeptId(job.getId());

    }
    resources.setId(job.getId());
    jobRepository.save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    jobRepository.deleteById(id);
    //删除该科室底下对应的投票权重
    departmentWeightSettingRepository.deleteDepartmentWeightSettingByDeptId(id);
  }
}
