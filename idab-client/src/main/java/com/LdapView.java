package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.NamingException;

public class LdapView {

	private static Scanner scanner;

	public static void main(String[] args) throws NamingException {
		scanner = new Scanner(System.in);
		Ldap ldap = new Ldap();
		// System.out.println(ldap.AddStudent());
		// System.out.println(ldap.UpdateStudent());
		// System.out.println(ldap.DeleteStudent());
		// System.out.println(ldap.GetStudents());

		while (true) {
			System.out.println("Press 1 to show the list of entries");
			System.out.println("Press 2 to add an entry");
			System.out.println("Press 3 to update an entry");
			System.out.println("Press 4 to delete an entry");
			System.out.println("Press 5 to exit the program");

			System.out.print("Enter Your Option:");
			String input = scanner.nextLine();
			switch (input) {
			case "1":
				System.out.println(ldap.GetStudents());
				System.out.println("\n");
				break;

			case "2":
				System.out.println("Please enter the These values");
				System.out.println("Please enter a value for sn:");
				String sn = scanner.nextLine();
				System.out.println("Please enter a value for Common Name:");
				String cn = scanner.nextLine();
				System.out.println("Please enter a value for description:");
				String description = scanner.nextLine();
				boolean AddEntrySuccess = ldap.AddStudent(sn, cn, description);
				while (!AddEntrySuccess) {
					System.out.println("you have entered the same value twice please re_enter the value of cn");
					System.out.println("Please enter a value for Common Name:");
					cn = scanner.nextLine();
					AddEntrySuccess = ldap.AddStudent(sn, cn, description);
				}
				System.out.println("The entry has been added press 1 to see the list");
				break;

			case "3":
				System.out.println("Please enter the entry name that you want to edit");
				String Entry = scanner.nextLine();
				List<String> Colomns = new ArrayList<String>();
				List<String> Values = new ArrayList<String>();
				String str = "";
				while (true) {
					System.out.println("Please enter the The colomns that you want to edit (cn,sn or description)");
					Colomns.add(scanner.nextLine());
					System.out.println("Please enter the Value for this colomn");
					Values.add(scanner.nextLine());
					System.out.println(
							"if you want to edit more colomns write anything, but if you dont want any more write exit");
					str = scanner.nextLine();
					if (str.equals("exit")) {
						break;
					}
				}
				System.out.println(Colomns);
				System.out.println(Values);

				boolean UpdateSuccess = false;
				for (int i = 0; i < Values.size(); i++) {
					UpdateSuccess = ldap.UpdateStudent(Entry, Colomns.get(i), Values.get(i));
				}
				if (!UpdateSuccess) {
					System.out.println("Update Failed No such Common Name");
				} else {
					System.out.println("Updated successfully");

				}
				break;
			case "4":
				boolean DeleteSuccess = false;
				System.out.println("Please enter the entry name that you want to delete");
				Entry = scanner.nextLine();
				DeleteSuccess = ldap.DeleteStudent(Entry);
				if (!DeleteSuccess) {
					System.out.println("Deleted Failed No such Common Name");
				} else {
					System.out.println("the record has been deleted successfully");

				}
				break;

			case "5":
				System.out.println("the program has been terminated");
				System.exit(0);
				break;
			default:
				System.out.println("No such an Operation");
				break;
			}

		}

	}
}