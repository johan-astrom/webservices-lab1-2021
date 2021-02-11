package se.iths.plugin;
import com.google.gson.Gson;
import java.util.List;

public class ConvertJson {

    static String convertToJson(List content) {

        Gson gson = new Gson();
        String json = gson.toJson(content);

        return json;

    }

}
