package pojazdy;

import fizyczne.Pasazer;
import fizyczne.Przystanek;
import symulacja.Godzina;
import symulacja.Symulacja;
import zdarzenia.ZdarzenieTramwaj;

public class Tramwaj extends Pojazd {
    // TODO: odkomentowac
    private Godzina godzinaStartu; // czas, kiedy tramwaj rozpoczyna swoj bieg
    private static int pojemnosc; // pojemnosc tramwaju (dla kazdego taka sama)
    private Pasazer[] pasazerowie; // tablica aktualnych pasazerow tramwaju
    private int liczbaPasazerow; // aktualna liczba pasazerow w tramwaju
    private int poprzedniPrzystanek; // indeks poprzedniego przystanku,
    // na ktorym byl tramwaj w tablicy przystankow danej linii. -1 lub liczba przystankow na linii,
    // jesli tramwaj ma jako nastepny przystanek jedna z petlii
    private int nastepnyPrzystanek; // indeks nastepnego przystanku,
    // na ktory przyjedzie tramwaj w tablicy przystankow danej linii lub aktualny przystanek
    // na ktorym tramwaj sie teraz znajduje
    private int poczatkowyPrzystanek; // przystanek, na ktorym tramwaj zaczyna swoj bieg

    public Tramwaj(int nrBoczny, Linia linia) {
        super(nrBoczny, linia);
        pasazerowie = new Pasazer[pojemnosc];
        liczbaPasazerow = 0;
    }
    @Override
    public String toString() {
        return super.toString() + " Jestem tramwajem o pojemnosci: " + Tramwaj.pojemnosc +
                " Moj poprzedni przystanek to: " + poprzedniPrzystanek;
    }
    public static void setPojemnosc(int pojemnosc) {
        Tramwaj.pojemnosc = pojemnosc;
    }
    public void setPoprzedniPrzystanek(int i) {
        poprzedniPrzystanek = i;
    }
    public void setNastepnyPrzystanek(int i) {
        nastepnyPrzystanek = i;
    }
    public int getPoprzedniPrzystanek() {
        return poprzedniPrzystanek;
    }
    public int getNastepnyPrzystanek() {
        return nastepnyPrzystanek;
    }
    public int getLiczbaPasazerow() {
        return liczbaPasazerow;
    }
    public int getPoczatkowyPrzystanek() { return poczatkowyPrzystanek; }
    public void setPoczatkowyPrzystanek(int i) { poczatkowyPrzystanek = i; }
    public void decLiczbaPasazerow() {
        liczbaPasazerow--;
    }
    public void setGodzinaStartu(Godzina godzina) { godzinaStartu = godzina; }
    public Godzina getGodzinaStartu() { return godzinaStartu; }

    // zamienia miejscami pasazera i oraz pasazera j w tablicy pasazerow tramwaju
    public void zamien(int i, int j) {
        Pasazer tmp = pasazerowie[i];
        pasazerowie[i] = pasazerowie[j];
        pasazerowie[j] = tmp;
    }

    // zwraca indeks pasazera w danym tramwaju
    public int indeksPasazera(Pasazer pasazer) {
        for (int i = 0; i < liczbaPasazerow; i++){
            if (pasazer == pasazerowie[i])return i;
        }
        return -1;
    }

    // dodaje pasazera do ludzi w tramwaju i zwraca true jesli sie udalo i false jak nie ma juz miejsca
    public boolean dodajPasazera(Pasazer pasazer) {
        if (liczbaPasazerow < pojemnosc) {
            pasazerowie[liczbaPasazerow] = pasazer;
            liczbaPasazerow++;
            return true;
        }
        else return false;
    }

    // wypuszcza wszystkich pasazerow, ktorzy wybrali dany przystanek jeslii jest na nim miejsce
    private void wypuscPasazerow(Symulacja symulacja, Godzina godzina) {
        for (int i = 0; i < liczbaPasazerow; i++) {
            if (pasazerowie[i].getWybranyPrzystanek() == nastepnyPrzystanek) {
                boolean czyWyszedl = pasazerowie[i].wyjdzZTramwaju(this);
                if (czyWyszedl) {
                    System.out.println(symulacja.getNrDnia() + ", " + godzina + ": Pasażer " +
                            pasazerowie[i].getNr() + " wysiadł z tramwaju linii " + this.getLinia().getNr() +
                            " (nr. bocz. " + this.getNrBoczny() + ") na przystanku " +
                            pasazerowie[i].getWybranyPrzystanek(this).getNazwa() + ".");
                    pasazerowie[i].setGodzinaOstatnieogoCzekania(godzina);
                    symulacja.incLiczbaCzekanNaPrzystanku();
                }

            }
        }
    }

