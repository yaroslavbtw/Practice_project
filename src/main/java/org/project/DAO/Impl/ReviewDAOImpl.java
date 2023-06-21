package org.project.DAO.Impl;

import org.project.DAO.ReviewDAO;
import org.project.entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewDAOImpl implements ReviewDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public ReviewDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO \"Reviews\" (uuid, recipe_id, user_id, rating, text, dt_create) VALUES (?, ?, ?, ?, ?, current_timestamp)");
            readAllStatement = connection.prepareStatement("SELECT * FROM \"Reviews\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM \"Reviews\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE \"Reviews\" SET rating = ?, text = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM \"Reviews\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Review entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setObject(2, entity.getRecipeId());
            createStatement.setObject(3, entity.getUserId());
            createStatement.setInt(4, entity.getRating());
            createStatement.setString(5, entity.getText());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Review> readAll() {
        List<Review> reviews = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                Review review = new Review(
                        result.getObject("uuid", UUID.class),
                        result.getString("text"),
                        result.getObject("recipe_id", UUID.class),
                        result.getObject("parent_review_id", UUID.class),
                        result.getObject("user_id", UUID.class),
                        result.getInt("likes"),
                        result.getTimestamp("dt_create"),
                        result.getInt("rating")
                );
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Review readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new Review(
                        result.getObject("uuid", UUID.class),
                        result.getString("text"),
                        result.getObject("recipe_id", UUID.class),
                        result.getObject("parent_review_id", UUID.class),
                        result.getObject("user_id", UUID.class),
                        result.getInt("likes"),
                        result.getTimestamp("dt_create"),
                        result.getInt("rating")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Review entity) {
        try {
            updateStatement.setInt(1, entity.getRating());
            updateStatement.setString(2, entity.getText());
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
