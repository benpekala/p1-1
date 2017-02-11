
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
				String[] catWeights)
		{
			this.letterGrades = letterGrades;
			this.minThreshholds = minThreshholds;
			this.catNames = catNames;
			this.catWeights = catWeights;

		}
		public static void main(String[] args) {
//		//////TESTING///////
//					String[] lg = {"A", "B", "C", "D", "F", "N"};
//					String[] mt = {"90", "80", "70", "60", "5", "0"};
//					String[] cn = {"homework", "program", "midterm", "final"};
//					String[] cw = {"20", "25", "34", "21"};
//					ScoreList scoreList = new ScoreList();
//					scoreList.add( new Score( "h1", 34, 50 ) );
//					scoreList.add( new Score( "h2", 29, 30 ) );
//					scoreList.add( new Score( "p1",195, 200 ) );
//					scoreList.add( new Score( "p2",230, 240 ) );
//					scoreList.add( new Score( "p3", 50, 80 ) );
//					scoreList.add( new Score( "m1", 57, 66 ) );
//					scoreList.add( new Score( "m2", 61, 66 ) );
//					scoreList.add( new Score( "f" , 78, 81 ) );
//					GradeEstimator ge1 = new GradeEstimator(lg, mt, cn, cw, scoreList);
//					System.out.println(ge1.getEstimateReport());
					////////////////////
			GradeEstimator ge;
			if (args.length < 1 || args[0] == null)
			{
				System.out.println(Config.USAGE_MESSAGE);
				//ge = new GradeEstimator(Config.GRADE_LETTER, Config.GRADE_THRESHOLD, Config.CATEGORY_KEY, Config.CATEGORY_WEIGHT);
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
						scoreList = new ScoreList();
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
//									for (int i = 0; i < letterGrades.length; i++)
//									{
//										if ((letterGrades[i].charAt(0) < 65 || (letterGrades[i].charAt(0) > 90 
//												&& letterGrades[i].charAt(0) < 97) || letterGrades[i].charAt(0) > 122))
//										{
//											throw new GradeFileFormatException();
//										}
//									}
									break;
								case 2:
									check = new Scanner(curLine);
									while (check.hasNext())
									{
										if (!(check.hasNextInt()))
										{
											throw new GradeFileFormatException();
										}
										check.next();
									}
									minThreshholds = curLine.split("\\s+");
									if (letterGrades.length != minThreshholds.length)
									{
										throw new GradeFileFormatException();
									}
									
//									for (int i = 0; i < letterGrades.length; i++)
//									{
//										if (minThreshholds[i].charAt(0) < 48 || minThreshholds[i].charAt(0) > 57)
//										{
//											throw new GradeFileFormatException();
//										}
//									}
									
									if (Integer.parseInt(minThreshholds[minThreshholds.length - 1]) != 0)
									{
										throw new GradeFileFormatException();
									}
									break;
								case 3:
									catNames = curLine.split("\\s+");
									break;
								case 4:
									catWeights = curLine.split("\\s+");
									break;
								default:
									tempScore = curLine.split("\\s+");
									scoreList.add(new Score(tempScore[0],Double.parseDouble(tempScore[1]), Double.parseDouble(tempScore[2])));
									
							}
							line++;
							
						}
						read.close();
					
					return new GradeEstimator(letterGrades, minThreshholds, catNames, catWeights);
				}
		
		public String getEstimateReport(){
		
			String estimates = "";
			String assignments = "";
			double weightedPercent = 0;
			
			
			for( int i = 0; i<catNames.length; i++){
				ScoreIterator curr = new ScoreIterator(scoreList, catNames[i].substring(0, 1).toLowerCase());
//System.out.println(catNames[i]+ " is the category");
				int numItems = 0;
				double grade=0;
				Score currentScore;
				if (!curr.hasNext())
				{
					grade = 100;
					numItems = 1;
				}
				while( curr.hasNext() ){
					currentScore = curr.next();
//System.out.println( currentScore.name );
					numItems++;
					grade += currentScore.getPercent();
					assignments += String.format(currentScore.getName() + "   %.2f"  + "\n", currentScore.getPercent());
					
				}
				double rawPercent = grade/numItems;
				int  weight = Integer.parseInt(catWeights[i]);
				double weightedAvg = weight * rawPercent / 100;
				weightedPercent += weightedAvg;
				estimates +=  String.format("%7.2f%% = %5.2f%% * " +  weight + "%% for " + catNames[i] + "\n", weightedAvg, rawPercent);
				
			}
			int gradeCounter = minThreshholds.length - 1;
			while (weightedPercent > Integer.parseInt(minThreshholds[gradeCounter]))
			{
				gradeCounter--;
			}
			
			//catAvgs[1].add(new Score("p1", 20, 30));
			//System.out.println(catAvgs[1].get(0).getCategory());
			String a = String.format("%7.2f", weightedPercent);
			return assignments + "Grade estimate is bassed on " + scoreList.size() + " scores\n" + estimates + "--------------------------------\n"
					+ a + "% weighted percent\nLetter Grade Estimate: " + letterGrades[gradeCounter + 1];
			
			
			
			
			
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
		
//		String message = "";
//		
//		for (int i = 0; i < catNames.length; i++)
//		{
//			
//		}
//		
//		
//		
//		
//		return null;
		}
	

		
	}