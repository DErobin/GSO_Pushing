/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import shared.IFonds;

/**
 *
 * @author Hovsep
 */
public class Fonds implements IFonds{

    String fondsnaam;
    double koers;

    public Fonds(String fondsnaam, double koers) {
        this.fondsnaam = fondsnaam;
        this.koers = koers;
    }

    public void setFondsnaam(String fondsnaam) {
        this.fondsnaam = fondsnaam;
    }
    
    @Override
    public double getKoers() {
        return koers;
    }

    public void setKoers(double koers) {
        this.koers = koers;
    }

    @Override
    public String getNaam()
    {
        return fondsnaam;
    }

}
