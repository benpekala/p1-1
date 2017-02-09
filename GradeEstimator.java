
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.Scanner;

	public class GradeEstimator {

		private static String[] letterGrades;
		private static String[] minThreshholds;
		private static String[] catNames;
		private static String[] catWeights;
		private static String[] tempScore;
		private static ScoreList scoreList;
		
		public GradeEstimator(String[] letterGrades, String[] minThreshholds, String[] catNames, 
				String[] catWeights, ScoreList scoreList)
		{
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
			}
			else
			{
				try{
					ge = createGradeEstimatorFromFile("no_file_exists.txt");
					ge.getEstimateReport();

				}catch(GradeFileFormatException e){
					
				}
				catch(FileNotFoundException e){
					System.out.println("File Not Found");
				}
			}
			
		}
		
		public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
			      throws FileNotFoundException, GradeFileFormatException
			      {
						File file = new File(gradeInfo);
						Scanner read = new Scanner( file );		
						while (read.hasNextLine())
						{
							int line = 1;
							
							String line1 = read.nextLine();
							if (line1.contains("#"))
							{
								line1 = line1.substring(0, line1.indexOf("#"));
							}
							
							switch (line)
							{
								case 1: 
									letterGrades = line1.split("\\s+");
									for (int i = 0; i < letterGrades.length; i++)
									{
										if ((letterGrades[i].charAt(0) < 65 || letterGrades[i].charAt(0) > 90) 
												|| (letterGrades[i].charAt(0) > 97 || letterGrades[i].charAt(0) > 122))
										{
											throw new GradeFileFormatException();
										}
									}
									break;
								case 2:
									minThreshholds = line1.split("\\s+");
									if (letterGrades.length != minThreshholds.length)
									{
										throw new GradeFileFormatException();
									}
									
									for (int i = 0; i < letterGrades.length; i++)
									{
										if (minThreshholds[i].charAt(0) < 48 || minThreshholds[i].charAt(0) > 57)
										{
											throw new GradeFileFormatException();
										}
									}
									break;
								case 3:
									catNames = line1.split("\\s+");
									break;
								case 4:
									catWeights = line1.split("\\s+");
									break;
								default:
									tempScore = line1.split("\\s+");
									scoreList.add(new Score(tempScore[0],Double.parseDouble(tempScore[1]), Double.parseDouble(tempScore[2])));
									
							}
							line++;
							
						}
						read.close();
					
					return new GradeEstimator(letterGrades, minThreshholds, catNames, catWeights, scoreList);
				}
		
		public String getEstimateReport(){
		//String message = "Grade estimate is based on " + scoreList.size() + " scores.";
//		
//		int aIndex = 0, pIndex = 0, mIndex = 0, fIndex = 0, hIndex = 0, dIndex = 0;
//		String defaultCase = "";
//		for (int i = 0; i < catNames.length; i++)
//		{
//			switch (catNames[i].substring(0, 1).toLowerCase())
//			{
//			case "a":
//				aIndex = i;
//				break;
//			case "p":
//				pIndex = i;
//				break;
//			case "m":
//				mIndex = i;
//				break;
//			case "f":
//				fIndex = i;
//				break;
//			case "h":
//				hIndex = i;
//				break;
//			default:
//				dIndex = i;
//				defaultCase = catNames[i].substring(0,1);
//				break;
//			}
//		}
//		
//		double[][] pointsPE = new double[catNames.length][2];
		//TODO change this to iterator
//		for (int i = 0; i < scoreList.size(); i++)
//		{
//			switch(scoreList.get(i).getCategory().toLowerCase())
//			{
//			case "h":
//				pointsPE[hIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[hIndex][1] += scoreList.get(i).getPoints();
//				break;
//			case "p":
//				pointsPE[pIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[pIndex][1] += scoreList.get(i).getPoints();
//				break;
//			case "m":
//				pointsPE[mIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[mIndex][1] += scoreList.get(i).getPoints();
//				break;
//			case "f":
//				pointsPE[fIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[fIndex][1] += scoreList.get(i).getPoints();
//				break;
//			case "a":
//				pointsPE[aIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[aIndex][1] += scoreList.get(i).getPoints();
//				break;
//			default:
//				pointsPE[dIndex][0] += scoreList.get(i).getPoints();
//				pointsPE[dIndex][1] += scoreList.get(i).getPoints();
//				break;
//			}
//		}
		
		String message = "";
		
		for (int i = 0; i < catNames.length; i++)
		{
			
		}
		
		
		
		
		return null;
		}
	

		
	}