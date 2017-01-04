package com.changhong.system.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.system.entity.ChRange;
import com.changhong.system.service.RangeService;
@Service
@Transactional
public class RangeServiceImpl extends BaseServiceImpl<ChRange> implements RangeService {

}
