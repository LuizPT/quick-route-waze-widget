package pt.rotarapida.waze;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

/** Persists the app language and supplies a localized context to app and widget views. */
public final class AppLanguage {
    private static final String PREFERENCES = "quick_route_preferences";
    private static final String LANGUAGE_KEY = "language";

    private AppLanguage() { }

    public static String currentCode(Context context) {
        String saved = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .getString(LANGUAGE_KEY, null);
        if (saved != null) return saved;
        return Locale.getDefault().getLanguage().equals("pt") ? "pt" : "en";
    }

    public static Context wrap(Context context) {
        Locale locale = Locale.forLanguageTag(currentCode(context));
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        if (Build.VERSION.SDK_INT >= 24) {
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
        } else {
            configuration.locale = locale;
        }
        return context.createConfigurationContext(configuration);
    }

    public static void select(Context context, String language) {
        String code = "pt".equals(language) ? "pt" : "en";
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(LANGUAGE_KEY, code).apply();
        QuickActionsWidgetProvider.refreshAll(context);
    }
}
