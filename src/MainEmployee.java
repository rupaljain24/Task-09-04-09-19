import java.util.List;
import java.util.Scanner;
import java.awt.Window.Type;
import java.sql.*;

public class MainEmployee {
	private int eno;
	private String ename;
	private String designation;
	private String department;
	private double salary;

	static Scanner sc = new Scanner(System.in);

	public int getEno() {
		return eno;
	}

	public void setEno(int eno) {
		this.eno = eno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int ch;
		System.out.println("Employee Menu");
		System.out.println("-----------------------");
		System.out.println("1. Add a new Employee");
		System.out.println("2. View all Employees");
		System.out.println("3. Remove Employee");
		System.out.println("4. Clear all data");
		System.out.println("5. Update Salary");
		System.out.println("6. Search a Employee");
		System.out.println("7. Show salary of Specific Employee");
		System.out.println("8. Exit");

		Class.forName("com.mysql.jdbc.Driver");
		String db_url = "jdbc:mysql://localhost:3306/java";
		String db_uname = "root";
		String db_upass = "root";
		Connection con = DriverManager.getConnection(db_url, db_uname, db_upass);
		while (true) {
			System.out.println("Enter your choice to perform Operation:");
			ch = sc.nextInt();

			switch (ch) {
			case 1:
				CallableStatement stmt = con.prepareCall("{call insert_employee(?,?,?)}");
				System.out.println("Enter Id:");
				int id = sc.nextInt();
				System.out.println("Enter ename:");
				String name = sc.next();
				System.out.println("Enter Salary:");
				int sal = sc.nextInt();
				stmt.setInt(1, id);
				stmt.setString(2, name);
				stmt.setInt(3, sal);
				stmt.execute();
				System.out.println("Data Added into Table");
				break;

			case 2:

				String q = "Select * from employee";
				Statement st = (Statement) con.createStatement();
				ResultSet rs = (ResultSet) st.executeQuery(q);
				while (rs.next()) {

					System.out.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getInt(3));
				}
				System.out.println("Data Displayed");
				break;

			case 3:
				System.out.println("Enter employee to delete:");
				String em = sc.next();
				PreparedStatement st1 = con.prepareStatement("delete from employee where Empno=" + em);
				st1.executeUpdate();
				System.out.println("Data of Employee " + em + "deleted.!");
				break;

			case 4:
				PreparedStatement st2 = con.prepareStatement("delete from employee");
				System.out.println("Data deleted");
				break;

			case 5:

				System.out.print("Enter Employee number whose salary you want to update :");
				int no = sc.nextInt();
				System.out.println("Enter new salary :");
				int NewSalary = sc.nextInt();
				PreparedStatement st3 = con.prepareStatement("update employee set salary=? where empno=?");
				st3.setInt(1, NewSalary);
				st3.setInt(2, no);
				st3.executeUpdate();
				System.out.println("Salary Updated !");

				break;

			case 6:
				System.out.print("Enter Employee number to search:");
				int n = sc.nextInt();

				PreparedStatement st4 = con.prepareStatement("select * from employee  where empno=" + n);
				st4.executeUpdate();

				break;
			case 7:
				System.out.println("Enter Id:");
				int id1 = sc.nextInt();

				CallableStatement stmt2 = con.prepareCall("{?=call return_sal(?)}");
				stmt2.registerOutParameter(1, Types.INTEGER);
				stmt2.setInt(2, id1);

				stmt2.execute();
				System.out.println("Salary displayed=" + stmt2.getInt(1));

				break;

			}
		}

	}
}
