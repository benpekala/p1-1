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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Reads a file. Creates a GradeEstimator object. Prints out the grade estimate
 *  report. If no file is provided will use the defaults from config.java. 
 *
 */
public class GradeEstimator {

	// Instance variable that holds the letter grades possible
	private  String[] letterGrades;
	// Instance  variable holding the thresholds needed for each letter grade
	private  double[] minThreshholds;
	// Holds the names of the categories for assignments
	private  String[] catNames;
	// holds the weighted percent that each category gets for the final grade
	private  double[] catWeights;
	// a list of scores
	private ScoreList scoreList;

	/**
	 * Constructor for a GradeEstimator object that initializes all the variables
	 * to the input values, throws an exception if the number of letter grades
	 * does not match the number of threshold numbers
	 * @param letterGrades the possible grades for the class
	 * @param minThreshholds the minimum scores needed for each letter grade
	 * @param catNames the names of all of the possible categories
	 * @param catWeights the weighted percent for each category
	 * @param scoreList the list of scores
	 */
	public GradeEstimator(String[] letterGrades, double[] minThreshholds, 
			String[] catNames, double[] catWeights, ScoreList scoreList)
	{
		if( letterGrades.length != minThreshholds.length ) 
			throw new IllegalArgumentException();
		this.letterGrades = letterGrades;
		this.minThreshholds = minThreshholds;
		this.catNames = catNames;
		this.catWeights = catWeights;
		this.scoreList = scoreList;

	}
	/**
	 * Runs the GradeEstimator on either an input file entered as an argument in
	 * the command line or from the defaults defined in config.java
	 * @param args Command line argument, should be a file name
	 */
	public static void main(String[] args) {
		
		// Used to hold a GradeEstimator instance
		GradeEstimator ge;
		
		// Check if there is an appropriate command line argument and either calls 
		// the GradeEstimator constructor on the default parameters if not, or calls
		// createGradeEstimatorFromFile to read in the file
		if (args.length != 1 || args[0] == null)
		{
			System.out.println(Config.USAGE_MESSAGE);

			ge = new GradeEstimator(Config.GRADE_LETTER, Config.GRADE_THRESHOLD,
					Config.CATEGORY_KEY, Config.CATEGORY_WEIGHT,  new ScoreList());

			System.out.println(ge.getEstimateReport()); 
		}
		else
		{
			try{
				ge = createGradeEstimatorFromFile(args[0]);
				System.out.println(ge.getEstimateReport());

			}catch(GradeFileFormatException e){
				System.out.println(e);
			}
			catch(FileNotFoundException e){
				System.out.println(e);
			}
		}

	}
	/**
	 * Attempts to make a GradeEstimator instance from a file ( the filename is 
	 * given as a String). If the file is not formatted correctly will throw a 
	 * GradeFileFormatException; if the file is not found will throw a 
	 * FileNotFoundException
	 * 
	 * @param gradeInfo The String containing the name of the file
	 * @return GradeEstimator instance that was constructed from the data in the file
	 * @throws FileNotFoundException If the file given does not exist
	 * @throws GradeFileFormatException If the file was formatted incorrectly
	 */
	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
			throws FileNotFoundException, GradeFileFormatException
	{
		// Variables that will be passed through the GradeEstimator constructor and 
		// assigned to their corresponding instance fields in the instantiated object
		String[] letterGrades = null;
		double[] minThreshholds = null;
		String[] catNames = null;
		double[] catWeights = null;
		ScoreList scoreList = new ScoreList();
		String[] tempScore = null;

		// The file object that will be attempted to be read
		File file = new File(gradeInfo);
		// A scanner for reading in the file
		Scanner read = new Scanner( file );
		// Counts the current line for input validation
		int line = 1;
		
		/* Goes through each line in the file, checking if it was formatted correctly
		 Ignores any words written after the #. Splits each line into a String array
		 then checks if the first four lines meet the requirements for the letterGrades,
		 minThreshholds, catNames, and catWeights variables and assigns them correspondingly.
		 Any lines after the first four are then read in as new Scores for the ScoreList.
		 
		 */
		while (read.hasNextLine())
		{
			String curLine = read.nextLine();
			if (curLine.contains("#"))
			{
				curLine = curLine.substring(0, curLine.indexOf("#"));
			}

			switch (line)
			{
			case 1: 
				Scanner check = new Scanner(curLine);
				while (check.hasNext())
				{
					if (check.hasNextInt() || check.hasNextDouble())
					{
						throw new GradeFileFormatException();
					}
					check.next();
				}
				letterGrades = curLine.split("\\s+");
				break;
			case 2:
				check = new Scanner(curLine);
				int i = 0; 
				minThreshholds = new double[letterGrades.length]; 
				
				/* Check that there is a next element in the String array. These are
				 * all expected to be doubles and the array should be the same length
				 * as the letterGrades array defined above if not a GradeFileFormatException 
				 * is thrown.
				 */
				while (check.hasNext())
				{
					if (!(check.hasNextDouble()))
					{
						throw new GradeFileFormatException();
					} else {

						if (i >= minThreshholds.length) 
						{
							throw new GradeFileFormatException(); 
						}

						minThreshholds[i] = check.nextDouble(); 
						i++; 
					}
				}

				break;
			case 3:
				/* We don't need to check for errors in this line because category names
				 can be a string or numbers or a combination of both and can be of arbitrary length
				 which will be checked in case 4
				 */
				catNames = curLine.split("\\s+");
				break;
			case 4:
				check = new Scanner(curLine);
				i = 0; 
				catWeights = new double[catNames.length]; 
				
				// This loop is similar to case 2
				while (check.hasNext())
				{
					if (!(check.hasNextDouble()))
					{
						throw new GradeFileFormatException();
					} else {

						if (i >= catWeights.length) 
						{
							throw new GradeFileFormatException(); 
						}

						catWeights[i] = check.nextDouble(); 
						i++; 
					}
				}

				break;
			default:
				tempScore = curLine.split("\\s+");

				/* Checking that the points and possible
				points for each score are doubles before parsing*/

				try 
				{
					scoreList.add(new Score(tempScore[0],Double.
							parseDouble(tempScore[1]), Double.
							parseDouble(tempScore[2])));
				} catch (Exception e) {
					throw new GradeFileFormatException(); 
				}
			}
			line++;

		}
		read.close();

		return new GradeEstimator(letterGrades, minThreshholds, catNames, 
				catWeights, scoreList);
	}

