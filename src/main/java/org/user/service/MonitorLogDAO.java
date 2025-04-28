package org.user.service;

import org.user.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class MonitorLogDAO {
    private static final String INSERT_STATEMENT = "INSERT INTO monitor_log(ativo, data_execucao, status, Error_Message) VALUES (?, ?, ?, ?)";

    public void save(String ativo, Date dataExecucao, int status, String Error_Message){
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_STATEMENT)) {

            ps.setString(1, ativo);
            ps.setObject(2, new java.sql.Timestamp(dataExecucao.getTime()));
            ps.setInt(3, status);
            ps.setString(4,Error_Message);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
