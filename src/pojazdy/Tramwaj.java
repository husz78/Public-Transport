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
    private int poprzedniPrzystanek; // indeks poprzedniego przystanku,
    // na ktorym byl tramwaj w tablicy przystankow danej linii. -1 lub liczba przystankow na linii,
    // jesli tramwaj ma jako nastepny przystanek jedna z petlii
    private int nastepnyPrzystanek; // indeks nastepnego przystanku,
    // na ktory przyjedzie tramwaj w tablicy przystankow danej linii lub aktualny przystanek
    // na ktorym tramwaj sie teraz znajduje

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
    public static int getPojemnosc() {
        return Tramwaj.pojemnosc;
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
