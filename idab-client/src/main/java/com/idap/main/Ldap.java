package com.idap.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

public class Ldap {
	String url = "ldap://clusterinfo.unineuchatel.ch:10389/";
	String base = "ou=students,dc=security,dc=ch";
	String userDn = "cn=admin,dc=security,dc=ch";
	String password = "security2017";
	DirContext dirContext = null;

	public Ldap() throws NamingException {
		dirContext = GetInitialProperties();
	}

	@SuppressWarnings("unchecked")
	public Object GetStudents() {
		List<String> list = new ArrayList<String>();
		try {
			LdapContextSource ctxSrc = new LdapContextSource();
			ctxSrc.setUrl(url);
			ctxSrc.setBase(base);
			ctxSrc.setUserDn(userDn);
			ctxSrc.setPassword(password);
			ctxSrc.afterPropertiesSet();
			LdapTemplate lt = new LdapTemplate(ctxSrc);

			AndFilter filter = new AndFilter();
			filter.and(new EqualsFilter("objectclass", "person"));
			list = lt.search("", filter.encode(), new ContactAttributeMapperJson());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	public boolean AddStudent(String sn_value,String cn_value,String description_value) {

		Attributes attributes = new BasicAttributes();
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("person");
		attributes.put(objectClass);

		Attribute sn = new BasicAttribute("sn");
		Attribute cn = new BasicAttribute("cn");
		Attribute description = new BasicAttribute("description");

		sn.add(sn_value);
		cn.add(cn_value);
		description.add(description_value);

		attributes.put(sn);
		attributes.put(cn);
		attributes.put(description);

		try {
			dirContext.createSubcontext("cn="+cn+",ou=students,dc=security,dc=ch", attributes);			
			return true;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public String UpdateStudent() {
		ModificationItem[] modItemsOne = new ModificationItem[1];
		modItemsOne[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("description", "description"));
		String entryDN = "cn=mytest,ou=students,dc=security,dc=ch";
		try {
			dirContext.modifyAttributes(entryDN, modItemsOne);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "The Entry has been Updated";
	}

	public String DeleteStudent() {

		String entryDN = "cn=mytest,ou=students,dc=security,dc=ch";
		try {
			dirContext.destroySubcontext(entryDN);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "The entry has been deleted";
	}

	public DirContext GetInitialProperties() throws NamingException {
		Properties initilaProperties = new Properties();
		initilaProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		initilaProperties.put(Context.PROVIDER_URL, "ldap://clusterinfo.unineuchatel.ch:10389/");
		initilaProperties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=security,dc=ch");
		initilaProperties.put(Context.SECURITY_CREDENTIALS, "security2017");
		DirContext dirContext = new InitialDirContext(initilaProperties);
		return dirContext;
	}

}
