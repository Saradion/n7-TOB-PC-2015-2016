import java.util.ArrayList;
import java.util.List;

/**
 * FabriqueJeuCartes est une classe utilitaire qui définit
 * plusieurs méthodes pur créer des jeux de cartes.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */

public class FabriqueJeuCartes {


    /**
     * Construire un jeu de cartes pour Memory à partir des familles
     * et valeurs fournies.
     *
     * @param familles les familles du jeu
     * @param valeurs  les valeurs pour chaque famille
     * @return le jeu initialisé
     */
    public static <F, V> JeuCartesMemory<F, V> jeuFamilles(F[] familles, V[] valeurs) {
        JeuCartesMemory<F, V> jeu = new JeuCartesMemory<>(valeurs.length);    // XXX OK : même paquetage
        for (F f : familles) {
            for (V v : valeurs) {
                jeu.ajouter(new CarteSimple<F, V>(f, v));
            }
        }
        return jeu;
    }

    /**
     * Construire un jeu de cartes pour Memory qui comporte nbFamilles
     * avec cahcune tailleFamille éléments.  Les familles sont des chaînes
     * de caractères disctinctes (A, B, C, ..., AA, AB, ...). Les valeurs
     * d'une familles sont les entiers de 0 à tailleFamille -1.
     *
     * @param nbFamilles    le nombre de famille
     * @param tailleFamille le nombre de valeurs dans une famille
     * @return le jeu construit
     */
    public static JeuCartesMemory<String, Integer> jeuFamilles(int nbFamilles, int tailleFamille) {
        StringBuilder uneFamille = new StringBuilder();
        List<String> familles = new ArrayList<>(nbFamilles);
        for (int i = 0; i < nbFamilles; i++) {
            // "incrémenter" uneFamille
            // (chaque caractères est un compteur qui varie de 'A' à 'Z')
            int indice = uneFamille.length() - 1;
            while (indice >= 0 && uneFamille.charAt(indice) == 'Z') {
                // Une retenue à propager
                uneFamille.setCharAt(indice, 'A');
                indice--;
            }
            if (indice >= 0) {    // incrémenter un caractère
                uneFamille.setCharAt(indice, (char) (uneFamille.charAt(indice) + 1));
            } else {            // ajouter un nouveau caractère
                uneFamille.insert(0, 'A');
            }

            // Ajouter la nouvelle uneFamille
            familles.add(uneFamille.toString());
        }

        // Construire la liste des valeurs
        List<Integer> valeurs = new ArrayList<>();
        for (int i = 0; i < tailleFamille; i++) {
            valeurs.add(i);
        }

        return jeuFamilles(familles.toArray(new String[]{}),
                valeurs.toArray(new Integer[]{}));
    }


    public static JeuCartes<Carte52> jeu52() {
        JeuCartes<Carte52> jeu = new JeuCartes<>();
        for (Carte52.Enseigne e : Carte52.Enseigne.values()) {
            for (int i = 1; i <= 13; i++) {
                Carte52 carte = new Carte52(e, i);
                jeu.ajouter(carte);
            }
        }
        return jeu;
    }

    public static JeuCartesMemory<String, Carte52> jeuMemory52() {
        JeuCartesMemory<String, Carte52> jeu = new JeuCartesMemory<>(2);
        for (Carte52 carte : jeu52()) {
            String couleur =
                    (carte.getEnseigne().equals(Carte52.Enseigne.PIQUE)
                            || carte.getEnseigne().equals(Carte52.Enseigne.TREFFLE))
                            ? "Noir"
                            : "Rouge";
            String famille = carte.getValeur() + " " + couleur;
            jeu.ajouter(new CarteValeur<String, Carte52>(famille, carte));
        }
        return jeu;
    }

    /**
     * CarteValeur est une spécialisation de CarteSimple qui affiche
     * simplement la carte (et pas la famille).
     */
    private static class CarteValeur<F, V> extends CarteSimple<F, V> {
        public CarteValeur(F f, V v) {
            super(f, v);
        }

        @Override
        public String toString() {
            return String.valueOf(getValeur());
        }
    }

}
