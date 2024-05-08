package zdarzenia;

import pojazdy.Tramwaj;
import symulacja.Godzina;

public class ZdarzenieTramwaj extends Zdarzenie{
    public ZdarzenieTramwaj(Godzina godzina, Tramwaj tramwaj) {
        this.czas = godzina;
        this.obiekt = tramwaj;
    }
}
