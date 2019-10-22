
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ClientHandler {
    SimpleWindow simplewindow;
    GameLogic gameLogic;
    static Connection connection;
    static Statement stmt;
    static ResultSet rs;

    public ClientHandler(SimpleWindow simplewindow, GameLogic gameLogic) {
        this.simplewindow = simplewindow;
        this.gameLogic = gameLogic;
    }

    public int login() throws ClassNotFoundException, SQLException, InterruptedException {
        // login
        simplewindow.addString("Enter your user name:\n");
        String name = simplewindow.getString();
        int id = 0;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://server.bilting.se/MooDB", "java2018", "uffe");
        stmt = connection.createStatement();
        rs = stmt.executeQuery("select id,name from players where name = '" + name + "'");
        if (rs.next()) {
            id = rs.getInt("id");
        } else {
            simplewindow.addString("User not in database, please register with admin");
            Thread.sleep(5000);
            simplewindow.exit();
        }
        return id;
    }

    public void execute(int answer, int id) throws SQLException {
        while (answer == JOptionPane.YES_OPTION) {
            String goal = gameLogic.makeGoal();
            simplewindow.clear();
            simplewindow.addString("New game:\n");
            //comment out or remove next line to play real games!
            simplewindow.addString("For practice, number is: " + goal + "\n");
            String guess = simplewindow.getString();
            simplewindow.addString(guess +"\n");
            int nGuess = 1;
            String bbcc = gameLogic.checkBC(goal, guess);
            simplewindow.addString(bbcc + "\n");
            while ( ! bbcc.equals("BBBB,")) {
                nGuess++;
                guess = simplewindow.getString();
                simplewindow.addString(guess +"\n");
                bbcc = gameLogic.checkBC(goal, guess);
                simplewindow.addString(bbcc + "\n");
            }
            int ok = stmt.executeUpdate("INSERT INTO results " +
                    "(result, player) VALUES (" + nGuess + ", " +	id + ")" );
            showTopTen();
            answer = JOptionPane.showConfirmDialog(null, "Correct, it took " + nGuess
                    + " guesses\nContinue?");

        }
        simplewindow.exit();
    }

    public void showTopTen() throws SQLException {
        ArrayList<GameLogic.PlayerAverage> topList = new ArrayList<>();
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
                topList.add(new GameLogic.PlayerAverage(name, (double)totalGuesses/nGames));
            }

        }
        simplewindow.addString("Top Ten List\n    Player     Average\n");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (GameLogic.PlayerAverage p : topList) {
            simplewindow.addString(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }

    }
}
