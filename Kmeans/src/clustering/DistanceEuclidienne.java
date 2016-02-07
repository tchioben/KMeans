package clustering;

public class DistanceEuclidienne implements Distance {

	@Override
	public double valeur(Donnee d1, Donnee d2) {
		double valeur = 0;
		for(int i=0;i<d1.nbDimensions();i++){
			valeur += Math.pow(d1.valeurDim(i)-d2.valeurDim(i),2);
		}
		return Math.sqrt(valeur);
	}

}
