package functionality;
import java.math.BigInteger;
import java.util.ArrayList;


public class Polynomial {

	private ArrayList<Term> poly = new ArrayList<Term>();
	
	public Polynomial()
	{

	}

	public void addTerm(Term t)
	{
		poly.add(t);		
	}

	public int findTerm(Term t)
	{
		return poly.indexOf(t);
	}

	public int getPolynomialDegree()
	{
		return poly.size();
	}

	public String printPolynomial()
	{
		String s="";
		int z = 0, a = 0;
		for(int i = poly.size() - 1; i >= 0; i--)
		{
			double co = poly.get(i).getCoefficient();
			double c10 = co * 10.0;
			int ci = (int)c10;
			int mod = ci % 10;
			String c;
			if(mod == 0)
				c = String.format("%.0f", co);
			else
				c = Double.toString(co); 
			
			if(poly.get(i).getCoefficient() > 0)
			{
				if(z == 1)
				{
					//System.out.print("+" + c + "X^" + poly.get(i).getTermDegree());
					if(poly.get(i).getTermDegree() == 0)
						if(i == poly.size() - 1)
							s = s + c;
						else
							s = s + "+" + c;
					else
						if(poly.get(i).getTermDegree() == 1)
							s = s + "+" + c + "X";
						else
							s = s + "+" + c + "X<sup>" + poly.get(i).getTermDegree() + "</sup>";
					a++;
				}
				else
				{
					//System.out.print(poly.get(i).getCoefficient() + "X^" + poly.get(i).getTermDegree());
					if(poly.get(i).getTermDegree() == 0)
						if(i == poly.size() - 1)
							s = s + c;
						else
							s = s + "+" + c;
					else
						if(poly.get(i).getTermDegree() == 1)
							s = s + "+" + c + "X";
						else
							s = s + c + "X<sup>" + poly.get(i).getTermDegree() + "</sup>";
					z = 1;
					a++;
				}
			}
			else if(poly.get(i).getCoefficient() < 0)
			{
				//System.out.print(poly.get(i).getCoefficient() + "X^" + poly.get(i).getTermDegree());
				if(poly.get(i).getTermDegree() == 0)
					s = s + c;
				else
					if(poly.get(i).getTermDegree() == 1)
						s = s + c +"X";
					else
						s = s + c + "X<sup>" + poly.get(i).getTermDegree() + "</sup>";
				a++;
			}
		}
		if(a == 0)
			s = s + "0";
		
		return s;
	}

	public Term getTerm(int i)
	{
		return poly.get(i);
	}


	public void resetTerms()
	{
		for(int i = 0; i < poly.size(); i++)
		{
			poly.get(i).setCoefficient(0);
		}		
	}
	
	public void copy(Polynomial p)
	{
		for(int i = 0; i < p.getPolynomialDegree(); i++)
		{
			poly.add(new Term(i, p.getTerm(i).getCoefficient()));
		}
	}

	public Term termExists(int exp)
	{
		for(int i = 0; i < poly.size(); i++)
		{
			if(exp == poly.get(i).getTermDegree())
			{
				return poly.get(i);
			}
		}
		return null;
	}

	public void completePolynomial()
	{
		int i, j, max = poly.get(0).getTermDegree(), k = 0;
		for(i = 1; i < poly.size(); i++)
		{
			if(poly.get(i).getTermDegree() > max) max = poly.get(i).getTermDegree();
		}
		for(i = 0; i < max; i++)
		{
			k = 0;
			for(j = 0; j < poly.size(); j++)
			{
				if(poly.get(j).getTermDegree() == i)
				{
					k = 1;
					break;
				}
			}

			if(k == 0)
			{
				poly.add(new Term(i, 0));
			}
		}
	}

	public void arrangePolynomial()
	{
		int k = 0;
		Term temp;
		for(int i = 0; i < poly.size(); i++)
		{
			if(poly.get(i).getTermDegree() != i)
			{
				for(int j = 0; j < poly.size(); j++)
				{
					if(poly.get(j).getTermDegree() == i) k = j;
				}

				temp = poly.get(k);
				poly.set(k, poly.get(i));
				poly.set(i, temp);				
			}
		}
	}

		
	public Polynomial shift(int x)
	{
		Polynomial temp = new Polynomial();
		for(int i = x; i < poly.size() + x; i++)
		{
			temp.addTerm(new Term(i, poly.get(i - x).getCoefficient()));
		}
		temp.completePolynomial();
		temp.arrangePolynomial();
		return temp;
	}
	
	public int getFirstTerm()
	{
		for(int i = poly.size() - 1; i >= 0; i--)
		{
			if(poly.get(i).getCoefficient() != 0) return i + 1;
		}
		return -1;
	}

}