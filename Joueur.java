/**
 * L'interface Joueur modélise un joueur.
 */
public interface Joueur {

    /**
     * Obtenir le nom du joueur.
     *
     * @return le nom du joueur
     */
    String getNom();

    /**
     * Evénement declenché par l'arbitre pour indiquer qu'une nouvelle
     * partie commence.
     *
     * @param tailleFamille la taille des familles à trouver
     */
    void commencer(int tailleFamille);

    /**
     * Méthode appelée par l'arbitre pour demander la carte choisie
     * par le joueur.
     *
     * @param numero le numéro de la carte demandée (1 pour la première)
     * @param tapis  le tapis de jeu du Memory
     */
    int carteChoisie(int numero, Tapis<? extends Carte<?, ?>> tapis);

    /**
     * Événement qui indique qu'une carte a été retournée et est donc visible.
     *
     * @param nomJoueur le nom du joueur qui l'a prise
     * @param numero    le numéro de la carte retournée (1 pour la première)
     * @param position  la position de la carte prise sur le tapis
     * @param tapis     le tapis sur lequel la carte a été prise
     */
    void carteRetournee(String nomJoueur, int numero, int position, Tapis<? extends Carte<?, ?>> tapis);

    /**
     * Événement qui indique qu'une carte a été prise.
     *
     * @param nomJoueur le nom du joueur qui l'a prise
     * @param position  la position de la carte prise sur le tapis
     * @param tapis     le tapis sur lequel la carte a été prise
     */
    void cartePrise(String nomJoueur, int position, Tapis<? extends Carte<?, ?>> tapis);

}
