package connexion;

import java.sql.*;

public class ConnexionMYSQL {
    public static Connection creeConnexion(){
        String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/journet9u_tdcpoa";
        url += "?serverTimezone=Europe/Paris";
        String login= "journet9u_appli";
        String pwd = "23JAN2001";
        Connection maConnexion=null;
        try {
            maConnexion=DriverManager.getConnection(url, login, pwd);
        } catch (SQLException sqle) {
            System.out.println("Erreur connexion " +sqle.getMessage());
            }
        return maConnexion;
        }       
}
