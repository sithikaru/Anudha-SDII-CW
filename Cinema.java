import java.util.InputMismatchException;
import java.util.Scanner;

public class Cinema {
    private static Scanner scanner = new Scanner(System.in);
    private static int[][] seatsPlan = new int[3][16];

    public static void main(String[] args) {
        System.out.println("Welcome to The London Lumiere");
        System.out.println("------------------------------");
        init();

        boolean running = true;
        while (running) {
            int choice = menu();

            switch (choice) {
                case 1:
                    buyTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                SeatsView();
                    break;
                case 4:
                    firstAvailable();
                    break;
                case 5:
                    // printTicketsInfo();
                    break;
                case 6:
                    // searchTicket();
                    break;
                case 7:
                    // sortTickets();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using The London Lumiere system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void init() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                seatsPlan[i][j] = 0;
            }
        }
    }

    private static int menu() {
        int choice = 0;
        boolean validInput = false;
        SeatsView();
        while (!validInput) {
            System.out.println("Please select an option:");
            System.out.println("1. Buy a ticket");
            System.out.println("2. Cancel a ticket");
            System.out.println("3. Display seating area");
            System.out.println("4. Find first available seat");
            System.out.println("5. Print tickets info");
            System.out.println("6. Search for a ticket");
            System.out.println("7. Sort tickets");
            System.out.println("8. Exit");

            System.out.print("Enter your choice (1-8): ");

            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 8) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number 1-8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
        }
        scanner.nextLine();

        return choice;
    }

    private static void SeatsView() {
        System.out.println("\t");
        System.out.println("\t");
        System.out.println("\t\tCurrent Seating Plan:");
        System.out.println("\t\t--------------------");
        System.out.println("\t\t       SCREEN       ");
        System.out.println("\t\t--------------------");

        // Print seat numbers
        System.out.print("  ");
        for (int i = 1; i <= 16; i++) {
            System.out.printf("%2d ", i);
            if (i == 8)
                System.out.print(" ");
        }
        System.out.println();

        // Print rows with seats
        String[] rowLabels = { "R1", "R2", "R3" };
        String[] prices = { "£12", "£10", "£8" };

        for (int i = 0; i < 3; i++) {
            System.out.print(rowLabels[i] + " ");
            for (int j = 0; j < 16; j++) {
                if (seatsPlan[i][j] == 0) {
                    System.out.print("O  ");
                } else {
                    System.out.print("X  ");
                }
                if (j == 7)
                    System.out.print(" ");
            }
            System.out.println(prices[i]);
        }
        System.out.println("\nO = Available, X = Sold");
    }

    private static void buyTicket() {
        System.out.println("Buy a Ticket");
        System.out.println("------------");

        // get Row
        int row = 0;
        boolean validRow = false;
        while (!validRow) {
            System.out.print("Enter row number (1-3): ");
            try {
                row = scanner.nextInt();
                if (row >= 1 && row <= 3) {
                    validRow = true;
                } else {
                    System.out.println("Enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number.");
                scanner.next();
            }
        }

        // Get seat number
        int seat = 0;
        boolean validSeat = false;
        while (!validSeat) {
            System.out.print("Enter seat number (1-16): ");
            try {
                seat = scanner.nextInt();
                if (seat >= 1 && seat <= 16) {
                    validSeat = true;
                } else {
                    System.out.println("Enter a number between 1 and 16.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number.");
                scanner.next();
            }
        }

        // Check if seat is available
        if (seatsPlan[row - 1][seat - 1] == 0) {
            seatsPlan[row - 1][seat - 1] = 1; // Mark seat as sold
            System.out.println("Ticket purchased :- row " + row + ", Seat " + seat);

            // Display price
            double price;
            switch (row) {
                case 1:
                    price = 12.0;
                    break;
                case 2:
                    price = 10.0;
                    break;
                case 3:
                    price = 8.0;
                    break;
                default:
                    price = 0.0;
            }
            System.out.printf("Price: £%.2f", price);
        } else {
            System.out.println("Seat not available! choose another seat.");
        }

        scanner.nextLine();
    }

    private static void cancelTicket() {
        System.out.println("Cancel a Ticket");
        System.out.println("---------------");

        // row
        int row = 0;
        boolean validRow = false;
        while (!validRow) {
            System.out.print("Enter row number (1-3): ");
            try {
                row = scanner.nextInt();
                if (row >= 1 && row <= 3) {
                    validRow = true;
                } else {
                    System.out.println("Enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number.");
                scanner.next();
            }
        }

        // seat number
        int seat = 0;
        boolean validSeat = false;
        while (!validSeat) {
            System.out.print("Enter seat number (1-16): ");
            try {
                seat = scanner.nextInt();
                if (seat >= 1 && seat <= 16) {
                    validSeat = true;
                } else {
                    System.out.println("Enter a number between 1 and 16.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number.");
                scanner.next();
            }
        }

        // check if seeat is sold and cancel it
        if (seatsPlan[row - 1][seat - 1] == 1) {
            seatsPlan[row - 1][seat - 1] = 0; // mark as available
            System.out.println("Ticket cancelled.");
        } else {
            System.out.println("This seat is currently not purchased. Cannot Cancel!");
        }

        scanner.nextLine();
    }

    private static void firstAvailable() {
        System.out.println("Find First Available Seat");
        System.out.println("-------------------------");
    
        boolean found = false;
        for (int row = 0; row < 3; row++) {
            for (int seat = 0; seat < 16; seat++) {
                if (seatsPlan[row][seat] == 0) {
                    System.out.println("The first available seat is in Row - "+ (row + 1)+ " Seat - "+ (seat + 1));
                    found = true;
                    return;
                }
            }
        }
        if (!found) {
            System.out.println("There are no available seats. All the seats are occupied!");
        }
    }
}