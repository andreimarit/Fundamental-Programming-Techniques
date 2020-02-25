package functionality;
import java.util.ArrayList;


public class PolynomialCollection {


	private ArrayList<Polynomial> collection = new ArrayList<Polynomial>();


	public PolynomialCollection()
	{

	}

	public void addPolynomial(Polynomial p)
	{
		collection.add(p);

	}

	public int getCollectionSize()
	{
		return collection.size();
	}

	public void updatePolynomial(int i, Polynomial p)
	{
		collection.set(i, p);
	}




	public int getSize()
	{
		return collection.size();
	}

	public Polynomial getPolynomial(int i)
	{
		return collection.get(i);
	}

	public void clearCollection()
	{
		collection.removeAll(collection);
	}
}
