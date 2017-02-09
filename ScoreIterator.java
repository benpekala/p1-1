import java.util.NoSuchElementException;

public class ScoreIterator implements ScoreIteratorADT {

	private ScoreList myList;
	private int curPos;
	private String catergory;
	private int[] indexArray;
	
	public ScoreIterator (ScoreList myList, String catergory)
	{
		this.myList = myList;
		curPos = 0;
		indexArray = new int[myList.size()];
		
		for (int i = 0; i < myList.size(); i++)
		{
			int counter = 0;
			if (myList.get(i).getCategory().toLowerCase().equals(catergory))
			{
				
				indexArray[counter] = i;
				counter++;
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
		return curPos < indexArray.length;	
	}

}