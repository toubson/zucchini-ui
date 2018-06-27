package io.zucchiniui.backend.support.mongo;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.String.format;

public class ZonedDateTimeCodec implements Codec<ZonedDateTime> {

    @Override
    public ZonedDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        final BsonType currentType = reader.getCurrentBsonType();
        if (!currentType.equals(BsonType.DATE_TIME)) {
            throw new CodecConfigurationException(format(
                "Could not decode into %s, expected '%s' BsonType but got '%s'.",
                getEncoderClass().getSimpleName(),
                BsonType.DATE_TIME,
                currentType
            ));
        }
        final long timestamp = reader.readDateTime();
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    @Override
    public void encode(BsonWriter writer, ZonedDateTime value, EncoderContext encoderContext) {
        writer.writeDateTime(value.toInstant().toEpochMilli());
    }

    @Override
    public Class<ZonedDateTime> getEncoderClass() {
        return ZonedDateTime.class;
    }

}
