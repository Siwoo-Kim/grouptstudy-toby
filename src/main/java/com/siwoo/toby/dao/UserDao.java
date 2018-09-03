package com.siwoo.toby.dao;

import com.siwoo.toby.entity.User;
import java.sql.*;

public class UserDao {
    private static final String MYSQL_URL = "jdbc:mysql://localhost/toby?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
    public static final String USER_NAME = "toby";
    public static final String PASSWORD = "1234";
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String SQL_INSERT =  "INSERT INTO USERS(ID, NAME, PASSWORD) VALUES (?, ?, ?)";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM USERS WHERE ID = ?";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.delete();

        User user1 = new User();
        String user1Id = "sm123tt";
        user1.setId(user1Id);
        user1.setPassword("1234");
        user1.setName("김시우");
        userDao.add(user1);
        user1 = userDao.get(user1Id);
        System.out.println(user1);

        User user2 = new User();
        String user2Id = "etoile89";
        user2.setId(user2Id);
        user2.setPassword("4321");
        user2.setName("이지현");
        userDao.add(user2);
        user2 = userDao.get(user2Id);
        System.out.println(user2);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_NAME);
        return DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
    }
    public void delete() throws ClassNotFoundException, SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS")) {
            preparedStatement.executeUpdate();
        }
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        User user = new User();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getString(COLUMN_ID));
                user.setName(resultSet.getString(COLUMN_NAME));
                user.setPassword(resultSet.getString(COLUMN_PASSWORD));
            }
            if(resultSet != null) {
                resultSet.close();
            }
        }
        return user;
    }
}
