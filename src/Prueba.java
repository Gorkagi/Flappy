import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import error.FirebaseException;
import error.JacksonUtilityException;
import model.FirebaseResponse;
import service.Firebase;

public class Prueba {

	public static void main(String[] args) throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException {

		
		// get the base-url (ie: 'http://gamma.firebase.com/username')
		String firebase_baseUrl = "https://flappy-3fdd8.firebaseio.com/";
		
		// create the firebase
		Firebase firebase = new Firebase( firebase_baseUrl );
				
		// "PUT" (test-map into a sub-node off of the fb4jDemo-root)
		/*Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put( "GOR", "5000" );
		dataMap.put( "JON", "3050" );
		dataMap.put( "AAA", "2050" );
		dataMap.put( "BBB", "1050" );
		dataMap.put( "CCC", "0050" );
		*/
		// FirebaseResponse response = firebase.put( "TOP-5", dataMap );
		//System.out.println( "\n\nResult of PUT (for the test-PUT):\n" + response );
		// System.out.println("\n");
		
		
		// "GET" (the test-PUT)
		FirebaseResponse response = firebase.get( "TOP-5" );
		
		Map<String, Object> responseMap = response.getBody();
		System.out.println(responseMap.get("JON"));
	
	}

}
