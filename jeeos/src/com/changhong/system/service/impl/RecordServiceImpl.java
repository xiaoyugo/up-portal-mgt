package com.changhong.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.DateUtils;
import com.changhong.base.web.Sessions;
import com.changhong.system.entity.ChRecord;
import com.changhong.system.entity.ChUser;
import com.changhong.system.service.RecordService;

@Service
@Transactional
public class RecordServiceImpl extends BaseServiceImpl<ChRecord> implements RecordService {

	@Override
	public void saveOperLog(String chFuncModuel, String chRecdType,
			String chRecdDesc, String chRecdMemo) throws Exception {
	     	ChRecord chRecd=new ChRecord();
		//    ChUser user=Sessions.getChUser();
	     	ChUser user=Sessions.getChUser();
			chRecd.setChRecdModule(chFuncModuel);
			chRecd.setChRecdDesc(chRecdDesc);
			chRecd.setChRecdType(chRecdType);
			
			if(chRecd!=null){
				chRecd.setChUser(user);
				chRecd.setChRecdTime(DateUtils.getCurrentDate());
				save(chRecd);
			}
	}


}
