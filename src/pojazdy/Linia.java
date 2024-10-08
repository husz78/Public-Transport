package pojazdy;

import fizyczne.Przystanek;
import symulacja.Symulacja;

public class Linia {
    private int liczbaPojazdow; // liczba pojazdow danej linii
    private int dlugoscTrasy; // dlugosc trasy danej linii
    private int[] czasMiedzyPrzystankami; // czas w minutach pomiedzy kolejnymi
    // przystankami na trasie linii w formie takiej samej jak na wejsciu symulacji
    private Przystanek[] przystanki; // przystanki w kolejnosci,
    // ktore naleza do danej trasy linii
    private int nr; // numer linii
    private Pojazd[] pojazdy; // tablica pojazdow nalezacych do danej linii
    public Linia(int nr, int liczbaPojazdow, int dlugoscTrasy) {
        this.nr = nr;
        this.liczbaPojazdow = liczbaPojazdow;
        this.dlugoscTrasy = dlugoscTrasy;
        this.czasMiedzyPrzystankami = new int[dlugoscTrasy];
        this.przystanki = new Przystanek[dlugoscTrasy];
        this.pojazdy = new Pojazd[liczbaPojazdow];
    }
    @Override
    public String toString() {
        return "Linia nr: " + nr + " Dlugosc trasy: " + dlugoscTrasy +
                " Liczba pojazdow: " + liczbaPojazdow;
    }

    // dodaje do przystanki i czasMiedzyPrzystankami odpowiednie wartosci pod indeksami i
    public void dodajTrase(Symulacja symulacja, String przystanek, int czasMiedzyPrzystankiem, int i) {
        assert i < dlugoscTrasy && i >= 0: "Nie mozna dodac trasy, niepoprawny indeks: " + i;
        Przystanek p = symulacja.getPrzystanek(przystanek);
        przystanki[i] = p;
        czasMiedzyPrzystankami[i] = czasMiedzyPrzystankiem;
    }

    public int liczbaPrzystankow() {
        return przystanki.length;
    }
    public Przystanek getItyPrzystanek(int i) {
        return przystanki[i];
    }
    public int getNr(){
        return nr;
    }

    // ustawia i-ty pojazd linii na pojazd
    public void wstawPojazd(Pojazd pojazd, int i) {
        pojazdy[i] = pojazd;
    }
    public int getLiczbaPojazdow() {
        return liczbaPojazdow;
    }

    // zwraca czas przejazdu pomiedzy i-tym i+1-szym przystankiem
    public int getCzasPrzejazdu(int i) {
        return czasMiedzyPrzystankami[i];
    }

    // zwraca czas postoju na petli
    public int getCzasPetla() {
        return czasMiedzyPrzystankami[liczbaPrzystankow() - 1];
    }

    // zwraca nam laczny czas przejazdu pelnego kolka na trasie linii
    public int czasPrzejazdu() {
        int czas = 0;
        for (int i : czasMiedzyPrzystankami)
            czas += i;
        return 2 * czas;
    }
}
