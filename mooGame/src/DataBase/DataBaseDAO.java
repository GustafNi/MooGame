package DataBase;

import java.sql.SQLException;

public interface DataBaseDAO {

    int getPlayerId(String name) throws ClassNotFoundException, SQLException;
    
    String getPlayerName(int id) throws SQLException;
    
    int noOfGuesses(int id) throws SQLException;
    
    int noOfPlayedGames(int id) throws SQLException;
    
    void insertIntoResults(int nGuess, int id)throws SQLException;
    
    int getResults() throws SQLException;
}
