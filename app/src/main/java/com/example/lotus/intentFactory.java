package com.example.lotus;

import android.content.Context;
import android.content.Intent;

public class intentFactory {
    public static Intent createIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        return intent;
    }
    public static Intent createIntent(Context context, Class<?> cls, String key, boolean value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }

    public static Intent createIntent(Context context, Class<?> cls, String key, char value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }

    public static Intent createIntent(Context context, Class<?> cls, String key, String value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }

    public static Intent createIntent(Context context, Class<?> cls, String key, int value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }
    public static Intent createIntent(Context context, Class<?> cls, String key, float value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }
    public static Intent createIntent(Context context, Class<?> cls, String key, double value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }

    public static Intent createIntent(Context context, Class<?> cls, String key, long value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        return intent;
    }
}
