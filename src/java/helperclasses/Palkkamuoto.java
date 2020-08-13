/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperclasses;

/**
 *
 * @author Programmer
 */
public class Palkkamuoto {
    private String muotoName;
    private String muotoText;

    public Palkkamuoto(String muotoName, String muotoText) {
        this.muotoName = muotoName;
        this.muotoText = muotoText;
    }

    public String getMuotoName() {
        return muotoName;
    }

    public void setMuotoName(String muotoName) {
        this.muotoName = muotoName;
    }

    public String getMuotoText() {
        return muotoText;
    }

    public void setMuotoText(String muotoText) {
        this.muotoText = muotoText;
    }
    
    
}
