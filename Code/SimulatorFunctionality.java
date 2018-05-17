import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.chrono.MinguoChronology;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.FileWriter;

public class SimulatorFunctionality {
	// Initialization
	int topology_Adj_matrix[][];
	String br_line;
	BufferedReader bufred = null;
	String line_wise_read[];
	String present_Line[];
	int length_matrix = 0;
	int[] visited_node;
	boolean SquareCheck = false;
	int pre_Defined[];
	int dist[];
	int next_hop = 0;
	int min_value;
	int source;
	int dest;
	int rtr_low = 0;
	static String inputFilePath = "";

	// To take the input as file from user and create network topology matrix
	public int[][] take_file() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the filename (Please Enter the path of the file)");
		inputFilePath = in.nextLine();
		try {
			bufred = new BufferedReader(new FileReader(inputFilePath));
			// reading each line from the input file
			line_wise_read = bufred.readLine().trim().split("\\s+");
			// To find the number of routers
			length_matrix = line_wise_read.length;
			// Generating the Adjacency matrix (Square matrix) with dimensions as no. of
			// routers
			topology_Adj_matrix = new int[length_matrix][length_matrix];
			// closed the bufred here
			bufred.close();
			// Reading the file to create the network topology
			BufferedReader buf_data = new BufferedReader(new FileReader(inputFilePath));
			String lines = "";
			int line_count = 0;
			while ((lines = buf_data.readLine()) != null) {
				// It will read the current line
				present_Line = lines.trim().split("\\s+");
				for (int i = 0; i < length_matrix; i++) {
					// each value in the matrix is assigned value by parsing into int
					topology_Adj_matrix[line_count][i] = Integer.parseInt(present_Line[i]);
				}
				// Incrementing the line count
				line_count++;
			}

			for (int i = 0; i < line_count; i++) {
				for (int j = 0; j < topology_Adj_matrix[i].length; j++) {
					if (topology_Adj_matrix[i].length == line_count)
						SquareCheck = true;
				}

			}
			// show_matrix() method is called in order to display the matrix
			// display the matrix only if it is a square matrix
			if (SquareCheck == true) {
				System.out.println("The # of Routers = " + length_matrix);
				show_matrix();
			}
		} catch (Exception fileNotFound) {
			// If the user input file was not found and also if the matrix in the text file
			// was not square matrix then prints "INCORRECT INPUT !"
			System.out.println("INCORRECT INPUT ! ");
		}
		// returns the matrix
		return topology_Adj_matrix;
	}

	// Display the adjacency matrix for the topology
	public void show_matrix() {
		System.out.println("********** The network topology is shown below: **********");
		// Display the router name in column
		for (int i = 0; i < length_matrix; i++) {
			System.out.print("\t" + "R" + (i));
		}
		System.out.println("\n");
		// Display router name in row
		for (int i = 0; i < length_matrix; i++) {
			System.out.print("R" + (i) + "\t");
			// Display cost for each entry in the matrix
			for (int j = 0; j < length_matrix; j++) {
				System.out.print(topology_Adj_matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	// Get the shortest path between source and every other routers comparing the
	// node which is not visited's cost with some node through which j is reached
	// (Dw + Dwj) where j is destination

	public Pair getShortestPaths(int sourceRouter, int[][] adjacencyMatrix) {
		// creating a copy of adjacency matrix
		int matrixLength = adjacencyMatrix.length;
		int newAdjMatrix[][] = new int[matrixLength][matrixLength];
		for (int i = 0; i < adjacencyMatrix.length; ++i) {
			for (int j = 0; j < adjacencyMatrix.length; ++j) {
				newAdjMatrix[i][j] = adjacencyMatrix[i][j];
			}
		}

		int[] path = new int[matrixLength];
		int[] cost = new int[matrixLength];
		boolean[] visited = new boolean[matrixLength];

		final int MAX_VAL = 99999;
		// If the entry in the matrix is -1 then set it to 99999
		for (int i = 0; i < matrixLength; i++) {
			path[i] = sourceRouter;
			cost[i] = MAX_VAL;
			visited[i] = false;
			for (int j = 0; j < matrixLength; j++) {
				if (adjacencyMatrix[i][j] == -1) {
					newAdjMatrix[i][j] = MAX_VAL;
				}
			}
		}

		cost[sourceRouter] = 0;
		visited[sourceRouter] = true;
		int currMin = 0;
		int currMinIndex = sourceRouter;
		for (int i = 0; i < matrixLength; i++) {
			for (int j = 0; j < matrixLength; j++) {
				if (!visited[j] && cost[j] > currMin + newAdjMatrix[currMinIndex][j]) {
					cost[j] = currMin + newAdjMatrix[currMinIndex][j];
					path[j] = currMinIndex;
				}
			}
			visited[currMinIndex] = true;
			// printArray(cost);
			int[] retArray = findMinimum(cost, visited);
			currMin = retArray[0];
			currMinIndex = retArray[1];
		}
		return new Pair(path, cost);
	}

	// used to display the shortest distance and path from source and destination
	public void getShortestPathBetweenRouters(int adjacencyMatrix[][], int source, int destination) {
		Pair result = getShortestPaths(source, adjacencyMatrix);
		// Print the shortest distance from Source to destination
		if (result.shortestDist[destination] == 99999) {
			System.out.println("\n There is no path from " + source + " to " + destination + "\n");
		} else {
			System.out.println("Shortest distance from " + source + " to " + destination + " is: "
					+ result.shortestDist[destination]);
			// Print the shortest path from Source to destination
			System.out.println("Shortest path from " + source + " to " + destination + " is: ");

			int currIndex = destination;
			System.out.print(currIndex);
			do {
				currIndex = result.shotestPath[currIndex];
				System.out.print("<--" + currIndex);
			} while (currIndex != source);

			System.out.println();
		}
	}

	// Finds the minimum cost by comparing currMin and cost[i]
	private int[] findMinimum(int[] cost, boolean[] visited) {
		// set the min. value as 99999
		int currMin = 99999;
		int currMinIndex = 0;
		for (int i = 0; i < cost.length; i++) {
			if (!visited[i] && currMin > cost[i]) {
				currMin = cost[i];
				currMinIndex = i;
			}
		}
		// returns the array with currMin and index of that router
		int[] retArray = new int[] { currMin, currMinIndex };
		return retArray;
	}

	// Get the forwarding table for the router
	public void getForwardingTable(int adjacencyMatrix[][], int homeRouter) {
		// check whether the router was shout before
		boolean shutFlag = true;
		for (int i = 0; i < adjacencyMatrix.length; ++i) {
			if (adjacencyMatrix[homeRouter][i] != -1) {
				shutFlag = false;
				break;
			}
		}
		// If the router was not shut then get the forwarding table for it
		if (shutFlag == false) {
			Pair result = getShortestPaths(homeRouter, adjacencyMatrix);
			String[] forwardingTable = new String[adjacencyMatrix.length];
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				// if the router is the home or the source router then there is no interface
				// link
				if (i == homeRouter) {
					forwardingTable[i] = "-";
				} else {
					int currentIndex = i;
					// unless the value of the shortest path's current index is the home router, set
					// the present index as the value of the shortest path's current index
					while (result.shotestPath[currentIndex] != homeRouter) {
						currentIndex = result.shotestPath[currentIndex];
					}
					// when the value of shortest path's current index is equal to home router then
					// that is the interface link
					forwardingTable[i] = String.valueOf(currentIndex);
				}
			}
			// Display the forwarding table with two columns 1."Destination" and
			// 2."Interface"
			System.out.println("Destination     Interface Link");
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				System.out.println("   " + i + "                " + forwardingTable[i]);
			}
		} else {
			System.out.println("The Router was down so there is no forwarding table for it");
		}
	}

	// get the best broadcasting router for the topology by calculating the router
	// that has the shortest path to all other routers
	public void getBroadcastingRouter(int adjacencyMatrix[][]) {
		int sum = 0;
		int minDistance = Integer.MAX_VALUE;
		int routerWithMinDistance = 0;
		for (int i = 0; i < adjacencyMatrix.length; i++) {

			sum = getSumOfArray(getShortestPaths(i, adjacencyMatrix).shortestDist);

			if (sum < minDistance) {
				minDistance = sum;
				routerWithMinDistance = i;
			}
		}

		// Display table for all the routers and their min. cost
		System.out.println("Best Router for Broadcasting is router " + routerWithMinDistance + " with a total cost of "
				+ minDistance);
		System.out.println("All other Routers     Total Cost");

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			// Print the forwarding table with two columns 1."Destination" and 2.
			// "Interface"

			System.out.println("   " + i + "                   "
					+ getSumOfArray(getShortestPaths(i, adjacencyMatrix).shortestDist));

		}

	}

	// to sum each entry in the array and returns the sum
	private int getSumOfArray(int[] arr) {
		int sum = 0;
		for (int i : arr) {
			sum += i;
		}
		return sum;
	}

	// used to get shortestpath and shortestDist
	private class Pair {
		public int[] shotestPath;
		public int[] shortestDist;

		Pair(int[] shotestPath, int[] shortestDist) {
			this.shotestPath = shotestPath;
			this.shortestDist = shortestDist;
		}
	}

	// This method is used to printArray.I used to print cost and path arrays
	/*
	 * private static void printArray(int[] array) { for (int val : array) {
	 * System.out.print(val + " "); } System.out.println(); }
	 */
	// used to check if number is numeric by parsing it into integer from string
	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	// used for the modification in topology by setting the entire row and the
	// column of the router which is to be shut as -1
	public int[][] modificationOfTopology(int topology_Adj_matrix[][]) {
		int newAdjMatrix[][] = null;
		try {
			// Getting the path of the input file and writing the file
			FileWriter modifyFile = new FileWriter(inputFilePath);
			BufferedWriter removeRouter = new BufferedWriter(modifyFile);
			newAdjMatrix = topology_Adj_matrix;
			Scanner routerDownInput = new Scanner(System.in);
			// Enter the router that is down
			System.out.println("Enter the router # to be shut:");
			String downRouter = routerDownInput.nextLine();
			int rtrnum = Integer.parseInt(downRouter);
			// set the shutflag true in beginning and is used to check whether the router
			// was shut before or not
			boolean shutFlag = true;
			// Turns the shutFlag false if the router was already shut before
			for (int i = 0; i < topology_Adj_matrix.length; ++i) {
				if (topology_Adj_matrix[rtrnum][i] != -1) {
					shutFlag = false;
					break;
				}
			}
			if (shutFlag == false) {
				if (isNumeric(downRouter) == true) {
					rtr_low = Integer.parseInt(downRouter);
				} else {
					System.out.println("\n ERROR: Destination router # not valid "
							+ "\n Enter a valid router no. from 0 to " + (length_matrix - 1) + " \n");

				}
				if (rtr_low < 0 || rtr_low > (length_matrix - 1)) {
					System.out.println("\n ERROR: Destination router # not valid "
							+ "\n Enter a valid router no. from 0 to " + (length_matrix - 1) + " \n");

				} else {
					// Display the new matrix with the matrix value as -1 for the router that is
					// down
					for (int i = 0; i < (newAdjMatrix.length); i++) {
						System.out.print("\t" + "R" + (i));
					}
					System.out.println("\n");
					for (int i = 0; i < (newAdjMatrix.length); i++) {
						System.out.print("R" + (i) + "\t");
						for (int j = 0; j < (newAdjMatrix.length); j++) {
							if (i == rtr_low || j == rtr_low) {
								newAdjMatrix[i][j] = -1;
							}
							removeRouter.write(newAdjMatrix[i][j] + " ");
							System.out.print(newAdjMatrix[i][j] + "\t");
						}
						removeRouter.newLine();
						System.out.println("\n");
					}
					removeRouter.close();
				}
			} else {
				System.out.println("The Router was already down");
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return newAdjMatrix;

	}

}
