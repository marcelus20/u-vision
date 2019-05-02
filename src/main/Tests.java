package main;
import api.Tools;
import models.members.Member;
import models.members.MusicMember;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void runTests() {
        validatePhone();
        validateNumericStrings();
        validateExpectedInputs();
        checkMembershipCard();
    }

    public void validatePhone(){
        assertEquals(Tools.validatePhone("899595657"), true);
        assertEquals(Tools.validatePhone("899595657erR"), false);
        assertEquals(Tools.validatePhone("899595GFD"), false);
        assertEquals(Tools.validatePhone("8 99 595657"), false);
        assertEquals(Tools.validatePhone("111222333"), true);
        assertEquals(Tools.validatePhone("1234567810"), false);
    }

    public void validateNumericStrings(){
        assertTrue(Tools.stringIsNumeric("1234567810"));
        assertFalse(Tools.stringIsNumeric("1234567 10"));
        assertTrue(Tools.stringIsNumeric("10"));
        assertTrue(Tools.stringIsNumeric("1"));
        assertFalse(Tools.stringIsNumeric(""));
        assertFalse(Tools.stringIsNumeric(" "));
        assertFalse(Tools.stringIsNumeric("Felipe20"));
        assertFalse(Tools.stringIsNumeric("jcvdjclnal"));
        assertTrue(Tools.stringIsNumeric("2.6"));
        assertFalse(Tools.stringIsNumeric("2.4.810"));
    }

    public void validateExpectedInputs(){
        assertEquals(Tools.inputIsExpected("a", "a", "b"), true);
        assertEquals(Tools.inputIsExpected("d", "a", "b"), false);
        assertEquals(Tools.inputIsExpected("DVD", "DVD", "CD", "BLUE_RAY"), true);
        assertEquals(Tools.inputIsExpected("CD", "DVD", "CD", "BLUE_RAY"), true);
        assertEquals(Tools.inputIsExpected("blue_ray", "DVD", "CD", "BLUE_RAY"), true);
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
