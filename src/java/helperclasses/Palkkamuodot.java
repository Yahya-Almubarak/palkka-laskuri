/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperclasses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Programmer
 */
public class Palkkamuodot {
    
    private static List<Palkkamuoto> palkkamuodot;

    public Palkkamuodot() {
        this.palkkamuodot = new ArrayList<>();
        this.palkkamuodot.add(new Palkkamuoto("Kuukausipalkka", "KuukausiPalkka"));
        this.palkkamuodot.add(new Palkkamuoto("Osa_aikainen","keskipäiväpalkka"));
        this.palkkamuodot.add(new Palkkamuoto("Prosentti","Työssolon ajan palkka"));
    }

    public static List<Palkkamuoto> getPalkkamuodot() {
        return palkkamuodot;
    }

    public void setPalkkamuodot(List<Palkkamuoto> palkkamuodot) {
        this.palkkamuodot = palkkamuodot;
    }
    
    public void addPalkkamuodot(Palkkamuoto palkkamuoto) {
        this.palkkamuodot.add(palkkamuoto);
    }
    
    
    
    
}
