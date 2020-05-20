package abstraction.eq6Distributeur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur1abs implements IActeur {

	protected Integer cryptogramme;
	protected Journal journalEq6;
	protected Variable stockHGE;
	protected Variable stockMG;
	protected Variable stockBG;
	protected Map<Integer,Map<Chocolat,Double>> evolutionCours;
	protected Map<ChocolatDeMarque,Double> MapStock;
	
	public Distributeur1abs() {
		this.journalEq6 = new Journal("Eq6 activites", this); 
		this.stockHGE=new Variable(getNom()+" stock "+ Chocolat.CHOCOLAT_HAUTE_EQUITABLE.toString(), this, 0, 1000000000, 1000000);
		this.stockMG=new Variable(getNom()+"stock"+ Chocolat.CHOCOLAT_MOYENNE.toString(), this, 0, 1000000000, 1000000);
		this.stockBG=new Variable(getNom()+"stock"+ Chocolat.CHOCOLAT_BASSE.toString(), this, 0, 1000000000, 1000000);
		this.journalEq6=new Journal(this.getNom()+" activites", this);
		evolutionCours = new HashMap<Integer,Map<Chocolat,Double>>();
		this.MapStock = new HashMap<ChocolatDeMarque,Double>();
	}
	
	public String getNom() {
		return "IMTermarché";
	}

	public String getDescription() {
		return "Distributeur bla bla bla";
	}
	
	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	/** @author Luca Pinguet & Mélissa Tamine */
	public void next() {
		journalEq6.ajouter("Etape="+Filiere.LA_FILIERE.getEtape());
		this.evolutionCours.put(Filiere.LA_FILIERE.getEtape(),new HashMap<Chocolat,Double>());
		if (Filiere.LA_FILIERE.getEtape()>=1) {
			for (ChocolatDeMarque chocos : this.MapStock.keySet()) {
				journalEq6.ajouter("Le prix moyen du chocolat \""+chocos.name()+"\" a l'etape precedente etait de "+Filiere.LA_FILIERE.prixMoyenEtapePrecedente(chocos));
			
		}
			for (ChocolatDeMarque chocos : this.MapStock.keySet()) {
				journalEq6.ajouter("Les ventes de chocolat \""+chocos.name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocos));
		
	}
		}
	}
	
	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq6);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}

