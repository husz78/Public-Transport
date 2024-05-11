package fizyczne;

public class Przystanek {
    private String nazwa; // nazwa przystanku
    private static int pojemnosc; // pojemnosc przystanku (dla kazdego taka sama)
    private int liczbaOsob; // aktualna liczba osob na przystanku
    private Pasazer[] oczekujacy; // aktualni pasazerowie czekajacy na przystanku
    private int nr; // numer przystanku

    public Przystanek(String nazwa, int nr) {
        this.nazwa = nazwa;
        this.nr = nr;
        this.liczbaOsob = 0;
        this.oczekujacy = new Pasazer[pojemnosc];
    }

    @Override
    public String toString() {
        return "Przystanek " + nazwa + " o numerze: " + nr + " Liczba osob oczekujacych: " +
                liczbaOsob + " Pojemnosc: " + Przystanek.getPojemnosc();
    }
    public static void setPojemnosc(int pojemnosc) {
        Przystanek.pojemnosc = pojemnosc;
    }
    public static int getPojemnosc() {
        return Przystanek.pojemnosc;
    }
    public void incLiczbaOsob() {
        liczbaOsob++;
    }
    public int getLiczbaOsob() {
        return liczbaOsob;
    }

    // zamienia i-tego oczekujacego i j-tego oczekujacego miejscami na przystanku
    private void zamien(int i, int j) {
        Pasazer tmp = oczekujacy[i];
        oczekujacy[i] = oczekujacy[j];
        oczekujacy[j] = tmp;
    }

    // Zwraca nam i-tego oczekujacego
    public Pasazer getItyOczekujacy(int i) {
        assert i >= 0 && i < Przystanek.getPojemnosc():
                "Niepoprawny indeks oczekujacego na przystanku: " + i;
        return oczekujacy[i];
    }

    // Dodaje pasazera do oczekujacych na przystanku (jesli jest jeszcze miejsce)
    public void addOczekujacy(Pasazer a) {
        assert Przystanek.getPojemnosc() > getLiczbaOsob(): "Nie mozna dodac juz oczekujacego" +
                "na przystanku o numerze: " + this.nr;
        oczekujacy[liczbaOsob] = a;
    }

    // usuwa i-tego pasazera z oczekujacych na przystanku
    public void usunItyOczekujacy(int i) {
        zamien(i, liczbaOsob - 1);
        liczbaOsob--;
    }
    public String getNazwa() {
        return nazwa;
    }
    public void oproznijPrzystanek() {
        liczbaOsob = 0;
    }
}
