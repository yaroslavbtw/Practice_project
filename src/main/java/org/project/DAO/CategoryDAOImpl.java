package org.project.DAO;

import org.project.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryDAOImpl implements CategoryDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public CategoryDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO \"Categories\" (uuid, title) VALUES (?, ?)");
            readAllStatement = connection.prepareStatement("SELECT * FROM \"Categories\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM \"Categories\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE \"Categories\" SET title = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM \"Categories\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Category entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setString(2, entity.getTitle());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> readAll() {
        List<Category> categories = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                Category category = new Category(
                        result.getObject("uuid", UUID.class),
                        result.getString("title")
                );
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new Category(
                        result.getObject("uuid", UUID.class),
                        result.getString("title")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category entity) {
        try {
            updateStatement.setString(1, entity.getTitle());
            updateStatement.setObject(2, entity.getUuid());
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
