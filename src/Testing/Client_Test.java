package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import modules.Client;
/**
 * Test class Client
 * @author Ernest Stachelski
 *
 */
class Client_Test {
/**
 * Test Client constructor
 * @throws FileNotFoundException
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@Test
	void contructor_test() throws FileNotFoundException, ClassNotFoundException, IOException {
		Client client = new Client("Filip","Filipny","99010100101");
		assertNotNull(client);
		assertEquals(client.getName(),"Filip");
		assertEquals(client.getSurname(),"Filipny");
		assertEquals(client.getPesel(),"99010100101");
	}

}
