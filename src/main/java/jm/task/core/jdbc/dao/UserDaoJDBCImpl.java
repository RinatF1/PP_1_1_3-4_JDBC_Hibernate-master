package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

private final Connection con = Util.getConnection();

 public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {

       try (Statement statement = con.createStatement()) {
          statement.executeUpdate("CREATE TABLE IF NOT EXISTS db1.users " +
                  "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(45)," +
                  " lastName VARCHAR(45), age INT(3))");
          System.out.println("Таблица создана");
       } catch (SQLException e) {
          throw new RuntimeException(e);
       }

    }

    public void dropUsersTable() {

       try (Statement statement = con.createStatement()) {
          statement.executeUpdate("DROP TABLE IF EXISTS db1.users ");
          System.out.println("Таблица удалена");
       } catch (SQLException e) {
          throw new RuntimeException(e);
       }

    }

    public void saveUser(String name, String lastName, byte age) {

      try (PreparedStatement preparedStatement = con
              .prepareStatement("INSERT INTO db1.users (name, lastName, age) VALUES (?, ?, ?)")) {
         preparedStatement.setString(1, name);
         preparedStatement.setString(2, lastName);
         preparedStatement.setInt(3, age);

         preparedStatement.executeUpdate();

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

    }

    public void removeUserById(long id) {

    try (PreparedStatement deleteID = con
            .prepareStatement("DELETE FROM db1.users WHERE ID = ?")) {
       deleteID.setLong(1, id);

       deleteID.executeUpdate();
    } catch (SQLException e) {
       throw new RuntimeException(e);
    }

    }

    public List<User> getAllUsers() {

    List<User> users = new ArrayList<>();

      try (ResultSet resultSet = con.createStatement()
              .executeQuery("SELECT id, name, lastName, age FROM db1.users")) {
        while (resultSet.next()) {
           User user = new User();
           user.setId(resultSet.getLong("id"));
           user.setName(resultSet.getString("name"));
           user.setLastName(resultSet.getString("lastName"));
           user.setAge(resultSet.getByte("age"));

           users.add(user);
        }

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
       return users;
    }

    public void cleanUsersTable() {

    try (Statement delete = con.createStatement()) {
       delete.executeUpdate("DELETE FROM db1.users");
       System.out.println("Таблица очищена");

   } catch (SQLException e) {
      e.printStackTrace();
       System.out.println("Таблицу не удалось очистить");
   }

    }


}
