package symulacja;

import fizyczne.Pasazer;

public class Godzina {
    private int godziny;
    private int minuty;
    public Godzina(int godziny, int minuty) {
        assert godziny > 0 && godziny <= 24: "Niepoprawna godzina.";
        assert minuty >= 0 && minuty < 60: "Niepoprawna minuta";
        this.godziny = godziny;
        this.minuty = minuty;
    }

    @Override
    public String toString() {
        String wynik = "";
        if (godziny < 10) wynik += "0";
        wynik += godziny;
        wynik += ":";
        if (minuty < 10) wynik += "0";
        wynik += minuty;
        return wynik;
    }
    public int getGodziny() {
        return godziny;
    }
    public int getMinuty() {
        return minuty;
    }


    // zwraca true jesli this jest wczesniej niz a
    public boolean mniejszaNiz(Godzina a) {
        if (godziny < a.getGodziny()) return true;
        else if (godziny > a.getGodziny()) return false;
        else {
            if (minuty < a.getMinuty()) return true;
            else return false;
        }
    }

    // zwraca godzine this + x minut
    public Godzina dodajMinuty(int x) {
        int noweMinuty;
        int noweGodziny;
        if (minuty + x < 60) {
            noweMinuty = x + minuty;
            noweGodziny = godziny;
        }
        else {
            noweGodziny = godziny + 1;
            noweMinuty = minuty + x - 60;
        }
        return new Godzina (noweGodziny, noweMinuty);
    }

    // zwraca roznice (w minutach) pomiedzy dwoma godzinami
    public int roznica(Godzina g) {
        return Math.abs(60 * (g.godziny - godziny) + g.minuty - minuty);
    }
}
