package miniProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DB 연결 담당 클래스 
public class DBConnection {
	private final static String DBID = "dungeon";
	private final static String DBPW = "dungeon";
	private final static String DBURL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static Connection conn = null;
	
	public static Connection connectDB() {
		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("[Error] 드라이버 로딩 실패 : DBConnection -> connectDB()");
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(DBURL, DBID, DBPW);
			if(!conn.isClosed()) {
				System.out.println("[info] connectDB() : DB 연결 성공");
			}
			
		}catch(SQLException e) {
			System.out.println("[Error] connectDB() : DB 연결 실패");
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public static void main(String[] args) {
		connectDB();
	}
}
