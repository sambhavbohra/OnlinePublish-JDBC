import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FrequentQueries {

    // 1. Articles published per author per month
    public static void getArticlesPublishedPerAuthorPerMonth(Connection conn) throws SQLException {
        String query = """
            SELECT
                a.author_id,
                u.first_name,
                u.last_name,
                MONTH(ar.published_at) AS publish_month,
                COUNT(*) AS total_articles
            FROM articles ar
            JOIN authors a ON ar.author_id = a.author_id
            JOIN users u ON a.user_id = u.user_id
            WHERE ar.status = 'published'
            GROUP BY a.author_id, publish_month
            ORDER BY a.author_id, publish_month;
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Articles Published Per Author Per Month:");
            while (rs.next()) {
                System.out.println(
                        "Author: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                                ", Month: " + rs.getInt("publish_month") +
                                ", Articles: " + rs.getInt("total_articles"));
            }
        }
    }

    // 2. Subscriber churn rate by plan
    public static void getSubscriberChurnRateByPlan(Connection conn) throws SQLException {
        String query = """
            SELECT
                sp.name AS plan_name,
                COUNT(s.subscription_id) AS total,
                SUM(CASE WHEN s.status IN ('canceled', 'expired') THEN 1 ELSE 0 END) AS churned
            FROM subscriptions s
            JOIN subscription_plans sp ON s.plan_id = sp.plan_id
            GROUP BY sp.name;
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Subscriber Churn Rate By Plan:");
            while (rs.next()) {
                int total = rs.getInt("total");
                int churned = rs.getInt("churned");
                double rate = (total == 0) ? 0 : ((double) churned / total) * 100;
                System.out.println("Plan: " + rs.getString("plan_name") + ", Churn Rate: " + Math.round(rate) + "%");
            }
        }
    }

    // 3. Top-viewed articles by topic
    public static void getTopViewedArticlesByTopic(Connection conn) throws SQLException {
        String query = """
            SELECT
                ar.title,
                c.name AS topic,
                ar.view_count
            FROM articles ar
            JOIN categories c ON ar.primary_category_id = c.category_id
            WHERE ar.status = 'published'
            ORDER BY ar.view_count DESC
            LIMIT 10;
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Top Viewed Articles By Topic:");
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("title") +
                        ", Topic: " + rs.getString("topic") +
                        ", Views: " + rs.getLong("view_count"));
            }
        }
    }

    // 4. Pending articles in review queue
    public static void getPendingArticlesInReviewQueue(Connection conn) throws SQLException {
        String query = """
            SELECT
                ar.title,
                u.first_name,
                u.last_name,
                ar.created_at
            FROM articles ar
            JOIN authors a ON ar.author_id = a.author_id
            JOIN users u ON a.user_id = u.user_id
            WHERE ar.status = 'in_review'
            ORDER BY ar.created_at ASC;
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Pending Articles In Review Queue:");
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                        ", Created At: " + rs.getTimestamp("created_at"));
            }
        }
    }

    // 5. Revenue per content category
    public static void getRevenuePerContentCategory(Connection conn) throws SQLException {
        String query = """
            SELECT 
                c.name AS category,
                SUM(ap.amount_paid) AS total_revenue
            FROM article_purchases ap
            JOIN categories c ON ap.primary_category_id = c.category_id
            GROUP BY c.name
            ORDER BY total_revenue DESC;
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n💰 Revenue Per Content Category:");
            while (rs.next()) {
                System.out.println("Category: " + rs.getString("category") +
                        ", Revenue: ₹" + rs.getDouble("total_revenue"));
            }
        }
    }
}