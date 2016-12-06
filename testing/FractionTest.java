class Test{
	
	@test(expected=Exception.class)
	public void testDivBy0(){
		Fraction f = new Fraction("1/0");
	}
	@Test(expected=Exception.class)
  	public void testNonFraction() {
    	new Fraction("6/4/7");
  }
	@test
	public void testNumerator(){
		Fraction f = new Fraction("7/11");
		assertEquals(f.getNumerator(),7)
	}
	@test
	public void testDenominator(){
		Fraction f = new Fraction("13/2");
		assertEquals(f.getDenominator(),2)
	}
	@test
	public void testReduce(){
		Fraction f = new Fraction("21/14");
		assertEquals(f.getNumerator(),3)
		assertEquals(f.getDenominator(),2)
	}
	@test
	public void testRatio(){
		Fraction f = new Fraction("15/3");
		assertEquals(f.getRatio(),5.0)
	}

}