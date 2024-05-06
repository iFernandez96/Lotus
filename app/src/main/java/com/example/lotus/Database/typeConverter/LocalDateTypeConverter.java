package com.example.lotus.Database.typeConverter;
import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTypeConverter {
    @TypeConverter
    public long convertDatetoLong(LocalDateTime date){
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    @TypeConverter
    public LocalDateTime convertLongtoDate(Long epochMilli){
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @TypeConverter
    public float [] convertStringToFloatArray(String s){
        String [] strings = s.split(",");
        float [] floats = new float[strings.length];
        for (int i = 0; i < strings.length; i++){
            floats[i] = Float.parseFloat(strings[i]);
        }
        return floats;
    }

    @TypeConverter
    public String convertFloatArrayToString(float [] floats){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < floats.length; i++){
            sb.append(floats[i]);
            if (i < floats.length - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
