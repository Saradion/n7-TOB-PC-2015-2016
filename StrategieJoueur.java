/**
 * Created by bigaigm on 12/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.1
 */
abstract class StrategieJoueur implements Strategie {
    public abstract int execute(int numero, Tapis<? extends Carte<?, ?>> tapis);

    public void interrupt(String nomJoueur) {

    }
}
