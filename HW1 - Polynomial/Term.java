package functionality;

public class Term {
	private int termDegree;
	private double coefficient;
	
	public Term(int t, double c)
	{
		this.termDegree = t;
		this.coefficient = c;
	}
	
	public Term()
	{
		this(0, 0);
	}
	
	public int getTermDegree()
	{
		return this.termDegree;
	}
	
	public double getCoefficient()
	{
		return this.coefficient;
	}
	public void setTermDegree(int d)
	{
		this.termDegree = d;
	}
	
	public void setCoefficient(double c)
	{
		this.coefficient = c;
	}
}
