package com.imooc.service;

import java.util.List;

import com.imooc.bean.Message;
import com.imooc.dao.MessageDao;
import com.imooc.util.Iconst;

/**
 * 列表相关的业务功能
 * 
 * @author Muscleape
 *
 */
public class QueryService {

	public List<Message> queryMessageList(String command, String description) {
		MessageDao messageDao = new MessageDao();
		return messageDao.queryMessageList(command, description);
	}

	public String queryByCommand(String command) {
		MessageDao messageDao = new MessageDao();

		List<Message> messageList;
		if (Iconst.HELP_CONTENT.equals(command)) {
			messageList = messageDao.queryMessageList(null, null);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < messageList.size(); i++) {
				if (i != 0) {
					sb.append("<br/>");
				}
				sb.append("回复【" + messageList.get(i).getCommand() + "】" + "可以查看【" + messageList.get(i).getDescription()
						+ "】");
			}
			return sb.toString();
		}
		messageList = messageDao.queryMessageList(command, null);
		if (messageList.size() > 0) {
			return messageList.get(0).getContent();
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
}
