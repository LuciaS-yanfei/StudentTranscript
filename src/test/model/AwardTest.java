package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//represents tests for award
public class AwardTest {

    private Award testAward;

    @BeforeEach
    public void setup() {
        testAward = new Award("Iwon!", 2023, 10000);
    }

    @Test
    public void testConstructor() {
        assertEquals("Iwon!", testAward.getAwardName());
        assertEquals(2023, testAward.getYear());
        assertEquals(10000, testAward.getAwardMoney());

    }

    @Test
    public void testSetters() {
        testAward.setAwardName("IwonAgain");
        testAward.setDate(2023);
        testAward.setPrize(100);

        assertEquals("IwonAgain", testAward.getAwardName());
        assertEquals(2023, testAward.getYear());
        assertEquals(100, testAward.getAwardMoney());

    }

}