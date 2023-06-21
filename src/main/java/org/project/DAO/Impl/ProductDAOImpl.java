package org.project.DAO.Impl;
import org.project.DAO.ProductDAO;
import org.project.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDAOImpl implements ProductDAO {
    private final PreparedStatement createStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public ProductDAOImpl(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO \"Products\" (uuid, dt_create, dt_update, title, calories, proteins, fats, carbohydrates) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            readAllStatement = connection.prepareStatement("SELECT * FROM \"Products\"");
            readByIdStatement = connection.prepareStatement("SELECT * FROM \"Products\" WHERE uuid = ?");
            updateStatement = connection.prepareStatement("UPDATE \"Products\" SET dt_update = ?, title = ?, calories = ?, proteins = ?, fats = ?,carbohydrates = ? WHERE uuid = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM \"Products\" WHERE uuid = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Product entity) {
        try {
            createStatement.setObject(1, entity.getUuid());
            createStatement.setTimestamp(2, entity.getDtCreate());
            createStatement.setTimestamp(3, entity.getDtUpdate());
            createStatement.setString(4, entity.getTitle());
            createStatement.setInt(5, entity.getCalories());
            createStatement.setDouble(6, entity.getProteins());
            createStatement.setDouble(7, entity.getFats());
            createStatement.setDouble(8, entity.getCarbohydrates());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> readAll() {
        List<Product> products = new ArrayList<>();
        try {
            ResultSet result = readAllStatement.executeQuery();
            while (result.next()) {
                Product product = new Product(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("title"),
                        result.getInt("calories"),
                        result.getDouble("proteins"),
                        result.getDouble("fats"),
                        result.getDouble("carbohydrates")
                );
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product readById(String id) {
        try {
            readByIdStatement.setObject(1, UUID.fromString(id));
            ResultSet result = readByIdStatement.executeQuery();
            if (result.next()) {
                return new Product(
                        result.getObject("uuid", UUID.class),
                        result.getTimestamp("dt_create"),
                        result.getTimestamp("dt_update"),
                        result.getString("title"),
                        result.getInt("calories"),
                        result.getDouble("proteins"),
                        result.getDouble("fats"),
                        result.getDouble("carbohydrates")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product entity) {
        try {
            updateStatement.setTimestamp(1, entity.getDtUpdate());
            updateStatement.setString(2, entity.getTitle());
            updateStatement.setInt(3, entity.getCalories());
            updateStatement.setDouble(4, entity.getProteins());
            updateStatement.setDouble(5, entity.getFats());
            updateStatement.setDouble(6, entity.getCarbohydrates());
            updateStatement.setObject(7, entity.getUuid());
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
