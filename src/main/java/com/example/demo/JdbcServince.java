package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcServince {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	private static Connection conn = null;

	// 将获得的数据库与java的链接返回（返回的类型为Connection）
	public static Connection getConnection() {
		if (conn == null) {
			try {
				// 1.加载驱动程序
				Class.forName("com.mysql.jdbc.Driver");
				// 2.获得数据库的连接
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public static void excuteUpdate(Connection cnn,String sql) {
		PreparedStatement pst = null;
		try {
			pst = cnn.prepareStatement(sql);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		finally{
//			try {
//				cnn.close();
//				pst.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public static ResultSet excutQuery(Connection cnn,String sql) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cnn.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


}
