package pojo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Commande {
    DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private int id; 
	private Date date; 
	private String date2;
	private LocalDate ld;
	
	private int idClient;
	private Client idClient2;
    private HashMap<Produit, LigneCommande> ligneCommande;
    
    
	public Commande(int id, Date date, int idClient, HashMap<Produit, LigneCommande> ligneCommande) {
		this.setId(id);
		this.setDate(date);
		this.setIdClient(idClient);
		this.setLigneCommande(ligneCommande);
	}
	
	public Commande(int id,Date d, int idClient) {
		this.setId(id);
		this.setDate(d);
		this.setIdClient(idClient);
	}
	
	public Commande(String date2, int idClient) {
		this.setDate2(date2);
		this.setIdClient(idClient);
	}
	
	 public Commande(int id2, LocalDate l, int idClient2, HashMap<Produit, LigneCommande> lc) {
		 this.setId(id);
		 this.setL(ld);
		 this.setLigneCommande(lc);
	}
	 
	 public Commande(int idCommande,Date dateCommande,Client idclient) {
			this.setId(idCommande);
			this.setDate(dateCommande);
			this.setIdClient2(idclient);	
		}

	private void setL(LocalDate ld2) {
		this.ld=ld2;
		
	}
	
	public LocalDate getL() {
		return ld;
	}

	public String getDate2() {
			return date2;
		}
    
    private void setDate2(String date2) {
		this.date2=date2;		
	}

	//getters et setters
    
    public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if (id > 0) 
			this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
			this.date = date;
	}
	
	public int getIdClient() {
		return idClient;
	}
	
	public void setIdClient(int idClient) {
		if (idClient > 0) 
			this.idClient = idClient;
	}
	
	public void setIdClient2(Client idClient2) {
		this.idClient2 = idClient2;
	}
	
	public Client getIdClient2() {
		return idClient2;
	}
	
	public HashMap<Produit, LigneCommande> getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(HashMap<Produit, LigneCommande> ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  date +" "  +idClient ;
    } 
    
}
