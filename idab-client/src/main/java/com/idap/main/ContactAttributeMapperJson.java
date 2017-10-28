package com.idap.main;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ldap.core.AttributesMapper;

public class ContactAttributeMapperJson implements AttributesMapper{

	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		// TODO Auto-generated method stub
		NamingEnumeration<String> ids = attributes.getIDs();
		JSONObject jo = new JSONObject();
		while(ids.hasMore()) {
			String id = ids.next();
			try {
				jo.put(id, attributes.get(id).get());
			}catch(JSONException e) {
				e.printStackTrace();
			}
			
		}
		return jo.toString();
	}

}
