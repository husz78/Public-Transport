package zdarzenia;

import pojazdy.Tramwaj;
import symulacja.Godzina;


// zdarzenie tramwaj odpowiada za zatrzymanie sie tramwaju na nastepnym przystanku
public class ZdarzenieTramwaj extends Zdarzenie{
    public ZdarzenieTramwaj(Godzina godzina, Tramwaj tramwaj) {
        super(godzina);
        this.obiekt = tramwaj;
    }
}
