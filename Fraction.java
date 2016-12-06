 /*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

class Fraction implements Comparable <Fraction> {

  private long numerator;
  private long denominator;

  public Fraction(String fractionStr) {

    String[] split = fractionStr.split("/");
    if (split.length != 2)
      throw new IllegalArgumentException("Fraction needs to be of the form: 'a/b'");

    this.numerator = Long.valueOf(split[0]);
    this.denominator = Long.valueOf(split[1]);

    if (this.denominator == 0) 
      throw new IllegalArgumentException("Fraction denominator cannot be zero");
    
    // Reducing the fraction will reduce the risk of overflow
    reduce();

  }

  public double getRatio() {
    return (double)getNumerator() / ((double)getDenominator());
  }

  public long getNumerator() {
    return numerator;
  }
  
  public long getDenominator() {
    return denominator;
  }

  // Get greatest common divisor
  private long gcd(long a, long b) {
    if (b == 0) return a;
    return gcd(b, a % b);
  }

  // Reduce fraction if you can
  private void reduce() {
    long gcf = gcd(numerator, denominator);
    numerator /= gcf;
    denominator /= gcf;
  }

  // Compare two fractions without getting their decimal representations.
  // Recall that one fraction a/b is less than c/d iff ad < bc and b != 0 and d != 0 of course..
  @Override public int compareTo(Fraction other) {
    long ad = numerator * other.getDenominator();
    long bc = other.getNumerator() * denominator;
    return Long.compare(ad, bc); 
  }

  @Override public String toString() {
    return getNumerator() + "/" + getDenominator();
  }

}
