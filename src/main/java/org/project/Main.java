package org.project;

import org.project.DAO.*;
import org.project.DAO.Impl.*;
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
                System.out.println();
                System.out.println("Choose table:");
                System.out.println("1. Reviews");
                System.out.println("2. Categories");
                System.out.println("3. Favorite Recipes");
                System.out.println("4. Recipes");
                System.out.println("5. Products");
                System.out.println("6. Product Modal");
                System.out.println("7. User");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> reviewMenu(connection);
                    case 2 -> categoryMenu(connection);
                    case 3 -> favoriteRecipeMenu(connection);
                    case 4 -> recipeMenu(connection);
                    case 5 -> productMenu(connection);
                    case 6 -> productModalMenu(connection);
                    case 7 -> userMenu(connection);
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    private static void reviewMenu(Connection connection) {
        ReviewDAO reviewDAO = new ReviewDAOImpl(connection);
        UserDAO userDAO = new UserDAOImpl(connection);
        while (true) {
            System.out.println();
            System.out.println("Reviews");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    Review review = createReview();
                    reviewDAO.create(review);
                    System.out.println("Review created!");
                }
                case 2 -> {
                    List<Review> reviews = reviewDAO.readAll();
                    for (Review r : reviews) {
                        User userById = userDAO.readById(r.getUserId().toString());
                        System.out.println(r.getUuid() + " | " + r.getText() + " | " + userById.getMail() + " | " + r.getParentReviewId() + " | " +
                                r.getRecipeId() + " | " + r.getDtCreate() + " | " + r.getLikes() + " | " + r.getRating());
                    }
                }
                case 3 -> {
                    System.out.println("Enter review ID:");
                    String id = scanner.next();
                    Review reviewById = reviewDAO.readById(id);
                    if (reviewById == null) {
                        System.out.println("Review not found");
                    } else {
                        User userById = userDAO.readById(reviewById.getUserId().toString());
                        System.out.println(reviewById.getUuid() + " | " + reviewById.getText() + " | " + userById.getMail() + " | " +
                                reviewById.getParentReviewId() + " | " + reviewById.getRecipeId() + " | " +
                                reviewById.getDtCreate() + " | " + reviewById.getLikes() + " | " + reviewById.getRating());
                    }
                }
                case 4 -> {
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
                }
                case 5 -> {
                    System.out.println("Enter review ID:");
                    String idToDelete = scanner.next();
                    Review reviewToDelete = reviewDAO.readById(idToDelete);
                    if (reviewToDelete == null) {
                        System.out.println("Review not found");
                    } else {
                        reviewDAO.delete(idToDelete);
                        System.out.println("Review deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
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
            System.out.println();
            System.out.println("Categories");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    Category category = createCategory();
                    categoryDAO.create(category);
                    System.out.println("Category created!");
                }
                case 2 -> {
                    List<Category> categories = categoryDAO.readAll();
                    for (Category c : categories) {
                        System.out.println(c.getUuid() + " | " + c.getTitle());
                    }
                }
                case 3 -> {
                    System.out.println("Enter category ID:");
                    String id = scanner.next();
                    Category categoryById = categoryDAO.readById(id);
                    if (categoryById == null) {
                        System.out.println("Category not found");
                    } else {
                        System.out.println(categoryById.getUuid() + " | " + categoryById.getTitle());
                    }
                }
                case 4 -> {
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
                }
                case 5 -> {
                    System.out.println("Enter category ID:");
                    String idToDelete = scanner.next();
                    Category categoryToDelete = categoryDAO.readById(idToDelete);
                    if (categoryToDelete == null) {
                        System.out.println("Category not found");
                    } else {
                        categoryDAO.delete(idToDelete);
                        System.out.println("Category deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
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

    private static Category findOrCreateCategory(Connection connection, String category_title) throws SQLException {
        CategoryDAO categoryDAO = new CategoryDAOImpl(connection);

        PreparedStatement readByTitleStatement = connection.prepareStatement("SELECT * FROM \"Categories\" WHERE title = ?");
        readByTitleStatement.setObject(1, category_title);

        ResultSet result = readByTitleStatement.executeQuery();
        Category cat;
        if (result.next()) {
            return new Category(result.getObject("uuid", UUID.class), result.getString("title"));
        } else {
            cat = new Category(UUID.randomUUID(), category_title);
            categoryDAO.create(cat);
            System.out.println("Category not found and was created.");
            return cat;
        }
    }

    private static void favoriteRecipeMenu(Connection connection) {
        FavoriteRecipeDAO favoriteRecipeDAO = new FavoriteRecipeDAOImpl(connection);

        while (true) {
            System.out.println();
            System.out.println("Favorite Recipes");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by user ID");
            System.out.println("4. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    FavoriteRecipe favoriteRecipe = createFavoriteRecipe();
                    favoriteRecipeDAO.create(favoriteRecipe);
                    System.out.println("Favorite recipe created!");
                }
                case 2 -> {
                    List<FavoriteRecipe> favoriteRecipes = favoriteRecipeDAO.readAll();
                    for (FavoriteRecipe fr : favoriteRecipes) {
                        System.out.println(fr.getUuid() + " | " + fr.getUserId() + " | " + fr.getRecipeId());
                    }
                }
                case 3 -> {
                    System.out.println("Enter user ID:");
                    String userId = scanner.next();
                    FavoriteRecipe favoriteRecipeByUserId = favoriteRecipeDAO.readById(userId);
                    if (favoriteRecipeByUserId != null) {
                        System.out.println(favoriteRecipeByUserId.getUuid() + " | " + favoriteRecipeByUserId.getUserId()
                                + " | " + favoriteRecipeByUserId.getRecipeId());
                    } else {
                        System.out.println("No favorite recipe found for user with ID: " + userId);
                    }
                }
                case 4 -> {
                    System.out.println("Enter favorite recipe ID:");
                    String idToDelete = scanner.next();
                    favoriteRecipeDAO.delete(idToDelete);
                    System.out.println("Favorite recipe deleted!");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
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

    private static void recipeMenu(Connection connection) throws SQLException {
        RecipeDAO recipeDAO = new RecipeDAOImpl(connection);
        ProductDAO productDAO = new ProductDAOImpl(connection);
        ProductModalDAO productModalDAO = new ProductModalDAOImpl(connection);
        CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
        while (true) {
            System.out.println();
            System.out.println("Recipes");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    Recipe recipe = createRecipe(connection);
                    recipeDAO.create(recipe);

                    while(true) {
                        System.out.println("Choose:");
                        System.out.println("1. Add existing product");
                        System.out.println("2. Create new product");
                        System.out.println("0. Back");
                        int choose = scanner.nextInt();
                        switch (choose) {
                            case 1 -> {
                                System.out.println("Enter product ID:");
                                String id = scanner.next();
                                Product productById = productDAO.readById(id);
                                if (productById == null) {
                                    System.out.println("Ingredient not found");
                                } else {
                                    System.out.println("Enter weight of product:");
                                    int weight = scanner.nextInt();
                                    ProductModal productModal = new ProductModal(UUID.randomUUID(), recipe.getUuid(), productById.getUuid(), weight);
                                    productModalDAO.create(productModal);
                                }
                            }
                            case 2 -> {
                                Product product = createProduct();
                                System.out.println("Enter weight of product:");
                                int weight = scanner.nextInt();
                                productDAO.create(product);
                                System.out.println("Product added to recipe.");
                                ProductModal productModal = new ProductModal(UUID.randomUUID(), recipe.getUuid(), product.getUuid(), weight);
                                productModalDAO.create(productModal);
                            }
                            case 0 -> {
                            }
                            default -> System.out.println("Invalid choice!");
                        }
                        if(choose == 0)
                            break;
                    }
                    System.out.println("Recipe created!");
                }
                case 2 -> {
                    List<Recipe> recipes = recipeDAO.readAll();
                    for (Recipe r : recipes) {
                        Category categoryById = categoryDAO.readById(r.getCategoryId().toString());
                        System.out.println(r.getUuid() + " | " + r.getTitle() + " | " + r.getDescription() + " | " +
                                categoryById.getTitle() + " | " + r.getComposition());
                    }
                }
                case 3 -> {
                    System.out.println("Enter recipe ID:");
                    String id = scanner.next();
                    Recipe recipeById = recipeDAO.readById(id);
                    if (recipeById == null) {
                        System.out.println("Recipe not found");
                    } else {
                        Category categoryById = categoryDAO.readById(recipeById.getCategoryId().toString());
                        System.out.println(recipeById.getUuid() + " | " + recipeById.getTitle() + " | " +
                                recipeById.getDescription() + " | " + categoryById.getTitle() + " | " +
                                recipeById.getComposition() );
                    }
                }
                case 4 -> {
                    System.out.println("Enter recipe ID:");
                    String idToUpdate = scanner.next();
                    Recipe recipeToUpdate = recipeDAO.readById(idToUpdate);
                    if (recipeToUpdate == null) {
                        System.out.println("Recipe not found");
                    } else {
                        updateRecipe(recipeToUpdate);
                        recipeDAO.update(recipeToUpdate);
                        System.out.println("Recipe updated!");
                    }
                }
                case 5 -> {
                    System.out.println("Enter recipe ID:");
                    String idToDelete = scanner.next();
                    Recipe recipeToDelete = recipeDAO.readById(idToDelete);
                    if (recipeToDelete == null) {
                        System.out.println("Recipe not found");
                    } else {
                        recipeDAO.delete(idToDelete);
                        System.out.println("Recipe deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static Recipe createRecipe(Connection connection) throws SQLException {
        System.out.println("Enter recipe title:");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Enter recipe description:");
        String description = scanner.nextLine();
        System.out.println("Enter category title:");
        String category_title = scanner.nextLine();

        Category cat = findOrCreateCategory(connection, category_title);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Recipe(UUID.randomUUID(), timestamp, timestamp, title, cat.getUuid(), description, null);
    }

    private static void updateRecipe(Recipe recipe) {
        System.out.println("Enter new title (current value: " + recipe.getTitle() + "):");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Enter new description (current value: " + recipe.getDescription() + "):");
        String description = scanner.nextLine();

        recipe.setTitle(title);
        recipe.setDescription(description);
    }

    private static void productMenu(Connection connection) {
        ProductDAO productDAO = new ProductDAOImpl(connection);

        while (true) {
            System.out.println();
            System.out.println("Products");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    Product product = createProduct();
                    productDAO.create(product);
                    System.out.println("Product created!");
                }
                case 2 -> {
                    List<Product> products = productDAO.readAll();
                    for (Product i : products) {
                        System.out.println(i.getUuid() + " | " + i.getTitle() + " | " + i.getCalories() + " | " + i.getProteins() + " | "
                                + i.getFats() + " | " + i.getCarbohydrates());
                    }
                }
                case 3 -> {
                    System.out.println("Enter product ID:");
                    String id = scanner.next();
                    Product productById = productDAO.readById(id);
                    if (productById == null) {
                        System.out.println("Ingredient not found");
                    } else {
                        System.out.println(productById.getUuid() + " | " + productById.getTitle() + " | " +
                                productById.getCalories() + " | " + productById.getProteins() + " | " +
                                productById.getFats() + " | " + productById.getCarbohydrates());
                    }
                }
                case 4 -> {
                    System.out.println("Enter product ID:");
                    String idToUpdate = scanner.next();
                    Product productToUpdate = productDAO.readById(idToUpdate);
                    if (productToUpdate == null) {
                        System.out.println("Product not found");
                    } else {
                        updateProduct(productToUpdate);
                        productDAO.update(productToUpdate);
                        System.out.println("Product updated!");
                    }
                }
                case 5 -> {
                    System.out.println("Enter product ID:");
                    String idToDelete = scanner.next();
                    Product ingredientToDelete = productDAO.readById(idToDelete);
                    if (ingredientToDelete == null) {
                        System.out.println("Product not found");
                    } else {
                        productDAO.delete(idToDelete);
                        System.out.println("Product deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static Product createProduct() {
        System.out.println("Enter product title:");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Enter product calories:");
        int calories = scanner.nextInt();
        System.out.println("Enter product proteins(,):");
        double proteins = scanner.nextDouble();
        System.out.println("Enter product fats(,):");
        double fats = scanner.nextDouble();
        System.out.println("Enter product carbohydrates(,):");
        double carbohydrates = scanner.nextDouble();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Product(UUID.randomUUID(), timestamp, timestamp, title, calories, proteins, fats, carbohydrates);
    }

    private static void updateProduct(Product product) {
        System.out.println("Enter new title (current value: " + product.getTitle() + "):");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter new calories (current value: " + product.getCalories() + "):");
        int calories = scanner.nextInt();

        product.setTitle(name);
        product.setCalories(calories);
    }

    public static void productModalMenu(Connection connection) {
        ProductModalDAO productModalDAO = new ProductModalDAOImpl(connection);

        while (true) {
            System.out.println();
            System.out.println("Product Modal");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    ProductModal productModal = createProductModal();
                    productModalDAO.create(productModal);
                    System.out.println("Product Modal created!");
                }
                case 2 -> {
                    List<ProductModal> productModals = productModalDAO.readAll();
                    for (ProductModal pm : productModals) {
                        System.out.println(pm.getRecipeId() + " | " + pm.getProductId() + " | " + pm.getWeight());
                    }
                }
                case 3 -> {
                    System.out.println("Enter Product Modal ID:");
                    String id = scanner.next();
                    ProductModal productModalById = productModalDAO.readById(id);
                    if (productModalById == null) {
                        System.out.println("Product Modal not found");
                    } else {
                        System.out.println(productModalById.getRecipeId() + " | " + productModalById.getProductId() +
                                " | " + productModalById.getWeight());
                    }
                }
                case 4 -> {
                    System.out.println("Enter Product Modal ID:");
                    String idToUpdate = scanner.next();
                    ProductModal productModalToUpdate = productModalDAO.readById(idToUpdate);
                    if (productModalToUpdate == null) {
                        System.out.println("Product Modal not found");
                    } else {
                        updateProductModal(productModalToUpdate);
                        productModalDAO.update(productModalToUpdate);
                        System.out.println("Product Modal updated!");
                    }
                }
                case 5 -> {
                    System.out.println("Enter Product Modal ID:");
                    String idToDelete = scanner.next();
                    ProductModal productModalToDelete = productModalDAO.readById(idToDelete);
                    if (productModalToDelete == null) {
                        System.out.println("Product Modal not found");
                    } else {
                        productModalDAO.delete(idToDelete);
                        System.out.println("Product Modal deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static ProductModal createProductModal() {
        System.out.println("Enter recipe ID:");
        UUID recipeId = UUID.fromString(scanner.next());
        System.out.println("Enter product ID:");
        UUID productId = UUID.fromString(scanner.next());
        System.out.println("Enter weight:");
        int weight = scanner.nextInt();

        return new ProductModal(UUID.randomUUID(), recipeId, productId, weight);
    }

    private static void updateProductModal(ProductModal productModal) {
        System.out.println("Enter new weight (current value: " + productModal.getWeight() + "):");
        int weight = scanner.nextInt();

        productModal.setWeight(weight);
    }

    public static void userMenu(Connection connection) {
        UserDAO userDAO = new UserDAOImpl(connection);

        while (true) {
            System.out.println();
            System.out.println("User");
            System.out.println("1. Create");
            System.out.println("2. Read all");
            System.out.println("3. Read by id");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    User user = createUser();
                    userDAO.create(user);
                    System.out.println("User created!");
                }
                case 2 -> {
                    List<User> users = userDAO.readAll();
                    for (User u : users) {
                        System.out.println(u.getUuid() + " | " + u.getFio() + " | " + u.getMail() + " | " + u.getRole()  + " | " + u.getStatus());
                    }
                }
                case 3 -> {
                    System.out.println("Enter User ID:");
                    String id = scanner.next();
                    User userById = userDAO.readById(id);
                    if (userById == null) {
                        System.out.println("User not found");
                    } else {
                        System.out.println(userById.getUuid() + " | " + userById.getFio() + " | " + userById.getMail() +
                                " | " + userById.getRole()  + " | " + userById.getStatus());
                    }
                }
                case 4 -> {
                    System.out.println("Enter User ID:");
                    String idToUpdate = scanner.next();
                    User userToUpdate = userDAO.readById(idToUpdate);
                    if (userToUpdate == null) {
                        System.out.println("User not found");
                    } else {
                        updateUser(userToUpdate);
                        userDAO.update(userToUpdate);
                        System.out.println("User updated!");
                    }
                }
                case 5 -> {
                    System.out.println("Enter User ID:");
                    String idToDelete = scanner.next();
                    User userToDelete = userDAO.readById(idToDelete);
                    if (userToDelete == null) {
                        System.out.println("User not found");
                    } else {
                        userDAO.delete(idToDelete);
                        System.out.println("User deleted!");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }

    }

    private static User createUser() {
        System.out.println("Enter fio:");
        String fio = scanner.nextLine();
        System.out.println("Enter mail:");
        String mail = scanner.next();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new User(UUID.randomUUID(), timestamp, timestamp, mail, fio, "USER", "NOT_ACTIVATED");

    }

    private static void updateUser(User user) {
        scanner.nextLine();
        System.out.println("Enter new fio (current value: " + user.getFio() + "):");
        String fio = scanner.nextLine();
        System.out.println("Enter new mail (current value: " + user.getMail() + "):");
        String mail = scanner.next();
        System.out.println("Enter new role (current value: " + user.getRole() + ")  (USER/ADMIN):");
        String role = scanner.next();
        System.out.println("Enter new status (current value: " + user.getStatus() + ")  (NOT_ACTIVATED/WAITING_ACTIVATION/ACTIVATED):");
        String status = scanner.next();
        user.setFio(fio);
        user.setMail(mail);
        user.setRole(role);
        user.setStatus(status);
    }
}