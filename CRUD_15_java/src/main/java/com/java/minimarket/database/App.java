package com.java.minimarket.database;

import java.util.Scanner;
import java.lang.invoke.VarHandle.AccessMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class App 
{

    private static Scanner scanner = new Scanner(System.in);
    private static final Database dbHelper = new Database();
    
    public static void main( String[] args )
    {
        User admin = new User("alfario", "Rio12");
        Login system = new Login(admin);
        Date date = new Date();
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss z");
        SimpleDateFormat sdfDate = new SimpleDateFormat("E, dd/MM/yyyy");

      

        if (!system.login()) {
            System.out.println("Login Gagal.");
            return;
        }

        int option;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Semua Barang");
            System.out.println("3. Ubah Barang");
            System.out.println("4. Hapus Barang");
            System.out.println("5. Transaksi");
            System.out.println("6. Keluar");
            System.out.print("Pilih opsi: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1: addBarang(); break;
                case 2: showAllBarang(); break;
                case 3: updateBarang(); break;
                case 4: deleteBarang(); break;
                case 5: transaksi(); break;
                case 6: System.out.println("Keluar."); break;
                default: System.out.println("Opsi Tidak");
            }
        } while (option != 6);

        try {
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Tambah Barang");
        System.out.println("2. Lihat Semua Barang");
        System.out.println("3. Ubah Barang");
        System.out.println("4. Hapus Barang");
        System.out.println("5. Transaksi");
        System.out.println("6. Keluar");
        System.out.print("Pilih opsi: ");
    }

    private static void addBarang() {
        System.out.println("Masukkan detail barang:");
        System.out.print("Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Barang: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Barang: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Barang barang = new Barang(0, nama, harga, stok);
        try {
            dbHelper.addBarang(barang);
            System.out.println("Barang berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan barang: " + e.getMessage());
        }
    }

    private static void showAllBarang() {
        try {
            List<Barang> barangList = dbHelper.getAllBarang();
            if (barangList.isEmpty()) {
                System.out.println("Tidak ada barang.");
                return;
            }
            System.out.println("Daftar Barang:");
            for (Barang barang : barangList) {
                System.out.printf("ID: %d, Nama: %s, Harga: %.2f, Stok: %d\n", barang.getId(), barang.getNama(), barang.getHarga(), barang.getStok());
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data barang: " + e.getMessage());
        }
    }

    private static void updateBarang() {
        System.out.print("Masukkan ID barang yang akan diperbarui: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Nama Barang baru: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Barang baru: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Barang baru: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Barang barang = new Barang(id, nama, harga, stok);
        try {
            dbHelper.updateBarang(barang);
            System.out.println("Barang berhasil diperbarui.");
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui barang: " + e.getMessage());
        }
    }

    private static void deleteBarang() {
        System.out.print("Masukkan ID barang yang akan dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            dbHelper.deleteBarang(id);
            System.out.println("Barang berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus barang: " + e.getMessage());
        }
    }

    private static void transaksi() {
        System.out.print("Nama Pembeli: ");
        String namaPembeli = scanner.nextLine();
        System.out.print("ID Barang: ");
        int barangID = scanner.nextInt();
        System.out.print("Jumlah Beli: ");
        int jumlah = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Barang barang = dbHelper.getBarangByID(barangID);
            if (barang == null) {
                System.out.println("Barang tidak ditemukan.");
                return;
            }
            if (jumlah > barang.getStok()) {
                System.out.println("Stok tidak cukup.");
                return;
            }

            double totalHarga = barang.getHarga() * jumlah;
            Datapenjualan struk = new Datapenjualan(namaPembeli, barangID, totalHarga, jumlah);
            dbHelper.addStruk(struk);
            printStruk(struk, barang.getNama(), barang.getHarga(), jumlah);
        } catch (SQLException e) {
            System.out.println("Gagal melakukan transaksi: " + e.getMessage());
        }
    }

    private static void printStruk(Datapenjualan struk, String namaBarang, double hargaSatuan, int jumlahBeli) {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy HH:mm:ss");
        System.out.println("\nSTRUK TRANSAKSI");
        System.out.println("Tanggal: " + sdf.format(new Date()));
        System.out.println("Nama Pembeli: " + struk.getNamaPembeli());
        System.out.println("Nama Barang: " + namaBarang);
        System.out.println("Harga Satuan: " + hargaSatuan);
        System.out.println("Jumlah Beli: " + jumlahBeli);
        System.out.println("Total Harga: " + struk.getTotalHarga());
        System.out.println("Terima Kasih!");
    }
}
 

