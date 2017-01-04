/**
 * 
 */
package com.changhong.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.system.entity.ChButton;
import com.changhong.system.service.ButtonService;

/**
 * @author killneo
 *
 */
@Service
@Transactional
public class ButtonServiceImpl extends BaseServiceImpl<ChButton> implements ButtonService{

}
