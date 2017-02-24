import java.io.*;
import java.util.*;
// Java libraries for input and output of command line 
// and opening files and operations within those files
/**
		* This is intended to be my Model tier of the Model View Controller 
		* design pattern for this Jaroop exercise
		* 
		*		
		*	Ideally the model would contain the actual work behind the scenes
		*   such as calulations, working with and storing data, It exchanges
		*   with the controller tier and has no contact with the view tier.
		*
		*	The requirement was for command line development
		*  so alot of debug statements and settings.
		*
		*
		*   IMHO this model is doing alot of work in working with the HTML file
		*   for the purposes here I choose not to rely on any 3rd party 
		*   it really was kind of neat because I had worked with html files
		*    before in java so I was able to reuse some stuff
		*
		*    So for purposes here my Model tier
		*
		*	sunms a balance and sends back to controller for display
		*	StoreTransaction data
		*	 having said that there is alot of methods called by 
		*	StoreTransactionData in working with storing data
		*		 PutFileinArrayList   to get entire contents of HTML in arraylist
		*
		*	
		*	
		*
		*
		*  @author   Jay Harkins
		* @version   V .75   February 24, 2017
		*
		*/
public class JaroopModel
	 // Model work with data and actually do something with it
	// based on what the controller has given us
 {	
		static ArrayList<String> currentFile = new ArrayList<String>();
		static File out;
		static FileWriter fw;
		static PrintWriter pw;
		static int arraylistposition;
		public static String transType;
		public static String amount;
		/**
		* Returns an Image object that can then be painted on the screen. 
		* The url argument must specify an absolute {@link URL}. The name
		* argument is a specifier that is relative to the url argument. 
		* <p>
		* This method always returns immediately, whether or not the 
		* image exists. When this applet attempts to draw the image on
		* the screen, the data will be loaded. The graphics primitives 
		* that draw the image will incrementally paint on the screen. 
		*		
		* @param  url  an absolute URL giving the base location of the image
		* @param  name the location of the image, relative to the url argument
		* @return      the image at the specified URL
		* @see         Image
		*/
		/**
		*  Balance Transaction
		*
		*
		*  When the user enters “Balance”, the program should read the contents of the attached log.html file 
		*  and sum the amounts within the “transactions” table. 
		*  This method does that and sends back for the message to be displayed
		* 
		*  @param mydeug to provide diagnostic output information for testing purposes
		*/
		public static float BalanceTransaction(boolean mydebug)
		{
			String line;
			ArrayList<String> currentFile = new ArrayList<String>();
			currentFile.clear();
			float myfloat=0;
			boolean transactionState = false;
			if (mydebug)
			{
				System.out.println("debug is "+Boolean.toString(mydebug));
			}
			// implement simple loose state machine check for line transactions
			// then after that look for <tr><td> lines and keep a running sum
			try 
			{
				FileReader fr = new FileReader("log.html");
				BufferedReader in = new BufferedReader(fr);
				while ((line = in.readLine()) != null)
				{
						if(line.indexOf("transactions")!=-1)
						{
							transactionState = true;
						}
						if (mydebug)
						{
							System.out.println("debug is "+Boolean.toString(mydebug));
							System.out.println("transaction state is "+Boolean.toString(transactionState));
						}
						if(transactionState)
						{
							int starttags;
							String lookfortags="<tr><td>";
							starttags =line.indexOf(lookfortags);
							if(starttags!=-1)
							{	
								// found the <tr><td> every between them and </tr><td> is the balance
								int closingtags;
								// we have transactions to sum
								closingtags =line.indexOf("</td></tr>");
								try
								{
									float f = Float.valueOf(line.substring(starttags+lookfortags.length(),closingtags)).floatValue();
									if (mydebug)
									{
										System.out.println("float is:"+Float.toString(f));
									}	
									myfloat = myfloat + f;
								}
								catch (NumberFormatException nfe)
								{
									System.out.println("Unable to find floats in input file");
								}
								if (mydebug)
								{
									System.out.println("line is:"+line);
								}
							}
				
						}
					}
			}
			catch (IOException e)
			{
				System.err.println("Unable to write to file");
				System.exit(-1);
			}
			if (mydebug)
			{
				// test for a pass throught with balance transaction will be required when putting transaction in
				PutFileinArrayList();
				OutputArrayListtoFile(false);
			}
			return myfloat;
		}
/**
		*  PutFileinArrayList
		*
		*
		*   No specific requirement other than storing data in the HTML file
		*   asthetically and pleasing to the human eye
		*
		*
		*    This helps the storing part by getting the entire file contents into array list
		*
		*	Gets the file and puts in arraylist for this class to work with
		*
		*
		*/
			
		static void PutFileinArrayList()
		{
			String line;
			currentFile.clear();
			float myfloat=0;
			boolean transactionState = false;
			// implement simple loose state machine check for line transactions
			// then after that look for <tr><td> lines and keep a running sum
			try 
			{
				FileReader fr = new FileReader("log.html");
				BufferedReader in = new BufferedReader(fr);
				while ((line = in.readLine()) != null)
				{
					currentFile.add(line);
				}
			}
			catch (IOException e)
			{
				System.err.println("Unable to write to file");
				System.exit(-1);
			}
		}
		 /**
		*  FindAppendLocationinArrayList
		*
		* No specific direct requirements otherwise storing the balance or withdrawl information
		* in an proper place in transactions section and make asthetically pleasing
		*
		*  the entire file is in arraylist and this has to merely get and set a location
		* class global arraylistposition to where the append location is in file
		*  that location should be at this time in the table with id=transactions
		*   just before the last </tbody> of the last trsansaction
		*  So we implement an ad-hoc state machine find transactions (true or false)
		*  and another ad-hoc state machine to find 2nd tbody (true or false)
		*   once both set record the line number in arraylistposition
		*   once both set turn them off to false and let this run to end of file
		*
		*
		*  @param mydeug to provide diagnostic output information for testing purposes
		*	          
		*/
		static void FindAppendLocationinArrayList(boolean mydebug)
		{
			int mysize;
			mysize = currentFile.size();
			boolean transactionState =false;
			// use a loose state machine to indicate we have found the transaction place of the file
			boolean foundtBody=false;
			// we are actually looking for a 2nd tbody tag after transaction so
			// also more accurately actually looking for the body> tag so we don't get hung up escape sequences
			// again another loose kind of state machine here to signal we have it
			// put the array list back to string array to use string based functions
			for (int j =0; j<currentFile.size(); j++)
			{	
					if(currentFile.get(j).indexOf("transactions")!=-1)
					{
						transactionState = true;
						
					}
					if (mydebug)
					{
						System.out.println("transaction state is "+Boolean.toString(transactionState));
					}
					if(transactionState)
					{
						// I didnt want to mess with the /tbody and esape sequene and just looking for body
						// and or worry about tab for body look for body> find the 3rd one and record the line
						//if (myArray[i].indexOf("body>")!=-1)
						if (currentFile.get(j).indexOf("body>")!=-1)
						{	
							if (!foundtBody)
							{
									// we haven't found tbody yet but we just did 1st find
								foundtBody=true;
								
							}
							else
							{
									// we fouund it prior and found it again 
									// record it and set everybody false let it run out
									// if we found another transaction that could be trouble
									// we might wind up here again
									foundtBody=false;
									transactionState=false;									
									arraylistposition = j; 
							}
							if (mydebug)
							{
								System.out.println("Find append");
								System.out.println("transaction state is "+Boolean.toString(transactionState));
								System.out.println("j  is "+Integer.toString(j));
								System.out.println("arraylistposition is "+Integer.toString(arraylistposition));
							}	
						}
					}
					
				//}					
			}
		}
		 /**
		*  OutputArrayListtoFile
		*
		*
		*   No specific requirement other than storing data in the HTML file
		*   asthetically and pleasing to the human eye
		*
		*   go through the arraylist - determined elsewhere
		*   go to location to append  - determined elsewhere
		*   we know the transaction type - if withdraw put a - (minus sing)
		*  other wise just put amount - we know valid amount determined elsewhere
		*
		* 
		*  @param mydeug to provide diagnostic output information for testing purposes
		*/
		static void OutputArrayListtoFile(boolean mydebug)
		{
			String stdPrefix = "               <tr><td>";
			// define a standard prefix so the output html looks properly align an ashetically pleasing
			// this is just goes through the current array list and puts it to an output file
			try 
			{
				out = new File("log.html");
				fw = new FileWriter(out);
				pw = new PrintWriter(fw);	
			}
			catch (IOException e)
			{
				System.err.println("Unable to write to outputfile");
				System.exit(-1);
			}
			for (int j =0; j<currentFile.size(); j++)
			{	
				if(arraylistposition==j)
				{
					String outstring;
					// we convert to all upper case to check for case insensitity
					if(transType.equals("WITHDRAW"))
					{
					      outstring = "-"+amount;
					}
					else
					{
						outstring = amount;
					}			
					pw.println(stdPrefix +outstring+"</td></tr>");
				}
				pw.println(currentFile.get(j));
				if (mydebug)
				{
					System.out.println("Output array");
					System.out.println("j  is "+Integer.toString(j));
					System.out.println("arraylistposition is "+Integer.toString(arraylistposition));
				}
			}
			pw.close();
		
		}
		 /**
		*  Store Transaction data
		*
		*
		*   No specific requirement other than storing data in the HTML file
		*   asthetically and pleasing to the human eye
		*
		*  1. Put the file in array list  - call PutFileinArrayList();
		*  2. find the location to put the new transaction - call FindAppendLocationinArrayList(true) 
		*  3. Put new transaction in and file and output call OutputArrayListtoFile(false);
		*
		* 
		*  @param mydeug to provide diagnostic output information for testing purposes
		*/
		public static void StoreTransactionData(boolean mydebug)
		{
			if (mydebug)
			{
					System.out.println("Store transaction here");
			}	
			// sequence for recording traqnsaction data
			// get file into arraylist
			// find append location in array list
			// examine type withdraw require - (minus)
			//  output array list to html file
			// output new transaction
			PutFileinArrayList();
			FindAppendLocationinArrayList(false);
			OutputArrayListtoFile(false);
			
		}
		
}
    
	    
	    
		    