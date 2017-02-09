/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p0
// FILE:             ScoreList.java
//
// Authors: Patrick Lown
// Author1: (name1,email1,netID1,lecture number1)
// Author2: (name2,email2,netID2,lecture number2)
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
// small change test
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * An ordered list that keeps Score objects 
 * 
 * @author Patrick Lown
 */
public class ScoreList implements ScoreListADT {
	
	private Score[] scoreList; //an array of Scores
	private int numItems; // to track number of items in scoreList array
	
	/**
	 * Constructor for the ScoreList object, creates a new ScoreList with 100 open spots
	 * and a number of items variable initially set to 0.
	 * 
	 */
	public ScoreList()
	{
		scoreList = new Score[100];
		numItems = 0;
	}
	
	/**
	 * The method returns the size of the scoreList
	 *
	 * PRECONDITIONS: (should be called on a scoreList w/ a numItems field)
	 * 
	 * @return returns numItems field of a ScoreList object
	 */
	public int size()
	{
		return numItems;
	}
	
	/**
	 * This method adds a Score object to the scoreList.  Will expand the list
	 * if it is full when called.
	 *
	 * PRECONDITIONS: (the input is expected to be a score)
	 * 
	 * POSTCONDITIONS: (list has been reordered with new Score at the end)
	 *
	 * @param (s) the variable of the new Score
	 */
	public void add(Score s) throws IllegalArgumentException
	{
		if (numItems == scoreList.length) //expand list if current list is full
			expandList();
		
		scoreList[numItems] = s;
		
		++numItems;
	}
	
	/**
	 * A method that removes a Score object from a scoreList.  If i is greater than numItes or less than zero, throws
	 * exception.  Removes Score object from scoreList and shifts all remaining scores above that score down
	 * one spot.
	 *
	 * PRECONDITIONS: (the specified index is within appropriate range)
	 * 
	 * POSTCONDITIONS: (list reordered after specified score is removed)
	 *
	 * @param (i) specified index
	 * @return returns the Score chosen to remove
	 */
	public Score remove(int i) throws IndexOutOfBoundsException
	{
		if (numItems == 0 ) //check if there are items in list
			throw new IndexOutOfBoundsException();
		if (i < 0 || i >= numItems) //check if outside of range
			throw new IndexOutOfBoundsException();
		
		Score tempScore = scoreList[i];
		scoreList[i] = null; 
		
		for (int j = i; j < numItems; j++) //move all Scores down one in the list
		{
			scoreList[j] = scoreList[j+1];
		}
		scoreList[numItems--] = null; //decrement numItems and set numItems spot to null
			
		
		return tempScore;
	}
	
	/**
	 * A get method that return a Score at the specified index of the scoreList.
	 * Throws an IndexOutOfBoundsException if the specifiend index is greater than numItems
	 * or less than 0.
	 *
	 * PRECONDITIONS: (the incoming index is expected to be within the appropriate range)
	 * 
	 * POSTCONDITIONS: (returns Score object)
	 *
	 * @param (i) specified index
	 * @return (scoreList[i]) the Score at the specified index
	 */
	public Score get(int i) throws IndexOutOfBoundsException
	{
		if (i > numItems || i < 0) //check for range
			throw new IndexOutOfBoundsException();
		return scoreList[i];
	}
	/**
	 * A method that is used by the add method above.  Expands array to twice its current
	 * size and sets scoreList variable to point to the new array.
	 *
	 * PRECONDITIONS: (incoming list is full (numItems == array.length))
	 * 
	 * POSTCONDITIONS: (the list is increased to twice its size)
	 */
	private void expandList()
	{
		Score[] newList = new Score[numItems * 2];
		
		for (int i = 0; i < numItems; i++) // copies items from scoreList to newList
		{
			newList[i] = this.scoreList[i];
		}
		
		this.scoreList = newList; //points scoreList to newList
		
	}
	
}