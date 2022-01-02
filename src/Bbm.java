import java.sql.SQLException;

public interface Bbm {
    public int noFaktur(int iharga) throws SQLException;
    public String admin(String iadm);
    public int harga(int iharga);
    public String tanggal(int iharga);
    public double jualPremium(int iharga);
    public double jualPertalite(int iharga);
    public double jualPertamax(int iharga);
}
