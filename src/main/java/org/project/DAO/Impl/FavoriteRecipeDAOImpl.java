package org.project.DAO.Impl;
import org.project.DAO.FavoriteRecipeDAO;
import org.project.entity.FavoriteRecipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FavoriteRecipeDAOImpl implements FavoriteRecipeDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public FavoriteRecipeDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO \"Favorite_recipes\" (uuid, recipe_id, user_id) VALUES (?, ?, ?)");
            readAllStatement = connection.prepareStatement("SELECT * FROM \"Favorite_recipes\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM \"Favorite_recipes\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE \"Favorite_recipes\" SET recipe_id = ?, user_id = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM \"Favorite_recipes\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(FavoriteRecipe entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setObject(2, entity.getRecipeId());
            createStatement.setObject(3, entity.getUserId());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FavoriteRecipe> readAll() {
        List<FavoriteRecipe> favoriteRecipes = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                FavoriteRecipe favoriteRecipe = new FavoriteRecipe(
                        result.getObject("uuid", UUID.class),
                        result.getObject("recipe_id", UUID.class),
                        result.getObject("user_id", UUID.class)
                );
                favoriteRecipes.add(favoriteRecipe);
            }
            return favoriteRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FavoriteRecipe readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new FavoriteRecipe(
                        result.getObject("uuid", UUID.class),
                        result.getObject("recipe_id", UUID.class),
                        result.getObject("user_id", UUID.class)
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FavoriteRecipe entity) {
        try {
            updateStatement.setObject(1, entity.getRecipeId());
            updateStatement.setObject(2, entity.getUserId());
            updateStatement.setObject(3, entity.getUuid());
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
