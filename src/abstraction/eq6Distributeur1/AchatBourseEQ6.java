package abstraction.eq6Distributeur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class AchatBourseEQ6 extends Stock implements IAcheteurChocolatBourse{
    
	public AchatBourseEQ6(double capaciteStockmax) {
		super(capaciteStockmax);
		// TODO Auto-generated constructor stub
	}


	
	private HashMap<Integer, HashMap<Chocolat, Double>> historiqueBourse;
	
	public double DemandeTotal(){
		double consomationAnnuel = Filiere.LA_FILIERE.getIndicateur("CLIENTFINAL consommation annuelle").getValeur();
		
		return consomationAnnuel;
	}
	
	public double EvolutionDemandeChocolat(Chocolat chocolat){ // il faut prendre en compte les ruptures de stocks
		
		double anneeYa1AN = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolat );
		
		if (Filiere.LA_FILIERE.getEtape()>24) {
			double anneeYa2AN = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-48, chocolat );//pareil que l'autre, mais pour le chocolat en question, en pourcentage
			
		    
			return anneeYa1AN + (anneeYa1AN-anneeYa2AN)/2;
			
		}

		if (Filiere.LA_FILIERE.getEtape()<=24) {
			return anneeYa1AN;
		}
		return 0;
	}
	
	
	
	
	public double getDemande(Chocolat chocolat, double cours) {
			double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme); // retourne l'argent du compte
			double max = solde/cours;
			double stockChoco = this.quantiteEnStockTypeChoco( chocolat);
			double DemandeTotal = this.DemandeTotal();
			double DeamndeChoco = this.EvolutionDemandeChocolat(chocolat);
			evolutionCours.get(Filiere.LA_FILIERE.getEtape()).put(chocolat, cours);
			if (DeamndeChoco<stockChoco) {
				return 0;
				
			}
			if (DeamndeChoco>=stockChoco) {
				
				double quantitéSouhaité =(DeamndeChoco/2 - stockChoco); //on fait l'hypothèse qu'on a la moitié du marché
				if (quantitéSouhaité*cours <solde) {
					return quantitéSouhaité;
					
				}
				else {
					return max;
				}
			}
			return 0;
		}
	
		
	
		public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {//codé
			if ( payee == true) {
				HashMap<Chocolat, Double> a = new HashMap<Chocolat, Double>();
				a.put(chocolat,quantiteObtenue);
				this.historiqueBourse.put(Filiere.LA_FILIERE.getEtape(),a );      //faut le completer avec Filiere.LA_FILIERE....., on fait un historique
			}
		}
	
		
	
		
		public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) { //coder
			if (superviseur !=null) {
				return this.cryptogramme;
			}
			return null;
		}

		@Override
		public void receptionner(ChocolatDeMarque chocolat, double quantite) {
			this.stocker(chocolat,  quantite);
			
		}
}