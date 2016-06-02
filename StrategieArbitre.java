/**
 * Created by bigaigm on 12/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.1
 */
abstract class StrategieArbitre implements Strategie {
    public abstract <F, V> int execute(Tapis<Carte<F, V>> tapis, ProxyTapis<Carte<F, V>> proxy, int numero);

    public abstract void handleCheater(Joueur player, Tapis cards);
}
