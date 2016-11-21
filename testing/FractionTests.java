/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

public class FractionTests {

  @Test(expected=Exception.class)
  public void testIllegalFractionCreation() {
    new Fraction("1/0");
  }

  @Test(expected=Exception.class)
  public void testIllegalFractionCreation1() {
    new Fraction("3/4/5/6/7");
  }

  @Test public void testFractionCreation() {
    Fraction f = new Fraction("1/2");
  }

  @Test public void testNumerator() {

    // 13 & 17 are prime. Hence the fraction will not reduce
    Fraction f = new Fraction("13/17");
    assertEquals(f.getNumerator(), 13);

    // Fraction gets reduced
    f = new Fraction("2/4");
    assertEquals(f.getNumerator(), 1);

    f = new Fraction("4/10");
    assertEquals(f.getNumerator(), 2);

  }


  @Test public void testDenominator() {

    // 13 & 17 are prime. Hence the fraction will not reduce
    Fraction f = new Fraction("13/17");
    assertEquals(f.getDenominator(), 17);

    // Fraction gets reduced
    f = new Fraction("2/4");
    assertEquals(f.getDenominator(), 2);

    f = new Fraction("4/10");
    assertEquals(f.getDenominator(), 5);

  }

}

