package nn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * XMLParser - parse a XML file then pass Queue of data to XMLInterpreter
 *
 * @author Kenneth Cason
 * kenneth.cason@gmail.com
 * www.Ken-Soft.com
 */
public class XMLParser {
	/*
	 * the "tags" Queue holds all Tags. the below example is a Queue containing
	 * the information for an IMAGE element with various attributes.
	 * 0 IMAGE
	 * 1 POSX
	 * 2 VALUE
	 * 3 POSY
	 * 4 VALUE
	 * 5 WIDTH
	 * 6 VALUE
	 * 7 HEIGHT
	 * 8 VALUE
	 * 9 IMAGE
	 * Then the XMLInterpreter uses the Queue as defined next.
	 * Since it is a Queue, i.e. LIFO behavior, first element 0,
	 * IMAGE, gets read and signals the XMLInterpreter to read the next set of tags
	 * (1-8) as attribute and value of the IMAGE tag until the next IMAGE is read.
	 * The second IMAGE, 9, represents the close tag. Also note that the VALUE in 2
	 * is the value for POSX in 1.
	 */
	private LinkedList<String> parsedXML;

	// mode enumerations to help keep track of what is being parsed
	public enum mode {
		TAG_NONE,   // DEFAULT
		TAG_COMMENT,// '<!--'
		TAG_DOCTYPE,// '<!'
		TAG_XMLINFO,// '<?'
		TAG_OPEN, 	// '<'
		TAG_CLOSE 	// '</'
	}

	/**
	 * XMLParser Constructor
	 */
	public XMLParser() {
		parsedXML = null;
	}

	/**
	 * getParsedData - returns a String <Queue> of parsed XML data
	 * @return
	 */
	public LinkedList<String> getParsedData() {
		return parsedXML;
	}
	
