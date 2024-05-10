package symulacja;
import fizyczne.Pasazer;
import fizyczne.Przystanek;
import pojazdy.Linia;
import pojazdy.Tramwaj;
import zdarzenia.Zdarzenie;
import zdarzenia.ZdarzeniePasazer;
import zdarzenia.ZdarzenieTramwaj;

import java.util.Scanner;

public class Symulacja {
    private int liczbaPrzejazdowRazem; // laczna liczba przejazdow
    // pasazerow ze wszystkich dni symulacji
    private int czasCzekaniaRazem; // laczny czas oczekiwania na przystankach
    // przez pasazerow ze wszystkich dni symulacji
    private int liczbaCzekanNaPrzystanku; // laczna liczba oczekiwan na przystanku przez
    // pasazerow danej symulacji
    private final int liczbaDni; // liczba dni calej symulacji
    private int nrDnia; // numer dnia danej symulacji
    private int liczbaPrzejazdow; // liczba przejazdow pasazerow z
    // danego dnia symulacji
    private int czasCzekania; // czas oczekiwania na przystankach
    // przez pasazerow z danego dnia symulacji
    private Przystanek[] przystanki; // wszystkie przystanki
    // nalezace do symulacji
    private Pasazer[] pasazerowie; // wszyscy pasazerowie
    // nalezacy do symulacji
    private Tramwaj[] tramwaje; // wszystkie tramwaje
    // nalezace do danej symulacji
    private KolejkaZdarzen kolejka; // kolejka zdarzen danej symulacji

    public Symulacja(int liczbaDni) {
        this.liczbaDni = liczbaDni;
        this.czasCzekania = 0;
        this.liczbaPrzejazdow = 0;
        this.czasCzekaniaRazem = 0;
        this.liczbaPrzejazdowRazem = 0;
        this.nrDnia = 0;
        this.kolejka = new KolejkaZdarzen();
    }
    @Override
    public String toString() {
        return "Aktualny dzień symulacji: " + nrDnia + "/" + liczbaDni +
                " Czas oczekiwania tego dnia: " + czasCzekania + " Liczba Przejazdow: " +
                liczbaPrzejazdow + " Liczba przejazdow lacznie: " + liczbaPrzejazdowRazem;
    }
    public void wczytajWartosci() {
        Scanner skaner = new Scanner(System.in);
        int pojemnoscPrzystanku = skaner.nextInt();
        Przystanek.setPojemnosc(pojemnoscPrzystanku);

        int liczbaPrzystankow = skaner.nextInt();
        if (liczbaPrzystankow != 0) {
            Przystanek[] przystanki = new Przystanek[liczbaPrzystankow];
            for (int i = 0; i < liczbaPrzystankow; i++) {
                String nazwa = skaner.next();
                przystanki[i] = new Przystanek(nazwa, i); // tworzenie przystankow
            }
            this.przystanki = przystanki;
        }
        else this.przystanki = null;

        int liczbaPasazerow = skaner.nextInt();
        if (liczbaPasazerow != 0) {
            Pasazer[] pasazerowie = new Pasazer[liczbaPasazerow];
            for (int i = 0; i < liczbaPasazerow; i++) {
                pasazerowie[i] = new Pasazer(i); // tworzenie pasazerow
            }
            this.pasazerowie = pasazerowie;
        }
        else this.pasazerowie = null;

        int pojemnoscTramwaju = skaner.nextInt();
        Tramwaj.setPojemnosc(pojemnoscTramwaju);
        int liczbaLinii = skaner.nextInt();
        if (liczbaLinii != 0) {
            int liczbaTramwajow = 0;
            // dwuwymiarowa tablica tramwajow (bo nie znamy dokladnej liczby od razu)
            Tramwaj[][] tramwaje = new Tramwaj[liczbaLinii][];

            for (int i = 0; i < liczbaLinii; i++){
                int liczbaTramwajowLinii = skaner.nextInt();
                //tworzenie tablicy nowych tramwajow
                tramwaje[i] = new Tramwaj[liczbaTramwajowLinii];
                int dlugoscTrasy = skaner.nextInt();
                // tworzenie nowych linii
                Linia l = new Linia(i, liczbaTramwajowLinii, dlugoscTrasy);

                for (int j = 0; j < liczbaTramwajowLinii; j++) {
                    // tworzenie nowych tramwajow
                    tramwaje[i][j] = new Tramwaj(liczbaTramwajow, l);
                    l.wstawPojazd(tramwaje[i][j], j);
                    liczbaTramwajow++;
                }
                for (int j = 0; j < dlugoscTrasy; j++) {
                    String nazwaPrzystanku = skaner.next();
                    int czasDojazdu = skaner.nextInt();
                    l.dodajTrase(this, nazwaPrzystanku, czasDojazdu, j);
                }
            }

            // przepisujemy 2D wymiarową tablice tramwaji na atrybut 1D this.tramwaje
            this.tramwaje = new Tramwaj[liczbaTramwajow];
            int licznik = 0;
            for (int i = 0; i < liczbaLinii; i++){
                for (Tramwaj t : tramwaje[i]) {
                    this.tramwaje[licznik++] = t;
                }
            }
        }
        else this.tramwaje = null;
    }

