package clustering ;

/**
 *  Cluster, dans un algorithme de clustering de type K-mean. 
 * Un cluster contient des données, et un certain nombre de statistiques sur ces données.
 * Les données sont des n-uplets de nombres réels (double), n estle nombre de dimensions de la donnée
 *
 * @author     Anne-Cécile Caron
 */
public class Cluster implements java.lang.Iterable<Donnee>{

    private java.util.List<Donnee> data = new java.util.LinkedList<Donnee>() ; // les données du cluster
    private int dimDonnee ; // la dimension de chaque donnee
    private double[] min ; // tableau ayant pour taille la dimension des données, contient le min de chaque dimension
    private double[] max ; // idem pour max
    private double[] moy ; // idem pour moyenne
    private double[] somme ; // idem pour somme
    private double[] sd ; // idem pour les ecarts types ou "standard derivations"
    private int nb=0 ; // nombre de données
    private boolean ok = false ; // pour savoir quand calculer les ecarts types
    private boolean premiereDonnee = true ; // pour initialiser min et max

    /** Constructeur, 
     * @param dim la dimension des données que l'on va ranger dans le cluster.
     */
    public Cluster(int dim){
        this.dimDonnee = dim ;
        this.min = new double[this.dimDonnee] ;
        this.max = new double[this.dimDonnee] ;
        this.moy = new double[this.dimDonnee] ;
        this.somme = new double[this.dimDonnee] ;
        this.sd = new double[this.dimDonnee] ;
    }

    /**
     * comme son nom l'indique, permet d'obtenir un itérateur sur les données.
     * @return un iterateur sur les données du cluster.
     */
    public java.util.Iterator<Donnee> iterator(){
        return this.data.iterator() ;
    }

    /**
     * renvoie le nombre de dimensions des données du cluster, toutes les données ont le même nombre de dimensions.
     * @return le nombre de dimensions des données du cluster 
     */
    public int nbDimensions(){
        return this.dimDonnee ;
    }
  
    /**
     * permet d'ajouter une donnée au cluster.
     * @param d la donnée à ajouter
     * @exception ClusterException si on ajoute une donnée qui n'a pas le nombre de dimensions prévu à la création du cluster.
     */
    public void add(Donnee d) throws ClusterException{
        if (this.dimDonnee != d.nbDimensions()) throw new ClusterException("les donnees doivent avoir toutes la même dimension");
        this.data.add(d) ;
        this.nb++ ;
        this.calculMinMax(d.nbDimensions());
        this.addSomme(d);
        this.nouvelleMoyenne();
        // A COMPLETER : l'actualisation des tableaux min, max, somme et moy
       // this.ok = false ; // il faudra (re)calculer les ecarts types
        this.premiereDonnee = false ;
    }

    
    /**
     * mise � jour des moyennes
     */
    public void nouvelleMoyenne(){
    	for (int i = 0;i<this.nbDimensions();i++){
    		moy[i] = somme[i]/this.nb; 
    	}
    }
    
    /**
     * actualisation des sommes
     * @param d
     */
    public void addSomme(Donnee d){
    	for (int i = 0;i<d.nbDimensions();i++){
    		somme[i] += d.valeurDim(i); 
    	}
    }
    
    
    /**
     * permet de récupérer la ième donnée (ici, on voit le cluster comme une liste).
     * Attention, l'implémentation du cluster est basée sur une liste chaînée, donc cette méthode n'est pas efficace.
     * Pour un parcours de toutes les données, utiliser un iterateur.
     * @param i la position où se trouve la donnée
     * @return la donnée en position i, sachant que la première position est 0.
     */
    public Donnee get(int i){
        return this.data.get(i) ;
    }

    /**
     * renvoie la taille du cluster.
     * @return le nombre de données du cluster
     */
    public int size(){return this.nb ;}
  
