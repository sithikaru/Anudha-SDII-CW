import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cinema {
    private static Scanner scanner = new Scanner(System.in);
    private static int[][] seatsPlan = new int[3][16];
    private static Ticket[][] tickets = new Ticket[3][16];
    private static ArrayList<Ticket> ticketList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to The London Lumiere");
        System.out.println("------------------------------");
        init();

        boolean running = true;
        while (running) {
            int choice = menu();

            switch (choice) {
                case 1:
                    buy_ticket();
                    break;
                case 2:
                    cancel_ticket();
                    break;
                case 3:
                    print_seating_area();
                    break;
                case 4:
                    find_first_available();
                    break;
                case 5:
                    printTicketsInfo();
                    break;
                case 6:
                    searchTicket();
                    break;
                case 7:
                    sortTickets();
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
        print_seating_area();
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

    private static void print_seating_area() {
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

    private static void buy_ticket() {
        System.out.println("Buy a Ticket");
        System.out.println("------------");

        // Get Row
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
            // Get person information
            scanner.nextLine(); // Consume newline
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            Person person = new Person(name, surname, email);

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

            Ticket ticket = new Ticket(row, seat, price, person);
            tickets[row - 1][seat - 1] = ticket;
            ticketList.add(ticket);
            seatsPlan[row - 1][seat - 1] = 1; // Mark seat as sold

            System.out.println("Ticket purchased :- row " + row + ", Seat " + seat);
            System.out.printf("Price: £%.2f%n", price);
        } else {
            System.out.println("Seat not available! Choose another seat.");
        }
    }

    private static void cancel_ticket() {
        System.out.println("Cancel a Ticket");
        System.out.println("---------------");

        // Row
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

        // Seat number
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

        // Check if seat is sold and cancel it
        if (seatsPlan[row - 1][seat - 1] == 1) {
            seatsPlan[row - 1][seat - 1] = 0; // Mark as available
            Ticket ticket = tickets[row - 1][seat - 1];
            tickets[row - 1][seat - 1] = null; // Remove the ticket
            ticketList.remove(ticket); // Remove from list
            System.out.println("Ticket cancelled.");
        } else {
            System.out.println("This seat is currently not purchased. Cannot Cancel!");
        }
    }

    private static void find_first_available() {
        System.out.println("Find First Available Seat");
        System.out.println("-------------------------");

        boolean found = false;
        for (int row = 0; row < 3; row++) {
            for (int seat = 0; seat < 16; seat++) {
                if (seatsPlan[row][seat] == 0) {
                    System.out.println("The first available seat is in Row - " + (row + 1) + " Seat - " + (seat + 1));
                    found = true;
                    return;
                }
            }
        }
        if (!found) {
            System.out.println("There are no available seats. All the seats are occupied!");
        }
    }

    private static void printTicketsInfo() {
        System.out.println("Tickets Information");
        System.out.println("-------------------");

        double totalPrice = 0.0;
        if (ticketList.isEmpty()) {
            System.out.println("No tickets have been sold.");
        } else {
            for (Ticket ticket : ticketList) {
                ticket.printTicketInfo();
                totalPrice += ticket.getPrice();
                System.out.println();
            }
            System.out.printf("Total price of tickets sold: £%.2f%n", totalPrice);
        }
    }

    private static void searchTicket() {
        System.out.println("Search for a Ticket");
        System.out.println("-------------------");

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

        Ticket ticket = tickets[row - 1][seat - 1];
        if (ticket != null) {
            ticket.printTicketInfo();
        } else {
            System.out.println("This seat is available.");
        }
    }

    private static void sortTickets() {
        System.out.println("Sort Tickets by Price");
        System.out.println("---------------------");

        if (ticketList.isEmpty()) {
            System.out.println("No tickets have been sold.");
        } else {
            ticketList.sort((t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));
            for (Ticket ticket : ticketList) {
                ticket.printTicketInfo();
                System.out.println();
            }
        }
    }
}
