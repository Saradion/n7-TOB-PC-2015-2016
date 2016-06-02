import java.util.*;

/**
 * La classe JoueurMemory modélise un joueur du jeu de Memory.
 * <p>
 * Un joueur dispose d'un nom et d'une stratégie propre. La stratégie
 * est matérialisée par une instance d'une classe héritant de la classe
 * memory.StrategieJoueur.
 * <p>
 * Il est possible de modifier à tout moment la stratégie mise en oeuvre
 * par un joueur par l'intermédiaire de la méthode setStrat().
 * <p>
 * Created by bigaigm on 11/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.2
 */
public class JoueurMemory implements Joueur {
    private String nom;
    private StrategieJoueur strat;
    private int taille;
    private HashMap<Object, Boolean> stateMemory;
    private HashMap<Object, Set<Integer>> memory;
    private ArrayList<Boolean> states;
    private ArrayList<Integer> thisTurn;

    public JoueurMemory(String newNom, String newStrat) {
        this.nom = newNom;
        this.setStrat(newStrat);
        this.memory = new HashMap<>();
        this.stateMemory = new HashMap<>();
        this.states = new ArrayList<>();
        this.thisTurn = new ArrayList<>();
    }

    private void initMemory(int nbCards) {
        for (int i = 0; i < nbCards; i++) {
            this.states.add(i, false);
        }
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public void commencer(int tailleFamille) {
        this.taille = tailleFamille;
    }

    @Override
    public int carteChoisie(int numero, Tapis<? extends Carte<?, ?>> tapis) {
        if (this.states.size() == 0) {
            initMemory(tapis.taille());
        }

        int result;
        do {
            result = strat.execute(numero, tapis);
        } while (result < 0 || result > tapis.taille());

        return result;
    }

    @Override
    public void carteRetournee(String nomJoueur, int numero, int position, Tapis<? extends Carte<?, ?>> tapis) {
        if (this.states.size() == 0) {
            initMemory(tapis.taille());
        }

        if (!this.memory.containsKey(tapis.get(position).getFamille())) {
            this.stateMemory.put(tapis.get(position).getFamille(), true);
            Set<Integer> new_family = new HashSet<>();
            new_family.add(position);
            this.memory.put(tapis.get(position).getFamille(), new_family);
        } else {
            this.memory.get(tapis.get(position).getFamille()).add(position);
        }

        this.states.set(position, true);

        strat.interrupt(nomJoueur);
    }

    @Override
    public void cartePrise(String nomJoueur, int position, Tapis<? extends Carte<?, ?>> tapis) {
        if (this.memory.containsKey(tapis.get(position).getFamille())) {
            this.stateMemory.put(tapis.get(position).getFamille(), false);
        }
    }

    private void setStrat(String newStrat) {
        switch (newStrat) {
            case "humain":
                strat = new StrategieHumain();
                break;
            case "naif":
                strat = new StrategieNaif();
                break;
            case "tricheur":
                strat = new StrategieTricheur();
                break;
            case "expert":
                strat = new StrategieExpert();
                break;
            default:
                throw new RuntimeException();
        }
    }

    private class StrategieNaif extends StrategieJoueur {
        Random rnd;

        StrategieNaif() {
            rnd = new Random();
        }

        @Override
        public int execute(int numero, Tapis<? extends Carte<?, ?>> tapis) {
            if (numero == 0) {
                thisTurn.clear();
            }
            return (rnd.nextInt(tapis.taille()));
        }
    }

    private class StrategieTricheur extends StrategieJoueur {
        private StrategieTricheur() {

        }

        @Override
        public int execute(int numero, Tapis<? extends Carte<?, ?>> tapis) {
            Object family;
            Iterator<Object> keys;
            Iterator<Integer> positions;
            int result = 0;

            if (numero == 0) {
                thisTurn.clear();
            }

            for (int i = 0; i < tapis.taille(); i++) {
                try {
                    carteRetournee(nom, i, i, tapis);
                } catch (CartePriseException | CarteVisibleException e) {
                }
            }

            keys = memory.keySet().iterator();

            do {
                family = keys.next();
            } while (keys.hasNext() && ((!stateMemory.get(family)) || (memory.get(family).size() != taille)));
            positions = memory.get(family).iterator();

            for (int i = 0; i <= numero; i++) {
                result = positions.next();
            }

            return result;
        }
    }

    private class StrategieExpert extends StrategieJoueur {
        private Random rnd;

        StrategieExpert() {
            this.rnd = new Random();
        }

        @Override
        public int execute(int numero, Tapis<? extends Carte<?, ?>> tapis) {
            Iterator<Object> keys = memory.keySet().iterator();
            Iterator<Integer> positions;
            Object family;
            int result;

            if (numero == 0) {
                thisTurn.clear();
            }

            try {
                do {
                    family = keys.next();
                } while ((!stateMemory.get(family)) || (memory.get(family).size() != taille));
            } catch (NoSuchElementException e) {
                keys = memory.keySet().iterator();
                do {
                    family = keys.next();
                } while ((!stateMemory.get(family)) || (memory.get(family).size() == taille));
            }

            if (memory.get(family).size() != taille) {
                do {
                    result = this.rnd.nextInt(tapis.taille());
                } while (states.get(result));
            } else {
                positions = memory.get(family).iterator();

                do {
                    result = positions.next();
                } while (thisTurn.contains(result));
            }

            thisTurn.add(numero, result);
            return result;
        }
    }

    private class StrategieHumain extends StrategieJoueur {
        Scanner scan;
        int result;

        StrategieHumain() {
            this.scan = new Scanner(System.in);
        }

        @Override
        public int execute(int numero, Tapis<? extends Carte<?, ?>> tapis) {
            if (numero == 0) {
                thisTurn.clear();
            }
            System.out.println("Entrez un entier... ");
            result = scan.nextInt();
            scan.skip(".*\n");

            return result;
        }

        @Override
        public void interrupt(String nomJoueur) {
            if (!nomJoueur.equals(nom)) {
                System.out.println("Appuyez sur Entrée... ");
                scan.nextLine();
            }
        }
    }
}