    // losuje nam przystanki domowe dla wszystkich pasazerow symulacji
    private void wylosujPrzystanki() {
        if (pasazerowie == null)return;
        for (Pasazer p : pasazerowie) {
            p.setNajblizszyPrzystanek(Losowanie.losujPrzystanek(this));
        }
    }

    // losuje godziny wyjscia dla pasazerow
    // i wstawia zdarzenia reprezentujace wyjscie na przystanek do kolejki
    private void wylosujGodzinyWyjscia() {
        if (pasazerowie == null) return;
        for (Pasazer p : pasazerowie) {
            kolejka.wstaw(p.zaplanujWyjscie());
        }
    }

    // ustawia tramwaje na odpowiednich przystankach poczatkowych zgodnie ze specyfikacja
    private void ustawTramwaje() {
        int counter = 0;
        if (tramwaje == null)return;
        if (tramwaje.length == 0)return;
        if (tramwaje.length == 1) {
            tramwaje[0].setPoprzedniPrzystanek(-1);
            tramwaje[0].setNastepnyPrzystanek(0);
            tramwaje[0].setPoczatkowyPrzystanek(0);
        }
        else { // ustawiamy tramwaje zgodnie ze specyfikacją zadania
            tramwaje[0].setPoprzedniPrzystanek(-1);
            tramwaje[0].setNastepnyPrzystanek(0);
            tramwaje[0].setPoczatkowyPrzystanek(0);
            counter++;
            for (int i = 1; i < tramwaje.length; i++) {
                if (tramwaje[i].getLinia().getNr() != tramwaje[i-1].getLinia().getNr()) {
                    counter = 1;
                    tramwaje[i].setNastepnyPrzystanek(0);
                    tramwaje[i].setPoprzedniPrzystanek(-1);
                    tramwaje[i].setPoczatkowyPrzystanek(0);
                }
                if (tramwaje[i].getLinia().getNr() == tramwaje[i-1].getLinia().getNr()) {
                    if (counter < (tramwaje[i].getLinia().getLiczbaPojazdow() + 1) / 2) {
                        counter++;
                        tramwaje[i].setPoprzedniPrzystanek(-1);
                        tramwaje[i].setNastepnyPrzystanek(0);
                        tramwaje[i].setPoczatkowyPrzystanek(0);
                    }
                    else {
                        counter++;
                        tramwaje[i].setNastepnyPrzystanek(tramwaje[i].getLinia().liczbaPrzystankow() - 1);
                        tramwaje[i].setPoprzedniPrzystanek(tramwaje[i].getLinia().liczbaPrzystankow());
                        tramwaje[i].setPoczatkowyPrzystanek(tramwaje[i].getLinia().liczbaPrzystankow() - 1);
                    }
                }
            }
        }
    }

    // ustala godziny wyjazdu wszystkich tramwajow symulacji
    // i wrzuca nowe zdarzenia dla tramwajow odpowiadajace ich przyjazdom na pierwszy przystanek
    private void ustalGodzinyWyjazdu() {
        if (tramwaje == null)return;
        if (tramwaje.length == 0)return;
        tramwaje[0].setGodzinaStartu(new Godzina(6, 0));
        for (int i = 1; i < tramwaje.length; i++) {
            if (tramwaje[i].getLinia().getNr() != tramwaje[i - 1].getLinia().getNr()) {
                tramwaje[i].setGodzinaStartu(new Godzina(6, 0));
            } else {
                int czasPrzejazdu = tramwaje[i].getLinia().czasPrzejazdu();
                int kwant = czasPrzejazdu / tramwaje[i].getLinia().getLiczbaPojazdow();
                if (tramwaje[i].getNastepnyPrzystanek() == tramwaje[i - 1].getNastepnyPrzystanek()) {
                    Godzina g = tramwaje[i - 1].getGodzinaStartu().dodajMinuty(kwant);
                    tramwaje[i].setGodzinaStartu(g);
                } else {
                    tramwaje[i].setGodzinaStartu(new Godzina(6, 0));
                }
            }
        }
        for (Tramwaj t : tramwaje)
            kolejka.wstaw(new ZdarzenieTramwaj(t.getGodzinaStartu(), t));
    }

