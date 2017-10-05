package util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import error.FirebaseException;
import model.FirebaseResponse;
import service.Firebase;

public class DatabaseUtility {

	String firebase_baseUrl = "https://flappy-3fdd8.firebaseio.com/";
	static Firebase firebase;
	
	public DatabaseUtility() {
		try {
			firebase = new Firebase( firebase_baseUrl );
		} catch (FirebaseException e) {
			e.printStackTrace();
		}
	}
	
	static public Map<String, Object> retrieveDataTop5() throws UnsupportedEncodingException, FirebaseException {
		FirebaseResponse response = firebase.get( "TOP-5" );		
		Map<String, Object> responseMap = response.getBody();
		
		return responseMap;
	}
}