	/**
	 * XMLParser Constructor
	 *
	 * @param fileName
	 */
	public XMLParser(String fileName) {
		try {
			parsedXML = parseXMLFile(fileName);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * parseXMLFile
	 * @throws IOException
	 * @Param fileName
	 * @Return LinkedList of Parsed Tag, Attributes, and
	 */
	public LinkedList<String> parseXMLFile(String fileName) throws IOException {
		LinkedList<String> parsedXML = new LinkedList<String>();
		FileReader r = new FileReader(fileName);
		BufferedReader br = new BufferedReader(r);
		String line = null;
		String[] parsed = new String[2];
		mode pMode = mode.TAG_NONE;
		do { 
			line = br.readLine();
			if(line != null) {
				line = line.trim();
			while(line.length() > 0) {
				line = line.trim();
				if (pMode == mode.TAG_NONE) {
					// The next Tag must be a type of Open Tag. i.e. <foo>, <!-- , <!, or <?
					// find out what kind of Open Tag it is
					// Also, make sure we don't get a NULLPointerException when using substring

					if (line.length() >= 4 &&
							line.substring(0,4).equals("<!--")) { // TAG_COMMENT
						line = line.substring(4);
						//System.out.println("OPEN COMMENT- REMAINDER:["+line+"]");
						pMode = mode.TAG_COMMENT;
					} else if (line.length() >= 2 &&
							line.substring(0,2).equals("<!")) { // TAG_DOCTYPE
						line = line.substring(2);
						//System.out.println("OPEN DOCTYPE- REMAINDER:["+line+"]");
						pMode = mode.TAG_DOCTYPE;
					} else if (line.length()  >= 2 &&
							line.substring(0,2).equals("<?")) { // TAG_XMLINFO
						line = line.substring(2);
						//System.out.println("OPEN XMLINFO- REMAINDER:["+line+"]");
						pMode = mode.TAG_XMLINFO;
					} else if (line.length()  >= 1 &&
							line.substring(0,1).equals("<")) { // TAG_OPEN
						//System.out.println("OPEN TAG-  REMAINDER:["+line+"]");
						pMode = mode.TAG_OPEN;
					} else if (line.length()  >= 2 &&
						line.substring(0,1).equals("</")) { // TAG_OPEN
						//System.out.println("CLOSE TAG-  REMAINDER:["+line+"]");
						pMode = mode.TAG_CLOSE;
					} else { // just plain data
						if(line.length() > 0) {
							//System.out.println("LINE!!! "+line);
							//parsed = parseAtFirstOccurance(line,"<",true);
							//System.out.println("ADDDED DATA- "+line.substring(0,line.indexOf("<")));
							parsedXML.add(line.substring(0,line.indexOf("<")));
							line = line.substring(line.indexOf("<"));
							//System.out.println("WHATS LEFT!!! "+ parsed[1]);
						}
						pMode = mode.TAG_NONE;
					}
				} else if (pMode == mode.TAG_COMMENT) {
					// Since Comments aren't necessary for the program, ignore them for now
				    parsed = parseAtFirstOccurance(line,"-->",true); // parsed[0] contains data in between the comments. 
				    												  //parsed[1] contains the rest.
				    line = parsed[1];
				    //System.out.println(" COMMENT:[" + parsed[0] + "]" + " REMAINDER:["+line+"]");
				    pMode = mode.TAG_NONE; // its done with the TAG_COMMENT
				} else if (pMode == mode.TAG_DOCTYPE) {
				    parsed = parseAtFirstOccurance(line,">",true); // parsed[0] contains data in between the DocTypes. 
				    //parsed[1] contains the rest.
				    line = parsed[1];
				    //System.out.println(" DOCTYPE:[" + parsed[0] + "]" + " REMAINDER:["+line+"]");
				    pMode = mode.TAG_NONE; // its done with the TAG_COMMENT
				} else if (pMode == mode.TAG_XMLINFO) {
				    parsed = parseAtFirstOccurance(line,"?>",true); // parsed[0] contains data in between the XML Infot. 
					  //parsed[1] contains the rest.
				    line = parsed[1];
				    //System.out.println(" XML Info:[" + parsed[0] + "]" + " REMAINDER:["+line+"]");
				    pMode = mode.TAG_NONE; // its done with the TAG_COMMENT
				} else if (pMode == mode.TAG_OPEN) {
					// get the info in between <>
					parsed = parseAtFirstOccurance(line,">",true);
					String data = parsed[0].substring(1).trim();
					//System.out.println("TAG DATA: "+data);
					//System.out.println("REMAINING LINE"+parsed[1]);
					parsedXML.add(data);
					line = parsed[1];
				   // System.out.println(" XML Info:[" + parsed[0] + "]" + " REMAINDER:["+line+"]");
				    pMode = mode.TAG_NONE; // its done with the TAG_COMMENT
				} else if (pMode == mode.TAG_CLOSE) {
					// do nothing
				} else {
					// nothing remaining in line, break and read next line.
					break;
				}
			}
			}
		} while (line != null);
		return parsedXML;
	}
	
	/**
	 * parseAtFirstOccurance - parse a string att firs occurance of Parse Key
	 * @Param string - string of data to be parsed
	 * @Param Parse Key 
	 * @Param Case Sensitive - If true, then search for key is case sensitive, else it is not.
	 * @Return String[0]  - Text up to Parse Key
	 * 	              [1]  - Text after parse Key
	 * 	              null - Parse Key not found
	 */
	public String[] parseAtFirstOccurance(String string, String key, boolean caseSensitive) {
		String[] parsed = new String[2];
		if (key == null || string == null || key.equals("") || string.equals("")) {
			return null;
		} else {
			boolean found = false;
			for (int i = 0; i < string.length(); i++) {
				if (i + key.length() > string.length()) { // if it hasn't found the parse key at this point, it DNE.
					return null;
				} else if (caseSensitive == false && 
						string.substring(i,i+key.length()).equalsIgnoreCase(key)) {
					found = true;
					
				} else if (string.substring(i,i+key.length()).equals(key)) {
					found = true;
				}
				if(found) {
					parsed[0] = string.substring(0, i);
					parsed[1] = string.substring(i+key.length());
					return parsed;
				}
			}
		}
		return null;
	}

}