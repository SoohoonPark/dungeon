package miniProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DB ��ü �ݴ� Ŭ����
public class DBConnectClose {
	public static void connectClose(Connection c, PreparedStatement p, ResultSet r) {
		try {
			if(r != null) {
				r.close();
				System.out.println("[info] ResultSet successfully closed");
			}
			if(p != null) {
				p.close();
				System.out.println("[info] PreparedStatement successfully closed");
			}
			if(c != null) {
				c.close();
				System.out.println("[info] Connection successfully closed");
			}
		}catch(SQLException e) {
			System.out.println("[Error] SQL ��ü �ݱ� ���� - connectClose()");
		}
	}
}
