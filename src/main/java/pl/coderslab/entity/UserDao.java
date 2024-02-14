package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;
import pl.coderslab.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM users";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public User read(int userId) {
        User user = new User();
        try (Connection conn = DbUtil.getConnection(); PreparedStatement preStat = conn.prepareStatement(READ_USER_QUERY)) {
            preStat.setInt(1, userId);
            try (ResultSet rs = preStat.executeQuery()) {
                while (rs.next()) {
                    user.setId(rs.getInt(1));
                    user.setEmail(rs.getString(2));
                    user.setUserName(rs.getString(3));
                    user.setPassword(rs.getString(4));
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void update(User user) {
        try (Connection conn = DbUtil.getConnection(); PreparedStatement preStat = conn.prepareStatement(UPDATE_USER_QUERY)) {
            preStat.setString(1, user.getUserName());
            preStat.setString(2, user.getEmail());
            preStat.setString(3, hashPassword(user.getPassword()));
            preStat.setInt(4, user.getId());
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User[] findAll() {
        User[] users = new User[0];
        try (Connection conn = DbUtil.getConnection(); Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(FIND_ALL_QUERY)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setPassword(rs.getString(4));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
private User[] addToArray(User u, User[] users) {
    User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
    tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
    return tmpUsers; // Zwracamy nową tablicę.
}

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection(); PreparedStatement preStat = conn.prepareStatement(DELETE_USER_QUERY)) {
            preStat.setInt(1, userId);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}