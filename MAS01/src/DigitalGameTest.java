package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DigitalGameTest {
    private DigitalGame game;

    @Test
    void testConstructor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("", "", "", 0d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Title cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame(null, "", "123", 10d, LocalDate.now(), Arrays.asList("Tag")));
        assertEquals("[ERROR] Title cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "", "", 0d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Publisher cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", null, "", 0d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Publisher cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "", 0d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Description cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 0d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Price cannot be null or less than zero", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, null, Arrays.asList("")));
        assertEquals("[ERROR] Date cannot be null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("")));
        assertEquals("[ERROR] Theme tag cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), null));
        assertEquals("[ERROR] Theme tags cannot be null or empty", exception.getMessage());
    }

    @BeforeEach
    void setUp() {
        game = new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2"));
    }

    @Test
    void testPrice(){
        assertEquals(10d, game.getPrice());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.setPrice(-100d));
        assertEquals("[ERROR] Price cannot be null or less than zero", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", -10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Price cannot be null or less than zero", exception.getMessage());
    }

    @Test
    void testTitle(){
        assertEquals("Title", game.getTitle());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.setTitle(""));
        assertEquals("[ERROR] Title cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Title cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame(null, "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Title cannot be null or empty", exception.getMessage());
    }

    @Test
    void testPublisher(){
        assertEquals("Publisher", game.getPublisher());

//         exception = assertThrows(IllegalArgumentException.class, () -> game.setPublisher(""));
//        assertEquals("[ERROR] Publisher cannot be null or empty", exception.getMessage());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "", "Description", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Publisher cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", null, "Description", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Publisher cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDescription(){
        assertEquals("Description", game.getDescription());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.setDescription(""));
        assertEquals("[ERROR] Description cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "   ", 10d, LocalDate.now(), Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Description cannot be null or empty", exception.getMessage());
    }

    @Test
    void testReleaseDate(){
        assertEquals(LocalDate.now(), game.getReleaseDate());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.setReleaseDate(null));
        assertEquals("[ERROR] Date cannot be null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, null, Arrays.asList("Tag1", "Tag2")));
        assertEquals("[ERROR] Date cannot be null", exception.getMessage());
    }

    @Test
    void testThemeTags(){
        assertEquals(List.of("Tag1", "Tag2"), game.getThemeTags());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("   ")));
        assertEquals("[ERROR] Theme tag cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), null));
        assertEquals("[ERROR] Theme tags cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList("Tag", null)));
        assertEquals("[ERROR] Theme tag cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new DigitalGame("Title", "Publisher", "Description", 10d, LocalDate.now(), Arrays.asList((String) null)));
        assertEquals("[ERROR] Theme tag cannot be null or empty", exception.getMessage());
    }

    @Test
    void testRemoveThemeTag(){
        game.removeThemeTag("Tag1");
        assertEquals(List.of("Tag2"), game.getThemeTags());

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> game.removeThemeTag("Tag2"));
        assertEquals("[ERROR] At least one theme tag is required", exception.getMessage());

        exception =  assertThrows(IllegalArgumentException.class, () -> game.removeThemeTag("Tag3"));
        assertEquals("[ERROR] Tag does not exist", exception.getMessage());
    }

    @Test
    void testAddThemeTag(){
        game.addThemeTag("Tag3");
        assertEquals(List.of("Tag1", "Tag2", "Tag3"), game.getThemeTags());

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> game.addThemeTag("Tag1"));
        assertEquals("[ERROR] Tag already exists", exception.getMessage());
    }

    @Test
    void testPublisherNote(){
        assertNull(game.getPublisherNote());
        game.setPublisherNote("Note");
        assertEquals("Note", game.getPublisherNote());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.setPublisherNote("   "));
        assertEquals("[ERROR] Publisher note cannot be set as whitespace", exception.getMessage());

        game.setPublisherNote("");
        assertNull(game.getPublisherNote());

        game.setPublisherNote(null);
        assertNull(game.getPublisherNote());
    }

    @Test
    void testSetCurrency(){
        assertEquals("EUR", game.getDefaultCurrency());

        DigitalGame.setCurrency("PLN");
        assertEquals("PLN", game.getDefaultCurrency());

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> DigitalGame.setCurrency(""));
        assertEquals("[ERROR] Invalid currency", exception.getMessage());
    }

    @Test
    void testToString(){
        String expected =
                "Title: Title"+
                "\nPublisher: Publisher" +
                "\nDescription: Description" +
                "\nPrice: 10,00 EUR" +
                "\nRelease Date: " + LocalDate.now() +
                "\nAge: 0 months old" +
                "\nTags: [Tag1, Tag2]" +
                "\n";
        assertEquals(expected, game.toString());
    }
}
