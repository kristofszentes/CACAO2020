package abstraction.eq5Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.ventesCacaoAleatoires.IAcheteurCacaoAleatoire;
import abstraction.eq8Romu.ventesCacaoAleatoires.SuperviseurVentesCacaoAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
@SuppressWarnings("unused")
public class Transformateur3 implements IActeur, IAcheteurCacaoCriee, IVendeurChocolatBourse, IAcheteurCacaoAleatoire {

	private Integer cryptogramme;
	private Journal journalEq5;
	protected AchatCacao acheteurCacao;
	protected AchatPate acheteurPate;
	protected VenteChocolat VendeurChocolat;
	protected Tresorerie tresorier;
	protected Stock stock;

	public Transformateur3() {
		this.journalEq5 = new Journal("Eq5 activites", this);
		this.acheteurCacao = new AchatCacao(this); //needs to be filled with parameters this will work for now
		this.acheteurPate = new AchatPate(this); 
		this.VendeurChocolat = new VenteChocolat(this); 
		this.tresorier = new Tresorerie(this);
		this.stock = new Stock(this);
	}

	public String getNom() {
		return "Whish'oco";
	}

	public String getDescription() {
		return "Transformateur qui aime le chocolat.";
	}

	public Color getColor() {
		return new Color(233, 30, 99);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void next() {
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(this.stock.getStockFeves().values());
		res.addAll(this.stock.getStockChocolat().values());
		return res;
	}

	public List<Variable> getParametres() {
		//TODO ici devront être mis les paramètres dont je parlais (finalement ce seront des variables qu'il faudra penser à ajouter ici)
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(this.journalEq5);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this == acteur) {
			System.out.println("RIP in pieces" + this.getNom());
		} else {
			System.out.println("Poor " + acteur.getNom() + "... We will miss you. " + this.getNom());
		}
	}

	public void notificationOperationBancaire(double montant) {
		String str = montant > 0 ? "On a gagné de l'argent ! " : "On a perdu de l'argent ! ";
		this.journalEq5.ajouter(str + montant);
	}

	// Vente de Chocolat en bourse IVenteChocolatBourse
	
	public double getOffre(Chocolat chocolat, double cours) {
		return this.VendeurChocolat.getOffre(chocolat, cours);
		
	}

	
	public void livrer(Chocolat chocolat, double quantite) {
		this.stock.getStockChocolat().get(chocolat).retirer(this, quantite);
	}

	// Achat de cacao en criée IAcheteurCacaoCriee
	
	public double proposerAchat(LotCacaoCriee lot) {
		return this.acheteurCacao.proposerAchat(lot);
	}

	
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.acheteurCacao.notifierPropositionRefusee(proposition);
	}

	
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		return this.acheteurCacao.getCryptogramme(superviseur);
	}

	
	public void notifierVente(PropositionCriee proposition) {
		this.acheteurCacao.notifierVente(proposition);
	}
	
	// Achat de cacao aléatoire IAcheteurCacaoAleatoire c'est bizarre ce protocole 
	
	public double quantiteDesiree(double quantiteEnVente, double prix) {
		return this.acheteurCacao.quantiteDesiree(quantiteEnVente, prix);
	}

	
	public void quantiteLivree(double quantiteLivree) {
		this.stock.getStockChocolat().get(null).ajouter(this, quantiteLivree);
	}

	
	public Integer getCryptogramme(SuperviseurVentesCacaoAleatoires superviseur) {
		
		return superviseur == null ? Integer.valueOf(0) : this.cryptogramme ;
	}
	//TODO ajouter les methodes pour acheter de la pate de cacao

}
