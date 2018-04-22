package com.imooc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;

/**
 * 和Message表相关的数据库操作
 * 
 * @author Muscleape
 *
 */
public class MessageDao {

	/**
	 * 根据查询条件查询消息列表
	 */
	public List<Message> queryMessageList(String command, String description) {

		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		List<Message> messageList = new ArrayList<Message>();
		try {
			sqlSession = dbAccess.getSqlSession();
			Message message = new Message();
			message.setCommand(command);
			message.setDescription(description);
			// 通过SQLSession执行SQl语句
			messageList = sqlSession.selectList("Message.queryMessageList", message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return messageList;
	}

	/**
	 * 单条删除
	 */
	public void deleteOne(int id) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			// 通过SQLSession执行SQl语句
			sqlSession.delete("Message.deleteOne", id);
			// MyBatis有事务，删除操作时需要手动提交事务
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 批量删除
	 */
	public void deleteBatch(List<Integer> ids) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.delete("Message.deleteBatch", ids);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static void main(String[] args) {
		MessageDao messageDao = new MessageDao();
		messageDao.queryMessageList("", "");
	}

	/**
	 * 根据查询条件查询参数列表
	 */
	// public List<Message> queryMessageList(String command, String description) {
	// List<Message> messageList = new ArrayList<Message>();
	// try {
	// // 新版本mysql驱动自动加载，不需要代码调用加载
	// Class.forName("com.mysql.cj.jdbc.Driver");
	// // 连接URL中需要附加时区信息
	// // Connection conn =
	// // DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/message", "root",
	// // "Test6530");
	// Connection conn = DriverManager.getConnection(
	// "jdbc:mysql://127.0.0.1:3306/message?serverTimezone=GMT&autoReconnect=true&useSSL=true",
	// "root",
	// "Test6530");
	// StringBuilder sql = new StringBuilder("select ID,COMMAND,DESCRIPTION,CONTENT
	// from MESSAGE where 1=1 ");
	// List<String> paramList = new ArrayList<String>();
	// if (command != null && !"".equals(command.trim())) {
	// sql.append(" and COMMAND=?");
	// paramList.add(command);
	// }
	// if (description != null && !"".equals(description.trim())) {
	// sql.append(" and DESCRIPTION like '%' ? '%' ");
	// paramList.add(description);
	// }
	// PreparedStatement statement = conn.prepareStatement(sql.toString());
	// for (int i = 0; i < paramList.size(); i++) {
	// statement.setString(i + 1, paramList.get(i));
	// }
	// ResultSet rs = statement.executeQuery();
	//
	// while (rs.next()) {
	// Message message = new Message();
	// messageList.add(message);
	// message.setId(rs.getString("ID"));
	// message.setCommand(rs.getString("COMMAND"));
	// message.setDescription(rs.getString("DESCRIPTION"));
	// message.setContent(rs.getString("CONTENT"));
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return messageList;
	// }
}
