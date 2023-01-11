package esercizio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DataBaseManagerSingleton {

	private static DataBaseManagerSingleton instance;

	private DataBaseManagerSingleton() {
		System.out.println("Instanziato il costruttore");
	}

	public static DataBaseManagerSingleton getInstance() {
		if (instance == null) {
			instance = new DataBaseManagerSingleton();
		}
		return instance;
	}

	public ArrayList<Message> getMessages(LocalDateTime lastCall) throws ClassNotFoundException, SQLException, IOException {
		//Preparing list to fill
		ArrayList<Message> messages = new ArrayList<>();

		//Driver of type "bridge jdbc-odbc"
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		//Connection String
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String name = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+name;

		//Username and password
		String username = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		//Connection with username and password
		Connection con = DriverManager.getConnection(url, username, password);

		//Create query
		String query = "SELECT * FROM messages WHERE serverReceivedTime > ?;";

		PreparedStatement pStatement = con.prepareStatement(query);

		//Give values to query
		pStatement.setTimestamp(1, Timestamp.valueOf(lastCall));

		//Execute query
		ResultSet res =  pStatement.executeQuery();

		while (res.next()) {

			//Creating Message object
			Message message = new Message();

			//Setting message fields
			message.setUserName(res.getString("userName"));
			message.setText(res.getString("textMessage"));
			message.setUserInsertedTime((res.getTimestamp("userInsertedTime")).toLocalDateTime());
			message.setServerReceivedTime((res.getTimestamp("serverReceivedTime")).toLocalDateTime());

			//Add message to list
			messages.add(message);

		}

		return messages;

	}

	public void insertMessage (Message  mex) throws ClassNotFoundException, SQLException, IOException {

		//Driver of type "bridge jdbc-odbc"
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		//Connection String
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String name = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+name;

		//Username and password
		String username = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		//Connection with username and password
		Connection con = DriverManager.getConnection(url, username, password);

		//Create query
		String query = "insert into messages (userName, textMessage, userInsertedTime, serverReceivedTime)\r\n"
				+ " values (?, ?, ?, ?)";

		PreparedStatement pStatement = con.prepareStatement(query);

		//Give values to query
		pStatement.setString(1, mex.getUserName());
		pStatement.setString(2, mex.getText());
		pStatement.setTimestamp(3, Timestamp.valueOf(mex.getUserInsertedTime()));
		pStatement.setTimestamp(4, Timestamp.valueOf(mex.getServerReceivedTime()));

		//Execute query
		int rowsUpdated = pStatement.executeUpdate();

		System.out.println(rowsUpdated + " rows have been updated");

		con.close();
		System.out.println("Connection closed");

	}

	public void updateMessages() throws ClassNotFoundException, SQLException, IOException {

		//Driver of type "bridge jdbc-odbc"
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		//Connection String
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String name = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+name;

		//Username and password
		String username = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		//Connection with username and password
		Connection con = DriverManager.getConnection(url, username, password);

		//Create query
		String query = "UPDATE messages SET textMessage = 'prova' WHERE userName = 'Matteo'";

		PreparedStatement pStatement = con.prepareStatement(query);

		//Give values to query

		//Execute query
		int rowsUpdated = pStatement.executeUpdate();

		System.out.println(rowsUpdated + " rows have been updated");

		con.close();
		System.out.println("Connection closed");

	}

	public void deleteMessages() throws ClassNotFoundException, SQLException, IOException {

		//Driver of type "bridge jdbc-odbc"
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		//Connection String
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String name = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+name;

		//Username and password
		String username = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		//Connection with username and password
		Connection con = DriverManager.getConnection(url, username, password);
		//Create query
		String query = "DELETE FROM messages WHERE userName = 'Matteo'";

		PreparedStatement pStatement = con.prepareStatement(query);

		//Give values to query

		//Execute query
		int rowsUpdated = pStatement.executeUpdate();

		System.out.println(rowsUpdated + " rows have been updated");

		con.close();
		System.out.println("Connection closed");

	}

}
