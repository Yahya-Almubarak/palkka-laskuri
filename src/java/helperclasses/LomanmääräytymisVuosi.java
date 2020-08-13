/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperclasses;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Programmer
 */
public class LomanmääräytymisVuosi {
    private LocalDate alkaisPäivä;
    private LocalDate loppuPäivä;
    private Period period;
    private int kuukausit;

    public LomanmääräytymisVuosi(LocalDate alkaisPäivä, LocalDate loppuPäivä) {
        this.alkaisPäivä = alkaisPäivä;
        this.loppuPäivä = loppuPäivä;
        this.period = Period.between(alkaisPäivä.minusDays(1), loppuPäivä.plusDays(1));
        this.kuukausit = this.period.getYears()*12 + this.period.getMonths();
        if(this.period.getDays() >= 14) this.kuukausit++; //Täytenä lomanmääräytymiskuukautena pidetään kalenterikuukautta, jolloin 
                                                          //työntekijälle on kertynyt vähintään 14 työssäolopäivää
       
    }

    public LocalDate getAlkaisPäivä() {
        return alkaisPäivä;
    }

    public void setAlkaisPäivä(LocalDate alkaisPäivä) {
        this.alkaisPäivä = alkaisPäivä;
    }

    public LocalDate getLoppuPäivä() {
        return loppuPäivä;
    }

    public void setLoppuPäivä(LocalDate loppuPäivä) {
        this.loppuPäivä = loppuPäivä;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getKuukausit() {
        return kuukausit;
    }

    public void setKuukausit(int kuukausit) {
        this.kuukausit = kuukausit;
    }
    
    
    
    
}
