package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String DB_URL, String DB_USER, String DB_PASS) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASS;
    }

    private Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public String getMostPlayedSong() {
        String songName = "";

        try (Connection conn = getConnection()) {
            String sql = "SELECT song FROM music_broadcast ORDER BY times_aired DESC LIMIT 2";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                songName = rs.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return songName;
    }

    public String getMostActiveArtist() {
        String artistName = "";

        try (Connection conn = getConnection()) {
            String sql = "SELECT artist, count(times_aired) AS activityCount FROM music_broadcast GROUP BY artist ORDER BY activityCount DESC";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                artistName = rs.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return artistName;
    }
}
