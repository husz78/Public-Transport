package symulacja;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner skaner = new Scanner(System.in);
        int liczbaDni = skaner.nextInt();
        Symulacja symulacja = new Symulacja(liczbaDni);
        symulacja.wczytajWartosci();
        symulacja.pierwszyDzien();
        while (liczbaDni > symulacja.getNrDnia()) {
            symulacja.nastepnyDzien();
        }
        System.out.println("Łączna liczba przejazdów pasażerów całej symulacji wynosi " +
                symulacja.getLiczbaPrzejazdowRazem() + ".");
        if (symulacja.getLiczbaCzekanNaPrzystanku() == 0)
            System.out.println("Średni czas czekania na przystanku dla całej symulacji wynosi 0 minut.");
        else
            System.out.println("Średni czas czekania na przystanku dla całej symulacji wynosi "
                    + symulacja.getCzasCzekaniaRazem()/symulacja.getLiczbaCzekanNaPrzystanku() + " minut.");

    }
}