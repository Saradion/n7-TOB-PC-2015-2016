import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe Tapis modélise un cards de cartes qui peuvent être visibles,
 * face cachée ou prises.
 *
 * @param <C> type des cartes du cards
 * @author Mathias Bigaignon
 * @version 1.2
 */
public class TapisMemory<C> implements Tapis<C> {

    private ArrayList<C> cards; //les cartes du cards ordonnées selon leur position sur le cards
    private HashMap<C, State> state; //la table des états des cartes sur le cards

    public TapisMemory(JeuCartes<C> jeu) {
        this.cards = new ArrayList<>();
        this.state = new HashMap<>();
        for (C card : jeu) {
            this.cards.add(card);
            this.state.put(card, State.HIDDEN);
        }
    }

    /**
     * La taille du cards.
     *
     * @return la taille du cards
     */
    @Override
    public int taille() {
        return this.cards.size();
    }

    /**
     * Le nombre de cartes encore présentes sur le cards.
     *
     * @return le nombre de cartes encore présentes sur le cards
     */
    @Override
    public int nbCartesRestantes() {
        int result = 0;
        for (C card : cards) {
            result = (state.get(card) == State.TAKEN) ? result : result + 1;
        }

        return result;
    }

    /**
     * Rendre visible une carte de ce cards.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     * @throws CarteVisibleException     la carte est visible
     */
    @Override
    public void montrer(int position) {
        testState(position, State.TAKEN, State.VISIBLE);
        this.state.remove(this.cards.get(position));
        this.state.put(this.cards.get(position), State.VISIBLE);

    }

    /**
     * Retourner la carte pour qu'elle soit face cachée.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     * @throws CarteMasqueeException     la carte est face cachée
     */
    @Override
    public void masquer(int position) {
        testState(position, State.TAKEN, State.HIDDEN);
        this.state.remove(this.cards.get(position));
        this.state.put(this.cards.get(position), State.HIDDEN);
    }

    /**
     * Prendre une carte de ce cards.
     *
     * @param position position de la carte
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    @Override
    public void prendre(int position) {
        testState(position, State.TAKEN);
        this.state.remove(this.cards.get(position));
        this.state.put(this.cards.get(position), State.TAKEN);
    }

    /**
     * Savoir si une carte a été prise.
     *
     * @param position position de la carte
     * @return vrai si la carte a été prise
     * @throws PositionInvalideException la position est invalide
     */
    @Override
    public boolean estPrise(int position) {
        testIsValid(position);
        return this.state.get(this.cards.get(position)) == State.TAKEN;
    }

    /**
     * Savoir si une carte est visible.
     *
     * @param position position de la carte
     * @return vrai si la carte est visible
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    @Override
    public boolean estVisible(int position) {
        testState(position, State.TAKEN);
        return this.state.get(this.cards.get(position)) == State.VISIBLE;
    }

    /**
     * Obtenir une carte de ce cards.
     *
     * @param position position de la carte
     * @return la carte à la position indiquée
     * @throws PositionInvalideException la position est invalide
     * @throws CartePriseException       la carte a été prise
     */
    @Override
    public C get(int position) {
        testState(position, State.TAKEN);
        return this.cards.get(position);
    }

    private State getState(int position) {
        testIsValid(position);
        return this.state.get(this.cards.get(position));
    }

    private void testIsValid(int position) {
        if (0 > position || this.cards.size() <= position) {
            throw new PositionInvalideException("Invalid position!");
        }
    }

    private void testState(int position, State... states) {
        for (State state : states) {
            if (this.getState(position) == state) {
                switch (state) {
                    case HIDDEN:
                        throw new CarteMasqueeException("Hidden card!");
                    case VISIBLE:
                        throw new CarteVisibleException("Visible card!");
                    case TAKEN:
                        throw new CartePriseException("Taken card!");
                }
            }
        }
    }

    private enum State {TAKEN, VISIBLE, HIDDEN}
}
