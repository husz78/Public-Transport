package pojazdy;

import fizyczne.Pasazer;
import fizyczne.Przystanek;
import zdarzenia.ZdarzenieTramwaj;

public class Tramwaj extends Pojazd{
    // TODO: odkomentowac
    //private Godzina godzinaStartu; // czas, kiedy tramwaj rozpoczyna swoj bieg
    private static int pojemnosc; // pojemnosc tramwaju (dla kazdego taka sama)
    private Pasazer[] pasazerowie; // tablica aktualnych pasazerow tramwaju
    private int liczbaPasazerow; // aktualna liczba pasazerow w tramwaju
    private Przystanek poprzedniPrzystanek; // poprzedni przystanek,
    // na ktorym byl tramwaj
    private Przystanek nastepnyPrzystanek; // Przystanek nastepny w kolejnosci,
    // na ktory przyjedzie tramwaj

    public Tramwaj(int nrBoczny, Linia linia) {
        super(nrBoczny, linia);
        pasazerowie = new Pasazer[pojemnosc];
        poprzedniPrzystanek = null;
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
    public static int getPojemnosc() {
        return Tramwaj.pojemnosc;
    }
    public void setPoprzedniPrzystanek(Przystanek p) {
        poprzedniPrzystanek = p;
    }
    public void setNastepnyPrzystanek(Przystanek p) {
        nastepnyPrzystanek = p;
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

    //TODO implementacja
    public void zatrzymajSie() {}
    public ZdarzenieTramwaj odjedzZPrzystanku() {return null;}

}
