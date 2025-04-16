package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final static String fileName = "gamesProperties";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("================ Choose an option ================");
            System.out.println("1) Create a new game");
            System.out.println("2) Search for similar games");
            System.out.println("3) Show all games");
            System.out.println("4) Save games");
            System.out.println("5) Load games");
            System.out.println("6) Add tags");
            System.out.println("7) Remove tags");
            System.out.println("8) Set currency");
            System.out.println("0) Exit");
            System.out.println("==================================================");
            var option = sc.next();
            if(!option.matches("\\d+") || Integer.parseInt(option) < 0 || Integer.parseInt(option) > 10) {
                System.out.println("[ERROR] Invalid option");
                continue;
            }
            switch (Integer.parseInt(option)) {
                case 1 -> createGame();
                case 2 -> {
                    System.out.println("Enter a tag to search for similar games: ");
                    var tag = sc.next();
                    DigitalGame.findWithSimilarTag(tag).forEach(System.out::println);
                }
                case 3 -> DigitalGame.show();
                case 4 -> saveGames();
                case 5 -> loadGames();
                case 6 -> addTags();
                case 7 -> removeTags();
                case 8 -> {
                    try {
                        System.out.println("Enter currency code: ");
                        var currency = sc.next();
                        DigitalGame.setCurrency(currency);
                        System.out.println("== Global currency successfully set ==");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 0 -> System.exit(0);
            }
        }
    }
    private static void createGame(){
        Publisher p1 = new Publisher("CD Project Red", "Big Polish studio");
        Publisher p2 = new Publisher("TVGS", "One person, that's creating indie games");
        Publisher p3 = new Publisher("Electronic Arts", "Massive American game creator");

        try {
            new DigitalGame("The Witcher 3", p1, "Game about some witcher dudes", 40d, LocalDate.of(2015, 5, 13), "Action, Adventure, Magic");
            new DigitalGame("Schedule I", p2, "Sell drugs and become the biggest kingpin ever", 29.99, LocalDate.of(2025, 3, 31), "Action, Adventure, Simulator", "Early Access");
            new DigitalGame("Need for Speed Heat", p3, "Break rules, while driving awesome cars", 50.99, LocalDate.of(2019, 10, 8), "Simulator, Racing, Action");

            System.out.println("== Games successfully created ==");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveGames(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            DigitalGame.saveExtent(oos);
            oos.close();
            System.out.println("== Games successfully saved ==");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void loadGames(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            DigitalGame.loadExtent(ois);
            ois.close();
            System.out.println("== Games successfully loaded ==");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeTags(){
        try {
            DigitalGame.getAllGames().getFirst().removeThemeTag("Action");
            DigitalGame.getAllGames().getFirst().removeThemeTag("Magic");
            DigitalGame.getAllGames().getFirst().removeThemeTag("Adventure");

            System.out.println("== Tags successfully removed ==");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

    }

    private static void addTags(){
        try {
            DigitalGame.getAllGames().getFirst().addThemeTag("Third Person");
            DigitalGame.getAllGames().getFirst().addThemeTag("RPG");
            DigitalGame.getAllGames().getFirst().addThemeTag("Adventure");

            System.out.println("== Tags successfully added ==");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}