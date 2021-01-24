package za.net.ether.mokse;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomTimeStageSerializer extends StdSerializer<TimeStage> {

    public CustomTimeStageSerializer() {
        this(null);
    }

    public CustomTimeStageSerializer(Class<TimeStage> timeStage) {
        super(timeStage);
    }

    @Override
    public void serialize(TimeStage timeStage, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(timeStage.getHour(), Integer.toString(timeStage.getStage()));
        jsonGenerator.writeEndObject();
    }
}
