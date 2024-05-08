package pojazdy;

import fizyczne.Pasazer;

public class Tramwaj extends Pojazd{
    // TODO: odkomentowac
    //private Godzina godzinaStartu; // czas, kiedy tramwaj rozpoczyna swoj bieg
    private static int pojemnosc; // pojemnosc tramwaju (dla kazdego taka sama)
    private Pasazer[] pasazerowie; // tablica aktualnych pasazerow tramwaju
    private String poprzedniPrzystanek; // nazwa poprzedniego przystanku,
    // na ktorym byl tramwaj

    public Tramwaj(int nrBoczny, Linia linia) {
        super(nrBoczny, linia);
        pasazerowie = new Pasazer[pojemnosc];
        poprzedniPrzystanek = null;
    }
    @Override
    public String toString() {
        return super.toString() + " Jestem tramwajem o pojemnosci: " + Tramwaj.pojemnosc +
                " Moj poprzedni przystanek to: " + poprzedniPrzystanek;
    }
    public static void setPojemnosc(int pojemnosc) {
        Tramwaj.pojemnosc = pojemnosc;
    }
    public static int getPojemnosc() {
        return Tramwaj.pojemnosc;
    }

    //TODO implementacja
    private void zatrzymajSie() {}

}
