/**
 * CarteMasqueeException indique qu'une carte est masquée.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */

public class CarteMasqueeException extends RuntimeException {

    public CarteMasqueeException(String message) {
        super(message);
    }

}
