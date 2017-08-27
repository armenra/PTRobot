import javax.swing.JOptionPane;

// Instead of hard-coding numbers into each of the loops I've used
// variables to keep track of how the arm is to be moved when moving
// blocks.

// This is by no means the only way of achieving the minimum number of
// moves for the robot - it is intended as an example only and it was not
// used as a guide when the assignments were marked.

class RobotControl
{
	private Robot r;
	public static StringBuilder sb;
	
	public RobotControl(Robot r)
	{
		this.r = r;
	}

	public void control(int barHeights[], int blockHeights[])
	{
		 //sampleControlMechanism(barHeights, blockHeights);
		run(barHeights, blockHeights);

	}

	public void sampleControlMechanism(int barHeights[], int blockHeights[])
	{
		// Internally the Robot object maintains the value for Robot height(h),
		// arm-width (w) and picker-depth (d).

		// These values are displayed for your convenience
		// These values are initialised as h=2 w=1 and d=0

		// When you call the methods up() or down() h will be changed
		// When you call the methods extend() or contract() w will be changed
		// When you call the methods lower() or raise() d will be changed

		// sample code to get you started
		// Try running this program with obstacle 555555 and blocks of height
		// 2222 (default)
		// It will work for fisrt block only
		// You are free to introduce any other variables

		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		int sourceHt = 12;

		// For Parts (a) and (b) assume all four blocks are of the same height
		// For Part (c) you need to compute this from the values stored in the
		// array blockHeights
		// i.e. sourceHt = blockHeights[0] + blockHeights[1] + ... use a loop!

		int targetCol1Ht = 0; // Applicable only for part (c) - Initially empty
		int targetCol2Ht = 0; // Applicable only for part (c) - Initially empty

		// height of block just picked will be 3 for parts A and B
		// For part (c) this value must be extracing the topmost unused value
		// from the array blockHeights

		int blockHt = 3;

		// clearance should be based on the bars, the blocks placed on them,
		// the height of source blocks and the height of current block

		// Initially clearance will be determined by the blocks at source
		// (3+3+3+3=12)
		// as they are higher than any bar and block-height combined

		int clearence = 12;

		// Raise it high enough - assumed max obstacle = 4 < sourceHt

		// this makes sure robot goes high enough to clear any obstacles
		while (h < clearence + 1)
		{
			// Raising 1
			r.up();

			// Current height of arm1 being incremented by 1
			h++;
		}

		System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// this will need to be updated each time a block is dropped off
		int extendAmt = 10;

		// Bring arm 2 to column 10
		while (w < extendAmt)
		{
			// moving 1 step horizontally
			r.extend();

			// Current width of arm2 being incremented by 1
			w++;
		}

		System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// lowering third arm - the amount to lower is based on current height
		// and the top of source blocks

		// the position of the picker (bottom of third arm) is determined by h
		// and d
		while (h - d > sourceHt + 1)
		{
			// lowering third arm
			r.lower();

			// current depth of arm 3 being incremented
			d++;
		}

		// picking the topmost block
		r.pick();

		// topmost block is assumed to be 3 for parts (a) and (b)
		blockHt = 3;

		// When you pick the top block height of source decreases
		sourceHt -= blockHt;

		// raising third arm all the way until d becomes 0
		while (d > 0)
		{
			r.raise();
			d--;
		}

		System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// why not see the effect of changing contractAmt to 6 ?
		int contractAmt = 7;

		// Must be a variable. Initially contract by 3 units to get to column 3
		// where the first bar is placed (from column 10)

		while (contractAmt > 0)
		{
			r.contract();
			contractAmt--;
		}

		System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// You need to lower the third arm so that the block sits just above the
		// bar
		// For part (a) all bars are initially set to 7
		// For Parts (b) and (c) you must extract this value from the array
		// barHeights

		int currentBar = 0;

		// lowering third arm
		while ((h - 1) - d - blockHt > barHeights[currentBar])
		{
			r.lower();
			d++;
		}

		System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// dropping the block
		r.drop();

		// The height of currentBar increases by block just placed
		barHeights[currentBar] += blockHt;

		// raising the third arm all the way
		while (d > 0)
		{
			r.raise();
			d--;
		}
		System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// This just shows the message at the end of the sample robot run -
		// you don't need to duplicate (or even use) this code in your program.

		JOptionPane.showMessageDialog(null,
				"You have moved one block from source "
						+ "to the first bar position.\n"
						+ "Now you may modify this code or "
						+ "redesign the program and come up with "
						+ "your own method of controlling the robot.",
				"Helper Code Execution", JOptionPane.INFORMATION_MESSAGE);
		// You have moved one block from source to the first bar position.
		// You should be able to get started now.
	}

