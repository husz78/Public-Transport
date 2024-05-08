package zdarzenia;

import fizyczne.Pasazer;
import symulacja.Godzina;

public class ZdarzeniePasazer extends Zdarzenie{

    public ZdarzeniePasazer(Godzina godzina, Pasazer pasazer) {
        this.czas = godzina;
        this.obiekt = pasazer;
    }

    @Override
    public String toString() {
        return czas + " " + obiekt;
    }
}
