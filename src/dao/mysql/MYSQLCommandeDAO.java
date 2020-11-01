package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import dao.modele.CommandeDAO;
import connexion.ConnexionMYSQL;
import pojo.*;

public class MYSQLCommandeDAO implements CommandeDAO{
    private static MYSQLCommandeDAO instance;
    private MYSQLCommandeDAO() {}	

    public static MYSQLCommandeDAO getInstance() {
		if (instance == null)
			instance = new MYSQLCommandeDAO();		
		return instance;
    }
    
    @Override
    public boolean create(Commande commande) throws SQLException{
		Connection laConnexion = ConnexionMYSQL.creeConnexion();			
		PreparedStatement requete = laConnexion.prepareStatement("insert into Commande (date_commande, id_client) values (?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);					
		requete.setDate(1, commande.getDate());
		requete.setInt(2, commande.getIdClient());	
		
		int nblignes=requete.executeUpdate();
		ResultSet res=requete.getGeneratedKeys();
		
		int cle;
		if(res.next()) {
			cle = res.getInt(1);
			commande.setId(cle);	
		}
		
		laConnexion.close();
		requete.close();
		res.close();
		
		return nblignes==1;
	}

    @Override
    public boolean delete(Commande commande) throws SQLException{
		Connection laConnexion = ConnexionMYSQL.creeConnexion();		
		int nbLignesCom = 0;	
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Commande` where id_commande=?");
		requete.setInt(1, commande.getId());
		PreparedStatement supprLc = laConnexion.prepareStatement("delete from `Ligne_commande` where id_commande=?");
		supprLc.setInt(1, commande.getId());
		nbLignesCom = requete.executeUpdate();
		supprLc.executeUpdate();;
		if (nbLignesCom == 0)
			throw new IllegalArgumentException("\nTentative de suppression d'une commande inexistante");
		if (laConnexion != null)
			laConnexion.close();			
		return (nbLignesCom==1);
	}

    @Override
    public boolean update(Commande commande) throws SQLException{
    	int nblignes=0;
    	Connection laConnexion = ConnexionMYSQL.creeConnexion();			
		PreparedStatement requete = laConnexion.prepareStatement("update Commande set date_commande=? ,"
				+ "id_client=? where id_commande=?", java.sql.Statement.RETURN_GENERATED_KEYS);					
		requete.setDate(1, commande.getDate());
		requete.setInt(2, commande.getIdClient());
		requete.setInt(3, commande.getId());
		
		nblignes=requete.executeUpdate();
		ResultSet res=requete.getGeneratedKeys();
		int cle;
		
		if(res.next()) {
			cle=res.getInt(1);
			commande.setId(cle);
		}
		laConnexion.close();
		requete.close();
		res.close();
		
		return nblignes==1;
	}

    @Override
    public Commande getById(int id) throws SQLException{
		Commande commande = null;		

		Connection laConnexion = ConnexionMYSQL.creeConnexion();	
		PreparedStatement requete = laConnexion.prepareStatement("select * from Commande` where id_commande=?" );
		requete.setInt(1, id);
		
		ResultSet res = requete.executeQuery();	
		Client c=null;
		int idclient=0;
		while (res.next()) {
			commande= new Commande(id,res.getDate(2), c);
			Date d=commande.getDate();
			commande.setDate(d);;
			idclient=res.getInt("id_client");
		}
		PreparedStatement req1=laConnexion.prepareStatement("select * from Client where id_client="+idclient);
		ResultSet res1 = req1.executeQuery();
		while (res1.next()) {
			c= new Client(idclient,res1.getString("nom"),res1.getString("prenom"));
			
		}
		commande.setIdClient2(c);
		laConnexion.close();
		requete.close();
		res.close();
		return commande;
	}
    
    @Override
    public ArrayList<Commande> findAll() throws SQLException{
    	ArrayList<Commande> listeCommande = new ArrayList<Commande>();
    	Connection laConnexion = ConnexionMYSQL.creeConnexion();
		PreparedStatement requete = laConnexion.prepareStatement("select * from Commande ");	
		ResultSet res = requete.executeQuery();
		while (res.next()) {
			listeCommande.add(new Commande(res.getInt("id_commande"),res.getDate("date_commande"), res.getInt("id_client")));
		}
		requete.close();
		res.close();
		return listeCommande;	
	}
}

