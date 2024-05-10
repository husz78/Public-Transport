package symulacja;
import fizyczne.*;
import pojazdy.*;
import zdarzenia.Zdarzenie;
import zdarzenia.ZdarzeniePasazer;
import zdarzenia.ZdarzenieTramwaj;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner skaner = new Scanner(System.in);
        int liczbaDni = skaner.nextInt();
        Symulacja symulacja = new Symulacja(liczbaDni);
        symulacja.wczytajWartosci();
//        System.out.println(symulacja);
        Pasazer[] pasazerowie = symulacja.getPasazerowie();
        Tramwaj[] tramwaje = symulacja.getTramwaje();
        Przystanek[] przystanki = symulacja.getPrzystanki();
        symulacja.pierwszyDzien();
        KolejkaZdarzen kolejka = symulacja.getKolejka();
        while (!kolejka.czyPusta()) {
            Zdarzenie z = kolejka.pobierz();
            System.out.println(z.getCzas());
        }
        for (Pasazer p : pasazerowie) {
            p.idzNaPrzystanek(symulacja);
        }
        for (Przystanek p : przystanki) {
            System.out.println("Na przystanku " + p.getNazwa() + " jest " + p.getLiczbaOsob() + " pasazerow.");
        }
        for (Tramwaj t : tramwaje) {
            t.zatrzymajSie(symulacja, new Godzina(7, 8));
        }
        for (Przystanek p : przystanki) {
            System.out.println("Na przystanku " + p.getNazwa() + " jest " + p.getLiczbaOsob() + " pasazerow.");
        }

//        for (Pasazer p : pasazerowie) System.out.println(p.getGodzinaWyjscia());
//        opcjaPasazer opcja = opcjaPasazer.idz;
//        ZdarzeniePasazer z1 = new ZdarzeniePasazer(pasazerowie[0].getGodzinaWyjscia(),
//                pasazerowie[0], opcja);
//        Zdarzenie[] zdarzenia = new Zdarzenie[symulacja.liczbaPasazerow()];
//        zdarzenia[0] = z1;
//
//        for (int i = 1; i < symulacja.liczbaPasazerow(); i++) {
//            zdarzenia[i] = new ZdarzenieTramwaj(pasazerowie[i].getGodzinaWyjscia(), tramwaje[0]);
//        }
//        KolejkaZdarzen kolejka = new KolejkaZdarzen();
//        for (int i = 0; i < zdarzenia.length; i++) {
//            kolejka.wstaw(zdarzenia[i]);
//        }
//        System.out.println("---------------------------");
//        while (!kolejka.czyPusta()) {
//            Zdarzenie z = kolejka.pobierz();
//            System.out.println(z.getCzas());
//        }

    }
}