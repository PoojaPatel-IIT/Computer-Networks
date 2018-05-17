/**
 * 
 */

/**
 * @author DELL
 *
 */
import java.util.Scanner;

public class Simulator {

	// Initialization
	public static int Adj_matix[][];
	static String source_router;
	static String dest_router;
	static int topologyFlag = 0;

	// To check whether the input from the user is integer or not
	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// Creating an instance for SimulatorFunctionality() class to use its methods
		final SimulatorFunctionality functionality = new SimulatorFunctionality();
		// Initialization
		int router_source = 0;
		int router_dest = 0;
		boolean flag = false;
		int menu_choosed = 0;
		Scanner choice = new Scanner(System.in);
		// Displaying the main menu
		String network_topology_menu = ("---   Simulator Displays Following Menu   ---\n"
				+ "What would you like simulator to perform: \n" + "1. Creation of the Network Topology\n"
				+ "2. Building a forwarding table\n"
				+ "3. Generation of the shortest path from source to destination router\n"
				+ "4. Modification in topology\n" + "5. Best broadcasting router\n" + "6. Exit\n");

		do {
			// Display the Simulator menu
			System.out.println(network_topology_menu);
			// Enter the choice from the menu
			System.out.println("Enter your choice from the Menu displayed");
			// take input from user for choosing the options from menu
			String menu_selected = choice.nextLine();
			// check if the input by user for menu option isnumeric is true
			if (isNumeric(menu_selected) == true) {
				// if the input is numeric then parse it to integer
				menu_choosed = Integer.parseInt(menu_selected);
				// Lets user enter only 1-6 as the input for Menu options
				if (menu_choosed == 1 || menu_choosed == 2 || menu_choosed == 3 || menu_choosed == 4
						|| menu_choosed == 5 || menu_choosed == 6) {
					switch (menu_choosed) {
					case 1:
						// For network topology
						System.out.println("********** Network topology **********");
						// calls take_file() method from SimulatorFunctionality class to take input as
						// the text file and display adjacency matrix
						Adj_matix = functionality.take_file();
						// If the user inputs the file path or file name then set flag as true
						flag = true;
						System.out.println();
						break;

					case 2:
						// For Forwarding table
						if (flag == false) {
							// does not let user to go for case 2 if the topology text file is not given as
							// an input from user
							System.out.println("Please create a network topology before proceeding");
						} else {
							System.out.println("********** Forwarding Table **********");
							// Enter the Router no. for which forwarding table is to be created
							System.out.println("Enter Router number:");
							Scanner homeRouterInput = new Scanner(System.in);
							String homeRouter = homeRouterInput.nextLine();
							if (isNumeric(homeRouter) == true) {
								// if the input is numeric then parse it to integer
								router_source = Integer.parseInt(homeRouter);
							} else {
								System.out.println("\n ERROR: Destination router # not valid "
										+ "\n Enter a valid router no. from 0 to " + (functionality.length_matrix - 1)
										+ " \n");
								break;
							}
							// if the user enters the router number less or greater then the no. of
							// available router then asks user to enter valid choice
							if (router_source < 0 || router_source > (functionality.length_matrix - 1)) {
								System.out.println(
										"\n ERROR: Not valid router #" + "\n Enter a valid Router no. from 0 to "
												+ (functionality.length_matrix - 1) + " \n");
							} else {
								// calling getForwardingTable method from SimulatorFunctionality class
								functionality.getForwardingTable(Adj_matix, router_source);
							}
						}
						break;

					case 3:
						// For Dijkstra's Shortest path
						if (flag == false) {
							// System.out.println("Please create a network topology before proceeding");
						} else {
							// Enter the source router
							System.out.println("Enter the Source Router");
							Scanner src_input = new Scanner(System.in);
							source_router = src_input.nextLine();
							if (isNumeric(source_router) == true) {
								// if the input is numeric then parse to integer
								router_source = Integer.parseInt(source_router);
							} else {
								System.out.println("\n ERROR: Source router # not valid "
										+ "\n Enter a valid router no. from 0 to " + (functionality.length_matrix - 1)
										+ " \n");
								break;
							}
							// if the user enters the router number less or greater then the no. of
							// available router then asks user to enter valid choice
							if (router_source < 0 || router_source > (functionality.length_matrix - 1)) {
								System.out.println("\n ERROR: Source router # not valid "
										+ "\n Enter a valid router no. from 0 to " + (functionality.length_matrix - 1)
										+ " \n");
							}
							// Enter the destination router
							else {
								System.out.println("Enter the Destination Router");
								Scanner dest_input = new Scanner(System.in);
								dest_router = dest_input.nextLine();
								if (isNumeric(dest_router) == true) {
									// if the input is numeric then parse to integer
									router_dest = Integer.parseInt(dest_router);
								} else {
									System.out.println("\n ERROR: Destination router # not valid "
											+ "\n Enter a valid router no. from 0 to "
											+ (functionality.length_matrix - 1) + " \n");
									break;
								}
								// if the user enters the router number less or greater then the no. of
								// available router then asks user to enter valid choice
								if (router_dest < 0 || router_dest > (functionality.length_matrix - 1)) {
									System.out.println("\n ERROR: Destination router # not valid "
											+ "\n Enter a valid router no. from 0 to "
											+ (functionality.length_matrix - 1) + " \n");

								} else {
									// calls getShortestPathBetweenRouters method from SimulatorFunctionality class
									functionality.getShortestPathBetweenRouters(Adj_matix, router_source, router_dest);
								}
							}

							break;
						}

					case 4:
						if (flag == false) {
							System.out.println("Please create a network topology before proceeding");
						} else {
							// if case 3 is not done yet then ask the user to input the source and
							// destination router
							if (router_source == 0 && router_dest == 0) {
								// calls modificationOfTopology method from SimulatorFunctionality class to
								// display the matrix again
								functionality.modificationOfTopology(Adj_matix);
								System.out.println(
										"In order to display Shortest path and distance no Source Router was found"
												+ "\nEnter the Source Router : ");
								Scanner src_input = new Scanner(System.in);
								source_router = src_input.nextLine();
								if (isNumeric(source_router) == true) {
									// if the input is numeric then parse to integer
									router_source = Integer.parseInt(source_router);
								} else {
									System.out.println("\n ERROR: Source router # not valid "
											+ "\n Enter a valid router no. from 0 to "
											+ (functionality.length_matrix - 1) + " \n");
									break;
								}
								if (router_source < 0 || router_source > (functionality.length_matrix - 1)) {
									System.out.println("\n ERROR: Source router # not valid "
											+ "\n Enter a valid router no. from 0 to "
											+ (functionality.length_matrix - 1) + " \n");
								}
								// Enter the destination router
								else {
									System.out.println(
											"In order to display Shortest path and distance no destination Router was found"
													+ "\nEnter the Destination Router : ");
									Scanner dest_input = new Scanner(System.in);
									dest_router = dest_input.nextLine();
									if (isNumeric(dest_router) == true) {
										router_dest = Integer.parseInt(dest_router);
									} else {
										System.out.println("\n ERROR: Destination router # not valid "
												+ "\n Enter a valid router no. from 0 to "
												+ (functionality.length_matrix - 1) + " \n");
										break;
									}
									// if the user enters the router number less or greater then the no. of
									// available router then asks user to enter valid choice
									if (router_dest < 0 || router_dest > (functionality.length_matrix - 1)) {
										System.out.println("\n ERROR: Destination router # not valid "
												+ "\n Enter a valid router no. from 0 to "
												+ (functionality.length_matrix - 1) + " \n");
									} else {
										// Display the shortest path after taking the source and the destination router
										// from case 4
										functionality.getShortestPathBetweenRouters(Adj_matix, router_source,
												router_dest);
									}
								}
							} else {
								// calling modificationOfTopology method from SimulatorFunctionality class
								functionality.modificationOfTopology(Adj_matix);
								functionality.getShortestPathBetweenRouters(Adj_matix, router_source, router_dest);
							}
						}
						break;

					case 5:
						// To get the best broadcasting router
						if (flag == false) {
							System.out.println("Please create a network topology before proceeding");
						} else {
							System.out.println("*****Best broadcasting router******");
							// calling getBroadcastingRouter method from SimulatorFunctionality class
							functionality.getBroadcastingRouter(Adj_matix);
						}
						break;

					case 6:
						// To exit from the Simulator
						System.out
								.println("Exiting from simulation" + "\nGOOD BYE !" + "\nCS542-04 Fall Project 2017 ");
						System.exit(0);
						break;
					}
				} else {
					System.out.println("\n Your choice should be from 1 - 6 Menu Options \n \n");
				}
			} else {
				System.out.println("\n ERROR:  Choice not valid " + "\nEnter a valid Menu option from 1 - 6 ");

			}
		} while (menu_choosed != 6);

	}

}
