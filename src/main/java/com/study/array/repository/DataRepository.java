package com.study.array.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.study.array.config.DBConnectionMgr;
import com.study.array.entity.DataObj;

public class DataRepository {

	private static DataRepository instance;
	private DBConnectionMgr pool;
	
	private DataRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	public static DataRepository getInstance() {
		if(instance == null) {
			instance = new DataRepository();
		}
		return instance;
	}
	
	public int addData(DataObj dataObj) {
		return 0;
	}
	
	public int save(DataObj dataObj) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "insert into data_array_tb(content) values(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dataObj.getContent());
			successCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return 0;
	}
}
