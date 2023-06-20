package org.project.DAO;

import org.project.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class UserDAOImpl implements UserDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public UserDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO public.\"Users\" (uuid, dt_create, dt_update, mail, fio, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            readAllStatement = connection.prepareStatement("SELECT * FROM public.\"Users\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM public.\"Users\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE public.\"Users\" SET dt_update = ?, mail = ?, fio = ?, role = ?, status = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM public.\"Users\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setTimestamp(2, entity.getDtCreate());
            createStatement.setTimestamp(3, entity.getDtUpdate());
            createStatement.setString(4, entity.getMail());
            createStatement.setString(5, entity.getFio());
            createStatement.setString(6, entity.getRole());
            createStatement.setString(7, entity.getStatus());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                User user = new User(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("mail"),
                        result.getString("fio"),
                        result.getString("role"),
                        result.getString("status")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new User(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("mail"),
                        result.getString("fio"),
                        result.getString("role"),
                        result.getString("status")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        try {
            updateStatement.setTimestamp(1, entity.getDtUpdate());
            updateStatement.setString(2, entity.getMail());
            updateStatement.setString(3, entity.getFio());
            updateStatement.setString(4, entity.getRole());
            updateStatement.setString(5, entity.getStatus());
            updateStatement.setObject(6, entity.getUuid());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            deleteStatement.setObject(1, UUID.fromString(id));
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
