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
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * A class for instantiating Score object with required fields
 * 
 * @author Patrick Lown
 */
public class Score {
	
	double pointsEarned; //holds pointsEarned in Score
	double pointsPos; //holds points possible in Score
	String name; //name for the score
	
	/**
	 * A constructor for a Score object that specifies the name, pointsEarned and pointsPos fields.
	 * Throws exception if name is null, checks the pointsPos is greater than pointsEarned
	 * @param (name) name of Score
	 * @param (pointsEarned) the number of points earned for that score
	 * @param (pointsPos) the number of points possible for a Score
	 */
	public Score(String name, double pointsEarned, double pointsPos)
	{
		if (name == null)
			throw new IllegalArgumentException();
		else
			this.name = name;
		
		if ((pointsPos < 0 || pointsEarned < 0) || pointsEarned > pointsPos) //check if either is negative
			throw new IllegalArgumentException();
		else
			this.pointsPos = pointsPos;
			this.pointsEarned = pointsEarned;
		
	}
	
	/**
	 * A method that returns the name of the Score
	 * 
	 * PRECONDITIONS: (Score is expected to have name)
	 * 
	 * @return (name of Score)
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * An accessor method for getting pointsEarned on a Score
	 *
	 * PRECONDITIONS: (The score has a pointsEarned)
	 *
	 * @return (pointsEarned by Score)
	 */
	public double getPoints()
	{
		return this.pointsEarned;
	}
	
	/**
	 * An accessor method for getting points possible on a Score
	 *
	 * PRECONDITIONS: (The score has a points possible)
	 *
	 * @return (pointsPos by Score)
	 */
	public double getMaxPossible()
	{
		return this.pointsPos;
	}
	
	/**
	 * An accessor method for getting a substring with only the first letter of the name
	 *
	 * PRECONDITIONS: (The score has a name)
	 *
	 * @return (first letter of score object's name)
	 */
	public String getCategory()
	{
		return this.name.substring(0,1);
	}
	
	/**
	 * An accessor method for getting the pointsEarned/pointsPos returned
	 * as a percentage out of 100
	 *
	 * PRECONDITIONS: (The score has a pointsEarned and pointsPos)
	 *
	 * @return (score percentage)
	 */
	public double getPercent() //the returns the percentage of points/possible times 100. 
	{
		return (this.pointsEarned/this.pointsPos) * 100;
	}
}

	
