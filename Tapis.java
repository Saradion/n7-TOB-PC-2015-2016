/**
 * La classe Tapis modélise un tapis de cartes qui peuvent être visibles,
 * face cachée ou prises.
 *
 * @param <C> type des cartes du tapis
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */
public interface Tapis<C> {

    /**
     * La taille du tapis.
     *
     * @return la taille du tapis
     */
    int taille();

    /**
     * Le nombre de cartes encore présentes sur le tapis.
     *
     * @return le nombre de cartes encore présentes sur le tapis
     */
    int nbCartesRestantes();

    /**
     * Rendre visible une carte de ce tapis.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     * @throws CarteVisibleException     la carte est visible
     */
    void montrer(int position);

    /**
     * Retourner la carte pour qu'elle soit face cachée.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     * @throws CarteMasqueeException     la carte est face cachée
     */
    void masquer(int position);

    /**
     * Prendre une carte de ce tapis.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    void prendre(int position);

    /**
     * Savoir si une carte a été prise.
     *
     * @param position position de la carte
     * @return vrai si la carte a été prise
     * @throws PositionInvalideException la position est invalide
     */
    boolean estPrise(int position);

    /**
     * Savoir si une carte est visible.
     *
     * @param position position de la carte
     * @return vrai si la carte est visible
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    boolean estVisible(int position);

    /**
     * Obtenir une carte de ce tapis.
     *
     * @param position position de la carte
     * @return la carte à la position indiquée
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    C get(int position);

}
