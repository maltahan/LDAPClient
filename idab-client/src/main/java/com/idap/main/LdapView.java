package com.idap.main;

import java.util.List;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

public class LdapView {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "ldap://clusterinfo.unineuchatel.ch:10389/";
		String base = "ou=students,dc=security,dc=ch";
		String userDn = "cn=admin,dc=security,dc=ch";
		String password = "security2017";
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
			@SuppressWarnings("unchecked")
			List<String> list = lt.search("", filter.encode(), new ContactAttributeMapperJson());
			System.out.println(list.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}