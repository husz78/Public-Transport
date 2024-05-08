package zdarzenia;

import fizyczne.Pasazer;
import symulacja.Godzina;

public class ZdarzeniePasazer extends Zdarzenie{

    private opcjaPasazer opcja;
    public ZdarzeniePasazer(Godzina godzina, Pasazer pasazer, opcjaPasazer opcja) {
        this.czas = godzina;
        this.obiekt = pasazer;
        this.opcja = opcja;
    }
    public opcjaPasazer getOpcja() {
        return opcja;
    }

    @Override
    public String toString() {
        return czas + " " + obiekt + " " + opcja;
    }
}
