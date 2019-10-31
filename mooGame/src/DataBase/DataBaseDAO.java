package DataBase;

import DataBase.JDBCHandler.PlayerAverage;
import java.util.ArrayList;

public interface DataBaseDAO {

    ArrayList<PlayerAverage> getTopTenResults() throws RuntimeException;

    int getPlayerId(String name) throws IllegalArgumentException;
  
    void insertIntoResults(int nGuess, int id)throws IllegalArgumentException;
    
}
