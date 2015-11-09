import java.awt.*;
import java.awt.image.BufferedImage;
import java.applet.*;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.*;

/*
CAStats.java
by Eric J.Parfitt (ejparfitt@gmail.com)

This program takes the evolution of an elementary cellular automaton,
and determines, on each step, how the number of black minus white tiles
has changed, and plots this.

Version: 1.0 alpha
*/

public class CAStats extends Applet implements Runnable {
	//Variables
	//private boolean[] ruleDigit = {false, true, true, false, true, true, true, false};	//Rule 110
	//private boolean[] ruleDigit = {false, false, false, true, true, true, true, false};		//Rule 30
	//private boolean[] ruleDigit = {false, true, true, true, true, false, false, false}; // 	rule 120
	private boolean[] ruleDigit = {false, true, true, false, true, false, true, false}; // 	rule 106
	private BufferedImage drawing;
	private Graphics2D g;
	private int key;
	private Thread myThread; 
	//private boolean[] row = {false, false, true, false, false};
	private boolean[] row = new boolean[127];
	private boolean[][] array = new boolean[1][row.length];
	private boolean[][] tempArray = new boolean[1][row.length];
	//private int rowLength = row.length;
	private boolean[] tempRow = new boolean[row.length];
	Scanner scan = new Scanner(System.in);


	// More variables
	
	Color redColor;
	Color weirdColor;
	Color bgColor;
	Color whiteColor;
	Color blackColor;
	Color greenColor;
	Color yellowColor;
	Color orangeColor;
	boolean cellA = false;
	boolean cellB = false;
	boolean cellC = false;
	private Random random = new Random();
	private int[] tDArr = new int[1];
	private double xSize = 1200;
	private double ySize = 700;
	private int stepNumber = 1;
	private int oldTCount = 0;
	int maxDiff = 0;
	int initRowL;
	int termiNum = 509;
	public void init() 
	{
		//System.out.println(array.length);
		// set up double buffering
		drawing = new BufferedImage((int) xSize, (int) ySize, BufferedImage.TYPE_4BYTE_ABGR);
		g = drawing.createGraphics();
		resize((int) xSize, (int) ySize);

		// Colors
		initRowL = row.length;
		redColor = Color.red;
		weirdColor = new Color(60,60,122);
		bgColor = Color.white;
		whiteColor = Color.white;
		blackColor = Color.black;
		greenColor = Color.green;
		yellowColor = Color.yellow;
		orangeColor = Color.orange;;

		setBackground(bgColor);
		
		for(int i = 0; i < row.length; i++) {
			row[i] = random.nextBoolean();
		}
		
		/*
		for (int i = 0; i < row.length; i++) {
			array[0][i] = row[i];
		}
		*/
	}
	

	public void stop()
	{
		myThread = null;
	}

