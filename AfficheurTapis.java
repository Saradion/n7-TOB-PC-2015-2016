/**
 * Cette classe est utilisée pour affiher un tapis.
 * L'affichage se fait de la manière suivante :
 * <pre>
 *  0- xxx |  1- xxx |  2- xxx |  3- xxx |  4- ~~~ |  5- ~~~
 *  6- ~~~ |  7- xxx |  8- ~~~ |  9- ~~~ | 10- B 1 | 11- ~~~
 * 12- A 2 | 13- ~~~ | 14- ~~~ | 15- C 1 | 16- xxx | 17- ~~~
 * </pre>
 * Chaque emplacement est représenté par sa position et le texte
 * de la carte contenue si elle est visible, xxx si elle est masquée
 * ~~~ si elle a été prise.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */

public class AfficheurTapis {

    private final String formatTexte;        // format pour afficher le texte
    private final String formatPosition;    // format pour afficher la position
    private final String masquee;    // chaîne à afficher si une carte est masquée
    private final String prise;        // chaîne à afficher si une carte est prise
    private static final String SEPARATEUR = "- ";
    // séparateur entre la position et le texte
    private static final String SEPARATEUR_COLONNE = " | ";
    // séparateur entre deux colonnes

    private final int nbColonnes;    // nombre de colonnes pour afficher un tapis


    /**
     * Initialiser un AfficheurTapis à partir d'un jeu
     * et d'une largeur d'écran.
     *
     * @param jeu              le jeu dont seront prises les cartes à afficher
     * @param largeurAffichage la largeur maximale de l'affichage
     */
    public AfficheurTapis(JeuCartes<?> jeu, int largeurAffichage) {
        if (jeu == null) {
            throw new IllegalArgumentException("Le paramètre 'jeu' doit être non null");
        }

        largeurAffichage = Math.max(0, largeurAffichage);

        // Calculer le texte le plus long et le nombre de cartes
        String plusLong = null;    // texte le plus long rencontré
        int longueurMax = 0;    // longueur du texte le plus long
        int nb = 0;                // nombre de cartes
        for (Object c : jeu) {
            nb++;
            String texte = c.toString();
            int longueur = texte.length();
            if (longueur > longueurMax) {
                longueurMax = longueur;
                plusLong = texte;
            }
        }

        // Définir les caractérisques de la partie position
        int largeurPosition = String.valueOf(nb).length();
        this.formatPosition = "%" + largeurPosition + "d";

        // Définir les caractérisques de la partie texte
        int largeurTexte = longueurMax;
        this.formatTexte = "%" + longueurMax + "s";
        this.masquee = plusLong.replaceAll(".", "x");
        this.prise = plusLong.replaceAll(".", "~");

        // définir le nombre de colonnes affichables
        int largeurColonne = largeurPosition + largeurTexte + SEPARATEUR.length();
        if (largeurAffichage <= largeurColonne) {
            this.nbColonnes = 1;
        } else {
            this.nbColonnes = 1 + (largeurAffichage - largeurColonne)
                    / (SEPARATEUR_COLONNE.length() + largeurColonne);
        }
    }

    /**
     * Obtenir la représentation d'un emplacement du tapis (position et texte).
     *
     * @param tapis    le tapis d'où provient la carte
     * @param position position sur le tapis de la carte à afficher
     * @return la chaîne décrivant l'emplacement
     */
    private String carteToString(Tapis<?> tapis, int position) {
        String texte = masquee;
        if (tapis.estPrise(position)) {
            texte = prise;
        } else if (tapis.estVisible(position)) {
            Object carte = tapis.get(position);
            texte = String.valueOf(carte);
        }

        return String.format(formatPosition, position) + SEPARATEUR
                + String.format(formatTexte, texte);
    }

    /**
     * Obtenir la représentation en chaîne de caractère d'un tapis.
     *
     * @param tapis le tapis à afficher
     */
    public String toString(Tapis<?> tapis) {
        if (tapis == null) {
            throw new IllegalArgumentException("'tapis' doit être non null");
        }
        if (tapis.taille() == 0) {
            throw new IllegalArgumentException("'tapis' doit être de taille non nulle");
        }
        String res = carteToString(tapis, 0);
        for (int p = 1; p < tapis.taille(); p++) {
            // Ajouter un séparateur
            if (p % nbColonnes == 0) {
                res += '\n';
            } else {
                res += SEPARATEUR_COLONNE;
            }

            // Afficher l'emplacement à la position p
            res += carteToString(tapis, p);
        }
        return res;
    }

}
