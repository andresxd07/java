package com.tuempresa.cursoapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tuempresa.cursoapp.model.Curso;

public class CursoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/tu_basededatos";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "contraseña";

    public void crearCurso(Curso curso) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO cursos (nombre, descripcion) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, curso.getNombre());
                statement.setString(2, curso.getDescripcion());
                statement.executeUpdate();
            }
        }
    }

    public List<Curso> obtenerCursos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT id, nombre, descripcion FROM cursos";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre");
                        String descripcion = resultSet.getString("descripcion");
                        cursos.add(new Curso(id, nombre, descripcion));
                    }
                }
            }
        }
        return cursos;
    }

    public Curso obtenerCursoPorId(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT nombre, descripcion FROM cursos WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String nombre = resultSet.getString("nombre");
                        String descripcion = resultSet.getString("descripcion");
                        return new Curso(id, nombre, descripcion);
                    }
                }
            }
        }
        return null;
    }

    public void actualizarCurso(Curso curso) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE cursos SET nombre = ?, descripcion = ? WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, curso.getNombre());
                statement.setString(2, curso.getDescripcion());
                statement.setInt(3, curso.getId());
                statement.executeUpdate();
            }
        }
    }

    public void borrarCurso(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM cursos WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
    }
}
