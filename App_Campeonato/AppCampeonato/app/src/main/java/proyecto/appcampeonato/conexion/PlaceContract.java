package proyecto.appcampeonato.conexion;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by PCCUMPA on 11/11/2017.
 */

public class PlaceContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "proyecto.appcampeonato";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String PATH_PLACES = "places";

    public static final class PlaceEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLACES).build();

        public static final String TABLE_NAME = "places";
        public static final String COLUMN_PLACE_ID = "placeID";
    }

}
