import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.InputMismatchException;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

public class Penjualan extends App implements Bbm{

    static Connection con;
    Scanner i = new Scanner(System.in);
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    double beli, belipm, belipt, belipx;

    int harga, no, ino;
    String admin,tgl,url;
    
    //contructor class Penjualan
    public Penjualan(){

    }
    
    @Override
    public int noFaktur(int iharga) throws SQLException {

        try {
            url = "jdbc:mysql://localhost:3306/SPBU";    
            con = DriverManager.getConnection(url,"root","");
            String sql ="SELECT MAX(No) FROM penjualan_bbm";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                int max = result.getInt(1);
                if(max>0){
                    no = max+1;
                }
                else{
                    no=1;
                }

            }
            
        } catch (Exception e) {
            System.out.println("Coba cek lagi yaa din semangat!");
            System.err.println(e.getMessage());
        }
        return no;
    }

    public String admin(String iadm){
        admin = iadm.toUpperCase();
        return admin;
    }

    public int harga(int iharga){
        harga = iharga;
        return harga;
    }

    public String tanggal(int iharga){
        tgl = myDateObj.format(myFormatObj);
        return tgl;
    }

    //premium = 6500/L
    public double jualPremium(int iharga){
        beli = (iharga/6500)+(iharga%6500);
        return beli;
    }
    //pertalit = 8000/L
    public double jualPertalite(int iharga){
        beli = (iharga/8000);
        return beli;
    }
    //pertamax = 10000/L
    public double jualPertamax(int iharga){
        beli = (iharga/10000);
        return beli;
    }

 
    public void tambahPenjualan(String iadm) throws SQLException{
        try {
            Clean.clearScreen();
            System.out.print("\nTAMBAH DATA TRANSAKSI PENJUALAN BBM");
            System.out.print("\nHarga : ");
            int iharga = i.nextInt();
            System.out.println("\n1. Premium\n2. Pertalite\n3. Pertamax");
            System.out.print("Jenis: ");
            int jenis = i.nextInt();
            if(jenis==1){
                beli = jualPremium(iharga)/1000;
                belipm = jualPremium(iharga);;
            }
            else if(jenis==2){
                beli = jualPertalite(iharga);
                belipt = jualPertalite(iharga);        
            }   
            else if(jenis==3)
            {
                beli = jualPertamax(iharga);
                belipx = jualPertamax(iharga);
           }
            else{
                System.out.println("Jenis BBM tidak tersedia");
            }
            
            url = "jdbc:mysql://localhost:3306/SPBU";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            String sql = "INSERT INTO penjualan_bbm (No, Tanggal, Admin, Kuantitas_Premium, Kuantitas_Pertalite, Kuantitas_Pertamax, Total_Harga) VALUES ('"+noFaktur(iharga)+"','"+tanggal(iharga)+"','"+admin(iadm)+"','"+belipm+"','"+belipt+"','"+belipx+"','"+harga(iharga)+"')";                   
            statement.executeUpdate(sql);     
            System.out.println("Bismillah yeyy Alhamdulillah");
        }

        catch (SQLException e) {
            System.out.println("Coba cek lagi yaa din semangat!");
            System.err.println(e.getMessage());
	    }
        
    }

    public void riwayatPenjualan(String iadm) throws SQLException{
        try{
            Clean.clearScreen();
            url = "jdbc:mysql://localhost:3306/SPBU";
            
            con = DriverManager.getConnection(url,"root","");
            String sql ="SELECT * FROM penjualan_bbm";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("\n\n---RIWAYAT TRANSAKSI PENJUALAN---");
            while (result.next()){
                System.out.print("\nNo Faktur  : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");                    
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nJenis      : ");
                if(result.getInt("Kuantitas_Premium")!=0)
                {
                    System.out.print("Premium");
                
                }
                else if(result.getInt("Kuantitas_Pertalite")!=0)
                {
                    System.out.print("Pertalite");
                
                }
                else if(result.getInt("Kuantitas_Pertamax")!=0)
                {
                    System.out.print("Pertamax");
                
                }
                System.out.print("\nJumlah     : ");
                if(result.getInt("Kuantitas_Premium")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Premium")+" Liter");
                
                }
                else if(result.getInt("Kuantitas_Pertalite")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Pertalite")+" Liter");
                
                }
                else if(result.getInt("Kuantitas_Pertamax")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Pertamax")+" Liter");
                
                }
                System.out.print("\nHarga      : ");                    
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
                }
        }
        catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    }
    }
 
    public void editPenjualan(String iadm) throws SQLException{
        try {
            System.out.print("Masukkan nomor faktur yang akan diedit : ");
            no = i.nextInt();
            String sql = "SELECT * FROM penjualan_bbm WHERE NO = " +no;
            url = "jdbc:mysql://localhost:3306/SPBU";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
           if (result.next()){
                System.out.println("\n\nNama sebelumnya : "+result.getString("ADMIN"));
                System.out.print("Nama terbaru : ");
                String inm = i.next();
                sql = "UPDATE penjualan_bbm SET ADMIN='"+inm+"' WHERE NO='"+no+"'";
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui nama admin pada faktur nomor "+no+" menjadi "+inm);
                }
            }
            statement.close();  
            i.nextLine();      
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }

    public void hapusPenjualan(String iadm) throws SQLException{
        try{
            con = DriverManager.getConnection(url,"root","");
		    Clean.clearScreen();
	        System.out.print("\nNo yang akan dihapus : ");
	        no = i.nextInt();
	        
	        String sql = "DELETE FROM penjualan_bbm WHERE No = "+ no;
	        Statement statement = con.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai dengan ID "+no);
	        }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data barang");
	        }        
    }

    public void cariPenjualan(String iadm) throws SQLException{
        try {
            System.out.print("Masukkan nomor faktur yang dicari: ");
            no = i.nextInt();
            url = "jdbc:mysql://localhost:3306/SPBU";String sql = "SELECT * FROM penjualan_bbm WHERE No LIKE '%"+no+"%'";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                System.out.print("\nNo Faktur  : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");                    
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nJenis      : ");
                if(result.getInt("Kuantitas_Premium")!=0)
                {
                    System.out.print("Premium");
                
                }
                else if(result.getInt("Kuantitas_Pertalite")!=0)
                {
                    System.out.print("Pertalite");
                
                }
                else if(result.getInt("Kuantitas_Pertamax")!=0)
                {
                    System.out.print("Pertamax");
                
                }
                System.out.print("\nJumlah     : ");
                if(result.getInt("Kuantitas_Premium")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Premium")+" Liter");
                
                }
                else if(result.getInt("Kuantitas_Pertalite")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Pertalite")+" Liter");
                
                }
                else if(result.getInt("Kuantitas_Pertamax")!=0)
                {
                    System.out.print(result.getInt("Kuantitas_Pertamax")+" Liter");
                
                }
                System.out.print("\nHarga      : ");                    
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
                }
            statement.close();        
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }

    }
}
