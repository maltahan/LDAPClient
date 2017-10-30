package com.idap.main;

import java.util.Scanner;

import javax.naming.NamingException;

public class LdapView {

	private static Scanner scanner;

	public static void main(String[] args) throws NamingException {
		 scanner = new Scanner( System.in );
		Ldap ldap = new Ldap();
		//System.out.println(ldap.AddStudent());
		//System.out.println(ldap.UpdateStudent());
		//System.out.println(ldap.DeleteStudent());
		//System.out.println(ldap.GetStudents());
		
		while(true) {
			System.out.println("Press 1 to show the list of entries");
			System.out.println("Press 2 to add an entry");
			System.out.println("Press 3 to update an entry");
			System.out.println("Press 4 to delete an entry");
			System.out.print("Enter Your Option:");
			String input = scanner.nextLine();
			switch(input) {
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
				boolean AddEntrySuccess = ldap.AddStudent(sn,cn,description);
				while(!AddEntrySuccess) {
					System.out.println("you have entered the same value twice please re_enter the value of cn");
					System.out.println("Please enter a value for Common Name:");
					cn = scanner.nextLine();
					AddEntrySuccess = ldap.AddStudent(sn,cn,description);
				}				
				System.out.println("The entry has been added press 1 to see the list");						
                break;
                
			case "3":
				System.out.println("Please enter the entry you want to edit");
				String Entry = scanner.nextLine();	
				System.out.println("Please enter the The colomns that you want to edit");
			}
			
		}

	}
}