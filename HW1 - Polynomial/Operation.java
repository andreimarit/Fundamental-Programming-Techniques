package functionality;

import java.math.BigInteger;

public class Operation {
	private PolynomialCollection collection = new PolynomialCollection();
	private Polynomial poly = new Polynomial();
	
	public Operation(PolynomialCollection c)
	{
		collection = c;
	}
	
	public Operation(Polynomial p)
	{
		poly = p;
	}
	
	public Polynomial addition()
	{
		Polynomial temp = new Polynomial();
		int degree, i, max, t, j, a1, a2;
		Term term = new Term();

		if(collection.getSize() > 0)
		{
			max = collection.getPolynomial(0).getPolynomialDegree();
			t = 0;
			for(i = 1; i < collection.getSize(); i++)
			{
				if(collection.getPolynomial(i).getPolynomialDegree() > max) 
				{
					max = collection.getPolynomial(i).getPolynomialDegree();
					t = i;
				}
			}

			for(i = 0; i < max; i++)
			{
				temp.addTerm(new Term(collection.getPolynomial(t).getTerm(i).getTermDegree(), collection.getPolynomial(t).getTerm(i).getCoefficient()));
			}



			for(i = 0; i < collection.getSize(); i++)
			{
				if(i != t)
					for(j = 0; j < collection.getPolynomial(i).getPolynomialDegree(); j++)
					{
						temp.getTerm(j).setCoefficient(temp.getTerm(j).getCoefficient() + collection.getPolynomial(i).getTerm(j).getCoefficient());	
					}

			}
			return temp;
		}
		else return null;

	}

	public Polynomial subtraction()
	{
		Polynomial temp = new Polynomial();
		int degree, i, max, t, j, a1, a2;
		Term term = new Term();

		if(collection.getSize() > 0)
		{
			max = collection.getPolynomial(0).getPolynomialDegree();
			for(i = 1; i < collection.getSize(); i++)
			{
				if(collection.getPolynomial(i).getPolynomialDegree() > max) 
				{
					max = collection.getPolynomial(i).getPolynomialDegree();					
				}
			}
			for(i = 0; i < max; i++)
			{
				if(i < collection.getPolynomial(0).getPolynomialDegree())
				{
					temp.addTerm(new Term(collection.getPolynomial(0).getTerm(i).getTermDegree(), collection.getPolynomial(0).getTerm(i).getCoefficient()));
				}
				else
				{
					temp.addTerm(new Term(0, 0));
				}
			}



			for(i = 1; i < collection.getSize(); i++)
			{
				for(j = 0; j < collection.getPolynomial(i).getPolynomialDegree(); j++)
				{
					temp.getTerm(j).setCoefficient(temp.getTerm(j).getCoefficient() - collection.getPolynomial(i).getTerm(j).getCoefficient());
					temp.getTerm(j).setTermDegree(j);
				}

			}
			return temp;
		}
		else return null;

	}


	private Polynomial multiply2(Polynomial a, Polynomial b)
	{
		Polynomial temp = new Polynomial();
		for(int i = 0; i < a.getPolynomialDegree() + b.getPolynomialDegree() - 1; i++)
		{
			temp.addTerm(new Term(i, 0));
		}
		for(int i = 0; i < a.getPolynomialDegree(); i++)
		{
			for(int j = 0; j < b.getPolynomialDegree(); j++)
			{
				temp.getTerm(i + j).setCoefficient(temp.getTerm(i + j).getCoefficient() + a.getTerm(i).getCoefficient() * b.getTerm(j).getCoefficient());
			}
		}
		return temp;
	}

	public Polynomial multiplication()
	{
		Polynomial temp = new Polynomial();
		if(collection.getSize() > 1)
		{
			temp = multiply2(collection.getPolynomial(0), collection.getPolynomial(1));
			for(int i = 2; i < collection.getSize(); i++)
			{
				temp = multiply2(temp, collection.getPolynomial(i));
			}
		}
		else
			if(collection.getSize() == 1)
			{
				temp = collection.getPolynomial(0);
			}
		return temp;

	}
	private Polynomial subtract2(Polynomial a, Polynomial b)
	{
		Polynomial temp = new Polynomial();
		Polynomial other;
		if(a.getPolynomialDegree() >= b.getPolynomialDegree())
		{
			temp.copy(a);
			other = b;
		}
		else
		{
			temp.copy(b);
			other = a;
		}
		
		for(int i = 0; i < temp.getPolynomialDegree(); i++)
		{
			if(i < other.getPolynomialDegree())
				temp.getTerm(i).setCoefficient(temp.getTerm(i).getCoefficient() - other.getTerm(i).getCoefficient());
			
		}
		return temp;
			
	}

	public PolynomialCollection division()
	{
		if(collection.getSize() < 2)
		{
			return null;
		}


		if(collection.getPolynomial(0).getPolynomialDegree() < collection.getPolynomial(1).getPolynomialDegree())
		{
			return null;
		}
		
		if(collection.getPolynomial(0).getPolynomialDegree() == 0 && collection.getPolynomial(0).getTerm(0).getCoefficient() == 0)
		{
			return null;
		}
		
		Polynomial a = new Polynomial();
		a.copy(collection.getPolynomial(0));
		Polynomial b = new Polynomial();
		b.copy(collection.getPolynomial(1));
		Polynomial q = new Polynomial();
		int k = 0;
		
		while(a.getFirstTerm() >= b.getFirstTerm())
		{
			
			Polynomial b1 = b.shift(a.getFirstTerm() - b.getFirstTerm());
			Term t = new Term(a.getFirstTerm() - b.getFirstTerm(), a.getTerm(a.getFirstTerm() - 1).getCoefficient() / b1.getTerm(b1.getFirstTerm() - 1).getCoefficient());
			q.addTerm(t);
			Polynomial q1 = new Polynomial();
			q1.addTerm(t);
			b1 = multiply2(b1, q1);
			a = subtract2(a, b1);
			k = a.getFirstTerm();
		
		}
		q.completePolynomial();
		q.arrangePolynomial();
		a.completePolynomial();
		a.arrangePolynomial();
		PolynomialCollection t = new PolynomialCollection();
		t.addPolynomial(q);
		t.addPolynomial(a);
		return t;

	}
	
	public Polynomial derivative()
	{
		Polynomial temp = new Polynomial();

		temp.addTerm(new Term(0, 0));
		for(int i = 1; i < poly.getPolynomialDegree(); i++)
		{
			temp.addTerm(new Term(i - 1, i * poly.getTerm(i).getCoefficient()));
		}

		return temp;
	}
	
	public Polynomial integral()
	{
		Polynomial temp = new Polynomial();
		
		for(int i = 0; i < poly.getPolynomialDegree(); i++)
		{
			temp.addTerm(new Term(i + 1, (1.0 / (i+1)) * poly.getTerm(i).getCoefficient()));
		}
		return temp;
	}

	public String compute(int x)
	{
		BigInteger result = new BigInteger("0");
		for(int i = 0; i < poly.getPolynomialDegree(); i++)
		{
			result = result.add(new BigInteger(Integer.valueOf((int)Math.pow(x, i) * (int)poly.getTerm(i).getCoefficient()).toString()));
		}
		return result.toString();
	}
}
