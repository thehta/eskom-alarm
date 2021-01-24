// package za.net.ether.mokse;

// import java.io.IOException;

// import com.fasterxml.jackson.core.JsonGenerator;
// import com.fasterxml.jackson.databind.SerializerProvider;
// import com.fasterxml.jackson.databind.ser.std.StdSerializer;

// public class CustomScheduleSerializer extends StdSerializer<Schedule> {

//     public CustomScheduleSerializer() {
//         this(null);
//     }

//     public CustomScheduleSerializer(Class<Schedule> s) {
//         super(s);
//     }

//     @Override
//     public void serialize(Schedule schedule, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
//         jsonGenerator.writeStartObject();
//         jsonGenerator.writeObject(schedule);
//         jsonGenerator.writeEndObject();
//     }
// }
