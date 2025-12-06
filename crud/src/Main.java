import java.sql.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {

    // -------------------------------------------
    // SINGLE CLEAN CONNECTION METHOD (Step 1 Fix)
    // -------------------------------------------
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  // IMPORTANT LINE
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeedb",
                "root",
                ""
        );
    }

    // -------------------------------------------
    // CREATE DATA
    // -------------------------------------------
    public static void createData() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\t\t\t*Please Remember 'Your ID' number for further Updatation.....");
        System.out.print("\t\t\tEnter employee ID number : ");
        int id = sc.nextInt();
        System.out.print("\t\t\tEnter employee name : ");
        String name = sc.next();
        System.out.print("\t\t\tEnter employee department : ");
        String department = sc.next();
        System.out.print("\t\t\tEnter employee Location : ");
        String location = sc.next();

        String query = "INSERT INTO employees (id, name, department, location) VALUES ('" + id + "','" + name + "','" + department + "','" + location + "')";

        try {
            Connection con = getConnection(); // FIX
            Statement stmt = con.createStatement();
            int rows = stmt.executeUpdate(query);

            if (rows == 1) System.out.println("\t\t\tRow inserted successfully!");
            else System.out.println("\t\t\tSomething is Wrong!");

            menu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    // READ DATA
    // -------------------------------------------
    public static void readData() {
        String query = "SELECT * FROM employees";

        try {
            Connection con = getConnection(); // FIX
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                String location = rs.getString("location");

                System.out.println("\t\t\tEmp ID: " + id + "  Name: " + name + "  Dept: " + department + "  Location: " + location);
            }

            menu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    // DELETE DATA
    // -------------------------------------------
    public static void deleteData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter Employee ID number : ");
        int id = sc.nextInt();
        String query = "DELETE FROM employees WHERE id = '" + id + "'";

        try {
            Connection con = getConnection(); // FIX
            Statement stmt = con.createStatement();
            int rows = stmt.executeUpdate(query);

            if (rows == 1) System.out.println("\t\t\tRow deleted!");
            else {
                System.out.println("\t\t\tIncorrect ID. Try again.");
                deleteData();
            }

            menu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    // UPDATE DATA
    // -------------------------------------------
    public static void updateData() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\t\t\t1 - Update ID");
        System.out.println("\t\t\t2 - Update Name");
        System.out.println("\t\t\t3 - Update Location");
        System.out.println("\t\t\t4 - Update Department");
        System.out.print("\t\t Choose Option -->> ");
        int opt = sc.nextInt();

        String query = null;

        if (opt == 1) {
            System.out.print("\n\t\t\tEnter Name: ");
            String name = sc.next();
            System.out.print("\t\t\tEnter New ID: ");
            int newId = sc.nextInt();
            query = "UPDATE employees SET id='" + newId + "' WHERE name='" + name + "'";
        } else if (opt == 2) {
            System.out.print("\n\t\t\tEnter ID: ");
            int id = sc.nextInt();
            System.out.print("\t\t\tEnter New Name: ");
            String newName = sc.next();
            query = "UPDATE employees SET name='" + newName + "' WHERE id='" + id + "'";
        } else if (opt == 3) {
            System.out.print("\n\t\t\tEnter ID: ");
            int id = sc.nextInt();
            System.out.print("\t\t\tEnter New Location: ");
            String newLoc = sc.next();
            query = "UPDATE employees SET location='" + newLoc + "' WHERE id='" + id + "'";
        } else if (opt == 4) {
            System.out.print("\n\t\t\tEnter ID: ");
            int id = sc.nextInt();
            System.out.print("\t\t\tEnter New Department: ");
            String newDept = sc.next();
            query = "UPDATE employees SET department='" + newDept + "' WHERE id='" + id + "'";
        } else {
            System.out.println("\n\t\t\tInvalid option!");
            updateData();
        }

        try {
            Connection con = getConnection(); // FIX
            Statement stmt = con.createStatement();
            int rows = stmt.executeUpdate(query);

            if (rows == 1) System.out.println("\t\t\tUpdated successfully!");
            else System.out.println("\t\t\tUpdate failed!");

            menu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    // MENU
    // -------------------------------------------
    public static void menu() {
        System.out.println("\n\n\t\t\t--------- CRUD OPERATION USING JAVA --------\n");
        System.out.println("\t\t\t1 - Create Data");
        System.out.println("\t\t\t2 - Read Data");
        System.out.println("\t\t\t3 - Update Data");
        System.out.println("\t\t\t4 - Delete Data");
        System.out.println("\t\t\t5 - Exit");
        System.out.print("\t\t Select Option -->>> ");

        Scanner sc = new Scanner(System.in);
        int select = sc.nextInt();

        switch (select) {
            case 1 -> createData();
            case 2 -> readData();
            case 3 -> updateData();
            case 4 -> deleteData();
            case 5 -> {
                System.out.println("\t\tExit... Thank You ♥");
                exit(0);
            }
            default -> {
                System.out.println("Wrong Option!");
                menu();
            }
        }
    }

    // -------------------------------------------
    // MAIN
    // -------------------------------------------
    public static void main(String[] args) {
        menu();
    }
}
