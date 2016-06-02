import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * La classe Arbitre permet de gérer une partie de Memory
 * entre plusieurs jourueurs.
 *
 * @author Mathias Bigaignon
 * @version 1.2
 */
public class Arbitre {
    private StrategieArbitre strat_arbitre;
    private LinkedList<Joueur> players;
    private int width;
    private AfficheurTapis renderer;
    private Scores scores;
    private Joueur current;
    private Set<String> players_names;

    public Arbitre(String strat, int width) {
        this.players = new LinkedList<>();
        this.players_names = new HashSet<>();
        switch (strat) {
            case "-méfiant":
                this.strat_arbitre = new StrategieMefiant();
                break;
            case "-confiant":
                this.strat_arbitre = new StrategieConfiant();
                break;
            default:
                throw new RuntimeException();
        }
        this.width = width;
        this.scores = new ScoresMemory();
    }

    /**
     * Inscrire un joueur.
     *
     * @param joueur le joueur à inscrire
     */
    public void inscrire(Joueur joueur) {
        if (this.players_names.add(joueur.getNom())) {
            this.players.add(joueur);
            this.scores.register(joueur);
        } else {
            throw new NomIllegalException("The name " + joueur.getNom() + "is already used!");
        }
    }

    /**
     * Arbitrer une partie de Memory.  La taille de famille utilisée doit être
     * un diviseur de la taille d'une famille du jeu de carte.
     *
     * @param jeu           le jeu de carte à utliser
     * @param tailleFamille la taille d'une famille à utilise.
     * @param <F>           le type des familles du jeu de cartes
     * @param <V>           le type des valeurs du jeu de cartes
     */
    public <F, V> void arbitrer(JeuCartesMemory<F, V> jeu, int tailleFamille) {
        // battre le jeu
        System.out.println("L'arbitre mélange les cartes...");
        jeu.battre();

        // créer le tapis et son proxy
        System.out.println("L'arbitre met en place le tapis...");
        Tapis<Carte<F, V>> tapis = new TapisMemory<>(jeu);
        ProxyTapis<Carte<F, V>> proxy = new ProxyTapis<>(tapis);

        // créer l'afficheur
        this.renderer = new AfficheurTapis(jeu, this.width);

        // avertir les joueurs du début de la partie
        System.out.println("Début de la partie! La taille des familles est de " + tailleFamille);
        for (Joueur j : players) {
            j.commencer(tailleFamille);
        }

        // jouer la partie
        while (tapis.nbCartesRestantes() > 0 && players.size() > 0) {
            // trouver le prochain joueur dans la liste ordonnée des joueurs
            current = players.peekFirst();
            System.out.println("C'est au tour de " + current.getNom() + ".");

            // afficher l'état du tapis
            System.out.println(renderer.toString(tapis));

            // l'arbitre fait jouer un joueur en lui faisant choisir trois
            // cartes
            jouer(tailleFamille, tapis, proxy);
        }

        System.out.println("Scores finaux : " + this.scores.toString());
    }

    private <F, V> void jouer(int familySize, Tapis<Carte<F, V>> tapis, ProxyTapis<Carte<F, V>> proxy) {
        int[] choices = new int[familySize];
        int currentChoice = 0;
        boolean found;
        try {
            for (int i = 0; i < choices.length; i++) {
                // choix d'une carte non encore retournée ou prise
                choices[i] = choisirCarte(i, proxy, tapis);

                // l'arbitre révèle la carte choisie
                tapis.montrer(choices[i]);

                // l'arbitre prévient les joueurs que la carte est retournée
                signalerRetournee(i, choices[i], tapis);

                currentChoice++;

                // l'arbitre affiche le nouvel état du tapis
                System.out.println(renderer.toString(tapis));
            }

            // l'arbitre vérifie si les trois cartes sont de la même
            // famille
            found = takeCheck(tapis, choices);

            // prendre ou non les cartes retournées par le joueur
            appliquerChoix(found, tapis, choices);

            if (!found) {
                System.out.println(current.getNom() + " n'a pas trouvé de famille!");
                this.players.removeFirst();
                this.players.addLast(current);
            } else {
                System.out.println(current.getNom() + " a trouvé une famille!");
                this.scores.increment(current.getNom());
            }


        } catch (AbandonException e) {
            this.handleAbandon(current);
            for (int j = 0; j <= currentChoice; j++) {
                if (tapis.estVisible(choices[j])) {
                    tapis.masquer(choices[j]);
                }
            }
        } catch (OperationInterditeException e) {
            this.strat_arbitre.handleCheater(current, tapis);
            for (int j = 0; j <= currentChoice; j++) {
                if (tapis.estVisible(choices[j])) {
                    tapis.masquer(choices[j]);
                }
            }
        }
    }

    private <F, V> void appliquerChoix(boolean take, Tapis<Carte<F, V>> tapis, int... choix) {
        for (int c : choix) {
            if (take) {
                signalerPrise(c, tapis);
                tapis.prendre(c);
            } else {
                tapis.masquer(c);
            }
        }
    }

    private <F, V> int choisirCarte(int numero, ProxyTapis<Carte<F, V>> proxy, Tapis<Carte<F, V>> tapis) {
        int result;

        do {
            result = this.strat_arbitre.execute(tapis, proxy, numero);
        } while ((tapis.estPrise(result)) || tapis.estVisible(result));

        return result;
    }

    private <F, V> void signalerRetournee(int numero, int position, Tapis<Carte<F, V>> tapis) {
        for (Joueur j : this.players) {
            j.carteRetournee(this.current.getNom(), numero, position, tapis);
        }
    }

    private <F, V> void signalerPrise(int position, Tapis<Carte<F, V>> tapis) {
        for (Joueur j : players) {
            j.cartePrise(this.current.getNom(), position, tapis);
        }
    }

    private <F, V> boolean takeCheck(Tapis<Carte<F, V>> tapis, int... choix) {
        boolean result = true;
        int i = 0;

        while (result && i < choix.length) {
            if (tapis.get(choix[i]).getFamille() != tapis.get(choix[0]).getFamille()) {
                result = false;
            }
            i++;
        }

        return result;
    }

    private void handleAbandon(Joueur abandonningPlayer) {
        System.out.println("Abandon du joueur " + abandonningPlayer.getNom());
        players.remove(abandonningPlayer);
        scores.remove(abandonningPlayer.getNom());
    }

    private class StrategieConfiant extends StrategieArbitre {
        @Override
        public <F, V> int execute(Tapis<Carte<F, V>> tapis, ProxyTapis<Carte<F, V>> proxy, int numero) {
            return current.carteChoisie(numero, tapis);
        }

        public void handleCheater(Joueur cheater, Tapis cards) {
        }
    }

    private class StrategieMefiant extends StrategieArbitre {
        @Override
        public <F, V> int execute(Tapis<Carte<F, V>> tapis, ProxyTapis<Carte<F, V>> proxy, int numero) {
            return current.carteChoisie(numero, proxy);
        }

        public void handleCheater(Joueur cheater, Tapis cards) {
            System.out.println("Le joueur " + cheater.getNom() + " a triché! Il est disqualifié!");
            players.remove(cheater);
            scores.remove(cheater.getNom());
        }
    }
}
