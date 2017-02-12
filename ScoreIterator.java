/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p1
// FILE:             ScoreList.java
//
// Authors: Patrick Lown, Hayley Raj, Ryan Ramsdell, Ilhan Bok, Abhi Kumar, Ben Pekala
// Author1: Hayley Raj, hraj@wisc.edu, hraj, 003
// Author2: Ryan Ramsdell, ramsdell2@wisc.edu, ramsdell2, 003
// Author3: Ilhan Bok, ibok@wisc.edu, ibok, 003
// Author 4: Abhi Kumar,akumar63@wisc.edi,abhik,003
// Author 5: Patrick Lown, lown@wisc.edu, lown, 003
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////

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