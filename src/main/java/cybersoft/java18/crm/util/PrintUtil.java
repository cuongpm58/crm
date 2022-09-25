package cybersoft.java18.crm.util;

import com.google.gson.*;
import lombok.experimental.UtilityClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class PrintUtil {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).setPrettyPrinting().create()

            /*new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()*/;

    public static void printJsonFromObject(HttpServletResponse resp, Object obj)
            throws ServletException, IOException {
        String json = gson.toJson(obj);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d:MMM:uuuu HH:mm:ss");

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }
}
