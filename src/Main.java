import Operations.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MainMenu menu = new MainMenu();
        EmployeeShow viewer = new EmployeeShow();

        int choice;

        while (true) {
            menu.menu();
            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1:
                    new EmployeeAdd().createEmployee();
                    break;

                case 2:
                    System.out.print("Enter Employee ID: ");
                    String viewID = input.nextLine();
                    viewer.viewEmployee(viewID);
                    break;

                case 3:
                    System.out.print("Enter Employee ID: ");
                    String updateID = input.nextLine();
                    System.out.print("Enter field to update (name/contact/email/position/salary): ");
                    String field = input.nextLine();
                    System.out.print("Enter new value: ");
                    String newValue = input.nextLine();
                    new EmployeeUpdate().updateEmployee(updateID, field, newValue);
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    String deleteID = input.nextLine();
                    new RemoveEmployee().removeEmployee(deleteID);
                    break;

                case 5:
                    new Exit().exit();
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
