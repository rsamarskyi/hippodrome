import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    public void testExpectedExceptionFirstParameterIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void testExpectedExceptionSecondParameterIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("null", -1, 1));
    }

    @Test
    public void testExpectedExceptionThirdParameterIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("null", 1, -1));
    }

    @ParameterizedTest
    @MethodSource("provideInput")
    public void testExpectedExceptionFirstParameterIsEmpty(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(input, 1, 1));
    }

    @Test
    public void testExpectedExceptionMessageFirstParameterIsNull() {
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", thrownException.getMessage());
    }

    @Test
    public void testExpectedExceptionMessageFirstParameterIsBlank() {
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> new Horse("", 1, 1));
        assertEquals("Name cannot be blank.", thrownException.getMessage());
    }

    @Test
    public void testExpectedExceptionMessageSecondParameterIsNegative() {
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> new Horse("null", -1, 1));
        assertEquals("Speed cannot be negative.", thrownException.getMessage());
    }

    @Test
    public void testExpectedExceptionMessageThirdParameterIsNegative() {
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> new Horse("null", 1, -1));
        assertEquals("Distance cannot be negative.", thrownException.getMessage());
    }

    @Test
    public void testGetName() {
        String name = "name";
        Horse horse = new Horse(name, 1, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    public void testGetSpeed() {
        double speed = 2;
        Horse horse = new Horse("name", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void testGetDistance() {
        double distance = 2;
        Horse horse = new Horse("name", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void testGetDistanceWhenDistanceIsNull() {
        double expected = 0;
        Horse horse = new Horse("name", 1);
        assertEquals(expected, horse.getDistance());
    }

    @Test
    public void testGetRandomDoubleWasCalled() {
        Horse horse = new Horse("name", 2, 3);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(4.0);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @MethodSource("provideInputNumber")
    public void testRandomDoubleWasUsed(Double input, Double expected) {
        Horse horse = new Horse("name", 2, 3);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(input);

            horse.move();

            assertEquals(expected, horse.getDistance());
        }
    }

    private static Stream<Arguments> provideInputNumber() {
        return Stream.of(
                Arguments.of(0.6, 4.2),
                Arguments.of(0.5, 4.0),
                Arguments.of(0.4, 3.8)
        );
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(
                Arguments.of(" "),
                Arguments.of(""),
                Arguments.of("   ")
        );
    }
}