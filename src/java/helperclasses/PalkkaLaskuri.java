/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperclasses;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Programmer
 */
public class PalkkaLaskuri {

    public static int laskeLomaPäivät(Date firstDate, Date secondDate, String palkkamuotoName) {
        int lomaPäivät = 0;
        int kuukausit = 0;
        LocalDate alkaisPäivä = dateToLocalDate(firstDate);
        LocalDate maksuPäivä = dateToLocalDate(secondDate);

        
        if (maksuPäivä.isBefore(alkaisPäivä)) {
            return 0;
        }

        Period period = Period.between(alkaisPäivä, maksuPäivä);
        int yearsInPeriod = period.getYears();
        int monthInPeriod = period.getMonths();
        int dayInPeriod = period.getDays();

        List<LomanmääräytymisVuosi> periods = periodSplitter(alkaisPäivä, maksuPäivä);

        String kuukausiPalkkamuotoName = Palkkamuodot.getPalkkamuodot().get(0).getMuotoName();
        String osaAikaPalkkamuotoName = Palkkamuodot.getPalkkamuodot().get(1).getMuotoName();

        if (palkkamuotoName.equals(kuukausiPalkkamuotoName) ||
                palkkamuotoName.equals(osaAikaPalkkamuotoName)) {
            if ((periods.size() == 1) && (periods.get(0).getKuukausit() < 12)) {
                lomaPäivät = periods.get(0).getKuukausit() * 2;
            } else {
                for (LomanmääräytymisVuosi lomanmääräytymisVuosi : periods) {
                    kuukausit += lomanmääräytymisVuosi.getKuukausit();
                }
                lomaPäivät = (int) (kuukausit * 2.5 + 0.5);
            }
        }
        
        
        System.out.println("size of periods " + periods.size());
        for (LomanmääräytymisVuosi lomanmääräytymisVuosi : periods) {
            System.out.println(lomanmääräytymisVuosi.getKuukausit());
        }

        return lomaPäivät;
    }

    public static double laskeLomaKorvaus(Date firstDate, Date secondDate,String palkkamuotoName, double palkka, double prosentti) {
        
        int lomaPäivät = laskeLomaPäivät(firstDate, secondDate, palkkamuotoName);
        double lomaKorvaus = 0;
        
        LocalDate alkaisPäivä = dateToLocalDate(firstDate);
        LocalDate maksuPäivä = dateToLocalDate(secondDate);
        
        String kuukausiPalkkamuotoName = Palkkamuodot.getPalkkamuodot().get(0).getMuotoName();
        String osaAikaPalkkamuotoName = Palkkamuodot.getPalkkamuodot().get(1).getMuotoName();
        String prosenttiPalkkamuotoName = Palkkamuodot.getPalkkamuodot().get(2).getMuotoName();

        if (palkkamuotoName.equals(kuukausiPalkkamuotoName)) {
            
            lomaKorvaus = palkka / 25 * lomaPäivät;
            
        } else if (palkkamuotoName.equals(osaAikaPalkkamuotoName)) {
            Period period = Period.between(alkaisPäivä, maksuPäivä);
            double t =(period.getYears() < 1)?0.09: .11;
                lomaKorvaus = palkka * t * lomaPäivät;
            
                
        } else if(palkkamuotoName.equals(prosenttiPalkkamuotoName)){
            lomaKorvaus = palkka*prosentti/100;
        }
        return (int)(lomaKorvaus*100)/100;
    }

