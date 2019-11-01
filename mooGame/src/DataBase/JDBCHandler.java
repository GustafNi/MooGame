
package DataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class JDBCHandler implements DataBaseDAO {
    Connection connection;
    Statement stmt;
    ResultSet rs;
    
    public JDBCHandler() throws IllegalArgumentException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://server.bilting.se/MooDB", "java2018", "uffe");
            stmt = connection.createStatement();
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); 
        }   
    }
    
    @Override
    public int getPlayerId(String name) throws IllegalArgumentException {
        try {
            int id = 0;
            rs = stmt.executeQuery("select id,name from players where name = '" + name + "'");
            if (rs.next()) {
                id = rs.getInt("id");
            }
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException();
        } 
    }

    @Override
    public void insertIntoResults(int nGuess, int id) throws IllegalArgumentException {
        try {
            int ok = stmt.executeUpdate("INSERT INTO results " +
                    "(result, player) VALUES (" + nGuess + ", " +	id + ")" );
            //integer not used by design
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ArrayList<PlayerAverage> getTopTenResults() throws RuntimeException {
        try {
            ArrayList<PlayerAverage> topList = new ArrayList();
            Statement resultStatement = connection.createStatement();
            ResultSet resultSetScore;
            rs = stmt.executeQuery("select * from players");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                resultSetScore = resultStatement.executeQuery("select * from results where player = "+ id );
                int nGames = 0;
                int totalGuesses = 0;
                while (resultSetScore.next()) {
                    nGames++;
                    totalGuesses += resultSetScore.getInt("result");
                }
                if (nGames > 0) {
                    topList.add(new PlayerAverage(name, (double)totalGuesses/nGames));
                }
            }
            topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
            return topList;
        } catch (SQLException ex) {
            throw new RuntimeException("AverageFail" + ex);
        }
    }
    public class PlayerAverage {
    public String name;
    public double average;
    public PlayerAverage(String name, double average) {
        this.name = name;
        this.average = average;
    }
 }
}