    // wpuszcza wszyskich pasazerow z aktualnego przystanku jesli jest miejsce w tramwaju
    private void wpuscPasazerow(Symulacja symulacja, Godzina godzina) {
        Przystanek przystanek = getLinia().getItyPrzystanek(nastepnyPrzystanek);
        for (int i = 0; i < przystanek.getLiczbaOsob(); i++) {
            Pasazer p = przystanek.getItyOczekujacy(i);
            boolean czyWszedl = przystanek.getItyOczekujacy(i).wejdzDoTramwaju(this);
            if (czyWszedl) {
                symulacja.incLiczbaPrzejazdow();
                p.dodajCzasCzekania(p.getGodzinaOstatnieogoCzekania().roznica(godzina));
                przystanek.usunItyOczekujacy(i);
                System.out.println(symulacja.getNrDnia() + ", " + godzina + ": Pasażer " +
                        p.getNr() + " wsiadł do tramwaju linii " + getLinia().getNr() +
                        " (nr bocz. " + getNrBoczny() + ") z zamiarem dojechania do " +
                        "przystanku " + p.getWybranyPrzystanek(this).getNazwa() + ".");
            }
        }
    }

    // zwraca true jesli tramwaj jest na petli i false jesli nie jest
    private boolean czyNaPetli() {
        if (((nastepnyPrzystanek == getLinia().liczbaPrzystankow()-1) && poprzedniPrzystanek == nastepnyPrzystanek-1)
                || (nastepnyPrzystanek == 0 && poprzedniPrzystanek == 1)) return true;
        return false;
    }

    // Tramwaj zatrzymuje sie na nastepnym przystanku wypuszcza, a nastepnia wpuszcza pasazerow
    // chyba ze jest na petli, wtedy nie wpuszcza pasazerow juz
    public void zatrzymajSie(Symulacja symulacja, Godzina godzina) {
        System.out.println(symulacja.getNrDnia() + ", " + godzina + ": Tramwaj linii " +
                getLinia().getNr() + " (nr bocz. " + getNrBoczny() + ") zatrzymał się" +
                " na przystanku " + getLinia().getItyPrzystanek(nastepnyPrzystanek).getNazwa() + ".");
        wypuscPasazerow(symulacja, godzina);
        if (!czyNaPetli()) wpuscPasazerow(symulacja, godzina);
    }

    // zwraca zdarzenie tramwaju odpowiadajace zatrzymaniu sie tramwaju na kolejnym przystanku
    public ZdarzenieTramwaj odjedzZPrzystanku(Godzina godzina) {
        // jesli jestesmy na petli w prawa strone
        if ((nastepnyPrzystanek == getLinia().liczbaPrzystankow()-1 && poprzedniPrzystanek == nastepnyPrzystanek-1)) {
            poprzedniPrzystanek = getLinia().liczbaPrzystankow();
            godzina = godzina.dodajMinuty(getLinia().getCzasPetla());
        }
        // jesli jestesmy na petli w lewa strone
        else if (nastepnyPrzystanek == 0 && poprzedniPrzystanek == 1) {
            poprzedniPrzystanek = -1;
            godzina = godzina.dodajMinuty(getLinia().getCzasPetla());
        }
        // jesli jedziemy w prawa strone
        else if (nastepnyPrzystanek > poprzedniPrzystanek) {
            godzina = godzina.dodajMinuty(getLinia().getCzasPrzejazdu(nastepnyPrzystanek));
            nastepnyPrzystanek++;
            poprzedniPrzystanek++;
        }
        // jesli jedziemy w lewa strone
        else {
            godzina = godzina.dodajMinuty(getLinia().getCzasPrzejazdu(nastepnyPrzystanek - 1));
            nastepnyPrzystanek--;
            poprzedniPrzystanek--;
        }
        ZdarzenieTramwaj zdarzenie = new ZdarzenieTramwaj(godzina, this);
        return zdarzenie;
    }

    // oprozniamy tramwaj z pasazerow
    public void oproznijTramwaj() {
        liczbaPasazerow = 0;
    }
}
