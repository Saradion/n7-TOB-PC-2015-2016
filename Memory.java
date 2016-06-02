import java.util.ArrayList;

/**
 * La classe Memory est la classe principale de l'application Memory.
 * Elle permet d'initialiser une partie de Memory en créant un jeu de cartes,
 * un arbitre et des joueurs qui sont inscrits auprès de l'arbitre.
 * <p>
 * Le lancement de l'application se fait par appel, dans la console, à la
 * commande :
 * <p>
 * > java Memory scrWidth nbFamily sizeFamily [-confiance] joueur@strategie...
 * <p>
 * *scrWidth, nbFamily, sizeFamily sont des entiers.
 * *-confiance est un paramètre optionnel qui peut prendre les valeurs :
 * -confiant | -mefiant
 * *l'argument joueur@strategie peut être répété autant de fois que désiré
 * <p>
 * Created by bigaigm on 11/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.2
 */
public class Memory {

    private static boolean hasOptions(String[] args) {
        return args[3].charAt(0) == '-';
    }

    private static String parseJudgeParam(String[] args) {
        String initParam;

        if (hasOptions(args)) {
            initParam = args[3];
        } else {
            initParam = "-méfiant";
        }

        return initParam;
    }

    private static ArrayList<String> parsePlayerParam(String[] args) {
        ArrayList<String> initParam = new ArrayList<>();
        int init = 3;
        if (hasOptions(args)) {
            init++;
        }

        for (int i = init; i < args.length; i++) {
            initParam.add((args[i].split("@"))[0]);
            initParam.add((args[i].split("@"))[1]);
        }

        return initParam;
    }

    private static void initPlayer(String[] args, Arbitre judge) {
        int init = 3;
        if (hasOptions(args)) {
            init++;
        }

        for (int i = init; i < args.length; i++) {
            judge.inscrire(new JoueurMemory((args[i].split("@"))[0], (args[i].split("@"))[1]));
        }

    }

    public static void main(String[] args) {
        Arbitre judge;
        JeuCartesMemory<String, Integer> jeu;

        try {
            // Création du jeu de cartes
            jeu = FabriqueJeuCartes.jeuFamilles(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

            // Création de l'arbitre
            judge = new Arbitre(parseJudgeParam(args), Integer.parseInt(args[0]));

            // Création et inscription des joueurs auprès de l'arbitre
            initPlayer(args, judge);

            // Lancement de la partie
            judge.arbitrer(jeu, Integer.parseInt(args[2]));

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur, paramètre incorrects!");
            System.out.println("Commande de lancement de l'application : java Memory largeurEcran nbFamille tailleFamille [-confiant] joueur@strat ...");
            System.out.println("Où largeurEcran, nbFamille et tailleFamille sont des entiers");
        }
    }
}
