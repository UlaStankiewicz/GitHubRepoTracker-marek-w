package pl.mwaszczuk.githubrepotracker.network.converter

import com.google.gson.*
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeSerializer : JsonSerializer<OffsetDateTime?>,
    JsonDeserializer<OffsetDateTime?> {

    override fun serialize(
        src: OffsetDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime {
        return FORMATTER.parse(json.asString, OffsetDateTime::from)
    }

    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    }
}