package me.manishcodes.hotelapplication;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HotelApplicationEnvTest {

    @TempDir
    Path tempDir;

    private Set<String> initialSystemProperties;

    @BeforeEach
    void setUp() {
        // Store initial system properties to clean up later
        initialSystemProperties = Set.copyOf(System.getProperties().stringPropertyNames());
    }

    @AfterEach
    void tearDown() {
        // Clean up any properties that were added during the test
        System.getProperties().stringPropertyNames().stream()
                .filter(key -> !initialSystemProperties.contains(key))
                .forEach(System::clearProperty);
    }

    @Test
    void testEnvironmentVariablesLoadedFromAppEnv() throws IOException {
        // Given: Create a test app.env file with sample environment variables
        Path envFile = tempDir.resolve("app.env");
        String envContent = """
                DB_HOST=localhost
                DB_PORT=5432
                DB_NAME=hotel_db
                DB_USER=testuser
                DB_PASSWORD=testpass
                """;
        Files.writeString(envFile, envContent);

        // When: Load the .env file and set system properties
        Dotenv dotenv = Dotenv.configure()
                .directory(tempDir.toString())
                .filename("app.env")
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        // Then: Verify that all environment variables are loaded as system properties
        assertEquals("localhost", System.getProperty("DB_HOST"));
        assertEquals("5432", System.getProperty("DB_PORT"));
        assertEquals("hotel_db", System.getProperty("DB_NAME"));
        assertEquals("testuser", System.getProperty("DB_USER"));
        assertEquals("testpass", System.getProperty("DB_PASSWORD"));

        // Verify that the dotenv object contains all expected entries
        assertNotNull(dotenv.get("DB_HOST"));
        assertNotNull(dotenv.get("DB_PORT"));
        assertNotNull(dotenv.get("DB_NAME"));
        assertNotNull(dotenv.get("DB_USER"));
        assertNotNull(dotenv.get("DB_PASSWORD"));
    }

    @Test
    void testEnvironmentVariablesAreSetAsSystemProperties() throws IOException {
        // Given: Create a minimal app.env file
        Path envFile = tempDir.resolve("app.env");
        String envContent = "TEST_KEY=test_value\nANOTHER_KEY=another_value\n";
        Files.writeString(envFile, envContent);

        // When: Load and set system properties
        Dotenv dotenv = Dotenv.configure()
                .directory(tempDir.toString())
                .filename("app.env")
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        // Then: Verify system properties are correctly set
        assertEquals("test_value", System.getProperty("TEST_KEY"));
        assertEquals("another_value", System.getProperty("ANOTHER_KEY"));
    }

    @Test
    void testEmptyEnvFileDoesNotSetProperties() throws IOException {
        // Given: Create an empty app.env file
        Path envFile = tempDir.resolve("app.env");
        Files.writeString(envFile, "");

        // When: Load the empty .env file
        Dotenv dotenv = Dotenv.configure()
                .directory(tempDir.toString())
                .filename("app.env")
                .load();

        int propertyCountBefore = System.getProperties().size();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        int propertyCountAfter = System.getProperties().size();

        // Then: Verify no new properties were added
        assertEquals(propertyCountBefore, propertyCountAfter);
    }
}
