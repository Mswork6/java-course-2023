package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task4Test {

    @Test
    @DisplayName("Проверка работоспособности метода")
    void testFileCreationAndContent() throws IOException {
        // Given
        String stringPath = "src/test/resources/test4.txt";
        Path path = Path.of(stringPath);

        // When
        Task4.writeToFile(stringPath);

        // Then
        assertTrue(Files.exists(path));

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, bytesRead);
            }

            assertTrue(content.toString().contains("Programming is learned by writing programs. ― Brian Kernighan"));
        }
    }
}

