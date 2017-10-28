package com.idap.main;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
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

public class LdapView {

	public static void main(String[] args) {

		Properties initilaProperties = new Properties();
		initilaProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		initilaProperties.put(Context.PROVIDER_URL, "ldap://clusterinfo.unineuchatel.ch:10389/");
		initilaProperties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=security,dc=ch");
		initilaProperties.put(Context.SECURITY_CREDENTIALS, "security2017");

		String url = "ldap://clusterinfo.unineuchatel.ch:10389/";
		String base = "ou=students,dc=security,dc=ch";
		String userDn = "cn=admin,dc=security,dc=ch";
		String password = "security2017";
		ModificationItem[] modItemsOne = new ModificationItem[1];
		try {
			DirContext dirContext = new InitialDirContext(initilaProperties);
			LdapContextSource ctxSrc = new LdapContextSource();
			ctxSrc.setUrl(url);
			ctxSrc.setBase(base);
			ctxSrc.setUserDn(userDn);
			ctxSrc.setPassword(password);
			ctxSrc.afterPropertiesSet();
			LdapTemplate lt = new LdapTemplate(ctxSrc);

			AndFilter filter = new AndFilter();
			filter.and(new EqualsFilter("objectclass", "person"));
			@SuppressWarnings("unchecked")
			List<String> list = lt.search("", filter.encode(), new ContactAttributeMapperJson());
			System.out.println(list.toString());

			Attributes attributes = new BasicAttributes();
			Attribute objectClass = new BasicAttribute("objectClass");
			objectClass.add("person");
			attributes.put(objectClass);

			Attribute sn = new BasicAttribute("sn");
			Attribute cn = new BasicAttribute("cn");
			Attribute description = new BasicAttribute("description");

			sn.add("really");
			cn.add("mytest");
			description.add("poor momo");

			attributes.put(sn);
			attributes.put(cn);
			attributes.put(description);

			// context.createSubcontext("cn=mytest,ou=students,dc=security,dc=ch",attributes);

//			modItemsOne[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("description", "description"));
//			String entryDN = "cn=mytest,ou=students,dc=security,dc=ch";
//			dirContext.modifyAttributes(entryDN, modItemsOne);
			String entryDN = "cn=mytest,ou=students,dc=security,dc=ch";
			dirContext.destroySubcontext(entryDN);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}