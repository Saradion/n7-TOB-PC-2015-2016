/**
 * Une carte est définie par une famille et une valeur.
 *
 * @param <F> type de la famille
 * @param <V> type de la valeur
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */
public interface Carte<F, V> {

    /**
     * La famille de la carte.
     *
     * @return la famille de la carte
     */
    F getFamille();

    /**
     * La valeur de la carte.
     *
     * @return la valeur de la carte
     */
    V getValeur();

}
