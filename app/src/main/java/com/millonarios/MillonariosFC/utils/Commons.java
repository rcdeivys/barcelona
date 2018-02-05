package com.millonarios.MillonariosFC.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Carlos-pc on 28/09/2017.
 */

public class Commons {

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = App.getAppContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float pxToDp(float px) {
        DisplayMetrics metrics = App.getAppContext().getResources().getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    /*Width Display pixelex*/
    public static int getWidthDisplay() {
        return App.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    /*Height Display pixeles*/
    public static int getHeightDisplay() {
        return App.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static List<String> getCountry() {
        Locale[] locales = Locale.getAvailableLocales();
        List<String> countries = new ArrayList<String>();
        HashMap<String, String> hasCountry = new HashMap<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();

            if (!country.equals(""))
                hasCountry.put(country, country);

        }
        for (HashMap.Entry<String, String> item : hasCountry.entrySet()) {
            countries.add(item.getValue());
        }

        Collections.sort(countries);

        return countries;
    }

    public static int getPositionCountry(String country) {
        int position = 0;
        List<String> countries = getCountry();
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).equals(country)) {
                position = i;
            }
        }
        return position;
    }

    public static int getColor(int color) {
        int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.get().getBaseContext().getColor(color);
        else
            result = App.get().getBaseContext().getResources().getColor(color);
        return result;
    }

    public static int getScreenOrientation(Activity activity) {
        Display getOrient = activity.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getOrient.getWidth() == getOrient.getHeight()) {
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }


    public static Drawable getDrawable(int drawable) {
        Drawable result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getDrawable(drawable);
        else
            result = App.getAppContext().getResources().getDrawable(drawable);
        return result;
    }

    public static String getString(int string) {
        String result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getString(string);
        else
            result = App.getAppContext().getString(string);
        return result;
    }

    public static String getStringFormat(int string, Object format) {
        String result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getString(string, format);
        else
            result = App.getAppContext().getString(string, format);
        return result;
    }

    /*Copi text clipboard*/
    public static boolean copyClipboard(Context context, String labelToCopy, String textToCopy) {
        try {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (textToCopy != null) {
                ClipData clip = ClipData.newPlainText(labelToCopy, textToCopy.replace("\\n", System.getProperty("line.separator")));
                clipboard.setPrimaryClip(clip);
                //Toast.makeText(context, R.string.text_copied, Toast.LENGTH_LONG).show();
                return true;
            } else {
                //Toast.makeText(context, R.string.text_not_copied, Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            //Toast.makeText(context, R.string.text_not_copied, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date d) {
        if (d != null) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.format(d);
        }
        return getString(R.string.undefined);
    }
    @SuppressLint("SimpleDateFormat")
    public static Date getDateString(String strFecha) {
        String format = "yyyy-MM-dd HH:mm:ss";
        String date = strFecha;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        try {
            Date dt = sdf.parse(date);
            System.out.println(dt);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static Date getParseDate(String string) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = format.parse(string);
            System.out.println(date);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringDate(String string) {
        try {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
            Date date = format2.parse(string);
            System.out.println(date);

            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringDate2(String string) {
        try {
            DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
            Date date = format2.parse(string);
            System.out.println(date);

            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String simpleDateFormat(String date) {
        if (date != null) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parse = null;
            try {
                parse = fmt.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            return fmtOut.format(parse);
        }
        return "";
    }

    public static String getStringHour(String string) {
        try {
            DateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("es", "ES"));
            Date date = format2.parse(string);
            System.out.println(date);

            return format.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void setBanner(Context context, String url, ImageView imageView) {
        //FactoryImg.setImageBanner(imageView, context, url, new SizeImage(Commons.getWidthDisplay(context), Commons.getHeightDisplay(context) / 11));

    }

    public static void bannerFooterRedirect(Context context, String urlString, String target) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        if (target.equals("Interno")) {
           /* Intent i = new Intent(context, InfografiaActivity.class);
            i.putExtra("url", urlString);
            context.startActivity(i); */
        } else {
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                context.startActivity(intent);
            }
        }

    }

    public static String normalizedString(String string) {
        String normalized = Normalizer.normalize(string, Normalizer.Form.NFD);
        String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return accentRemoved;
    }

    public static String encodeImage(Bitmap bm) {
        Bitmap resize = Commons.scaleDown(bm, 748, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resize.compress(Bitmap.CompressFormat.PNG, 25, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth()
                , (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Millonarios FC");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    public static void seTypeFaceTextView(FCMillonariosTextView view, int path) {
        if (Build.VERSION.SDK_INT < 23) {
            view.setTypeface(FontCache.getTypeface(App.getAppContext(),Commons.getString(path)));

        } else {
            view.setTypeface(FontCache.getTypeface(App.getAppContext(),Commons.getString(path)));

        }
    }

}
