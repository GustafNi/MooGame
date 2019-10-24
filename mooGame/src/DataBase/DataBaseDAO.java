package DataBase;

import java.sql.SQLException;


public interface DataBaseDAO {

    int getPlayerName(String name) throws ClassNotFoundException, SQLException;
    
    void insertIntoResults(int nGuess, int id)throws SQLException;
}
