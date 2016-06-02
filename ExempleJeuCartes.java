/**
 * Exemple de jeu de cartes créés en utilisation FabriqueJeuCartes.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */

public class ExempleJeuCartes {

    public static void afficher(JeuCartes<?> jeu) {
        for (Object carte : jeu) {
            System.out.println(carte);
        }
    }

    public static void main(String[] args) {

        System.out.println();
        System.out.println("Jeu de 6 familles à 2 valeurs");
        JeuCartesMemory<String, Integer> jeu6_2
                = FabriqueJeuCartes.jeuFamilles(6, 2);
        afficher(jeu6_2);

        System.out.println();
        System.out.println("Jeu de 52 cartes :");
        JeuCartesMemory<String, Carte52> jeu = FabriqueJeuCartes.jeuMemory52();
        afficher(jeu);

        System.out.println();
        System.out.println("Jeu des 7 familles métiers");
        JeuCartesMemory<Metier, Membre> jeu7metiers
                = FabriqueJeuCartes.jeuFamilles(Metier.values(), Membre.values());
        afficher(jeu7metiers);
    }

    public static enum Membre {GrandMere, GrandPere, Mere, Pere, Fille, Fils}

    public static enum Metier {
        Pecheur, Garagiste, Fermier, Macon, Menuisier,
        Boulanger, Epicier
    }

    ;

}
