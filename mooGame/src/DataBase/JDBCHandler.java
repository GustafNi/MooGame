
package DataBase;

import Game.GameLogic;
import Game.PlayerAverage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class JDBCHandler implements DataBaseDAO {
    Connection connection;
    Statement stmt;
    ResultSet rs;
    
    public JDBCHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://server.bilting.se/MooDB", "java2018", "uffe");
        stmt = connection.createStatement();
    }
    
    @Override
    public int getPlayerId(String name) throws SQLException {
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
        //integer not used by design
    }

    @Override
    public int getResults() throws SQLException {
        Statement stmt2 = connection.createStatement();
        ResultSet rs2;
        int nGames = 0;
        rs = stmt.executeQuery("select * from players");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = getPlayerName(id);
            rs2 = stmt2.executeQuery("select * from results where player = "+ id );
            int totalGuesses = 0;
            while (rs2.next()) {
                nGames++;
                totalGuesses += rs2.getInt("result");
            }
        }
        return nGames;
    }
    
    @Override
    public String getPlayerName(int id) throws SQLException {
        rs = stmt.executeQuery("select id,name from players where id = '" + id + "'");
        if (! rs.next()) {
            return "No player with that ID";
        } 
        return rs.getString("name");
    }
    
    @Override
    public int noOfGuesses(int id) throws SQLException {
        rs = stmt.executeQuery("select * from results where player = "+ id);
        int totalGuesses = 0;
        while (rs.next()) {
            totalGuesses += rs.getInt("result");
        }
        return totalGuesses;
    }
    @Override
    public int noOfPlayedGames(int id) throws SQLException {
        rs = stmt.executeQuery("select * from results where player = "+ id);
        int noOfGames = 0;
        while(rs.next()) {
            noOfGames ++;
        }
        return noOfGames;
    }
    @Override
    public ArrayList getTopTenResults(ArrayList topList) throws SQLException {
        Statement stmt2 = connection.createStatement();
	ResultSet rs2;
	rs = stmt.executeQuery("select * from players");
	while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            rs2 = stmt2.executeQuery("select * from results where player = "+ id );
            int nGames = 0;
            int totalGuesses = 0;
            while (rs2.next()) {
		nGames++;
		totalGuesses += rs2.getInt("result");
            }
            if (nGames > 0) {
		topList.add(new PlayerAverage(name, (double)totalGuesses/nGames));
            }    
			
	}
    return topList;
    } 
}
