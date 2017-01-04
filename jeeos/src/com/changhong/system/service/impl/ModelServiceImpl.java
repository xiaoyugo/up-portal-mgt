package com.changhong.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.system.entity.ChModel;
import com.changhong.system.service.ModelService;
@Service
@Transactional
public class ModelServiceImpl extends BaseServiceImpl<ChModel> implements ModelService {

}