	public void start() {
		if(myThread == null) {
			myThread = new Thread(this, "curvechange");
			myThread.start();
		}
	}
	//Gets keys and updates graphics
	public void run() {
		while(true) {
			//speed
			try {Thread.sleep(30);}
			catch(Exception e) {}
			update(getGraphics());
			
		}
	}
	public void update(Graphics gr) {
		paint(gr);
	}
	// Draws stuff on screen
	public void paint(Graphics gr) {
		// Creates a new Grapher object
		//toGraph.incrementExtra(0);
		
		if (stepNumber == termiNum + 1){
			while(true);
		}
		
		//Fills background
		g.setColor(bgColor);
		g.fillRect(0, 0, (int) xSize, (int) ySize);
		
		//Prints list values
		String list = "";
		for(int i = 0; i < row.length; i++) {
			String tempList = list + " " + row[i];
			list = tempList;
		}
		//System.out.println(list);
		
		//Finds difference in true values from one row to the next
		int trueCount = 0;
		for (int i = 0; i < row.length; i++) {
			if (row[i] == true) {
				trueCount ++;
			}
		}
		System.out.println((double) trueCount / row.length);
		int trueDiff = trueCount - oldTCount;
		oldTCount = trueCount;
		//int tFDiff = trueCount - (row.length - 4 - trueCount);
		//System.out.println(tFDiff);
		//System.out.println(trueDiff);
		//System.out.println(trueCount);
		
		//Makes array of trueDiff values
		int[] tDTemp = new int[stepNumber];
		for (int i = 0; i < tDArr.length; i++) {
			tDTemp[i] = tDArr[i];
		}
		tDArr = new int[stepNumber];
		for (int i = 0; i < tDTemp.length; i++) {
			tDArr[i] = tDTemp[i];
			//System.out.print(tDArr[i]);
		}
		 /*
		if (stepNumber > 1) {
			System.out.println(trueDiff);
			//System.out.println(stepNumber - 1 + " " + trueDiff);
			if(Math.abs(trueDiff) > maxDiff) {
				maxDiff = Math.abs(trueDiff);
			}
			System.out.println("maxDiff = " + maxDiff);
		}
		*/
		if (stepNumber == termiNum) {
			for (int i = 1; i < tDArr.length; i++) {
				System.out.print(Math.abs(tDArr[i]) + ", ");
				//System.out.println(tDArr[i]);
			}
			System.out.println();
			System.out.println();
			for (int i = 1; i < tDArr.length; i++) {
				System.out.print(tDArr[i] + ", ");
				//System.out.println(tDArr[i]);
			}
			System.out.println();
			System.out.println();
			for (int i = 1; i < tDArr.length; i++) {
				//System.out.print(tDArr[i] + ", ");
				System.out.println(tDArr[i]);
			}
		}
		
		
		//System.out.println();
		
		//g.setColor(blackColor);
		//g.drawRect(5, 30 + trueDiff, 0, 0);
		//Graphs trueDiff values
		
		g.setColor(blackColor);
		for (int i = 4; i < tDArr.length; i++){
			//g.drawRect(5 + i, 50 + tDArr[tDArr.length - i], 0, 0);
			g.drawLine(5 + i, 100 - (tDArr[tDArr.length - i]), 5 + i, 100);
			//System.out.print(tDArr[i]);
		}
		
		//System.out.println();
		
		//Updates TrueDiff array
		tDArr[tDArr.length - 1] = trueDiff; //TrueDiff
		//tDArr[tDArr.length - 1] = (int) Math.round(Math.random() * 10) - 1; //Rand # 1-10
		//tDArr[tDArr.length - 1] = (int) Math.round(1.0 / (1 / (1 - Math.random()) - 1)); //Power law distribution
		
		//Prints step number
		///System.out.println(row.length - 4);
		
		//Draws single line automata
		
		/*
		for (int i = 0; i < row.length; i++){
			if (row[i] == true) {
				g.setColor(blackColor);
			}
			else {
				g.setColor(whiteColor);
			}
			g.drawRect((int) (xSize / 2) - row.length + i + stepNumber, 0, 0, 0);
		}
		*/
		//array[0][0] = true;
		//Draws 2D array automata
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[stepNumber - 1][j] = row[j];
				if (array[i][j] == true) {
					g.setColor(blackColor);
					//System.out.print("true");
				}
				else {
					g.setColor(whiteColor);
				}
				g.drawRect((int) (xSize / 2) + j, 50 + i, 0, 0);
				//g.drawRect(450 - array[i].length + j + stepNumber, 100 + array.length, 0, 0);
			}
		}
		
		//Increases tempArray row number
		tempArray = new boolean[stepNumber + 1][row.length];
		/*
		for (int i = 0; i < tempArray.length; i++) {
			tempArray[i] = new boolean[stepNumber * 2 + initRowL];
		}
		*/
		
		//Copy array into tempArray
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				tempArray[i][j] = array[i][j];
			}
		}
		
		//Increase array row number
		array = new boolean[stepNumber + 1][row.length];
		/*
		for (int i = 0; i < array.length; i++) {
			array[i] = new boolean[stepNumber * 2 + initRowL];
		}
		*/
		
		//Copy tempArray into array
		for (int i = 0; i < tempArray.length; i++) {
			for (int j = 0; j < tempArray[i].length; j++) {
				array[i][j] = tempArray[i][j];
			}
		}
		
		
		//System.out.println();
		//System.out.println(array.length);
		/*
		for (int i = 0; i < array[0].length; i++) {
			System.out.print(array[0][i]);
		}
		System.out.println();
		*/
		//g.drawRect(0, 0, 0, 0);
		//System.out.println(row[0]);
		for(int i = 0; i < row.length; i++){
			
			if (i - 2 >= 0 ) {
				cellA = row[i - 2];
				cellB = row[i - 1];
				cellC = row[i];
			}
			if (i == 1) {
				cellA = row[row.length - 1];
				cellB = row[i - 1];
				cellC = row[i];
			}
			if (i == 0) {
				cellA = row[row.length - 2];
				cellB = row[row.length - 1];
				cellC = row[i];
			}
			
			if (cellA == false && cellB == false &&  cellC == false) {
				tempRow[i] = ruleDigit[7];
			}
			if (cellA == false && cellB == false && cellC == true) {
				tempRow[i] = ruleDigit[6];
			}
			if (cellA == false && cellB == true && cellC == false) {
				tempRow[i] = ruleDigit[5];
			}
			if (cellA == false && cellB == true && cellC == true) {
				tempRow[i] = ruleDigit[4];
			}
			if (cellA == true && cellB == false && cellC == false) {
				tempRow[i] = ruleDigit[3];
			}
			if (cellA == true && cellB == false && cellC == true) {
				tempRow[i] = ruleDigit[2];
			}
			if (cellA == true && cellB == true && cellC == false) {
				tempRow[i] = ruleDigit[1];
			}
			if (cellA == true && cellB == true && cellC == true) {
				tempRow[i] = ruleDigit[0];
			}
		}
		/*
		for (int i = 0; i < row.length; i++) {
			tempRow[i] = row[i];
		}
		*/
		//rowLength += 1;
		
		//Increments row length
		//row = new boolean[row.length + 2];
		for (int i = 0; i < tempRow.length; i++) {
			row[i] = tempRow[i];
		}
		tempRow = new boolean[row.length];
		
		//Increments step number
		stepNumber ++;
		gr.drawImage(drawing, 0,0, this);
	}
}
	
