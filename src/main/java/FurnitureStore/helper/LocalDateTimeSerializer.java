package FurnitureStore.helper;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
  
	@Override
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		// TODO Auto-generated method stub
		 Instant instant = src.atZone(ZoneId.systemDefault()).toInstant();
	        Date date = Date.from(instant);
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        String returnDateString=dateFormat.format(date);
	        ZonedDateTime d = ZonedDateTime.ofInstant(date.toInstant(),
                    ZoneId.systemDefault());
	        LocalDate localDate= date.toInstant()
	        	      .atZone(ZoneId.systemDefault())
	        	      .toLocalDate();
	        LocalDateTime localDateTime= date.toInstant()
	        	      .atZone(ZoneId.systemDefault())
	        	      .toLocalDateTime();
	        return new JsonPrimitive(localDateTime.toString());
	}
}
