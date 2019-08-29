package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modules.center.User;

class User_Test {

	@Test
	void constructor_test() {
		User user = new User("Bank","123","Center","Center");
		
		assertEquals(user.getLogin(),"Bank");
		assertEquals(user.getPassword(),"123");
		assertEquals(user.getRights(),"Center");
		assertEquals(user.getOrgName(),"Center");
	}
	@Test
	void constructor_test2() {
		User user = new User("Bank","123","Center","");
		assertEquals(user.getLogin(),"Bank");
		assertEquals(user.getPassword(),"123");
		assertEquals(user.getRights(),"Center");
		assertEquals(user.getOrgName(),"");
		
		
	}
	
}