    // TODO nie skończony pierwszy dzien
    public void pierwszyDzien() {
        wylosujPrzystanki();
        ustawTramwaje();
        nastepnyDzien();
    }

    // obsluguje zdarzenie sciagniete z gory kolejki
    private void nastepneZdarzenie(Zdarzenie zdarzenie) {
        Godzina koniecWyjazdowZZajezdni = new Godzina(23, 0);
        Godzina godzina = zdarzenie.getCzas();

        if (zdarzenie instanceof ZdarzenieTramwaj) {
            ZdarzenieTramwaj zdarzenieT = (ZdarzenieTramwaj) zdarzenie;
            Tramwaj tramwaj = (Tramwaj) zdarzenieT.getObiekt();
            if (koniecWyjazdowZZajezdni.mniejszaNiz(zdarzenieT.getCzas()))
                if (tramwaj.getNastepnyPrzystanek() == tramwaj.getPoczatkowyPrzystanek()) return;
            tramwaj.zatrzymajSie(this, godzina);
            ZdarzenieTramwaj noweZdarzenie = tramwaj.odjedzZPrzystanku(godzina);
            kolejka.wstaw(noweZdarzenie);
        }
        else if (zdarzenie instanceof ZdarzeniePasazer) {
            ZdarzeniePasazer zdarzenieP = (ZdarzeniePasazer) zdarzenie;
            Pasazer pasazer = (Pasazer) zdarzenieP.getObiekt();
            pasazer.idzNaPrzystanek(this);
        }
    }
    public void nastepnyDzien() {
        if (nrDnia >= liczbaDni) return;
        wylosujGodzinyWyjscia();
        ustalGodzinyWyjazdu();

        while (!kolejka.czyPusta()) {
            Zdarzenie zdarzenie = kolejka.pobierz();
            nastepneZdarzenie(zdarzenie);
        }
        System.out.println("Łączna liczba przejazdów dnia nr: " + nrDnia + " wynosi " + liczbaPrzejazdow);

        for (Pasazer p : pasazerowie) czasCzekania += p.getCzasCzekania();
        System.out.println("Łączny czas czekania na przystankach dnia " + nrDnia + " wynosi " + czasCzekania);
        czasCzekaniaRazem += czasCzekania;
        liczbaPrzejazdow = 0;
        czasCzekania = 0;
        // na koniec dnia pasazerowie wracaja do domu
        ustawTramwaje();
        for (Przystanek p : przystanki) p.oproznijPrzystanek();
        for (Tramwaj t : tramwaje) t.oproznijTramwaj();
        for (Pasazer p : pasazerowie) p.resetujCzasCzekania();
        nrDnia++;
    }


    // zwraca przystanek o danej nazwie
    public Przystanek getPrzystanek(String nazwa) {
        for (Przystanek p : przystanki) {
            if (p.getNazwa().equals(nazwa)) return p;
        }
        return null;
    }
    public Tramwaj[] getTramwaje() {
        return tramwaje;
    }
    public Przystanek[] getPrzystanki() {
        return przystanki;
    }
    public Pasazer[] getPasazerowie() {
        return pasazerowie;
    }
    public int liczbaPrzystankow() {
        return przystanki.length;
    }
    public void incLiczbaPrzejazdow() {
        liczbaPrzejazdow++;
        liczbaPrzejazdowRazem++;
    }
    public void incLiczbaCzekanNaPrzystanku() {
        liczbaCzekanNaPrzystanku++;
    }
    public int getLiczbaPrzejazdowRazem() {
        return liczbaPrzejazdowRazem;
    }
    public int getCzasCzekaniaRazem() {
        return czasCzekaniaRazem;
    }
    public int getLiczbaCzekanNaPrzystanku() {
        return liczbaCzekanNaPrzystanku;
    }
    public int liczbaPasazerow() {
        return pasazerowie.length;
    }
    // TODO Moze do usuniecia ten getter
    public KolejkaZdarzen getKolejka() {
        return kolejka;
    }
    public int getNrDnia() {
        return nrDnia;
    }

    // zwraca przystanek o indeksie i w symulacji
    public Przystanek getPrzystanek(int i) {
        assert i >= 0 && i < liczbaPrzystankow():
                "Niepoprawny indeks przystanku";
        return przystanki[i];
    }
}
