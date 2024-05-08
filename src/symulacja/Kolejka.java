package symulacja;
import zdarzenia.*;

public interface Kolejka {
    public void wstaw(Zdarzenie zdarzenie);
    public Zdarzenie pobierz();
    public boolean czyPusta();
}
