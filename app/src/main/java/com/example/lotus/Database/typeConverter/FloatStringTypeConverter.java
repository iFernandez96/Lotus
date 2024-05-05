package com.example.lotus.Database.typeConverter;

import androidx.room.TypeConverter;

public class FloatStringTypeConverter {

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
