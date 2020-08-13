/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import helperclasses.PalkkaLaskuri;
import helperclasses.Palkkamuodot;
import helperclasses.Palkkamuoto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Programmer
 */
@ManagedBean(name = "model", eager = true)
@SessionScoped
public class Model {

    /**
     * Creates a new instance of Model
     */
    public Model() {

        palkkamuodot = new Palkkamuodot().getPalkkamuodot();
        palkkamuoto = palkkamuodot.get(0).getMuotoName();
        palkkamuotoText = palkkamuodot.get(0).getMuotoText();
        aika = "";
        alkaisPäiväTempo = Calendar.getInstance().getTime();
        maksuPäiväTempo = Calendar.getInstance().getTime();
        työSuhde = "true";
        jatkuva = true;
        maksuPäiväText = "MaksuPäivä: "; 
        palkka = 0.0;
        rendered = true;
        notRendered = false;
        

    }

    private List<Palkkamuoto> palkkamuodot;
    private String palkkamuoto;
    private String palkkamuotoText;

    private Double palkka;
    private int lomaPäivat;
    private Double lomaKorvaus;

    private List<String> menuNames;

    private LocalDate alkaisPäivä;
    private LocalDate maksuPäivä;

    private Date alkaisPäiväTempo;
    private Date maksuPäiväTempo;
    
    private Date modifiedLoppuPäivä;

    private Boolean rendered;
    private Boolean notRendered;
    private String aika;
    
    private String työSuhde = "true";
    private boolean jatkuva;
    
    private String maksuPäiväText;
    private double prosentti;

    public void menuValueChanged(ValueChangeEvent vce) {
        
        String newValue = vce.getNewValue().toString();
        
        int i;
        for (i = 0; i < palkkamuodot.size(); i++) {
            Palkkamuoto pm = palkkamuodot.get(i);
            if (pm.getMuotoName().equals(newValue)) {
                break;
            }

        }
        this.setPalkkamuoto(newValue);
        this.setPalkkamuotoText(palkkamuodot.get(i).getMuotoText());
        
        this.rendered =(newValue.equals(palkkamuodot.get(2).getMuotoName()))? false:true;
        this.notRendered = !rendered;
    }
    
    public void jatkuvaValueChanged(ValueChangeEvent vce) {
       
        String newValue = vce.getNewValue().toString();
        
        if(newValue.equals("true")){
            jatkuva= true;
            maksuPäiväText = "MaksuPäivä: ";
        }           
        else {
            jatkuva = false;
            maksuPäiväText = "Työsude päätynyt: ";
        }
        this.setTyöSuhde(newValue);
        
    }

    public void päiväValueChanged(ValueChangeEvent vce) {
        
        this.setAika(PalkkaLaskuri.laskeAikaero(alkaisPäiväTempo, maksuPäiväTempo));
        

    }

    

    public void laske(ActionEvent actionEvent) {
        
        
        modifiedLoppuPäivä = PalkkaLaskuri.adjustLuppoPäivä(maksuPäiväTempo, jatkuva);
        this.setAika(PalkkaLaskuri.laskeAikaero(alkaisPäiväTempo, modifiedLoppuPäivä));
        this.lomaPäivat = PalkkaLaskuri.laskeLomaPäivät(alkaisPäiväTempo, modifiedLoppuPäivä, palkkamuoto);
        this.lomaKorvaus = PalkkaLaskuri.laskeLomaKorvaus(alkaisPäiväTempo, modifiedLoppuPäivä, palkkamuoto, this.palkka, prosentti);
        
        
        
       

    }
    
    

    public List<String> getMenuNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < palkkamuodot.size(); i++) {
            names.add(palkkamuodot.get(i).getMuotoName());
        }
        //for(Palkkamuoto muoto: palkkamuodot)
        //   names.add(muoto.getMuotoName());
        return names;

    }

    public Date getAlkaisPäiväTempo() {
        return alkaisPäiväTempo;
    }

    public void setAlkaisPäiväTempo(Date alkaisPäiväTempo) {
        this.alkaisPäiväTempo = alkaisPäiväTempo;
    }

    public Date getMaksuPäiväTempo() {
        return maksuPäiväTempo;
    }

    public void setMaksuPäiväTempo(Date maksuPäiväTempo) {
        this.maksuPäiväTempo = maksuPäiväTempo;
    }

    public int getLomaPäivat() {
        return lomaPäivat;
    }

    public void setLomaPäivat(int lomaPäivat) {
        this.lomaPäivat = lomaPäivat;
    }

    public Double getPalkka() {
        return palkka;
    }

    public void setPalkka(Double palkka) {
        this.palkka = palkka;
    }

    public String getPalkkamuoto() {
        return palkkamuoto;
    }

    public void setPalkkamuoto(String palkkamuoto) {
        this.palkkamuoto = palkkamuoto;
    }

    public String getPalkkamuotoText() {
        return palkkamuotoText;
    }

    public void setPalkkamuotoText(String palkkamuotoText) {
        this.palkkamuotoText = palkkamuotoText;
    }

    public Boolean getRendered() {
        return rendered;
    }

    public void setRendered(Boolean rendered) {
        this.rendered = rendered;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public Double getLomaKorvaus() {
        return this.lomaKorvaus;
    }

    public void setLomaKorvaus(Double lomaKorvaus) {
        this.lomaKorvaus = lomaKorvaus;
    }

    public String getTyöSuhde() {
        return työSuhde;
    }

    public void setTyöSuhde(String työSuhde) {
        this.työSuhde = työSuhde;
    }

    public String getMaksuPäiväText() {
        return maksuPäiväText;
    }

    public void setMaksuPäiväText(String maksuPäiväText) {
        this.maksuPäiväText = maksuPäiväText;
    }

    public double getProsentti() {
        return prosentti;
    }

    public void setProsentti(double prosentti) {
        this.prosentti = prosentti;
    }

    public Boolean getNotRendered() {
        return notRendered;
    }

    public void setNotRendered(Boolean notRendered) {
        this.notRendered = notRendered;
    }

    
    
    
    
    
    
    

    
}
