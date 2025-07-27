import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaCreator {

    public static void dropAllTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String[] dropStatements = new String[]{
                "DROP TABLE IF EXISTS article_votes",
                "DROP TABLE IF EXISTS article_views",
                "DROP TABLE IF EXISTS comments",
                "DROP TABLE IF EXISTS article_purchases",
                "DROP TABLE IF EXISTS subscriptions",
                "DROP TABLE IF EXISTS subscription_plans",
                "DROP TABLE IF EXISTS article_reviews",
                "DROP TABLE IF EXISTS article_tags",
                "DROP TABLE IF EXISTS tags",
                "DROP TABLE IF EXISTS articles",
                "DROP TABLE IF EXISTS series",
                "DROP TABLE IF EXISTS categories",
                "DROP TABLE IF EXISTS authors",
                "DROP TABLE IF EXISTS user_roles",
                "DROP TABLE IF EXISTS roles",
                "DROP TABLE IF EXISTS users"
        };

        for (String sql : dropStatements) {
            stmt.executeUpdate(sql);
        }

        stmt.close();
    }

    public static void createAllTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS users (
                user_id INT PRIMARY KEY AUTO_INCREMENT,
                username VARCHAR(255),
                email VARCHAR(255),
                password_hash VARCHAR(255),
                first_name VARCHAR(100),
                last_name VARCHAR(100),
                created_at TIMESTAMP,
                profile_picture_url VARCHAR(255)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS roles (
                role_id INT PRIMARY KEY AUTO_INCREMENT,
                role_name VARCHAR(50)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS user_roles (
                user_id INT,
                role_id INT,
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (role_id) REFERENCES roles(role_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS authors (
                author_id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT,
                bio TEXT,
                FOREIGN KEY (user_id) REFERENCES users(user_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS categories (
                category_id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255),
                slug VARCHAR(255),
                description TEXT
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS series (
                series_id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255),
                description TEXT,
                author_id INT,
                FOREIGN KEY (author_id) REFERENCES authors(author_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS articles (
                article_id INT PRIMARY KEY AUTO_INCREMENT,
                title VARCHAR(255),
                slug VARCHAR(255),
                content_draft TEXT,
                content_published TEXT,
                status ENUM('draft', 'in_review', 'published', 'archived'),
                created_at TIMESTAMP,
                published_at TIMESTAMP,
                primary_category_id INT,
                author_id INT,
                series_id INT,
                is_premium BOOLEAN,
                view_count BIGINT,
                FOREIGN KEY (primary_category_id) REFERENCES categories(category_id),
                FOREIGN KEY (author_id) REFERENCES authors(author_id),
                FOREIGN KEY (series_id) REFERENCES series(series_id),
                INDEX idx_articles_author_status_published_at (author_id, status, published_at),
                INDEX idx_articles_category_views (primary_category_id, view_count),
                INDEX idx_articles_status_created_at (status, created_at)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS tags (
                tag_id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255),
                slug VARCHAR(255)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS article_tags (
                article_id INT,
                tag_id INT,
                FOREIGN KEY (article_id) REFERENCES articles(article_id),
                FOREIGN KEY (tag_id) REFERENCES tags(tag_id),
                INDEX idx_article_tags_tag_id_article_id (tag_id, article_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS article_reviews (
                review_id INT PRIMARY KEY AUTO_INCREMENT,
                article_id INT,
                reviewer_id INT,
                status ENUM('approved', 'rejected', 'needs_changes'),
                feedback_notes TEXT,
                created_at TIMESTAMP,
                FOREIGN KEY (article_id) REFERENCES articles(article_id),
                FOREIGN KEY (reviewer_id) REFERENCES users(user_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS subscription_plans (
                plan_id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255),
                price_monthly DECIMAL(10,2),
                price_annually DECIMAL(10,2),
                description TEXT,
                is_active BOOLEAN
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS subscriptions (
                subscription_id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT,
                plan_id INT,
                start_date TIMESTAMP,
                end_date TIMESTAMP,
                status ENUM('active', 'canceled', 'expired'),
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (plan_id) REFERENCES subscription_plans(plan_id),
                INDEX idx_subscriptions_plan_status_end_date (plan_id, status, end_date)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS article_purchases (
                purchase_id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT,
                article_id INT,
                primary_category_id INT,
                amount_paid DECIMAL(10,2),
                currency VARCHAR(10),
                purchase_date TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (article_id) REFERENCES articles(article_id),
                FOREIGN KEY (primary_category_id) REFERENCES categories(category_id),
                INDEX idx_purchases_category_amount (primary_category_id, amount_paid)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS comments (
                comment_id INT PRIMARY KEY AUTO_INCREMENT,
                article_id INT,
                user_id INT,
                content TEXT,
                created_at TIMESTAMP,
                parent_comment_id INT,
                FOREIGN KEY (article_id) REFERENCES articles(article_id),
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (parent_comment_id) REFERENCES comments(comment_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS article_views (
                view_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                article_id INT,
                user_id INT,
                view_timestamp TIMESTAMP,
                ip_address VARCHAR(45),
                FOREIGN KEY (article_id) REFERENCES articles(article_id),
                FOREIGN KEY (user_id) REFERENCES users(user_id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS article_votes (
                user_id INT,
                article_id INT,
                vote_type ENUM('upvote', 'downvote'),
                voted_at TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (article_id) REFERENCES articles(article_id)
            );
        """);

        stmt.close();
        System.out.println(" All tables created successfully.");
    }
}