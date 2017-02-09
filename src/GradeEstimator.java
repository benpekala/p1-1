
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
		
		
		ScoreList homeworks = new ScoreList();
		ScoreList programs = new ScoreList();
		ScoreList midterms = new ScoreList();
		ScoreList finals = new ScoreList();
		ScoreList assignments = new ScoreList();
		
		for (int i = 0; i < scoreList.size(); i++)
		{
			switch(scoreList.get(i).getCategory().toLowerCase())
			{
			case "h":
				homeworks.add(scoreList.get(i));
				break;
			case "p":
				programs.add(scoreList.get(i));
				break;
			case "m":
				midterms.add(scoreList.get(i));
				break;
			case "f":
				finals.add(scoreList.get(i));
				break;
			case "a":
				assignments.add(scoreList.get(i));
				break;
			}
		}
		
		
		
		return null;
		}
	}

//		private double averageScore(){
//			
//		}
//		
//		}