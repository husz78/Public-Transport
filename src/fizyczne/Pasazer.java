package fizyczne;

import pojazdy.Tramwaj;
import symulacja.Godzina;
import symulacja.Losowanie;
import zdarzenia.ZdarzeniePasazer;

public class Pasazer {
    private int nr; // numer danego pasazera
    private Przystanek najblizszyPrzystanek; // najblizszy przystanek pasazera miejsca zamieszkania
    private int wybranyPrzystanek; // indeks wybranego przystanku w danej linii tramwajowej,
    // ktory pasazer wybiera podczas podrozy tramwajem
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
        ZdarzeniePasazer zdarzenie = new ZdarzeniePasazer(godzinaWyjscia, this);
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

    // pasazer wchodzi do tramwaju i wybiera przystanek na ktorym wysiadzie
    // zwraca true jesli udalo sie wejsc i false jesli nie udalo sie wejsc
    public boolean wejdzDoTramwaju(Tramwaj tramwaj) {
        boolean czyWszedl = tramwaj.dodajPasazera(this);
        if (!czyWszedl) return false;
        if (tramwaj.getPoprzedniPrzystanek() <= tramwaj.getNastepnyPrzystanek()) {
            wybranyPrzystanek = Losowanie.losuj(tramwaj.getNastepnyPrzystanek() + 1,
                    tramwaj.getLinia().liczbaPrzystankow() - 1);
        }
        else {
            wybranyPrzystanek = Losowanie.losuj(0, tramwaj.getNastepnyPrzystanek() + 1);
        }
        return true;
    }
    private void wybierzPrzystanek(Tramwaj tramwaj) {}
    public boolean wyjdzZTramwaju() {return false;}

}
