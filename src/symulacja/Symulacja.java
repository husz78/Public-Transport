package symulacja;
import fizyczne.Pasazer;
import fizyczne.Przystanek;
import pojazdy.Linia;
import pojazdy.Tramwaj;
import java.util.Scanner;

public class Symulacja {
    private int liczbaPrzejazdowRazem; // laczna liczba przejazdow
    // pasazerow ze wszystkich dni symulacji
    private int czasCzekaniaRazem; // laczny czas oczekiwania na przystankach
    // przez pasazerow ze wszystkich dni symulacji
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
                liczbaPrzejazdow + " Czas oczekiwania lacznie: " + czasCzekaniaRazem +
                " Liczba przejazdow lacznie: " + liczbaPrzejazdowRazem;
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
                int counter = 0;
                for (int j = 0; j < liczbaTramwajowLinii; j++) {
                    // tworzenie nowych tramwajow
                    tramwaje[i][j] = new Tramwaj(liczbaTramwajow, l);
                    if (counter < liczbaTramwajowLinii / 2) {
                        tramwaje[i][j].setPoprzedniPrzystanek(l.liczbaPrzystankow());
                        tramwaje[i][j].setNastepnyPrzystanek(l.liczbaPrzystankow() - 1);
                    }
                    else {
                        tramwaje[i][j].setPoprzedniPrzystanek(-1);
                        tramwaje[i][j].setNastepnyPrzystanek(0);
                    }
                    counter++;
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
        for (Pasazer p : pasazerowie) {
            p.setNajblizszyPrzystanek(Losowanie.losujPrzystanek(this));
        }
    }

    // losuje godziny wyjscia dla pasazerow
    // i wstawia zdarzenia reprezentujace wyjscie na przystanek do kolejki
    private void wylosujGodzinyWyjscia() {
        for (Pasazer p : pasazerowie) {
            kolejka.wstaw(p.zaplanujWyjscie());
        }
    }

    // TODO nie skończony pierwszy dzien
    public void pierwszyDzien() {
        wylosujPrzystanki();
        wylosujGodzinyWyjscia();
    }
    public void nastepneZdarzenie() {}
    public void nastepnyDzien() {}


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
    public int liczbaPasazerow() {
        return pasazerowie.length;
    }
    // TODO Moze do usuniecia ten getter
    public KolejkaZdarzen getKolejka() {
        return kolejka;
    }

    // zwraca przystanek o indeksie i w symulacji
    public Przystanek getPrzystanek(int i) {
        assert i >= 0 && i < liczbaPrzystankow():
                "Niepoprawny indeks przystanku";
        return przystanki[i];
    }
}
