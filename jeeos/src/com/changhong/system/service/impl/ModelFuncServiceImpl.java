package com.changhong.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.system.entity.ChModelfunc;
import com.changhong.system.service.ModelFuncService;
@Service
@Transactional
public class ModelFuncServiceImpl extends BaseServiceImpl<ChModelfunc> implements ModelFuncService {

}
