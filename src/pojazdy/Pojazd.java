package pojazdy;

public abstract class Pojazd {
    private int nrBoczny; // numer boczny pojazdu
    private Linia linia; // linia do ktorej nalezy dany pojazd
    public Pojazd(int nrBoczny, Linia linia) {
        this.nrBoczny = nrBoczny;
        this.linia = linia;
    }
    @Override
    public String toString() {
        return "Pojazd: numer boczny: " + nrBoczny + " " + linia;
    }
    public Linia getLinia() {
        return linia;
    }
    public int getNrBoczny() { return nrBoczny; }

}
