
package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DbHandler implements DataBaseDAO {
    Connection connection;
    Statement stmt;
    ResultSet rs;
    
    public DbHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://server.bilting.se/MooDB", "java2018", "uffe");
        stmt = connection.createStatement();
    }
    
    @Override
    public int getPlayerName(String name) throws SQLException {
        int id = 0;
        rs = stmt.executeQuery("select id,name from players where name = '" + name + "'");
        if (rs.next()) {
            id = rs.getInt("id");
        } 
        return id;
    }

    @Override
    public void insertIntoResults(int nGuess, int id) throws SQLException {
        int ok = stmt.executeUpdate("INSERT INTO results " +
                    "(result, player) VALUES (" + nGuess + ", " +	id + ")" );   
    }
}
