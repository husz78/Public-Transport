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
    public Przystanek getWybranyPrzystanek(Tramwaj tramwaj) {
        Przystanek przystanek = tramwaj.getLinia().getItyPrzystanek(wybranyPrzystanek);
        return przystanek;
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
        wybierzPrzystanek(tramwaj);
        return true;
    }

    // wybiera przystanek, na ktorym wysiadzie, pasazerowi z pozostalych na trasie
    private void wybierzPrzystanek(Tramwaj tramwaj) {
        if (tramwaj.getPoprzedniPrzystanek() <= tramwaj.getNastepnyPrzystanek()) {
            wybranyPrzystanek = Losowanie.losuj(tramwaj.getNastepnyPrzystanek() + 1,
                    tramwaj.getLinia().liczbaPrzystankow() - 1);
        }
        else {
            wybranyPrzystanek = Losowanie.losuj(0, tramwaj.getNastepnyPrzystanek() + 1);
        }
    }

    // pasazer wysiada na przystanku wybranym uprzednio
    // metoda zwraca true jesli to sie udalo i false w przeciwnym przypadku
    public boolean wyjdzZTramwaju(Tramwaj tramwaj) {
        Przystanek przystanek = tramwaj.getLinia().getItyPrzystanek(wybranyPrzystanek);
        if (tramwaj.indeksPasazera(this) == -1) return false;
        if (przystanek.getLiczbaOsob() < Przystanek.getPojemnosc()) {
            tramwaj.zamien(tramwaj.indeksPasazera(this), tramwaj.getLiczbaPasazerow() - 1);
            tramwaj.decLiczbaPasazerow();
            przystanek.addOczekujacy(this);
            przystanek.incLiczbaOsob();
            return true;
        }
        else return false;
    }

}
