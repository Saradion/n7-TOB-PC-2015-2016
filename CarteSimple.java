/**
 * Une carte est définie par une couleur et une famille.
 *
 * @param <F> type de la famille
 * @param <V> type de la valeur
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */
public class CarteSimple<F, V> implements Carte<F, V> {

    private F famille;
    private V valeur;

    public CarteSimple(F famille, V valeur) {
        this.famille = famille;
        this.valeur = valeur;
    }

    public F getFamille() {
        return this.famille;
    }

    public V getValeur() {
        return this.valeur;
    }

    @Override
    public String toString() {
        return "" + this.famille + " " + this.valeur;
    }

}
