package wolox.training.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Utils {
    public static String asJsonString(Object obj) {
        try {
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(df);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
