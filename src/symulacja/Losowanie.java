package symulacja;

import fizyczne.Przystanek;

import java.util.Random;

public abstract class Losowanie {
    public static int losuj(int dolna, int gorna){
        Random random = new Random();
        int liczba = random.nextInt(gorna - dolna + 1) + dolna;
        return liczba;
    }

    // losuje godzine pomiedzy 6 a 12
    public static Godzina losujGodzine(){
        int godziny = losuj(6, 11);
        int minuty = losuj(0, 59);
        Godzina godzina = new Godzina(godziny, minuty);
        return godzina;
    }

    // losuje nam przystanek ze wszystkich w danej symulacji
    public static Przystanek losujPrzystanek(Symulacja symulacja) {
        int nrPrzystanku = losuj(0, symulacja.liczbaPrzystankow() - 1);
        return symulacja.getPrzystanek(nrPrzystanku);
    }
}
