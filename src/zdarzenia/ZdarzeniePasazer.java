package zdarzenia;

import fizyczne.Pasazer;
import symulacja.Godzina;

// zdarzenie pasazer odpowiada za zdarzenie gdy pasazer wychodzi na swoj domowy przystanek rano
public class ZdarzeniePasazer extends Zdarzenie{

    public ZdarzeniePasazer(Godzina godzina, Pasazer pasazer) {
        super(godzina);
        this.obiekt = pasazer;
    }

    @Override
    public String toString() {
        return czas + " " + obiekt;
    }
}
