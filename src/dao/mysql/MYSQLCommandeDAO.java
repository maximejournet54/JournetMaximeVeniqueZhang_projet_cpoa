package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
		int nbLignes = 0;		
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Commande` (`date_commande`, `id_client`) values (?, ?)");					
		requete.setDate(1, java.sql.Date.valueOf(commande.getDate()));
		requete.setInt(2, commande.getId());	
		//Verification de l'id client rentre
		PreparedStatement verifIdClient = laConnexion.prepareStatement("select id_client from `Client`");
		ResultSet resIdClient = verifIdClient.executeQuery();		
		boolean existId = false;
		while (resIdClient.next()) {
			if (commande.getId() == resIdClient.getInt(1))
				existId = true;
		}	
		if (existId)
			requete.setInt(2, commande.getIdClient());
		else 
			throw new IllegalArgumentException("\nIdentifiant de client inexistant");
		nbLignes = requete.executeUpdate();
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nErreur de la creation de la commande");
		if (laConnexion != null)
			laConnexion.close();	
		return nbLignes==1;
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
		Connection laConnexion = ConnexionMYSQL.creeConnexion();	
		int nbLignes = 0;		
		PreparedStatement requete = laConnexion.prepareStatement("update `Commande` set date_commande=?, id_client=? where id_commande=?");
		requete.setDate(1, java.sql.Date.valueOf(commande.getDate()));	
		//Verification de l'id client rentre
		PreparedStatement verifIdClient = laConnexion.prepareStatement("select id_client from `Client`");
		ResultSet resIdClient = verifIdClient.executeQuery();				
		boolean existId = false;
		while (resIdClient.next()) {
			if (commande.getIdClient() == resIdClient.getInt(1))
				existId = true;
		}	
		if (existId)
			requete.setInt(2, commande.getIdClient());
		else 
			throw new IllegalArgumentException("\nIdentifiant de client inexistant");	
		requete.setInt(3, commande.getId());		
		nbLignes = requete.executeUpdate();		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'une commande inexistante");	
		if (laConnexion != null)
			laConnexion.close();
		return nbLignes==1;
	}

    @Override
    public Commande getById(int id) throws SQLException{
		Commande commande = null;		
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();	
		Connection laConnexion = ConnexionMYSQL.creeConnexion();	
		//requete pour obtenir la commande desiree
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Commande` where id_commande=" + id);
		ResultSet res = requete.executeQuery();		
		if (res.next()) {
			//requete pour obtenir toute les lignes de commandes concernees par cette commande
			PreparedStatement requeteLc = laConnexion.prepareStatement("select * from `Ligne_Commande` where id_commande=" + id);
			ResultSet resLc = requeteLc.executeQuery();	
			while (resLc.next()) {
				//requete pour obtenir pour les produits concernes par la commande
				PreparedStatement requeteProd = laConnexion.prepareStatement("select * from `Produit` where id_produit=" + resLc.getInt(2));
				ResultSet resProd = requeteProd.executeQuery();				
				Produit prod = null;
				if (resProd.next()) 
					prod = new Produit(resProd.getInt(1), resProd.getString(2), resProd.getString(3), resProd.getFloat(4), resProd.getString(5), resProd.getInt(6));
				else 
					throw new IllegalArgumentException("Aucun produit ne possede cet identifiant");				
				ligneCommande.put(prod, new LigneCommande(resLc.getInt(1), resLc.getInt(2), resLc.getInt(3), resLc.getFloat(4)));
			}		
			commande = new Commande(res.getInt(1), res.getDate(2).toLocalDate(), res.getInt(3), ligneCommande);
		}
		else 
			throw new IllegalArgumentException("\nAucune commande ne possede cet identifiant");		
		if (laConnexion != null)
			laConnexion.close();
		return commande;
	}

	@Override
	public ArrayList<Commande> findAll() throws SQLException{
		ArrayList<Commande> listeCommande = new ArrayList<Commande>();
		Connection laConnexion = ConnexionMYSQL.creeConnexion();		
		//requete pour obtenir toutes les commandes
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Commande`");
		ResultSet res = requete.executeQuery();		
		while (res.next()) {
			HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();		
			//requete pour obtenir toute les lignes de commandes concernees par cette commande
			PreparedStatement requeteLc = laConnexion.prepareStatement("select * from `Ligne_Commande` where id_commande=" + res.getInt(1));
			ResultSet resLc = requeteLc.executeQuery();		
			while (resLc.next()) {
				//requete pour obtenir pour les produits concernes par la commande
				PreparedStatement requeteProd = laConnexion.prepareStatement("select * from `Produit` where id_produit=" + resLc.getInt(2));
				ResultSet resProd = requeteProd.executeQuery();				
				Produit produit = null;
				if (resProd.next()) 
					produit = new Produit(resProd.getInt(1), resProd.getString(2), resProd.getString(3), resProd.getFloat(4), resProd.getString(5), resProd.getInt(6));
				else 
					throw new IllegalArgumentException("Aucun produit ne possede cet identifiant");				
				LigneCommande ligne = new LigneCommande(resLc.getInt(1), resLc.getInt(2), resLc.getInt(3), resLc.getFloat(4));				
				ligneCommande.put(produit, ligne);
			}
			
			listeCommande.add(new Commande(res.getInt(1), res.getDate(2).toLocalDate(), res.getInt(3), ligneCommande));
		}		
		if (laConnexion != null)
			laConnexion.close();		
		return listeCommande;
	}
}
