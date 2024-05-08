package symulacja;

import zdarzenia.Zdarzenie;

public class Heap implements Kolejka{
    private Zdarzenie[] zdarzenia; // de facto kopiec ze zdarzeniami
    private int rozmiar; // rozmiar kopca w danej chwili
    private int maxRozmiar; // maksymalny rozmiar kopca w danej chwili

    @Override
    public void wstaw(Zdarzenie zdarzenie) {

    }
    @Override
    public Zdarzenie pobierz() {
        return null;
    }
    @Override
    public boolean czyPusta() {
        return true;
    }
}
