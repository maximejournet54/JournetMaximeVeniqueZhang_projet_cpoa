package dao.listememoire;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import pojo.Commande;
import pojo.Produit;
import pojo.LigneCommande;

public class ListeMemoireCommandeDAO implements CommandeDAO {
    private static ListeMemoireCommandeDAO instance;
	private List<Commande> donnees;

	public static  ListeMemoireCommandeDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireCommandeDAO();
		}
		return instance;
	}

	private ListeMemoireCommandeDAO() {
		this.donnees = new ArrayList<Commande>();
		HashMap <Produit, LigneCommande> ligneCommande1 = new HashMap<Produit, LigneCommande>(); 
		ligneCommande1.put(new Produit(2,"Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1), new LigneCommande(1,2,2,(float)41.5));
		ligneCommande1.put(new Produit(6, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l''elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2), new LigneCommande(1,6,1,15));
		this.donnees.add(new Commande(1, LocalDate.of(2020, Month.SEPTEMBER, 02),1,ligneCommande1)); 		
		HashMap <Produit, LigneCommande> ligneCommande2 = new HashMap<Produit, LigneCommande>(); 
		ligneCommande2.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		this.donnees.add(new Commande(2, LocalDate.of(2020, Month.AUGUST, 30),1,ligneCommande2)); 
	}

	@Override
	public boolean create(Commande objet) throws Exception {
		boolean ok=true;
		objet.setId(3);
		// Ne fonctionne que si l'objet métier est bien fait...
		while (this.donnees.contains(objet)) {
			objet.setId(objet.getId() + 1);
		}
		try {
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().getById(objet.getIdClient());
			
		} catch (IllegalArgumentException e) {
			ok = false;
			//System.out.println(e.getMessage());
		}
		
		if (ok)
			this.donnees.add(objet);
		
		return ok;
	}

	@Override
	public boolean update(Commande objet) throws Exception{
		boolean ok = true;	
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			ok = false;
			throw new IllegalArgumentException("Tentative de modification d'une commande inexistante");
		} 
		else {
			try {
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().getById(objet.getIdClient());				
			} catch (IllegalArgumentException e) {
				ok = false;
				System.out.println(e.getMessage());
			}			
			if (ok)
				this.donnees.set(idx, objet);
		}		
		return ok;
	}

	@Override
	public boolean delete(Commande objet) {
		HashMap<Produit, LigneCommande> ligneCommande = objet.getLigneCommande();		
		ligneCommande.clear();		
		// Ne fonctionne que si l'objet metier est bien fait...
		boolean ok = this.donnees.remove(objet);
		if (!ok) 
			throw new IllegalArgumentException("Tentative de suppression d'une commande inexistante");	
		return ok;
	}

	public Commande getById(int id) {
		// Ne fonctionne que si l'objet métier est bien fait...
		HashMap<Produit, LigneCommande> ligneCommandeTest = new HashMap<Produit, LigneCommande>();
		int idx = this.donnees.indexOf(new Commande(id, LocalDate.of(2020, Month.JANUARY, 1),0,ligneCommandeTest));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	public ArrayList<Commande> findAll() {
		return (ArrayList<Commande>) this.donnees;
	} 
    
}
