package main;
import static api.Tools.*;
import models.members.Member;
import models.members.MusicMember;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void runTests() {
        validatePhones();
        validateNumericStrings();
        validateExpectedInputs();
        checkMembershipCard();
    }

    public void validatePhones(){
        assertEquals(validatePhone("899595657"), true);
        assertEquals(validatePhone("899595657erR"), false);
        assertEquals(validatePhone("899595GFD"), false);
        assertEquals(validatePhone("8 99 595657"), false);
        assertEquals(validatePhone("111222333"), true);
        assertEquals(validatePhone("1234567810"), false);
    }

    public void validateNumericStrings(){
        assertTrue(stringIsNumeric("1234567810"));
        assertFalse(stringIsNumeric("1234567 10"));
        assertTrue(stringIsNumeric("10"));
        assertTrue(stringIsNumeric("1"));
        assertFalse(stringIsNumeric(""));
        assertFalse(stringIsNumeric(" "));
        assertFalse(stringIsNumeric("Felipe20"));
        assertFalse(stringIsNumeric("jcvdjclnal"));
        assertTrue(stringIsNumeric("2.6"));
        assertFalse(stringIsNumeric("2.4.810"));
    }

    public void validateExpectedInputs(){
        assertEquals(inputIsExpected("a", "a", "b"), true);
        assertEquals(inputIsExpected("d", "a", "b"), false);
        assertEquals(inputIsExpected("DVD", "DVD", "CD", "BLUE_RAY"), true);
        assertEquals(inputIsExpected("CD", "DVD", "CD", "BLUE_RAY"), true);
        assertEquals(inputIsExpected("blue_ray", "DVD", "CD", "BLUE_RAY"), true);
    }

    public void checkMembershipCard(){
        Member member = new MusicMember("member", "111111111");
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(0));
        member.updatePoints();
        member.updatePoints();
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(20));
        IntStream.range(0,8).forEach(i->member.updatePoints());
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(100));
        member.updatePoints();
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(0));

        IntStream.range(0,8).forEach(i->member.updatePoints());
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(80));

        member.penalizeMember(5.0);

        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(70));

        member.penalizeMember(3.0);
        member.penalizeMember(2.0);
        member.penalizeMember(1.6);

        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(70));

        member.penalizeMember(10.0);
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(35));

        member.penalizeMember(10.0);
        assertEquals(member.getMembershipCard().getPointBalance(), Integer.valueOf(0));

    }
}
