package util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public final class FontUtils {



    /**
     * SansBold
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFaceSansBold(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/DroidSansBold.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
    /**
     * ht.ttf
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFaceht(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ht.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
    /**
     * kt.ttf
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFacekt(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/kt.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
    /**
     * xinsong
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFacels(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ls.TTF");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
    /**
     * st.ttf
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFacest(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/st.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
    /**
     * youyuan
     *
     * @param context
     * @param textViews
     */
    public static void setTypeFaceyy(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/yy.TTF");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }

}