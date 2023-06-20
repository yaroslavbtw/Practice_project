package org.project;

import org.project.DAO.*;
import org.project.entity.*;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/practice";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345678";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database");

            while (true) {
                System.out.println("Choose table:");
                System.out.println("1. Reviews");
                System.out.println("2. Categories");
                System.out.println("3. Favorite Recipes");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reviewMenu(connection);
                        break;
                    case 2:
                        categoryMenu(connection);
                        break;
                    case 3:
                        favoriteRecipeMenu(connection);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    private static void reviewMenu(Connection connection) {
        ReviewDAO reviewDAO = new ReviewDAOImpl(connection);

        while (true) {
            System.out.println("Reviews");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Review review = createReview();
                    reviewDAO.create(review);
                    System.out.println("Review created!");
                    break;
                case 2:
                    List<Review> reviews = reviewDAO.readAll();
                    for (Review r : reviews) {
                        System.out.println(r.getText() + " | " + r.getUserId() + " | " + r.getParentReviewId() + " | " +
                                r.getRecipeId() + " | " + r.getDtCreate() + " | " + r.getLikes() + " | " + r.getRating());
                    }
                    break;
                case 3:
                    System.out.println("Enter review ID:");
                    String id = scanner.next();
                    Review reviewById = reviewDAO.readById(id);
                    if (reviewById == null) {
                        System.out.println("Review not found");
                    } else {
                        System.out.println(reviewById.getText() + " | " + reviewById.getUserId() + " | " +
                                reviewById.getParentReviewId() + " | " + reviewById.getRecipeId() + " | " +
                                reviewById.getDtCreate() + " | " + reviewById.getLikes() + " | " + reviewById.getRating());
                    }
                    break;
                case 4:
                    System.out.println("Enter review ID:");
                    String idToUpdate = scanner.next();
                    Review reviewToUpdate = reviewDAO.readById(idToUpdate);
                    if (reviewToUpdate == null) {
                        System.out.println("Review not found");
                    } else {
                        updateReview(reviewToUpdate);
                        reviewDAO.update(reviewToUpdate);
                        System.out.println("Review updated!");
                    }
                    break;
                case 5:
                    System.out.println("Enter review ID:");
                    String idToDelete = scanner.next();
                    Review reviewToDelete = reviewDAO.readById(idToDelete);
                    if (reviewToDelete == null) {
                        System.out.println("Review not found");
                    } else {
                        reviewDAO.delete(idToDelete);
                        System.out.println("Review deleted!");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static Review createReview() {
        System.out.println("Enter recipe ID:");
        UUID recipeId = UUID.fromString(scanner.next());
        System.out.println("Enter parent review ID:");
        UUID parentReviewId = UUID.fromString(scanner.next());
        System.out.println("Enter user ID:");
        UUID userId = UUID.fromString(scanner.next());
        System.out.println("Enter rating:");
        int rating = scanner.nextInt();
        System.out.println("Enter text:");
        scanner.nextLine();
        String text = scanner.nextLine();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return new Review(UUID.randomUUID(), text, recipeId, parentReviewId, userId, 0, timestamp, rating);
    }

    private static void updateReview(Review review) {
        System.out.println("Enter new comment (current value: " + review.getText() + "):");
        scanner.nextLine();
        String text = scanner.nextLine();
        System.out.println("Enter new rating (current value: " + review.getRating() + "):");
        int rating = scanner.nextInt();

        review.setRating(rating);
        review.setText(text);
    }

    private static void categoryMenu(Connection connection) {
        CategoryDAO categoryDAO = new CategoryDAOImpl(connection);

        while (true) {
            System.out.println("Categories");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Category category = createCategory();
                    categoryDAO.create(category);
                    System.out.println("Category created!");
                    break;
                case 2:
                    List<Category> categories = categoryDAO.readAll();
                    for (Category c : categories) {
                        System.out.println(c.getTitle());
                    }
                    break;
                case 3:
                    System.out.println("Enter category ID:");
                    String id = scanner.next();
                    Category categoryById = categoryDAO.readById(id);
                    if (categoryById == null) {
                        System.out.println("Category not found");
                    } else {
                        System.out.println(categoryById.toString());
                    }
                    break;
                case 4:
                    System.out.println("Enter category ID:");
                    String idToUpdate = scanner.next();
                    Category categoryToUpdate = categoryDAO.readById(idToUpdate);
                    if (categoryToUpdate == null) {
                        System.out.println("Category not found");
                    } else {
                        updateCategory(categoryToUpdate);
                        categoryDAO.update(categoryToUpdate);
                        System.out.println("Category updated!");
                    }
                    break;
                case 5:
                    System.out.println("Enter category ID:");
                    String idToDelete = scanner.next();
                    Category categoryToDelete = categoryDAO.readById(idToDelete);
                    if (categoryToDelete == null) {
                        System.out.println("Category not found");
                    } else {
                        categoryDAO.delete(idToDelete);
                        System.out.println("Category deleted!");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static Category createCategory() {
        System.out.println("Enter category title:");
        String title = scanner.next();
        return new Category(UUID.randomUUID(), title);
    }

    private static void updateCategory(Category category) {
        System.out.println("Enter new title (current value: " + category.getTitle() + "):");
        String title = scanner.next();
        category.setTitle(title);
    }

    private static void favoriteRecipeMenu(Connection connection) {
        FavoriteRecipeDAO favoriteRecipeDAO = new FavoriteRecipeDAOImpl(connection);

        while (true) {
            System.out.println("Favorite Recipes");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by user ID");
            System.out.println("4. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    FavoriteRecipe favoriteRecipe = createFavoriteRecipe();
                    favoriteRecipeDAO.create(favoriteRecipe);
                    System.out.println("Favorite recipe created!");
                    break;
                case 2:
                    List<FavoriteRecipe> favoriteRecipes = favoriteRecipeDAO.readAll();
                    for (FavoriteRecipe fr : favoriteRecipes) {
                        System.out.println(fr.getUserId() + " | " + fr.getRecipeId());
                    }
                    break;
                case 3:
                    System.out.println("Enter user ID:");
                    String userId = scanner.next();
                    FavoriteRecipe favoriteRecipeByUserId = favoriteRecipeDAO.readById(userId);
                    if (favoriteRecipeByUserId != null) {
                        System.out.println(favoriteRecipeByUserId.getUserId() + " | " + favoriteRecipeByUserId.getRecipeId());
                    } else {
                        System.out.println("No favorite recipe found for user with ID: " + userId);
                    }
                    break;
                case 4:
                    System.out.println("Enter favorite recipe ID:");
                    String idToDelete = scanner.next();
                    favoriteRecipeDAO.delete(idToDelete);
                    System.out.println("Favorite recipe deleted!");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static FavoriteRecipe createFavoriteRecipe() {
        System.out.println("Enter user ID:");
        UUID userId = UUID.fromString(scanner.next());

        System.out.println("Enter recipe ID:");
        UUID recipeId = UUID.fromString(scanner.next());

        return new FavoriteRecipe(UUID.randomUUID(), userId, recipeId);
    }
}