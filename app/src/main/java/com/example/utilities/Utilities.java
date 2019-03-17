package com.example.utilities;

import com.example.model.Genre;
import com.example.model.MovieShowtime;
import com.example.model.Scr;
import com.example.model.T;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utilities {

    public static String getGenre(List<Genre> genres) {
        String strGenre = "";
        if(genres != null && !genres.isEmpty()) {
            for (Genre g : genres) {
                strGenre += g.getName() + " | ";
            }
            strGenre = strGenre.substring(0, strGenre.length()-3);
        }
        return strGenre;
    }

    public static String getDuration(Integer runtime) {
        String duration = "";
        if(runtime != null) {
            duration = String.valueOf(runtime / 60);
        }
        return duration;
    }

    public static String getTimes(List<Scr> scrList, Date date) {
        String timesStr = "";
        String strDay = formatDate(date, Constants.DATE_FORMAT_CSV);
        for(Scr scr : scrList) {
            if(scr.getD().equals(strDay)) {
                for(T t : scr.getT()) {
                    timesStr += t.getName() + " | ";
                }
                timesStr = timesStr.substring(0, timesStr.length()-3);
                break;
            }
        }
        return timesStr;
    }

    public static List<Date> getMoviesDays(List<MovieShowtime> movieShowtimes) {
        Set<Date> daysSet = new HashSet<Date>();
        for(MovieShowtime m : movieShowtimes) {
            for(Scr scr : m.getScr()) {
                Date date = parseDate(scr.getD());
                if(date != null) {
                    daysSet.add(date);
                }
            }
        }
        List<Date> daysList = new ArrayList<Date>(daysSet);
        Collections.sort(daysList);
        return daysList;
    }

    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_CSV);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return date;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(parseDate(dateStr));
    }

    public static List<MovieShowtime> getMovieShowtimesByDate(List<MovieShowtime> movieShowtimes, Date date) {
        List<MovieShowtime> movieShowtimesByDate = new ArrayList<MovieShowtime>();
        String strDay = formatDate(date, Constants.DATE_FORMAT_CSV);
        for(MovieShowtime m : movieShowtimes) {
            for (Scr scr : m.getScr()) {
                if (scr.getD().equals(strDay)) {
                    movieShowtimesByDate.add(m);
                }
            }
        }
        return movieShowtimesByDate;
    }

}
