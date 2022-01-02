import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.InputMismatchException;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

//class App;
public class App {

    static Connection con;

    //constructor class App
    public App(){

    }

    public static void main(String[] args){
    String url = "jdbc:mysql://localhost:3306/spbu";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url,"root","");
        Scanner i = new Scanner(System.in);
        boolean lanjut = true;
        Penjualan j = new Penjualan();
        String iadm;
		System.out.println("Driver Ready (Enter sekali lagi)");
        System.out.print("Admin : ");
        iadm = i.nextLine();
        LocalDateTime myDateObj = LocalDateTime.now();
        //201522012RAHMADINA
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        do{
            System.out.println("\n\n==================================");
            System.out.println("|              MENU               |");
            System.out.println("==================================");
            System.out.println("1. Lihat Data");
            System.out.println("2. Tambah Data");
            System.out.println("3. Edit Data");
            System.out.println("4. Hapus Data");
            System.out.println("5. Cari Data");
            System.out.print("no : ");
            int menu = Integer.parseInt(i.nextLine());
            
            if (menu==1){
                
            Clean.clearScreen();
                j.riwayatPenjualan(iadm);
            }
            else if(menu==2){
                Clean.clearScreen();
                j.tambahPenjualan(iadm);
            }
            else if(menu==3){
                Clean.clearScreen();
                j.editPenjualan(iadm);
            }
            else if(menu==4){
                Clean.clearScreen();
                j.hapusPenjualan(iadm);
            }
            else if(menu==5){
                Clean.clearScreen();
                j.cariPenjualan(iadm);
                
            }    
            else{
                Clean.clearScreen();
                System.out.println("Menu tidak tersedia");
            }             
            System.out.print("\n\nKembali? (y/n) : ");
            String y = i.nextLine();
            lanjut = y.equalsIgnoreCase("y");
            }while(lanjut);
            System.out.println("\nTerima kasih telah menggunakan program ini! \nRahmadina, Padang, "+formattedDate);

            i.close();
    }
    catch (ClassNotFoundException ex) {
        System.err.println("Driver eror");
        System.exit(0);
    }
    catch(SQLException e){
        System.err.println("Tidak berhasil Koneksi");
    }
    }
}
