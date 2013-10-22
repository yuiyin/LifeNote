package data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.SortType;
import util.Utility;
import data.model.DetailedDiary;
import data.model.SimpleDiary;

public class SQLiteManager implements IDatabaseManager {
	
	private String url = Utility.SQL_URL;
	
	public static void main(String[] args) {
//		// 加载驱动
//		try {
//			Class.forName("org.sqlite.JDBC");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			System.out.println("数据库驱动未找到!");
//		}
//		// 得到连接 会在你所填写的目录建一个你命名的文件数据库
//		Connection conn;
//		try {
//			conn = DriverManager.getConnection("jdbc:sqlite:./diary/test.db",null,null);
//			// 设置自动提交为false
//			conn.setAutoCommit(false);
//			Statement stmt = conn.createStatement();
//
//			//判断表是否存在
//			ResultSet rsTables = conn.getMetaData().getTables(null, null, "student", null);
//			if(rsTables.next()){
//				System.out.println("表存在,创建表的事情不要做了");
//			} else {
//				stmt.executeUpdate("create table student (id,name);");
//			}
//
//			stmt.executeUpdate("insert into student values (1,'hehe');");
//			stmt.executeUpdate("insert into student values (2,'xixi');");
//			stmt.executeUpdate("insert into student values (3,'haha');");
//			// 提交
//			conn.commit();
//			// 得到结果集
//			ResultSet rs = stmt.executeQuery("select * from student;");
//			while (rs.next()) {
//				System.out.println("id = " + rs.getString("id"));
//				System.out.println("name = " + rs.getString("name"));
//			}
//			rs.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("SQL异常!");
//		}
		SQLiteManager m = new SQLiteManager();
		//m.init(Utility.SQL_URL, "", "");
		m.getDiaryList(null, null, 201310102151L, 201310102152L, null);
		DetailedDiary dd = m.getDiary(123456789);
		dd.setTime(201310120000L);
		m.setDiary(dd, false);
		m.setDiary(new DetailedDiary(423456789, "title4", "4", 201310111100L, "./diary/content/123456789", ""), false);
		m.getDiaryList(null, null, 0, 0, null);
	}

	@Override
	public void init(String url, String user, String password) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();

			//判断表是否存在
			ResultSet rsTables = conn.getMetaData().getTables(null, null, "diary", null);
			if(rsTables.next()){
				System.out.println("表存在,创建表的事情不要做了");
			} else {
				stmt.executeUpdate("CREATE TABLE diary (id,title,tag,time,content,voice);");
			}

//			stmt.executeUpdate("insert into diary values (123456789, 'title1', '1', 201310102151, './diary/content/123456789', './diary/voice/123456789');");
//			stmt.executeUpdate("insert into diary values (223456789, 'title2', '2', 201310102152, './diary/content/223456789', '');");
//			stmt.executeUpdate("insert into diary values (323456789, 'title3', '3', 201310102153, './diary/content/323456789', '');");
			// 提交
			conn.commit();
			// 得到结果集
			ResultSet rs = stmt.executeQuery("SELECT * FROM diary;");
			while (rs.next()) {
				System.out.println("id = " + rs.getString("id"));
				System.out.println("title = " + rs.getString("title"));
				System.out.println("tag = " + rs.getString("tag"));
				System.out.println("time = " + rs.getString("time"));
				System.out.println("content = " + rs.getString("content"));
				System.out.println("voice = " + rs.getString("voice"));
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL异常!");
		}
		this.url = url;
	}

	@Override
	public ArrayList<SimpleDiary> getDiaryList(String title, String tag,
			long startTime, long endTime, SortType sortType) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		ArrayList<SimpleDiary> ret = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			String query = "";
			if (title != null && !title.equals(""))
				query += "title LIKE '%" + title + "%'";
			if (tag != null && !tag.equals("")) {
				if (!query.equals(""))
					query += " AND " + "tag LIKE '%" + tag + "%'";
				else
					query += "tag LIKE '%" + tag + "%'";
			}
			if (startTime > 0) {
				if (!query.equals(""))
					query += " AND " + "time >= " + startTime;
				else
					query += "time >= " + startTime;
			}
			if (endTime > 0) {
				if (!query.equals(""))
					query += " AND " + "time <= " + endTime;
				else
					query += "time <= " + endTime;
			}
			
			if (!query.equals(""))
				query = "SELECT * FROM diary WHERE " + query + " ORDER BY time;";
			else
				query = "SELECT * FROM diary ORDER BY time;";
			System.out.println(query);
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("id = " + rs.getString("id"));
				System.out.println("title = " + rs.getString("title"));
				System.out.println("tag = " + rs.getString("tag"));
				System.out.println("time = " + rs.getString("time"));
				System.out.println("content = " + rs.getString("content"));
				System.out.println("voice = " + rs.getString("voice"));
				int id = Integer.valueOf(rs.getString("id")).intValue();
				String title_ = rs.getString("title");
				String tag_ = rs.getString("tag");
				long time = Long.valueOf(rs.getString("time")).longValue();
				boolean hasVoice = !rs.getString("voice").equals("");
				ret.add(new SimpleDiary(id, title_, tag_, time, hasVoice));
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public DetailedDiary getDiary(int id) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		DetailedDiary ret = null;
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM diary WHERE id = " + id + ";";
			System.out.println(query);
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("id = " + rs.getString("id"));
				System.out.println("title = " + rs.getString("title"));
				System.out.println("tag = " + rs.getString("tag"));
				System.out.println("time = " + rs.getString("time"));
				System.out.println("content = " + rs.getString("content"));
				System.out.println("voice = " + rs.getString("voice"));
				int id_ = Integer.valueOf(rs.getString("id")).intValue();
				String title = rs.getString("title");
				String tag = rs.getString("tag");
				long time = Long.valueOf(rs.getString("time")).longValue();
				String contentURL = rs.getString("content");
				String voiceURL = rs.getString("voice");
				ret = new DetailedDiary(id_, title, tag, time, contentURL, voiceURL);
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void setDiary(DetailedDiary diary, boolean isDeletion) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			String query = "DELETE FROM diary WHERE id = " + diary.getId() + ";";
			System.out.println(query);
			
			stmt.executeUpdate(query);
			conn.commit();
			
			if (!isDeletion) {
				query = "INSERT INTO diary VALUES (";
				query += diary.getId() + ", ";
				query += "'" + diary.getTitle() + "', ";
				query += "'" + diary.getTag() + "', ";
				query += diary.getTime() + ", ";
				query += "'" + diary.getContentURL() + "', ";
				query += "'" + diary.getVoiceURL() + "');";
				System.out.println(query);
				
				stmt.executeUpdate(query);
				conn.commit();
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}