package zdarzenia;
import symulacja.Godzina;
public abstract class Zdarzenie {
    protected Godzina czas;
    protected Object obiekt;

    public Godzina getCzas() {
        return czas;
    }
    public Object getObiekt() {
        return obiekt;
    }
}
