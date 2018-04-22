package com.imooc.service;

import com.imooc.dao.MessageDao;

/**
 * 维护相关的业务功能
 */
public class MaintainService {
	/**
	 * 单条删除
	 */
	public void deleteOne(String id) {
		if (id != null && !"".equals(id)) {
			MessageDao messageDao = new MessageDao();
			messageDao.deleteOne(Integer.parseInt(id));
		}
	}
}
