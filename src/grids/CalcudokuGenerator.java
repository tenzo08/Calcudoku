package grids;

import static main.CalcudokuGame.numberOfGrids;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CalcudokuGenerator {
	private Grid grid[][] = new Grid[numberOfGrids][numberOfGrids];
	
	public CalcudokuGenerator() {
		resetValues();
		initializeBeforeSolveSudoku();
		solveSudoku();
		groupings();
		addGrid();
	}
	
	private void resetValues() {
		setGrid(new Grid[numberOfGrids][numberOfGrids]);
		
		for(int row = 0; row < numberOfGrids; row++) {
	          for(int col = 0; col < numberOfGrids; col++) {
	        	  getGrid()[row][col] = new Grid(0, row, col);
	        	  getGrid()[row][col].setValue(0);
	          }
		}
	}
	
	private void initializeBeforeSolveSudoku() {
		Random random = new Random();
		
        for (int i = 0; i < numberOfGrids; i++) {
            int value = random.nextInt(1, numberOfGrids + 1);

            while (valueExistsInRow(value, i)) {
                value = random.nextInt(1, numberOfGrids + 1);
            }

            getGrid()[0][i].setValue(value);
        }
    }
	
	private boolean valueExistsInRow(int value, int endIndex) {
        for (int i = 0; i < endIndex; i++) {
            if (getGrid()[0][i].getValue() == value) {
                return true;
            }
        }
        return false;
    }
	
	//This code was based on the code of a youtube channel called Coding with John
	//The link of the video is here: https://youtu.be/mcXc8Mva2bA?si=_SZXjesvzkbNe2v5
	private boolean solveSudoku() {
        int size = getGrid().length;

        for (int row = 0; row < numberOfGrids; row++) {
            for (int col = 0; col < numberOfGrids; col++) {
                if (getGrid()[row][col].getValue() == 0) {
                    for (int num = 1; num <= size; num++) {
                        if (isValidMove(getGrid(), row, col, num)) {
                        	getGrid()[row][col].setValue(num);
                            if (solveSudoku()) {
                                return true;
                            }
                            getGrid()[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
	
	//This code was based on the code of a youtube channel called Coding with John
	//The link of the video is here: https://youtu.be/mcXc8Mva2bA?si=_SZXjesvzkbNe2v5
    private boolean isValidMove(Grid[][] grids, int row, int col, int num) {
        int size = grids.length;

        for (int i = 0; i < size; i++) {
            if (grids[row][i].getValue() == num || grids[i][col].getValue() == num) {
                return false;
            }
        }
        int subgridSize = (int) Math.sqrt(size);
        int subgridRow = row - row % subgridSize;
        int subgridCol = col - col % subgridSize;

        for (int i = 0; i < subgridSize; i++) {
            for (int j = 0; j < subgridSize; j++) {
                try {
                    if (grids[i + subgridRow][j + subgridCol].getValue() == num) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        return true;
    }
    
    private void groupings() {
    	int numberOfTotalBlocks = numberOfGrids * numberOfGrids;
    	
        while (numberOfTotalBlocks > 0) {
            int numberOfCombinations = 0;

            for (int y = 0; y < numberOfGrids; y++) {
                for (int x = 0; x < numberOfGrids; x++) {
                    if (!getGrid()[y][x].isGrouped()) {
                        int result = 0;
                        int XWithResult = -1;
                        int YWithResult = -1;
                        int numberOfBlocks = 0;
                        if(numberOfGrids > 6) {
                        	numberOfBlocks = ThreadLocalRandom.current().nextInt(2, 5);
                    	} else {
                    		numberOfBlocks = ThreadLocalRandom.current().nextInt(1, 3);
                    	}
                        int currentX = x;
                        int currentY = y;

                        while (numberOfBlocks >= 0) {
                            boolean add = ThreadLocalRandom.current().nextBoolean();

                            if (add) {
                                result += getGrid()[currentY][currentX].getValue();
                                grid[currentY][currentX].setGroupIndex(numberOfCombinations);
                                grid[currentY][currentX].setGrouped(true);
                                if(XWithResult == -1 && YWithResult == -1) {
                                	XWithResult = currentX;
                                	YWithResult = currentY;
                                }
                                numberOfTotalBlocks--;

                                currentX++;
                                if (currentX == numberOfGrids) {
                                	currentX--;
                                    currentY++;
                                    if (currentY == numberOfGrids) break;
                                    if (grid[currentY][currentX].isGrouped()) break;
                                }

                                if (grid[currentY][currentX].isGrouped()) {
                                    currentX--;
                                    currentY++;
                                    if (currentY == numberOfGrids) break;
                                    if (grid[currentY][currentX].isGrouped()) break;
                                }
                            } else {
                                result += grid[currentY][currentX].getValue();
                                grid[currentY][currentX].setGroupIndex(numberOfCombinations);
                                grid[currentY][currentX].setGrouped(true);
                                if(XWithResult == -1 && YWithResult == -1) {
                                	XWithResult = currentX;
                                	YWithResult = currentY;
                                }
                                numberOfTotalBlocks--;

                                currentY++;
                                if (currentY == numberOfGrids) {
                                    currentY--;
                                    currentX++;
                                    if (currentX == numberOfGrids) break;
                                    if (grid[currentY][currentX].isGrouped()) break;
                                }

                                if (grid[currentX][currentY].isGrouped()) {
                                    currentY--;
                                    currentX++;
                                    if (currentX == numberOfGrids) break;
                                    if (grid[currentY][currentX].isGrouped()) break;
                                }
                            }
                            numberOfBlocks--;
                        }
                        getGrid()[YWithResult][XWithResult].setGroupResult(result);
                        result = 0;
                        numberOfCombinations++;
                    }
                }
            }
        }
    }
    private void addGrid() {
    	for(int row = 0; row < numberOfGrids; row++) {
	        for(int col = 0; col < numberOfGrids; col++) {
	        	try {
	        		if(getGrid()[row][col].getGroupIndex() != getGrid()[row][col - 1].getGroupIndex()) {  
		        		getGrid()[row][col].setX(getGrid()[row][col].getX() + 2);
		        		getGrid()[row][col].setGridSizeX(getGrid()[row][col].getGridSizeX() - 2);
		        	}
	        	} catch(Exception e) {}
	        	try {
	        		if(getGrid()[row][col].getGroupIndex() != getGrid()[row][col - 1].getGroupIndex()) {  
		        		getGrid()[row][col - 1].setGridSizeX(getGrid()[row][col - 1].getGridSizeX() - 2);
		        	}
	        	} catch(Exception e) {}
	        	try {
	        		if(getGrid()[row][col].getGroupIndex() != getGrid()[row - 1][col].getGroupIndex()) {  
	        			getGrid()[row][col].setY(getGrid()[row][col].getY() + 2);
		        		getGrid()[row][col].setGridSizeY(getGrid()[row][col].getGridSizeY() - 2);
		        	}
	        	} catch(Exception e) {}
	        	try {
	        		if(getGrid()[row][col].getGroupIndex() != getGrid()[row - 1][col].getGroupIndex()) {
		        		getGrid()[row - 1][col].setGridSizeY(getGrid()[row - 1][col].getGridSizeY() - 2);
		        	}
	        	} catch(Exception e) {}
	        }
		}
    	for(int row = 0; row < numberOfGrids; row++) {
	        for(int col = 0; col < numberOfGrids; col++) {
	        	getGrid()[row][col].setBounds();
	        	getGrid()[row][col].setResult();
	        }
		}
	}

	public Grid[][] getGrid() {
		return grid;
	}

	public void setGrid(Grid grid[][]) {
		this.grid = grid;
	}
}
