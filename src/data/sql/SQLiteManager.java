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
import data.model.User;

public class SQLiteManager implements IDatabaseManager {
	
	private String url = Utility.SQL_URL;
	
	public static void main(String[] args) {

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
				stmt.executeUpdate("CREATE TABLE diary (id,title,tag,time,content,voice,user,share,comment);");
			}

//			stmt.executeUpdate("insert into diary values (123456789, 'title1', '1', 201310102151, './diary/content/123456789', './diary/voice/123456789');");
//			stmt.executeUpdate("insert into diary values (223456789, 'title2', '2', 201310102152, './diary/content/223456789', '');");
//			stmt.executeUpdate("insert into diary values (323456789, 'title3', '3', 201310102153, './diary/content/323456789', '');");
			// 提交
			conn.commit();
			
			rsTables = conn.getMetaData().getTables(null, null, "user", null);
			if(rsTables.next()){
				System.out.println("表存在,创建表的事情不要做了");
			} else {
				stmt.executeUpdate("CREATE TABLE user (id,username,password);");
			}
//			stmt.executeUpdate("insert into user values (123456789, 'user1', '123');");
//			stmt.executeUpdate("insert into user values (223456789, 'user2', '123');");
//			stmt.executeUpdate("insert into user values (323456789, 'user3', '123');");
			// 提交
			conn.commit();
			
			rsTables = conn.getMetaData().getTables(null, null, "log", null);
			if(rsTables.next()){
				System.out.println("表存在,创建表的事情不要做了");
			} else {
				stmt.executeUpdate("CREATE TABLE log (time,type,message);");
			}
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
				System.out.println("user = " + rs.getString("user"));
				System.out.println("share = " + rs.getString("share"));
				System.out.println("comment = " + rs.getString("comment"));
			}
			rs = stmt.executeQuery("SELECT * FROM user;");
			while (rs.next()) {
				System.out.println("id = " + rs.getString("id"));
				System.out.println("username = " + rs.getString("username"));
				System.out.println("password = " + rs.getString("password"));
			}
			rs = stmt.executeQuery("SELECT * FROM log;");
			while (rs.next()) {
				System.out.println("time = " + rs.getString("time"));
				System.out.println("type = " + rs.getString("type"));
				System.out.println("message = " + rs.getString("message"));
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
	public User getUser(String username, String password) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		User ret = null;
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ret = new User(Integer.valueOf(rs.getString("id")).intValue(), rs.getString("username"));
			}
			
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public User setUser(int id, String username, String password) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn;
		User ret = null;
		try {
			conn = DriverManager.getConnection(url, null, null);
			// 设置自动提交为false
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM user WHERE username = '" + username + "';";
			
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				rs.close();
				conn.close();
				return ret;
			}
			
			query = "INSERT INTO user VALUES (" + id + ", '" + username + "', '" + password +"');";
			stmt.executeUpdate(query);
			conn.commit();
			ret = new User(id, username);
			
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public ArrayList<SimpleDiary> getDiaryList(String title, String tag,
			long startTime, long endTime, SortType sortType, String user) {
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
			if (user != null && !user.equals("")) {
				if (!query.equals(""))
					query += " AND (" + "user = '" + user + "' OR share = 'share')";
				else
					query += "user = '" + user + "' OR share = 'share'";
			}
			
			if (!query.equals(""))
				query = "SELECT * FROM diary WHERE " + query + " ORDER BY time;";
			else
				query = "SELECT * FROM diary ORDER BY time;";
			System.out.println(query);
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
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
				int id_ = Integer.valueOf(rs.getString("id")).intValue();
				String title = rs.getString("title");
				String tag = rs.getString("tag");
				long time = Long.valueOf(rs.getString("time")).longValue();
				String contentURL = rs.getString("content");
				String voiceURL = rs.getString("voice");
				String username = rs.getString("user");
				String shareOrPrivate = rs.getString("share");
				String comment = rs.getString("comment");
				ret = new DetailedDiary(id_, title, tag, time, contentURL, voiceURL, username, shareOrPrivate, comment);
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
				query += "'" + diary.getVoiceURL() + "', ";
				query += "'" + diary.getUsername() + "', ";
				query += "'" + diary.getShareOrPrivate() + "', ";
				query += "'" + diary.getComment() + "');";
				System.out.println(query);
				
				stmt.executeUpdate(query);
				conn.commit();
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeLog(long time, String type, String message) {
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
			
			String query = "INSERT INTO log VALUES (" + time + ", '" + type + "', '" + message + "');";
			System.out.println(query);
			
			stmt.executeUpdate(query);
			conn.commit();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}