	public void run(int barHeights[], int blockHeights[])
	{
		final int maxHt = 13; //Maximum height of the robots main arm
		final int sourceColumn = 10;//The column of the source stack
		final int contractBlockOne = 9; //Contract 9 times from column 10 to reach column 1
		final int contractBlockTwo = 8; //Contract 8 times from column 10 to reach column 2

		int highestObstacle = 0;//Represents the highest obstacle the robot current faces
		int sourceHt = 0;//The height of the original stack, gets smaller during process
		int targetCol1Ht = 0;//The column where blocks of size 1 are placed, this is it's height
		int targetCol2Ht = 0;//The column where blocks of size 2 are placed, this is it's height
		int currentBar = 0;//The next bar where a block of size 3 will be placed
		
		int height = 2;//The height of the robot
		int armDepth = 1;//The depth of the robots arm
		int currentColumn = 1;//The starting column
	
		int contractAmt = 0;
		int lowerAmt = 0;
		
		/**
		 * When the program starts all the blocks have their heights added together this is the initial source height (sourceHt)
		 * */
		for (int j = 0; j < blockHeights.length; j++) {
			sourceHt += blockHeights[j];
		}

		/**
		 * Start of the loop, will sequentially move the blocks from the source stack to the appropriate positions
		 * the block at the top of the stack is the last one in the array so int i will be assigned (the number of blocks - 1)
		 * and will continue until i is equal to 0
		 * */
		for (int i = blockHeights.length - 1; i >= 0; i--) {

			/**
			 * Find the highestObstacle for the robot by checking the height of the target columns,source stacks and the bars
			 * */
			highestObstacle = targetCol1Ht;
			if (targetCol2Ht > targetCol1Ht) {
				highestObstacle = targetCol2Ht;
			}
			for (int j = 0; j < barHeights.length; j++) {
				if (barHeights[j] > highestObstacle) {
					highestObstacle = barHeights[j];
				}
			}

			// 1. Raise the height up to highest obstacle plus the blockHt
			/**
			 * Adjust the height of the robot so it can reach past the highest obstacle from its current location.
			 * At column 1 the obstacles are: column 2, all bars, source stack
			 * At column 2 the obstacles are: all the bars and source stack
			 * At bar x the obstacles are all bars to the right and source stack 
			 * */
			if (i == blockHeights.length - 1) {
				if (sourceHt >= highestObstacle + blockHeights[i]) {
					while (height <= sourceHt) {
						height++;
						r.up();
					}
				} else {
					while (height - blockHeights[i] <= highestObstacle) {
						height++;
						r.up();
					}
				}
			}
			
			// 2. extend to the end
			for (int j = currentColumn; j < sourceColumn; j++) {
				currentColumn++;
				r.extend();
			}

			// 3. lower and grab the block
			while (armDepth + sourceHt < height) {
				armDepth++;
				r.lower();
			}
			r.pick();

			//Reduce the height of the source stack by the current block being picked up
			sourceHt -= blockHeights[i];

			/**
			 * Find the location to put this block using its size, as well as the highest obstacle
			 * it will encounter reaching this column
			 * */
			if (blockHeights[i] == 1) {
				highestObstacle = targetCol1Ht;
				if (targetCol2Ht > targetCol1Ht) {
					highestObstacle = targetCol2Ht;
				}
				for (int j = 0; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			} else if (blockHeights[i] == 2) {
				highestObstacle = targetCol2Ht;
				for (int j = 0; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			} else if (blockHeights[i] == 3) {
				highestObstacle = barHeights[currentBar];
				for (int j = currentBar; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			}

			// 4. raise so that blockHt passes highest obstacle and lower the
			// arm and lower then drop it in the correct
			// location depending on blockHt
			// for Block of ht 3 contract (7 - currentBar)
			while (height - armDepth - blockHeights[i] < highestObstacle) {
				if (armDepth > 1) {
					armDepth--;
					r.raise();
				} else {
					break;
				}
			}

			//Calculate contract amount and lower amount
			if (blockHeights[i] == 1) {
				contractAmt = contractBlockOne;
				lowerAmt = height - blockHeights[i] - targetCol1Ht - armDepth;
				targetCol1Ht += blockHeights[i];
			} else if (blockHeights[i] == 2) {
				contractAmt = contractBlockTwo;
				lowerAmt = height - blockHeights[i] - targetCol2Ht - armDepth;
				targetCol2Ht += blockHeights[i];
			} else if (blockHeights[i] == 3) {
				contractAmt = 7 - currentBar;
				lowerAmt = height - blockHeights[i] - barHeights[currentBar] - armDepth;
				barHeights[currentBar] += blockHeights[i];
				currentBar++;
			}
			
			for (int j = 0; j < contractAmt; j++) {
				currentColumn--;
				r.contract();
			}

			for (int j = 0; j < lowerAmt; j++) {
				armDepth++;
				r.lower();
			}

			r.drop();

			if (i == 0) {
				break;
			}
			
			// 5. raise the arm again so that it can pass obstacles and grab the
			// next block

			highestObstacle = sourceHt;
			//Add code that also takes into account the current column 
			//and then the highest obstacle is everything to its right
			
			if (blockHeights[i - 1] == 1) {
				if (targetCol2Ht > highestObstacle) {
					highestObstacle = targetCol2Ht;
				}
				for (int j = 0; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			} else if (blockHeights[i - 1] == 2) {
				if (barHeights[0] > highestObstacle) {
					highestObstacle = barHeights[0];
				}
				for (int j = 0; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			} else if (blockHeights[i - 1] == 3) {
				if (barHeights[currentBar] > highestObstacle) {
					highestObstacle = barHeights[currentBar];
				}
				for (int j = currentBar; j < barHeights.length; j++) {
					if (barHeights[j] > highestObstacle) {
						highestObstacle = barHeights[j];
					}
				}
			}

			// Adjust the height before the armDepth
			while (height - blockHeights[i - 1] <= highestObstacle) {
				if (height < maxHt) {
					height++;
					r.up();
				}else{
					break;
				}
			}

			highestObstacle=sourceHt;
			if(currentColumn==1){
				//highest obstacle is stack 2, and the bars
				if(targetCol2Ht>highestObstacle){
					highestObstacle=targetCol2Ht;
				}
				for(int j=0;j<barHeights.length;j++){
					if(barHeights[j]>highestObstacle){
						highestObstacle=barHeights[j];
					}
				}
			}else if(currentColumn==2){
				//highest obstacle is the bars
				for(int j=0;j<barHeights.length;j++){
					if(barHeights[j]>highestObstacle){
						highestObstacle=barHeights[j];
					}
				}
			}else{
				//find the current bar and everything to the right is an obstacle
				for(int j=currentBar;j<barHeights.length;j++){
					if(barHeights[j]>highestObstacle){
						highestObstacle=barHeights[j];
					}
				}
			}
			
			while (height - armDepth < highestObstacle) {
				armDepth--;
				r.raise();
			}

		}
	}
}
