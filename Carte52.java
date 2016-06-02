/**
 * Définition des cartes d'un jeu de 52 cartes.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */
public class Carte52 {

    public static enum Enseigne {PIQUE, COEUR, CARREAU, TREFFLE}

    private Enseigne enseigne;
    private int valeur;

    public Carte52(Enseigne enseigne, int valeur) {
        this.enseigne = enseigne;
        this.valeur = valeur;
    }

    public Enseigne getEnseigne() {
        return this.enseigne;
    }

    public int getValeur() {
        return this.valeur;
    }

    @Override
    public String toString() {
        return "" + this.getValeur() + " " + this.getEnseigne();
    }

}
