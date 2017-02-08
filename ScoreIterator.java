import java.util.NoSuchElementException;

public class ScoreIterator implements ScoreIteratorADT {

	private ScoreList myList;
	private int curPos;
	
	public ScoreIterator (ScoreList myList, String catergory)
	{
		this.myList = myList;
		curPos = 0;
		//TODO implement catergory selection
	}
	
	@Override
	public Score next() {
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}
		Score result = myList.get(curPos);
		curPos++;
		return result;
	}

	@Override
	public boolean hasNext() {
		return curPos < myList.size();	
	}

}