    /**
     * permet d'enlever une donnée du cluster.
     * @param d la donnee à retirer
     */
    public void remove(Donnee d){
        this.data.remove(d) ;
        this.nb-- ;
        // on réactualise les tableaux min, max, somme et moy
        for (int i=0 ; i<this.dimDonnee ; i++){
            double vali = d.valeurDim(i) ;
            if (vali == min[i] || vali == this.max[i]) calculMinMax(i) ;
            this.somme[i] -= vali ;
            this.moy[i] = this.somme[i]/nb;
        }
    }

    // recherche la plus petite et la plus grande valeur de chaque dimension pour l'ensemble des données 
    private void calculMinMax(int i){
    	if (nb==1){
    	for (int n =0;n<i;n++){
    		this.min[n]=this.data.get(0).valeurDim(n);
        	this.max[n]=this.data.get(0).valeurDim(n);
    	}
    	}
   		for (int n = 0; n<i;n++){
   			for (Donnee d: this.data){
            double vali = d.valeurDim(n) ;
	            if (vali < min[n]) this.min[n]=vali ;
	            if (vali > max[n]) this.max[n]=vali ;
   			}
        }
    }
  
    /**
     * renvoie les valeurs min pour toutes les dimensions. Donc min ne renvoie pas forcément une donnée présente dans le cluster.
     * @return une donnée constitutée des valeurs minimales pour toutes les dimensions
     */
    public Donnee min(){
        return new Donnee(this.min) ;
    }
  
    /**
     * renvoie les valeurs max pour toutes les dimensions. Donc max ne renvoie pas forcément une donnée présente dans le cluster.
     * @return une donnée constitutée des valeurs maximales pour toutes les dimensions
     */
    public Donnee max(){
        return new Donnee(this.max) ;
    }

    /**
     * renvoie les valeurs moyennes pour toutes les dimensions, donc renvoie le barycentre du cluster.
     * @return une donnée constitutée des valeurs moyennes pour toutes les dimensions
     */
    public Donnee moyenne(){
        return new Donnee(this.moy) ;
    }

    /**
     * renvoie les écarts types pour toutes les dimensions.
     * @return une donnée constitutée des ecarts types pour toutes les dimensions
     */
    public Donnee ecartType(){
    	Donnee nouvelle = new Donnee(sd);
        if (! ok){// on recalcule this.sd
            
            }
            this.ok = true ;
        return new Donnee(sd);
    }

    /**
     * La compacité WC = somme pour toutes les données d de la distance de d au barycentre du cluster.
     * @return la compacité WC du cluster
     */
    public double wc(){
        double som = 0.0 ;
        // A COMPLETER
        return som ;
    }

    /**
     * Montre un exemple d'utilisation de la classe Cluster.
     */
    public static void main(String args[]) throws ClusterException{
        // on teste avec des données à 1 seule dimension
        // petit exemple avec la série 5, 5, 10, 10, 10, 10, 15, 15, 20.
     /*   double[] t5 = {5.0};
        double[] t10 = {10.0};
        double[] t15 = {15.0};
        double[] t20 = {20.0};
        Cluster lesDonnees = new Cluster(1) ;
        lesDonnees.add(new Donnee(t5));
        lesDonnees.add(new Donnee(t5));
        lesDonnees.add(new Donnee(t10));
        lesDonnees.add(new Donnee(t10));
        lesDonnees.add(new Donnee(t10));
        lesDonnees.add(new Donnee(t10));
        lesDonnees.add(new Donnee(t15));
        lesDonnees.add(new Donnee(t15));
        lesDonnees.add(new Donnee(t20));
        System.out.println("nb donnees : "+lesDonnees.size()) ;
        System.out.println("min : "+lesDonnees.min()) ;
        System.out.println("max : "+lesDonnees.max()) ;
        System.out.println("moy : "+lesDonnees.moyenne()) ;
        System.out.println("ecart type : "+lesDonnees.ecartType()) ;
       */
        /* affiche
           nb donnees : 9
           min : (5.0)
           max : (20.0)
           moy : (11.11111111111111)
           ecart type : (4.581228472908512)
        */
    }
}
