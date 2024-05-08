package symulacja;

import zdarzenia.Zdarzenie;

public class KolejkaZdarzen implements Kolejka {
    // Implementujemy kolejke zdarzen jako kopiec dynamicznie rozszerzany
    private Zdarzenie[] kolejka; // tablica zdarzen. Pierwszym elementem tablicy
    // jest zdarzenie o najwczesniejszej godzinie danego dnia
    // zerowy element tablicy to puste pole
    private int pojemnosc; // pojemnosc kolejki (ile zdarzen zmiesci)
    private int rozmiar; // aktualna liczba zdarzen w kolejce + 1 (za puste pole)

    public KolejkaZdarzen() {
        pojemnosc = 1;
        rozmiar = 1;
        kolejka = new Zdarzenie[1];
    }

    // wstawia zdarzenie do kolejki zachowujac jej strukture
    public void wstaw(Zdarzenie zdarzenie) {
        sprawdzPojemnosc();
        kolejka[rozmiar] = zdarzenie;
        kopcuj(rozmiar);
        rozmiar++;
    }

    // zwraca zdarzenie o najwczesniejszej godzinie lub null jesli kolejka jest pusta
    // i usuwa je z kolejki
    public Zdarzenie pobierz() {
        if (czyPusta()) return null;
        Zdarzenie wynik = kolejka[1];
        zamien(1, rozmiar - 1);
        rozmiar--;
        kopcujWDol(1);
        return wynik;
    }
    public Zdarzenie[] getKolejka() {
        return kolejka;
    }
    // true jesli kolejka jest pusta, false w przeciwnym przypadku
    public boolean czyPusta() {
        return rozmiar <= 1;
    }

    // zamienia miejscami dwa zdarzenia w kolejce o indeksach i oraz j
    private void zamien(int i, int j) {
        Zdarzenie tmp = kolejka[i];
        kolejka[i] = kolejka[j];
        kolejka[j] = tmp;
    }

    // naprawia strukture kopca od indeksu i w gore
    private void kopcuj(int i) {
        if (i <= 1) return; // doszlismy na sama gore kopca
        if (kolejka[i].getCzas().mniejszaNiz(kolejka[rodzic(i)].getCzas())) {
            // jesli godzina rodzica jest pozniej niz dziecka to zamieniamy je miejscami
            zamien(i, rodzic(i));
            kopcuj(rodzic(i));
        }
    }

    // naprawia strukture kopca od indeksu i w dol
    private void kopcujWDol(int i) {
        if (leweDziecko(i) >= rozmiar)return; // doszlismy na sam dol kopca
        if (leweDziecko(i) == rozmiar - 1) { // mamy tylko jedno dziecko
            if (kolejka[leweDziecko(i)].getCzas().mniejszaNiz(kolejka[i].getCzas())) {
                zamien(i, leweDziecko(i));
            }
            return;
        }

        // zamieniamy rodzica z mniejszym z dzieci zeby zachowac strukture kopca
        if (kolejka[leweDziecko(i)].getCzas().mniejszaNiz(kolejka[praweDziecko(i)].getCzas())) {
            if (kolejka[leweDziecko(i)].getCzas().mniejszaNiz(kolejka[i].getCzas())) {
                zamien(i, leweDziecko(i));
                kopcujWDol(leweDziecko(i));
            }
        }
        else {
            if (kolejka[praweDziecko(i)].getCzas().mniejszaNiz(kolejka[i].getCzas())){
                zamien(i, praweDziecko(i));
                kopcujWDol(praweDziecko(i));
            }
        }
    }

    // sprawdza czy rozmiar jest rowny pojemnosci i jesli tak to podwaja pojemnosc kopca
    private void sprawdzPojemnosc() {
        if (pojemnosc == rozmiar) {
            Zdarzenie[] kolejkaTmp = new Zdarzenie[pojemnosc * 2];
            System.arraycopy(kolejka, 0, kolejkaTmp, 0, pojemnosc);
            pojemnosc *= 2;
            kolejka = kolejkaTmp;
        }
    }

    // zwraca indeks rodzica zdarzenia o indeksie i
    private int rodzic(int i) {
        return i / 2;
    }

    //zwraca indeks lewego dziecka zdarzenia o indeksie i
    private int leweDziecko(int i) {
        return i * 2;
    }

    //zwraca indeks prawego dziecka zdarzenia o indeksie i
    private int praweDziecko(int i) {
        return i * 2 + 1;
    }
}
