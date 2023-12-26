package com.java.minimarket.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {
    private Connection connection;

    public Database() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud_jdbc", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan barang berdasarkan ID
    public Barang getBarangByID(int id) throws SQLException {
        String query = "SELECT * FROM barang WHERE id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Barang(rs.getInt("id"), rs.getString("nama_barang"), rs.getDouble("harga"), rs.getInt("stok"));
                }
            }
        }
        return null;
    }

    // Menambahkan struk
    public void addStruk(Datapenjualan struk) throws SQLException {
        String query = "INSERT INTO struk (nama_pembeli, barangID, total_harga_beli, jumlah_beli) VALUES (?, ?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, struk.getNamaPembeli());
            pstmt.setInt(2, struk.getBarangID());
            pstmt.setDouble(3, struk.getTotalHarga());
            pstmt.setInt(4, struk.getJumlahBeli());
            pstmt.executeUpdate();
        }
    }

    // Menambahkan barang
    public void addBarang(Barang barang) throws SQLException {
        String query = "INSERT INTO barang (nama_barang, harga, stok) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, barang.getNama());
            pstmt.setDouble(2, barang.getHarga());
            pstmt.setInt(3, barang.getStok());
            pstmt.executeUpdate();
        }
    }

    // Mendapatkan semua barang
    public List<Barang> getAllBarang() throws SQLException {
        List<Barang> barangList = new ArrayList<>();
        String query = "SELECT * FROM barang;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                barangList.add(new Barang(rs.getInt("id"), rs.getString("nama_barang"), rs.getDouble("harga"), rs.getInt("stok")));
            }
        }
        return barangList;
    }

    // Memperbarui barang
    public void updateBarang(Barang barang) throws SQLException {
        String query = "UPDATE barang SET nama_barang = ?, harga = ?, stok = ? WHERE id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, barang.getNama());
            pstmt.setDouble(2, barang.getHarga());
            pstmt.setInt(3, barang.getStok());
            pstmt.setInt(4, barang.getId());
            pstmt.executeUpdate();
        }
    }

    // Menghapus barang
    public void deleteBarang(int id) throws SQLException {
        String query = "DELETE FROM barang WHERE id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Menutup koneksi database
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}