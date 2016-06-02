/**
 * CartePriseException indique qu'une carte a été prise.
 *
 * @author Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */

public class CartePriseException extends RuntimeException {

    public CartePriseException(String message) {
        super(message);
    }

}
