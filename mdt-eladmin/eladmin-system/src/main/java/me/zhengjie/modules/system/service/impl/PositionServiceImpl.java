package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Position;
import me.zhengjie.modules.system.repository.PositionRepository;
import me.zhengjie.modules.system.service.PositionService;
import me.zhengjie.modules.system.service.dto.PositionDTO;
import me.zhengjie.modules.system.service.dto.PositionQueryCriteria;
import me.zhengjie.modules.system.service.mapper.PositionMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PositionServiceImpl implements PositionService {
  @Autowired
  private PositionRepository positionRepository;
  @Autowired
  private PositionMapper positionMapper;
  @Override
  public PositionDTO findById(Long id) {
    return null;
  }

  @Override
  public PositionDTO create(Position resources) {
    return null;
  }

  @Override
  public PositionDTO update(Position resources) {
    return null;
  }

  @Override
  public PositionDTO delete(Long id) {
    return null;
  }
  @Override
  public Object queryAll(Pageable pageable) {
    return positionMapper.toDto(positionRepository.findAll(pageable).getContent());
  }
  @Override
  public Object queryAll(PositionQueryCriteria criteria, Pageable pageable) {
    Page<Position> page = positionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
    List<PositionDTO> positions = new ArrayList<>();
//    for (Position position : page.getContent()) {
//      positions.add(positionMapper.toDto());
//    }
    return PageUtil.toPage(positions,page.getTotalElements());
  }
}