    public static String laskeAikaero(Date firstDate, Date secondDate) {
        LocalDate alkaisPäivä = dateToLocalDate(firstDate);
        LocalDate maksuPäivä = dateToLocalDate(secondDate);

        Period period = Period.between(alkaisPäivä, maksuPäivä);
        
        if(period.isNegative() || period.isZero()) {
            return "Ethan saa loma ennen kuin 31.3;";
        }

        String years = "", months = "", days = "";

        if (period.getYears() == 1) {
            years = period.getYears() + " vuosi ";
        }
        if (period.getYears() > 1) {
            years = period.getYears() + " vuotta ";
        }

        if (period.getMonths() == 1) {
            months = period.getMonths() + " kuukausi ";
        }
        if (period.getMonths() > 1) {
            months = period.getMonths() + " kuukauta ";
        }

        if (!years.equals("") && !months.equals("")) {
            years += " ja ";
        }

        if (period.getDays() == 1) {
            days = period.getDays() + " päivä ";
        }
        if (period.getDays() > 1) {
            days = period.getDays() + " päivää ";
        }

        if (!months.equals("") && !days.equals("")) {
            months += " ja ";
        }

        return (years + months + days);

    }

    private static List<LomanmääräytymisVuosi> periodSplitter(LocalDate alkaisPäiva, LocalDate loppuPäivä) {
        List<LomanmääräytymisVuosi> list = new ArrayList<>();
        LomanmääräytymisVuosi lomanmääräytymisVuosi;

        LocalDate tempStartDate;
        int alkaisVuosi = alkaisPäiva.getYear();

        LocalDate tempEndDate;
        int loppuVuosi = loppuPäivä.getYear();

        Period period = Period.between(alkaisPäiva, loppuPäivä);
        if (period.getYears() == 0) {
            lomanmääräytymisVuosi = new LomanmääräytymisVuosi(alkaisPäiva, loppuPäivä);
            list.add(lomanmääräytymisVuosi);
            return list;
        } else {
            int am = alkaisPäiva.getMonthValue();
            int ad = alkaisPäiva.getDayOfMonth();
            if (((am > 4) || ((am == 4) && ad >= 1))) {// the day is after before 1.4 of the same starting year
                alkaisVuosi++;
            }
            tempStartDate = LocalDate.of(alkaisVuosi, Month.APRIL, 1);
            LocalDate firstPeriodLoppuPäivä = tempStartDate.minusDays(1);
            lomanmääräytymisVuosi = new LomanmääräytymisVuosi(alkaisPäiva, firstPeriodLoppuPäivä);
            list.add(lomanmääräytymisVuosi);

            int lm = loppuPäivä.getMonthValue();
            int ld = loppuPäivä.getDayOfMonth();
            if (((lm < 3) || ((lm == 3) && ld <= 31))) {// the day is  before 31.3 of the same starting year
                loppuVuosi--;
            }
            tempEndDate = LocalDate.of(loppuVuosi, Month.MARCH, 31);
            LocalDate lastPeriodLoppuPäivä = tempEndDate.plusDays(1);
            lomanmääräytymisVuosi = new LomanmääräytymisVuosi(lastPeriodLoppuPäivä, loppuPäivä);
            list.add(lomanmääräytymisVuosi);

            int tempAlkaisVuosi = alkaisVuosi;
            int tempLuppoVuosi = tempAlkaisVuosi + 1;
            while (tempAlkaisVuosi < lastPeriodLoppuPäivä.getYear()) {
                tempStartDate = LocalDate.of(tempAlkaisVuosi, Month.APRIL, 1);
                tempEndDate = LocalDate.of(tempLuppoVuosi, Month.MARCH, 31);
                lomanmääräytymisVuosi = new LomanmääräytymisVuosi(tempStartDate, tempEndDate);
                list.add(lomanmääräytymisVuosi);
                tempAlkaisVuosi++;
                tempLuppoVuosi++;
            }

            return list;

        }

    }

    public static Date adjustLuppoPäivä(Date date, boolean jatkuva) {
        if (!jatkuva) {
            return date;
        }
        LocalDate oldLocalDate = dateToLocalDate(date);
        LocalDate newLocalDate = LocalDate.of(oldLocalDate.getYear(), Month.MARCH, 31);
        if (oldLocalDate.isBefore(newLocalDate))
            newLocalDate = newLocalDate.minusYears(1);
        return localDateToDate(newLocalDate);
    }

    private static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static Date localDateToDate(LocalDate localDate) {
        
        
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
    }

}
