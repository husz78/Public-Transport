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
            p.idzNaPrzystanek();
        }
        for (Przystanek p : przystanki) {
            System.out.println("Na przystanku " + p.getNazwa() + " jest " + p.getLiczbaOsob() + " pasazerow.");
        }
        for (Pasazer p : pasazerowie) {
            boolean czyWszedl = p.wejdzDoTramwaju(tramwaje[0]);
            if (czyWszedl) {
                System.out.println("Pasazer " + p.getNr() + " wsiadl do tramwaju o numerze " +
                        tramwaje[0].getNrBoczny() + " na przystanku " + tramwaje[0].getNastepnyPrzystanek() +
                        " i wybral przystanek " + p.getWybranyPrzystanek(tramwaje[0]).getNazwa());
            }
            else System.out.println("Pasazer " + p.getNr() + " nie wsiadl do tramwaju.");
        }
        for (Pasazer p : pasazerowie) {
            boolean czyWyszedl = p.wyjdzZTramwaju(tramwaje[0]);
            if (czyWyszedl) {
                System.out.println("Pasazer " + p.getNr() + " wysiadl na przystanek " +
                        p.getWybranyPrzystanek(tramwaje[0]));
            }
            else {
                System.out.println("Pasazer " + p.getNr() + " nie wysiadl na przystanku " +
                        p.getWybranyPrzystanek(tramwaje[0]) + " bo nie bylo miejsca na nim");
            }
        }
        for (Przystanek p : przystanki) {
            System.out.println("Na przystanku " + p.getNazwa() + " jest " + p.getLiczbaOsob() + " pasazerow.");
        }
        for (Tramwaj t : tramwaje) {
            System.out.println(t.getPoprzedniPrzystanek());
        }
        for (Tramwaj t : tramwaje) {
            System.out.println(t.getGodzinaStartu());
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