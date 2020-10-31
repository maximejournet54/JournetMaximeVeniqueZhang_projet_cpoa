package pojo;

public class Client {
    private int id;
	private String nom ;
	private String prenom;
	private String identifiant;
	private String mdp;
	private int numero;
	private String rue;
	private int cp;
	private String ville;
	private String pays;

	public Client(int id, String nom, String prenom) {
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
	public Client(int id, String nom, String prenom, String identifiant, String mdp, int numero, String rue, int cp, String ville, String pays) {
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setIdentifiant(identifiant);
		this.setMdp(mdp);
		this.setNumero(numero);
		this.setRue(rue);
		this.setCp(cp);
		this.setVille(ville);
		this.setPays(pays);
	}
	
	//getters et setters

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id > 0) this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		if (nom == null || nom.trim().length() == 0) {
			throw new IllegalArgumentException("Nom vide !");
		}
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		if (prenom == null || prenom.trim().length() == 0) {
			throw new IllegalArgumentException("Prenom vide !");
		}
		this.prenom = prenom;
	}
	
	public void setIdentifiant(String identifiant) {
		if (identifiant == null || identifiant.trim().length() == 0) {
			throw new IllegalArgumentException("Identifiant vide !");
		}
		this.identifiant = identifiant;
	}
	
	public String getIdentifiant() {
		return this.identifiant;
	}
	
	private void setMdp(String mdp) {
		if (mdp == null || mdp.trim().length() == 0) {
			throw new IllegalArgumentException("Mot de passe vide !");
		}
		this.mdp = mdp;
	}
	
	public String getMdp() {
		return this.mdp;
	}
	
	private void setNumero(int numero) {
		//a rajouter condition
		this.numero = numero;
		
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	private void setRue(String rue) {
		if (rue == null || rue.trim().length() == 0) {
			throw new IllegalArgumentException("Rue vide !");
		}
		this.rue = rue;
	}
	
	public String getRue() {
		return this.rue;
	}

	private void setCp(int cp) {
		//a rajouter condition
		this.cp= cp;
	}
	
	public int getCp() {
		return this.cp;
	}

	private void setVille(String ville) {
		if (ville == null || ville.trim().length() == 0) {
			throw new IllegalArgumentException("Ville vide !");
		}
		this.ville = ville;
	}
	
	public String getVille() {
		return this.ville;
	}
	
	private void setPays(String pays) {
		if (pays == null || pays.trim().length() == 0) {
			throw new IllegalArgumentException("Pays vide !");
		}
		this.pays = pays;
	}
	
	public String getPays() {
		return this.pays;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  nom + " " + prenom +" " +identifiant +"\n " +mdp +" " +numero +" " +rue +" " +cp +" " +ville +" " +pays;
	}
	
}
    

