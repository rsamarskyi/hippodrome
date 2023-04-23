import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Mock
    private Horse horse;


    @Test
    public void testExpectedExceptionHorsesListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void testExpectedExceptionMessageHorsesListIsEmpty() {
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyList()));
        assertEquals("Horses cannot be empty.", thrownException.getMessage());
    }

    @Test
    public void testGetHorses() {
        List<Horse> horses = createHorsesList(30);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void testMove() {
        List<Horse> horses = createHorsesMocksList(50, horse);

        Hippodrome hippodrome = new Hippodrome(horses);
        Mockito.doNothing().when(horse).move();

        hippodrome.move();
        verify(horse, atLeast(50)).move();

    }

    @Test
    public void testGetWinner() {
        List<Horse> horses = createHorsesList(3);

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse actualHorse = hippodrome.getWinner();

        assertEquals(horses.get(2), actualHorse);
    }

    private List<Horse> createHorsesList(int quantity) {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            horses.add(new Horse("horse" + i, 1 + i, 2 + i));
        }
        return horses;
    }

    private List<Horse> createHorsesMocksList(int quantity, Horse horse) {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            horses.add(horse);
        }
        return horses;
    }
}