package com.idap.main;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Ldap {

	public void addEntry() {
		Properties initilaProperties = new Properties();
		initilaProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		initilaProperties.put(Context.PROVIDER_URL, "ldap://clusterinfo.unineuchatel.ch:10389/");
		initilaProperties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=security,dc=ch");
		initilaProperties.put(Context.SECURITY_CREDENTIALS, "security2017");
		try {
			DirContext context = new InitialDirContext(initilaProperties);
			System.out.println("Existing users in ou=users , ou=system");
			listEntries(context);
			System.out.println("Adding new user..");
			addUser(context);
			System.out.println("New list of users...");
			listEntries(context);
			context.close();

		} catch (NamingException e) {

			e.printStackTrace();
		}

	}

	public void addUser(DirContext context) {
		Attributes attributes = new BasicAttributes();
		Attribute attribute = new BasicAttribute("objectClass");
		attribute.add("inetOrgPerson");
		attributes.put(attribute);
		Attribute sn = new BasicAttribute("sn");
		Attribute cn = new BasicAttribute("cn");
		sn.add("Karthik");
		cn.add("users");
		attributes.put(sn);
		attributes.put(cn);
		attributes.put("telephoneNumber", "777777777");
		try {
			context.createSubcontext("employeeNumber= 333333,ou=users,ou=system", attributes);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void listEntries(DirContext context) {
		String searchFilter = "(objectClass=inetOrgPerson)";
		String[] requiredAttributes = { "sn", "cn" };

		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(requiredAttributes);

		NamingEnumeration users;
		try {
			users = context.search("ou=students,dc=security,dc=ch", searchFilter, controls);

			SearchResult searchResult = null;
			String commonName = null;
			String empNumber = null;
			String telephoneNumber = null;
			while (users.hasMore()) {

				searchResult = (SearchResult) users.next();
				Attributes attr = searchResult.getAttributes();
				commonName = attr.get("cn").get(0).toString();
				empNumber = attr.get("sn").get(0).toString();
				//telephoneNumber = attr.get("telephoneNumber").get(0).toString();
				System.out.println("Name = " + commonName);
				System.out.println("Employee Number = " + empNumber);
				System.out.println("Phone Number = " + telephoneNumber);

			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
