import java.util.*;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Game {
    private Scanner sc = new Scanner(System.in);
    private Connection conn;

    public Game(Connection conn) {
        this.conn = conn;
    }

    boolean play(String username) {
        int wins = 0;
        int maxTries = 10;
        for (int attempt = 0; attempt < maxTries; attempt++) {
            int max = 4;
            int rand = (int) (Math.random() * (max - 1)) + 1;

            for (int i = 0; i < 3; i++) {
                System.out.println("Your remaining chances: " + (3 - i));
                System.out.println("    ***         ***         ***     ");
                System.out.println("   *****       *****       *****    ");
                System.out.println("  *******     *******     *******   ");
                System.out.println("  *******     *******     *******   ");
                System.out.println("  *******     *******     *******   ");
                System.out.println("  *******     *******     *******   ");
                System.out.println(" *********   *********   *********  ");
                System.out.println("*********** *********** *********** ");
                System.out.println("Enter Your Choice");
                int n = sc.nextInt();
                if (n == rand) {
                    wins++;
                    System.out.println("Correct! You found Buddy!");
                    break;
                } else if (i < 2) {
                    System.out.println("Next Try");
                }
            }
        }

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET wins = ? WHERE username = ?");
            ps.setInt(1, wins);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wins > 0;
    }
}

class Database {
    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:game.db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, wins INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean register(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, wins) VALUES (?, ?, 0)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getScoreboard() {
        List<String> scoreboard = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, wins FROM users ORDER BY wins DESC LIMIT 10");
            while (rs.next()) {
                scoreboard.add(rs.getString("username") + ": " + rs.getInt("wins"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreboard;
    }
}

class DummyUserCreator implements Runnable {
    private Database db;
    private String username;
    private String password;

    public DummyUserCreator(Database db, String username, String password) {
        this.db = db;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        db.register(username, password);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database db = new Database();
        Connection conn = db.getConnection();

        System.out.println("=============================");
        System.out.println(" ----------Welcome---------- ");
        System.out.println("=============================");
        System.out.println();
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        String username = "";
        String password = "";
        boolean loggedIn = false;

        if (choice == 1) {
            System.out.println("Enter username:");
            username = sc.nextLine();
            System.out.println("Enter password:");
            password = sc.nextLine();
            if (db.register(username, password)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed. Username may already exist.");
            }
        } else if (choice == 2) {
            System.out.println("Enter username:");
            username = sc.nextLine();
            System.out.println("Enter password:");
            password = sc.nextLine();
            if (db.login(username, password)) {
                System.out.println("Login successful!");
                loggedIn = true;
            } else {
                System.out.println("Login failed. Check your username and password.");
            }
        }

        if (loggedIn) {
            Game game = new Game(conn);
            boolean result = game.play(username);
            if (result) {
                System.out.println("You won!");
            } else {
                System.out.println("You lost.");
            }

            List<String> scoreboard = db.getScoreboard();
            System.out.println("Scoreboard:");
            for (String score : scoreboard) {
                System.out.println(score);
            }
        }

        // Creating dummy users via threads
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.execute(new DummyUserCreator(db, "dummyUser" + i, "password"));
        }
        executor.shutdown();
    }
}
