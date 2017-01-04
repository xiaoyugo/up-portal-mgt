package com.changhong.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.system.entity.ChUserfunc;
import com.changhong.system.service.UserFuncService;
@Service
@Transactional
public class UserFuncServiceImpl extends BaseServiceImpl<ChUserfunc> implements UserFuncService {

}
