package utilidades;

/**
 * Created by Ubaldo Torres Juárez on 28/11/2017.
 */

public class StringWithTags {

    public String string;
    public Object tag;

    public StringWithTags(String stringPart, Object tagPart) {
        string = stringPart;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }

}
