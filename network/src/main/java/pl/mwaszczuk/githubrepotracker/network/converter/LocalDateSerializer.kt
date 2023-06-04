package pl.mwaszczuk.githubrepotracker.network.converter

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateSerializer : JsonSerializer<LocalDate?>,
    JsonDeserializer<LocalDate?> {

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate? {
        return FORMATTER.parse(json.asString, LocalDate::from)
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE
    }

}
