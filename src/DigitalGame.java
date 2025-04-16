package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class DigitalGame implements Serializable {
    //Attributes
    private static List<DigitalGame> extent = new ArrayList<>();
    private String title;
    private Publisher publisher;
    private String description;
    private Double price;
    private LocalDate releaseDate;
    private List<String> themeTags = new ArrayList<>();
    private String publisherNote = null;
    private static String defaultCurrency = "EUR";
    public Long gameAge(){
        return Period.between(releaseDate, LocalDate.now()).toTotalMonths();
    }

    public DigitalGame(String title, Publisher publisher, String description, Double price, LocalDate releaseDate, String themeTags) {
        setTitle(title);
        setPublisher(publisher);
        setDescription(description);
        setPrice(price);
        setReleaseDate(releaseDate);
        setThemeTags(themeTags);

        extent.add(this);
    }

    public DigitalGame(String title, Publisher publisher, String description, Double price, LocalDate releaseDate, String themeTags, String publisherNote) {
        this(title, publisher, description, price, releaseDate, themeTags);
        setPublisherNote(publisherNote);
    }

    public static void saveExtent(ObjectOutputStream oos) throws IOException {
        oos.writeObject(extent);
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        extent = (List<DigitalGame>) ois.readObject();
    }

    public static List<DigitalGame> findWithSimilarTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(extent).stream().filter(game -> game.getThemeTags().contains(tag)).toList();
    }

    public static void show(){
        for(DigitalGame game : Collections.unmodifiableList(extent)){
            System.out.println(game);
        }
    }

    public String getTitle() {
        return title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<String> getThemeTags() {
        return Collections.unmodifiableList(themeTags);
    }

    public String getPublisherNote() {
        return publisherNote;
    }

    public String getDefaultCurrency(){
        return defaultCurrency;
    }

    public static List<DigitalGame> getAllGames() {
        return Collections.unmodifiableList(extent);
    }


    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Title cannot be null or empty");
        }

        this.title = title;
    }

    public void setPublisher(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("[ERROR] Publisher cannot be null or empty");
        }

        this.publisher = publisher;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Description cannot be null or empty");
        }

        this.description = description;
    }

    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("[ERROR] Price cannot be null or less than zero");
        }
        if(Math.round(price * 100) != price * 100){
            throw new IllegalArgumentException("[ERROR] Price cannot have more than two decimal places");
        }
        this.price = price;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null || releaseDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("[ERROR] Date cannot be null");
        }

        this.releaseDate = releaseDate;
    }

    private void setThemeTags(String themeTags) {
        if (themeTags == null || themeTags.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Theme tags cannot be null or empty");
        }

        for (String tag : themeTags.split(",")) {
            this.themeTags.add(tag.trim());
        }
    }

    public void addThemeTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Theme tag cannot be null or empty");
        }
        if (this.themeTags.contains(tag)) {
            throw new IllegalArgumentException("[ERROR] Tag already exists");
        }

        this.themeTags.add(tag);
    }

    public void removeThemeTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Theme tag cannot be null or empty");
        }
        if (!this.themeTags.contains(tag)) {
            throw new IllegalArgumentException("[ERROR] Tag does not exist");
        }
        if (this.themeTags.size() == 1) {
            throw new IllegalArgumentException("[ERROR] At least one theme tag is required");
        }

        this.themeTags.remove(themeTags.indexOf(tag));
    }


    public void setPublisherNote(String publisherNote) {
        if (publisherNote == null || publisherNote.isEmpty()) {
            this.publisherNote = null;
        } else if (publisherNote.isBlank()) {
            throw new IllegalArgumentException("[ERROR] Publisher note cannot be set as whitespace");
        } else {
            this.publisherNote = publisherNote;
        }

    }

    public static void setCurrency(String currency){
        if (Currency.getAvailableCurrencies().stream().noneMatch(c -> c.getCurrencyCode().equals(currency))) {
            throw new IllegalArgumentException("[ERROR] Invalid currency");
        }

        defaultCurrency = currency;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() +
                "\nPublisher:\n" + getPublisher() +
                "\nDescription: " + getDescription() +
                "\nPrice: " + String.format("%.2f", getPrice()) + " " + defaultCurrency +
                "\nRelease Date: " + getReleaseDate() +
                "\nAge: " + gameAge() + " months old" +
                "\nTags: " + getThemeTags().toString() +
                (getPublisherNote() != null ? "\nNote: " + getPublisherNote(): "") +
                "\n";
    }
}