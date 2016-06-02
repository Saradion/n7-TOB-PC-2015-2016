/**
 * Jeu de cartes pour le Memory.
 *
 * @param <F> le type des familles des cartes
 * @param <V> le type des valeurs des cartes
 * @author Xavier CrÃ©gut <Prenom.Nom@enseeiht.fr>
 */
public class JeuCartesMemory<F, V> extends JeuCartes<Carte<F, V>> {

    private final int tailleFamille;

    public JeuCartesMemory(int tailleFamille) {
        super();
        this.tailleFamille = tailleFamille;
    }

    public int tailleFamille() {
        return this.tailleFamille;
    }

}
