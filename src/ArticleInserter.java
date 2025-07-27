import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleInserter {

    public static void insertUser(Connection conn, String username, String email, String passwordHash,
                                  String firstName, String lastName, java.sql.Timestamp createdAt,
                                  String profilePictureUrl) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, first_name, last_name, created_at, profile_picture_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.setTimestamp(6, createdAt);
            stmt.setString(7, profilePictureUrl);
            stmt.executeUpdate();
        }
    }

    public static void insertRole(Connection conn, String roleName) throws SQLException {
        String sql = "INSERT INTO roles (role_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roleName);
            stmt.executeUpdate();
        }
    }

    public static void insertUserRole(Connection conn, int userId, int roleId) throws SQLException {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            stmt.executeUpdate();
        }
    }

    public static void insertAuthor(Connection conn, int userId, String bio) throws SQLException {
        String sql = "INSERT INTO authors (user_id, bio) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, bio);
            stmt.executeUpdate();
        }
    }

    public static void insertCategory(Connection conn, String name, String slug, String description) throws SQLException {
        String sql = "INSERT INTO categories (name, slug, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, slug);
            stmt.setString(3, description);
            stmt.executeUpdate();
        }
    }

    public static void insertSeries(Connection conn, String name, String description, int authorId) throws SQLException {
        String sql = "INSERT INTO series (name, description, author_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, authorId);
            stmt.executeUpdate();
        }
    }

    public static void insertArticle(Connection conn, String title, String slug, String contentDraft,
                                     String contentPublished, String status, java.sql.Timestamp createdAt,
                                     java.sql.Timestamp publishedAt, int primaryCategoryId, int authorId,
                                     int seriesId, boolean isPremium, long viewCount) throws SQLException {
        String sql = "INSERT INTO articles (title, slug, content_draft, content_published, status, created_at, " +
                "published_at, primary_category_id, author_id, series_id, is_premium, view_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, slug);
            stmt.setString(3, contentDraft);
            stmt.setString(4, contentPublished);
            stmt.setString(5, status);
            stmt.setTimestamp(6, createdAt);
            stmt.setTimestamp(7, publishedAt);
            stmt.setInt(8, primaryCategoryId);
            stmt.setInt(9, authorId);
            stmt.setInt(10, seriesId);
            stmt.setBoolean(11, isPremium);
            stmt.setLong(12, viewCount);
            stmt.executeUpdate();
        }
    }

    public static void insertTag(Connection conn, String name, String slug) throws SQLException {
        String sql = "INSERT INTO tags (name, slug) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, slug);
            stmt.executeUpdate();
        }
    }

    public static void insertArticleTag(Connection conn, int articleId, int tagId) throws SQLException {
        String sql = "INSERT INTO article_tags (article_id, tag_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            stmt.setInt(2, tagId);
            stmt.executeUpdate();
        }
    }

    public static void insertArticleReview(Connection conn, int articleId, int reviewerId, String status, String feedbackNotes, java.sql.Timestamp createdAt) throws SQLException {
        String sql = "INSERT INTO article_reviews (article_id, reviewer_id, status, feedback_notes, created_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            stmt.setInt(2, reviewerId);
            stmt.setString(3, status);
            stmt.setString(4, feedbackNotes);
            stmt.setTimestamp(5, createdAt);
            stmt.executeUpdate();
        }
    }

    public static void insertSubscriptionPlan(Connection conn, String name, double monthly, double annually, String description, boolean isActive) throws SQLException {
        String sql = "INSERT INTO subscription_plans (name, price_monthly, price_annually, description, is_active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, monthly);
            stmt.setDouble(3, annually);
            stmt.setString(4, description);
            stmt.setBoolean(5, isActive);
            stmt.executeUpdate();
        }
    }

    public static void insertSubscription(Connection conn, int userId, int planId, java.sql.Timestamp startDate, java.sql.Timestamp endDate, String status) throws SQLException {
        String sql = "INSERT INTO subscriptions (user_id, plan_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, planId);
            stmt.setTimestamp(3, startDate);
            stmt.setTimestamp(4, endDate);
            stmt.setString(5, status);
            stmt.executeUpdate();
        }
    }

    public static void insertArticlePurchase(Connection conn, int userId, int articleId, int categoryId, double amountPaid, String currency, java.sql.Timestamp purchaseDate) throws SQLException {
        String sql = "INSERT INTO article_purchases (user_id, article_id, primary_category_id, amount_paid, currency, purchase_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, articleId);
            stmt.setInt(3, categoryId);
            stmt.setDouble(4, amountPaid);
            stmt.setString(5, currency);
            stmt.setTimestamp(6, purchaseDate);
            stmt.executeUpdate();
        }
    }
}
