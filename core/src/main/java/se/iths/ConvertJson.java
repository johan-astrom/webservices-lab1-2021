package se.iths;
import com.google.gson.Gson;
import java.util.List;

public class ConvertJson {

    static String convertToJson(List allBooks) {

        Gson gson = new Gson();
        String json = gson.toJson(allBooks);

        return json;

    }

}
