import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class GradeEstimator {

	private  String[] letterGrades;
	private  double[] minThreshholds;
	private  String[] catNames;
	private  double[] catWeights;
	private ScoreList scoreList;


	public GradeEstimator(String[] letterGrades, double[] minThreshholds, String[] catNames, 
			double[] catWeights, ScoreList scoreList)
	{
		if( letterGrades.length != minThreshholds.length ) throw new IllegalArgumentException();
		this.letterGrades = letterGrades;
		this.minThreshholds = minThreshholds;
		this.catNames = catNames;
		this.catWeights = catWeights;
		this.scoreList = scoreList;

	}
	public static void main(String[] args) {
		GradeEstimator ge;
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

	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
			throws FileNotFoundException, GradeFileFormatException
	{

		String[] letterGrades = null;
		double[] minThreshholds = null;
		String[] catNames = null;
		double[] catWeights = null;
		ScoreList scoreList = new ScoreList();
		String[] tempScore = null;

		File file = new File(gradeInfo);
		Scanner read = new Scanner( file );		
		int line = 1;
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
				catNames = curLine.split("\\s+");
				break;
			case 4:
				check = new Scanner(curLine);
				i = 0; 
				catWeights = new double[catNames.length]; 

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

		return new GradeEstimator(letterGrades, minThreshholds, catNames, catWeights, scoreList);
	}

	public String getEstimateReport(){

		String estimates = "";
		String assignments = "";
		double weightedPercent = 0;


		for( int i = 0; i < catNames.length; i++){
			ScoreIterator curr = new ScoreIterator(scoreList, 
					catNames[i].substring(0, 1).toLowerCase());

			int numItems = 0;
			double grade= 0;
			Score currentScore;
			if (!curr.hasNext())
			{
				grade = 100;
				numItems = 1;
			}
			while( curr.hasNext() ){
				currentScore = curr.next();

				numItems++;
				grade += currentScore.getPercent();
				assignments += String.format(currentScore.getName() + 
						"   %.2f"  + "\n", currentScore.getPercent());

			}
			double rawPercent = grade / numItems;
			double weight = catWeights[i];
			double weightedAvg = weight * rawPercent / 100;
			weightedPercent += weightedAvg;
			estimates +=  String.format("%7.2f%% = %5.2f%% of " +  weight + 
					"%% for " + catNames[i] + "\n", weightedAvg, rawPercent);

		}
		int gradeCounter = minThreshholds.length - 1;
		String letterGrade = "unable to estimate letter grade for " + weightedPercent; 

		while (weightedPercent > minThreshholds[gradeCounter])
		{
			gradeCounter--;
			letterGrade = letterGrades[gradeCounter + 1];
			if (gradeCounter <= -1) 
			{
				break;  
			}


		}

		String a = String.format("%7.2f", weightedPercent);
		return assignments + "Grade estimate is based on " + scoreList.size()
		+ " scores\n" + estimates + "--------------------------------\n" + a +
		"% weighted " + "percent\nLetter Grade Estimate: " + 
		letterGrade;

	}



}
