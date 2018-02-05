package com.millonarios.MillonariosFC.utils;

import android.util.Log;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Leonardojpr on 26/01/2018.
 * dateStar -> past time
 * dateNow -> actual time
 * Clase para gestionar el tiempo transcurrido desde un evento entre otros
 */
public class CustomDate {

    /**
     * Etiqueta de la clase
     */
    private static String tag = "CustomDate";

    /**
     * Formatea una fecha a un formato determiando por {@link Constant::DATE_FORMAT}
     *
     * @param dateString Fecha a formatear
     * @return Fecha formateada
     */
    public static DateTime convertStringToDateTime(String dateString) {
        return convertStringToDateTime(Constant.DATE_FORMAT, dateString);
    }

    /**
     * Formatea una fecha a un formato determiando por dateFormat
     *
     * @param dateFormat Formato
     * @param dateString Fecha a formatear
     * @return Fecha formateada
     */
    public static DateTime convertStringToDateTime(String dateFormat, String dateString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat).withLocale(Locale.US);
        return formatter.parseDateTime(dateString);
    }

    /**
     * Formatea una fecha a un formato determiando por {@link Constant::DATE_FORMAT}
     *
     * @param dateString Fecha a formatear
     * @return Fecha formateada
     */
    public static Date convertStringToDate(String dateString) {
        return convertStringToDate(Constant.DATE_FORMAT, dateString);
    }

    /**
     * Formatea una fecha en string a un objeto date, el formato de la fecha esta definido por
     * dateFormat
     *
     * @param dateFormat Formato
     * @param dateString Fecha a formatear
     * @return Un objeto Date
     * @throws ParseException
     */
    public static Date convertStringToDate(String dateFormat, String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date fechaDate = null;
        try {
            fechaDate = format.parse(dateString);
        } catch (ParseException e) {
            Log.e(tag, "convertStringToDate -> ParseException " + e.toString());
        }
        return fechaDate;
    }

    /**
     * Transforma el tiempo dado en milisegundo a una fecha para finalmente retornar una cadena
     * en el formato @link Constants::DATE_FORMAT}
     *
     * @param milliSeconds Tiempo en milisegundos
     * @return String que representa la fecha expresada en milisegundos, en es formato @link Constants::DATE_FORMAT}
     */
    public static String convertLongToDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Calcula el tiempo transcurrido desde una fecha hasta el presente
     *
     * @param dateStar Fecha de referencia
     * @return Cadena que representa el tiempo transcurrido
     */
    public static String timeAgo(long dateStar) {
        DateTimeZone tz = DateTimeZone.forID("America/Bogota");
        DateTime nowLocal = new DateTime();
        Log.d("CustomDate", "dateTime " + nowLocal.withZone(tz).toDateTime().toString());
        return timeAgo(dateStar, nowLocal.withZone(tz).getMillis());
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Cadena que representa el tiempo transcurrido
     */
    public static String timeAgo(long dateStar, long dateEnd) {
        // JodaTime
        //DateTime fecha = new DateTime(2015, 1, 31, 12, 00,
        //        DateTimeZone.forID("Europe/Madrid"));
        //Log.d(tag, "--------------------JodaTime--------------------");
        //Log.d(tag, "Hora Madrid:\t\t %s\n" + fecha);
        //Log.d(tag, "Hora Buenos Aires:\t %s\n" + fecha.withZone(DateTimeZone.forID("America/Argentina/Buenos_Aires")));
        Period p = new Period(dateStar, dateEnd);
        Log.d(tag, " periodDefault ---> " + PeriodFormat.getDefault().print(p));

        if (p.getYears() != 0) {
            if (p.getYears() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getYears() + " " + Commons.getString(R.string.date_year);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getYears() + " " + Commons.getString(R.string.date_years);
            }
        }

        if (p.getMonths() != 0) {
            if (p.getMonths() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getMonths() + " " + Commons.getString(R.string.date_month);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getMonths() + " " + Commons.getString(R.string.date_months);
            }
        }

        if (p.getWeeks() != 0) {
            if (p.getWeeks() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getWeeks() + " " + Commons.getString(R.string.date_week);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getWeeks() + " " + Commons.getString(R.string.date_weeks);
            }
        }

        if (p.getDays() != 0) {
            if (p.getDays() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getDays() + " " + Commons.getString(R.string.date_day);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getDays() + " " + Commons.getString(R.string.date_days);

            }
        }


        if ((p.getHours()) != 0) {
            if (p.getHours() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getHours() + " " + Commons.getString(R.string.date_hour);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getHours() + " " + Commons.getString(R.string.date_hours);

            }
        }

        if (p.getMinutes() != 0) {
            if (p.getMinutes() == 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getMinutes() + " " + Commons.getString(R.string.date_minute);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getMinutes() + " " + Commons.getString(R.string.date_minutes);
            }
        }


        if (p.getSeconds() != 0) {
            if (p.getSeconds() <= 1) {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getSeconds() + " " + Commons.getString(R.string.date_second);
            } else {
                return Commons.getString(R.string.date_suffix_ago) + " " + p.getSeconds() + " " + Commons.getString(R.string.date_seconds);
            }
        }

        Calendar calendar = Calendar.getInstance();
        Calendar.getInstance().setTimeInMillis(dateStar);
        return Commons.dateToString(calendar.getTime());
    }

    /**
     * Calcula el tiempo transcurrido desde una fecha hasta el presente
     *
     * @param dateStar Fecha de referencia
     * @return Cadena que representa el tiempo transcurrido
     */
    public static String timeAgo(DateTime dateStar) {
        return timeAgo(dateStar, new DateTime().withZone(DateTimeZone.forID("America/Bogota")));
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Cadena que representa el tiempo transcurrido
     */
    public static String timeAgo(DateTime dateStar, DateTime dateEnd) {
        return timeAgo(dateStar, dateEnd);
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Periodo de tiempo transcurrido
     */
    public static Period diferenceBetewnDate(long dateStar, long dateEnd) {
        Period period = new Period(dateStar, dateEnd);
        //Log.d(tag, "periodDefault " + PeriodFormat.getDefault().print(period));
        return period;
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Periodo de tiempo transcurrido
     */
    public static Period diferenceBetewnDateInDay(long dateStar, long dateEnd) {
        Period period = new Period(dateStar, dateEnd, PeriodType.dayTime());
        //Log.d(tag, "periodDefault In Day " + PeriodFormat.getDefault().print(period));
        return period;
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Periodo de tiempo transcurrido
     */
    public static Period diferenceBetewnDate(DateTime dateStar, DateTime dateEnd) {
        return diferenceBetewnDate(dateStar.getMillis(), dateEnd.getMillis());
    }

    /**
     * Calcula el tiempo transcurrido entre dos fechas
     *
     * @param dateStar Fecha de inicio
     * @param dateEnd  Fecha de finalizacion
     * @return Periodo de tiempo transcurrido
     */
    public static Period diferenceBetewnDateInDay(DateTime dateStar, DateTime dateEnd) {
        return diferenceBetewnDateInDay(dateStar.getMillis(), dateEnd.getMillis());
    }


}
