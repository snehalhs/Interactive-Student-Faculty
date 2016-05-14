import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;


public class Driver {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Connection conn;
	static Statement stmt;
	static CallableStatement cStmt;
	static Scanner sc = new Scanner(System.in);
	static String id;
	public static void main(String args[]) throws IOException {
		int ch=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl"; 
			conn = DriverManager.getConnection(url, "ssonawa1","Apply123");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		while(ch!=-1) {
			System.out.println("CS425 FINAL PROJECT DEMO: \n");
			System.out.println("Choose Section: \n 1. Authorization and Priveleges \n 2. General Processes \n 3. Queries \n");
			ch = sc.nextInt();
			switch(ch) {
			case 1:
				System.out.println("Select a Question: \n");
				System.out.println("1: Show that the students can hide or make public selected information or all information in her profile \n");
				System.out.println("2: Show that a student who is not the TA of the course cannot be assigned to become the moderator of a course. \n");
				System.out.println("3: Show that only the site wide moderator can assign moderators to an interest group/club/course \n");
				int q1 = sc.nextInt();
				switch(q1) {
				case 1:
					ques1_1();
					break;
				case 2:
					break;
				case 3:
					ques1_3();
					break;
				}
				break;
			case 2:
				System.out.println("Select a Question: \n");
				System.out.println("1: Demonstrate the process of registering user into an interest group. \n");
				System.out.println("2: Demonstrate the process of posting comments and discussions. The process will check that the posting will fail if the user is not registered to the course or group and the course or group moderators will be alerted. \n");
				System.out.println("3: Demonstrate the process of assigning a TA to a course and the assignment will fail if the person is also a student of the course. \n");
				System.out.println("4: Demonstrate the process of opening a discussion forum for an interest group. \n");
				System.out.println("5: Demonstrate the process of modifying and filtering messages. \n");
				int q2 = sc.nextInt();
				switch(q2) {
				case 1:
					ques2_1();
					break;
				case 2:
					ques2_2();
					break;
				case 3:
					ques2_3();
					break;
				case 4:
					ques2_4();
					break;
				case 5:
					ques2_5();
					break;
					
				}
				break;
			case 3:
				System.out.println("Select a Question: \n");
				System.out.println("1: Display the 5 most recently discussions/comments from a specific interest group/club/course \n");
				System.out.println("2: Display the 5 most recently entered discussions/comments from all the interest group/club/course that a student has registered to. \n");
				System.out.println("3: Display the past average GPA of all the courses taught by a faculty. \n");
				System.out.println("4: Display the courses with the highest and lowest average GPA by a faculty and by all faculties. \n");
				System.out.println("5: Display the list of all moderators, the group/club/course that they moderate and are members of. \n");
				System.out.println("6: Find the most commented on group/club/course. \n");
				int q3 = sc.nextInt();
				switch(q3) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					ques3_5();
					break;
				case 6:
					ques3_6();
					break;
				}
				break;
			}
		}
	}	
	public static void ques1_1() throws IOException {
		System.out.println("Enter User ID: (20321965)");
		int p1 = sc.nextInt();
		System.out.println("Hide GPA? (Y:1/N:0): (1)");
		int p2 = sc.nextInt();
		System.out.println("Hide Interest Group Details? (Y:1/N:0): (1)");
		int p3 = sc.nextInt();
		System.out.println("\n Displaying Student Information:");
		try {
			stmt = conn.createStatement();
			stmt.executeQuery("UPDATE PRIVACY SET GPA = "+p2+"WHERE ID ="+p1);
			stmt.executeQuery("UPDATE PRIVACY SET INTERESTGROUP = "+p3+"WHERE ID ="+p1);
			ResultSet rs  = stmt.executeQuery("SELECT S.ID, CASE WHEN P.GPA='0' THEN S.GPA ELSE 0 END AS GPA, S.GPA,CASE WHEN P.INTERESTGROUP='0' THEN E.INTERESTGROUP ELSE '0' END AS INTERESTGROUP,S.YEAR,S.SEMESTER,S.DEGREETYPE,S.DEGREESTATUS,S.POINTS,S.USERID FROM STUDENT S INNER JOIN PRIVACY P ON P.ID = S.ID LEFT JOIN ENROLLMENTIG E ON S.USERID = E.ID WHERE S.ID="+p1);
			while(rs.next()) {
				System.out.println(rs.getInt("ID")+"\t"+rs.getInt("GPA")+"\t"+rs.getString("INTERESTGROUP")+"\t"+rs.getInt("YEAR")+"\t"+rs.getString("SEMESTER")+"\t"+rs.getString("DEGREETYPE")+"\t"+rs.getString("DEGREESTATUS")+"\t"+rs.getInt("POINTS")+"\t"+rs.getInt("USERID")+"\t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean ques1_3() throws IOException{
		System.out.println("Enter your site wide admin id");
		id=sc.next();
		try {
			stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery("select * from SITE_WIDE_ADMIN where MODERATOR_ID = '"+ id +"'");
			while(rs.next()) {
				if(rs.getString(1)!=null){
					System.out.println("Site Wide admin 1. Sign Up Into Interest Group");
					System.out.println("Enter the Moderator ID");
					String mod_id=sc.next();
					System.out.println("Enter the Interest Group: Greek Culture");
					String interest=sc.next();
					stmt.executeQuery("INSERT INTO MODERATOR VALUES ('"+mod_id +"','"+interest +"')");

					return true;
				}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	public static void ques3_5() throws IOException{
		try {
			stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery("select TA.STUDENTID from courseTA, course, TA where coursetA.id=course.id and courseTA.TAId = TA.ID");
			while(rs.next()) {
				System.out.println(rs.getInt("STUDENTID")+"\t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	
	public static void ques3_6() throws IOException{
		try {
			stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery("SELECT INTERESTGROUP.NAME FROM FORUMTITLE, INTERESTGROUP WHERE FORUMTITLE.INTERESTGROUP=INTERESTGROUP.NAME GROUP BY INTERESTGROUP.NAME HAVING COUNT(FORUMTITLE.TITLE) >=1");
			while(rs.next()) {
				System.out.println(rs.getString("NAME")+"\t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	public static void ques2_1() throws IOException {
		System.out.println("Enter User ID: (20321965)");
		int p1 = sc.nextInt();
		System.out.println("Enter Interest Group Name: (Greek Culture)");
		String p2 = br.readLine();
		try {
			cStmt = conn.prepareCall("{CALL PROC_REG_IG(?,?)}");
			cStmt.setInt(1, p1);
			cStmt.setString(2, p2);
			cStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ques2_2() throws IOException {
		System.out.println("Enter Forum ID: (1001)");
		int p1 = sc.nextInt();
		System.out.println("Enter User ID: (20321965)");
		int p2 = sc.nextInt();
		System.out.println("Enter Comment Text: (Test Comment)");
		String p3 = br.readLine();
		try {
			cStmt = conn.prepareCall("{CALL PROC_POST_COMMENT(?,?,?)}");
			cStmt.setInt(1, p1);
			cStmt.setInt(2, p2);
			cStmt.setString(3, p3);
			cStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ques2_3() {
		System.out.println("Enter Course ID: (425)");
		int p1 = sc.nextInt();
		System.out.println("Enter TA ID: (90321966)");
		int p2 = sc.nextInt();
		try {
			cStmt = conn.prepareCall("{CALL PROC_TA_ASSIGN(?,?)}");
			cStmt.setInt(1, p1);
			cStmt.setInt(2, p2);
			cStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ques2_4() throws IOException {
		System.out.println("Enter Forum ID: (1002)");
		int p1 = sc.nextInt();
		System.out.println("Enter Forum Title: (Mundane Mondays)");
		String p2 = br.readLine();
		System.out.println("Enter Interest Group Name: (Greek Culture)");
		String p3 = br.readLine();
		System.out.println("Enter User ID: (20321965)"); //Enter any other User ID to fail creation of discussion forum
		int p4 = sc.nextInt();

		try {
			cStmt = conn.prepareCall("{CALL PROC_OPEN_FORUM(?,?,?,?)}");
			cStmt.setInt(1, p1);
			cStmt.setString(2, p2);
			cStmt.setString(3, p3);
			cStmt.setInt(4, p4);
			cStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ques2_5() throws IOException {
		System.out.println("Enter Moderator/User ID: (50321999)");
		int p1 = sc.nextInt();
		System.out.println("Enter Interest Group Name: (Greek Culture)");
		String p2 = br.readLine();
		System.out.println("Enter Forum Title ID: (1001)");
		int p3 = sc.nextInt();
		System.out.println("Enter Text to be Filtered: (bad)");
		String p4 = br.readLine();
		System.out.println("Enter Replacement/Modification Text: (***)");
		String p5 = br.readLine();
		try {
			cStmt = conn.prepareCall("{CALL PROC_FILTER_COMMENTS(?,?,?,?,?)}");
			cStmt.setInt(1, p1);
			cStmt.setString(2, p2);
			cStmt.setInt(3, p3);
			cStmt.setString(4, p4);
			cStmt.setString(5, p5);
			cStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
