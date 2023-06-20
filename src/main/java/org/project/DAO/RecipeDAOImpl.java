package org.project.DAO;

import org.project.entity.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeDAOImpl implements RecipeDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public RecipeDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO \"Recipes\" (uuid, dt_create, dt_update, title, category_id, description) VALUES (?, ?, ?, ?, ?, ?)");
            readAllStatement = connection.prepareStatement("SELECT * FROM \"Recipes\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM \"Recipes\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE \"Recipes\" SET dt_update = ?, title = ?, category_id = ?, description = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM \"Recipes\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Recipe entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setTimestamp(2, entity.getDtCreate());
            createStatement.setTimestamp(3, entity.getDtUpdate());
            createStatement.setString(4, entity.getTitle());
            createStatement.setObject(5, entity.getCategoryId());
            createStatement.setString(6, entity.getDescription());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Recipe> readAll() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                Recipe recipe = new Recipe(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("title"),
                        result.getObject("category_id", UUID.class),
                        result.getString("description")
                );
                recipes.add(recipe);
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new Recipe(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("title"),
                        result.getObject("category_id", UUID.class),
                        result.getString("description")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Recipe entity) {
        try {
            updateStatement.setTimestamp(1, entity.getDtUpdate());
            updateStatement.setString(2, entity.getTitle());
            updateStatement.setObject(3, entity.getCategoryId());
            updateStatement.setString(4, entity.getDescription());
            updateStatement.setObject(5, entity.getUuid());
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