	/**
	 * Constructs a String that displays each Score object with their name 
	 * and percentage, then displays the weighted percentage of each category 
	 * and finally gives the letter grade estimate based the scores
	 * @return The string described above
	 */
	public String getEstimateReport(){
		/* Will contain lines for weighted scores in each category formated as such:
		*  [categoryA weighted average score]% = [categoryA average score]% * [categoryA weight]% for [categoryA name]
		*  [categoryB weighted average score]% = [categoryB average score]% * [categoryB weight]% for [categoryB name]
		*  [...repeat for each category...]
		*/
		String estimates = "";
		// Will hold the name and percentage of each Score object in scoreList
		String assignments = "";
		// The overall grade that will be the sum of weighted scores for each category
		double weightedPercent = 0;

		/* Creates a ScoreIterator for each category and calculates the score received
		 * in each category based on all scores for assignments in that category.
		 */
		for( int i = 0; i < catNames.length; i++){
			ScoreIterator curr = new ScoreIterator(scoreList, 
					catNames[i].substring(0, 1).toLowerCase());

			int numItems = 0;
			double grade= 0;
			Score currentScore;
			
			// If no scores exist in a specified category the final weighted grade is 100%
			if (!curr.hasNext())
			{
				grade = 100;
				numItems = 1;
			}
			
			/* While there is a score in the category, increment the number of scores for
			 * that category, add the scores percentage to the total grade, and add a new line
			 * to the assignments string storing the specific assignments info for the return
			 * statement
			 */
			while( curr.hasNext() ){
				currentScore = curr.next();

				numItems++;
				grade += currentScore.getPercent();
				assignments += String.format(currentScore.getName() + 
						"   %.2f"  + "\n", currentScore.getPercent());

			}
			
			// The un-weighted percent received for a specific category
			double rawPercent = grade / numItems;
			// Stores the weight of the category being evaluated
			double weight = catWeights[i];
			// The percent for the category that's been weight-adjusted 
			double weightedAvg = weight * rawPercent / 100;
			
			weightedPercent += weightedAvg;
			estimates +=  String.format("%7.2f%% = %5.2f%% of " +  weight + 
					"%% for " + catNames[i] + "\n", weightedAvg, rawPercent);

		}
		
		/* Counter variable used to find the letter grade earned, starts at the last
		index of the minThreshholds array
		*/
		int gradeCounter = minThreshholds.length - 1;
		
		/* The letterGrade that is given, initialized to the default if a grade
		   is less than the lowest threshold
		*/
		String letterGrade = "unable to estimate letter grade for " + 
					weightedPercent; 

		/*Starts from the last index of minThreshholds, checks if the weighted percent
		 * is greater than that, if so decrement the gradeCounter and check again. If the
		 * score is higher than the highest threshold value break out of the loop
		 */
		while (weightedPercent > minThreshholds[gradeCounter])
		{
			gradeCounter--;
			letterGrade = letterGrades[gradeCounter + 1];
			if (gradeCounter <= -1) 
			{
				break;  
			}
		}

		// Holds the formated weighted percent to make the return statement easier to read
		String a = String.format("%7.2f", weightedPercent);
		return assignments + "Grade estimate is based on " + scoreList.size()
		+ " scores\n" + estimates + "--------------------------------\n" + a +
		"% weighted " + "percent\nLetter Grade Estimate: " + letterGrade;

	}



}
