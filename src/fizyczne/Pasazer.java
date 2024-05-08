package fizyczne;

import pojazdy.Tramwaj;
import symulacja.Godzina;
import symulacja.Losowanie;
import zdarzenia.ZdarzeniePasazer;
import zdarzenia.opcjaPasazer;

public class Pasazer {
    private int nr; // numer danego pasazera
    private Przystanek najblizszyPrzystanek; // najblizszy przystanek pasazera
    private Godzina godzinaWyjscia; // godzina wyjscia na przystanek danego dnia

    public Pasazer(int nr) {
        this.nr = nr;
    }
    public int getNr() {
        return nr;
    }
    public Przystanek getNajblizszyPrzystanek() {
        return najblizszyPrzystanek;
    }
    public void setNajblizszyPrzystanek(Przystanek p) {
        najblizszyPrzystanek = p;
    }
    public Godzina getGodzinaWyjscia() {
        return godzinaWyjscia;
    }
    @Override
    public String toString() {
        return "Pasazer o numerze: " + nr +
                " ma najblizszy przystanek: " + najblizszyPrzystanek +
                " wychodzi dzis o godzinie: " + godzinaWyjscia;
    }
    // TODO zaimplementowac ponizsze metody
    // zwraca zdarzenie reprezentujace wyjscie pasazera na przystanek o losowej godzinie
    public ZdarzeniePasazer zaplanujWyjscie() {
        godzinaWyjscia = Losowanie.losujGodzine();
        opcjaPasazer opcja = opcjaPasazer.zaplanuj;
        ZdarzeniePasazer zdarzenie = new ZdarzeniePasazer(godzinaWyjscia, this, opcja);
        return zdarzenie;
    }

    // Pasazer idzie na przystanek i jesli jest miejsce to czeka na tramwaj
    // a jesli nie ma to wraca do domu
    public void idzNaPrzystanek() {
        // jesli jest jeszcze miejsce na przystanku
        if (najblizszyPrzystanek.getLiczbaOsob() < Przystanek.getPojemnosc()) {
            najblizszyPrzystanek.addOczekujacy(this);
            najblizszyPrzystanek.incLiczbaOsob();
        }
    }
    public boolean wejdzDoTramwaju(Tramwaj tramwaj) {return false;}
    private void wybierzPrzystanek(Tramwaj tramwaj) {}
    public boolean wyjdzZTramwaju() {return false;}

}
