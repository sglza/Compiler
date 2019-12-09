/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

public class Token {
	// Elements of a token
	public String tokenId;
	public String content;
	//////////////////

	public Token(String id, String string) {
		tokenId = id;
		content = string;
	}// end constructor

	public String getTokenId() {
		return tokenId;
	}// end getToken

	public String getContent() {
		return content;
	}// end getContent

	// Representation of the token
	public String toString() {
		return "[ " + tokenId + " :  '" + content + "' ]";
	}// end toString

}// end class
