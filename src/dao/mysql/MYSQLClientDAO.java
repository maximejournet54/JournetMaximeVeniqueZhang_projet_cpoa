package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.modele.ClientDAO;
import connexion.ConnexionMYSQL;
import pojo.Client;

public class MYSQLClientDAO implements ClientDAO{
    private static MYSQLClientDAO instance;
    private MYSQLClientDAO() {}
    
    public static MYSQLClientDAO getInstance() {
		if (instance == null)
			instance = new MYSQLClientDAO();
		return instance;
    }
    
    @Override
    public boolean create(Client client) throws SQLException{
		Connection laConnexion = ConnexionMYSQL.creeConnexion();
		int nbLignes = 0;
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Client` (`nom`, `prenom`, `identifiant`, `mot_de_passe`, `adr_numero`, `adr_voie`, `adr_code_postal`, `adr_ville`, `adr_pays`) "
				+ "VALUES (?, ?,'identifiant', 'mdp' , 0, 0, 0, 'HOME', 'LAND')");	
		requete.setString(1, client.getNom());	
		requete.setString(2, client.getPrenom());	
		nbLignes = requete.executeUpdate(); 
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nEchec lors de la creation du client");
		if (laConnexion != null)
			laConnexion.close();	
		return nbLignes==1;
	}

    @Override
    public boolean delete(Client client) throws SQLException{				
		Connection laConnexion = ConnexionMYSQL.creeConnexion();		
		int nbLignes = 0;		
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Client` where id_client=?");
		requete.setInt(1,client.getId());
		nbLignes = requete.executeUpdate();	
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'un client inexistant");
		if (laConnexion != null)
			laConnexion.close();	
		return nbLignes==1;
	}

    @Override
    public boolean update(Client client) throws SQLException{
		Connection laConnexion = ConnexionMYSQL.creeConnexion();
		int nbLignes = 0;		
		PreparedStatement requete = laConnexion.prepareStatement("update `Client` set nom=?, " + "prenom=? " + "where id_client=?");																		
		requete.setString(1,client.getNom());
		requete.setString(2,client.getPrenom());
		requete.setInt(3,client.getId());
		nbLignes = requete.executeUpdate();
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'un client inexistant");	
		if (laConnexion != null)
			laConnexion.close();	
		return nbLignes==1;
	}

    @Override
    public Client getById(int id) throws SQLException {
        Client client = null;		
		Connection laConnexion = ConnexionMYSQL.creeConnexion();		
		PreparedStatement requete = laConnexion.prepareStatement("select id_client, nom, prenom from `Client` where id_client =" + id);		
		ResultSet res = requete.executeQuery();
		//S'il y a une valeur dans le resultat
		if (res.next()) {
			client = new Client(res.getInt(1), res.getString(2), res.getString(3));
		}
		else {
			throw new IllegalArgumentException("\nAucun client ne correspond a cet identifiant");
		}			
		if (laConnexion != null)
			laConnexion.close();
		return client;
    }

	@Override
	public ArrayList<Client> findAll() throws SQLException{
		ArrayList<Client> listeClient = new ArrayList<Client>();
		Connection laConnexion = ConnexionMYSQL.creeConnexion();
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Client` ");		
		ResultSet res = requete.executeQuery();		
		while (res.next()) {
			listeClient.add(new Client(res.getInt(1), res.getString(2), res.getString(3)));
		}		
		if (laConnexion != null)
			laConnexion.close();
		return listeClient;
	}
}
