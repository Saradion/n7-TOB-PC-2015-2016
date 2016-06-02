/**
 * Created by bigaigm on 12/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.1
 */
class ProxyTapis<C> implements Tapis<C> {
    private Tapis<C> tapisReel;

    ProxyTapis(Tapis<C> tapis) {
        this.tapisReel = tapis;
    }

    @Override
    public int taille() {
        return this.tapisReel.taille();
    }

    @Override
    public int nbCartesRestantes() {
        return this.tapisReel.nbCartesRestantes();
    }

    @Override
    public void montrer(int position) {
        this.tapisReel.montrer(position);
    }

    @Override
    public void masquer(int position) {
        this.tapisReel.masquer(position);
    }

    @Override
    public void prendre(int position) {
        this.tapisReel.prendre(position);
    }

    @Override
    public boolean estPrise(int position) {
        return this.tapisReel.estPrise(position);
    }

    @Override
    public boolean estVisible(int position) {
        return this.tapisReel.estVisible(position);
    }

    @Override
    public C get(int position) {
        throw new OperationInterditeException("Attention! Triche!");
    }
}
