import java.util.NoSuchElementException;

public class ScoreIterator implements ScoreIteratorADT {

	private ScoreList myList;
	private int curPos;
	private String catergory;
	private int[] indexArray;
	int catSize;
	
	public ScoreIterator (ScoreList myList, String catergory)
	{
		this.myList = myList;
		this.catergory = catergory;
		curPos = 0;
		indexArray = new int[myList.size()];
		catSize = 0;
		
		for (int i = 0; i < myList.size(); i++)
		{
			if (myList.get(i).getCategory().toLowerCase().equals(catergory))
			{
				
				indexArray[catSize] = i;
				catSize++;
			}
		}
		
	}           
	
	@Override
	public Score next() {
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}
		return myList.get(indexArray[curPos++]);
	}

	@Override
	public boolean hasNext() {
		return curPos < catSize;	
	}

}