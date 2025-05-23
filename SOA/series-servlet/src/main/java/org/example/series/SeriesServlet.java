package org.example.series;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/series")
public class SeriesServlet extends HttpServlet {
    private Connection connection;
    private Gson gson = new Gson();

    // DB config
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/series_db",
                    "series_user",
                    "strongpassword"
            );
        } catch (Exception e) {
            throw new ServletException("Database connection failure", e);
        }
    }

    // GET /series?title=Friends
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        List<Series> seriesList = new ArrayList<>();

        try (PreparedStatement statement = (title != null)
                ? connection.prepareStatement("SELECT * FROM series WHERE title = ?")
                : connection.prepareStatement("SELECT * FROM series")) {

            if (title != null) {
                statement.setString(1, title);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Series s = new Series();
                    s.setId(rs.getLong("id"));
                    s.setTitle(rs.getString("title"));
                    s.setGenre(rs.getString("genre"));
                    s.setYear(rs.getInt("year"));
                    seriesList.add(s);
                }
            }

        } catch (SQLException e) {
            response.sendError(500, "Database error: " + e.getMessage());
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(seriesList));
    }

    // POST /series
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Series series = gson.fromJson(request.getReader(), Series.class);

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO series (title, genre, year) VALUES (?, ?, ?)")) {

            statement.setString(1, series.getTitle());
            statement.setString(2, series.getGenre());
            statement.setInt(3, series.getYear());
            statement.executeUpdate();

        } catch (SQLException e) {
            response.sendError(500, "Database error: " + e.getMessage());
            return;
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("{\"message\":\"Series added successfully\"}");
    }

    // Cleanup resources
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {}
    }
}
