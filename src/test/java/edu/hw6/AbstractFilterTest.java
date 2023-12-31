package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractFilterTest {
    @Test
    @DisplayName("Проверка фильтрации png файлов")
    void checkPngFilter() {
        // given
        Path dir = Paths.get("src/test/resources/");
        List<Path> filteredPaths = new ArrayList<>();

        AbstractFilter filter = AbstractFilter.REGULAR_FILE
            .and(AbstractFilter.WRITABLE)
            .and(AbstractFilter.magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G'))
            .and(AbstractFilter.largerThan(100_000))
            .and(AbstractFilter.globMatches("*.png"))
            .and(AbstractFilter.regexContains("[-image]"));

        // when
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(filteredPaths::add);
        } catch (IOException e) {
            System.err.println("Ошибка во время фильтрации: " + e.getMessage());
        }

        // then
        assertEquals(filteredPaths.size(), 8);
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image.png")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image1.png")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image2.png")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image3.png")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image4.png")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/Cyberpunk-image5.png")));
//        assertTrue(filteredPaths.contains(Path.of("photomode_25072023_211629")));
//        assertTrue(filteredPaths.contains(Path.of("photomode_25072023_211822")));
        // я хз почему но гит не хочет видеть эти файлы, я даже не знаю как это проверить особо :-(
    }

    @Test
    @DisplayName("Проверка фильтрации png файлов")
    void checkPngNonExistFilter() {
        // given
        Path dir = Paths.get("src/test/resources/");
        List<Path> filteredPaths = new ArrayList<>();

        AbstractFilter filter = AbstractFilter.REGULAR_FILE
            .and(AbstractFilter.WRITABLE)
            .and(AbstractFilter.magicNumber((byte) 0x89, (byte) 'L', (byte) 'M', (byte) 'B'))
            .and(AbstractFilter.largerThan(10_000))
            .and(AbstractFilter.globMatches("*.png"))
            .and(AbstractFilter.regexContains("NonExistingFiles"));

        // when
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(filteredPaths::add);
        } catch (IOException e) {
            System.err.println("Ошибка во время фильтрации: " + e.getMessage());
        }

        // then
        assertEquals(filteredPaths.size(), 0);
    }

    @Test
    @DisplayName("Проверка фильтрации txt файлов")
    void checkTxtFilter() {
        // given
        Path dir = Paths.get("src/test/resources/");
        List<Path> filteredPaths = new ArrayList<>();

        AbstractFilter filter = AbstractFilter.REGULAR_FILE
            .and(AbstractFilter.READABLE)
            .and(AbstractFilter.largerThan(-1))
            .and(AbstractFilter.globMatches("*.txt"))
            .and(AbstractFilter.regexContains("test_diskmap"));

        // when
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(filteredPaths::add);
        } catch (IOException e) {
            System.err.println("Ошибка во время фильтрации: " + e.getMessage());
        }

        // then
        assertEquals(filteredPaths.size(), 6);
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap.txt")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap1.txt")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap2.txt")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap3.txt")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap4.txt")));
        assertTrue(filteredPaths.contains(Path.of("src/test/resources/test_diskmap5.txt")));
    }
}
