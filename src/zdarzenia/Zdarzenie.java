package zdarzenia;
import symulacja.Godzina;
public abstract class Zdarzenie {
    protected Godzina czas;
    protected Object obiekt;

    public Zdarzenie(Godzina godzina) {
        this.czas = godzina;
    }

    public Godzina getCzas() {
        return czas;
    }
    public Object getObiekt() {
        return obiekt;
    }
}
