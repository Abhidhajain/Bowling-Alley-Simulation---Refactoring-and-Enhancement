/*
 * Bowler.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log: Bowler.java,v $
 *     Revision 1.3  2003/01/15 02:57:40  ???
 *     Added accessors and and equals() method
 *
 *     Revision 1.2  2003/01/12 22:23:32  ???
 *     *** empty log message ***
 *
 *     Revision 1.1  2003/01/12 19:09:12  ???
 *     Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Class that holds all bowler info
 *
 */

public class Bowler {

    private String fullName;
    private String nickName;
    private String email;

    public Bowler( String nickName, String fullName, String email ) {
	this.nickName = nickName;
	this.fullName = fullName;
  	this.email = email;
    }

    public String getNickName() {
        return nickName;
    }
	public String getFullName ( ) {
			return fullName;
	}
	public String getEmail ( ) {
		return email;	
	}
	public String getNick ( ) {
		return nickName;
	}
}