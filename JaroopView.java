import java.io.*;
import java.util.*;
// Java libraries for input and output of command line 
// and opening files and operations within those files
 /**
		* This is intended to be my view tier of the Model View Controller 
		* design pattern for this Jaroop exercise
		* 
		*		
		*	Ideally the view would be the user interface text fields, buttons, etc.
		*	in terms of program data. It would echange with the controller in terms 
		*   of what menus/ functions the user wishes to do as far any data
		*  the user has entered.
		*
		*	This is just getting the menu item selection Exit, Balance, Withdraw, or Deposit
		*	and an amount if a Balance or Withdraw
		*
		*  @author   Jay Harkins
		* @version   V .75   February 24, 2017
		*
		*/
 public class JaroopView
	 // View tier presentation/ UI to get user input
{
/**
		*
		*  GetTransaction method per requirements
		*
		*   When the program first executes, it should prompt the user with the text “Please enter in a command (Deposit, Withdraw, Balance, Exit) :“
		*
		*	When the user inputs “Deposit”, the program should prompt the user again for an amount with the text “Please enter an amount to deposit:”.
		*	Similarly, when the user inputs 
		*
		*	Per the MVC philosphy IMHO were just getting the users response
		*    the controller calls when it determines it is necessary
		*
		*  @param mydebug to provide diagnostic output information for testing purposes
		*  
		*  @author   Jay Harkins
		* @version   V .75   February 24, 2017
		*
		*/
	    public static String GetTransaction(boolean mydebug) 
	{
		    boolean viewdebug;
		    boolean valid;
		    String balance=null;
		    String transaction = null;
		    String transactionprompt = null;
		    viewdebug = mydebug;
		    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println(" ");
		    System.out.println(" ");
		   System.out.println("Please enter in a command (Deposit, Withdraw, Balance, Exit)");
		    try {
			transaction = reader.readLine();
		    }
		    catch(IOException e)
		    {
			    e.printStackTrace();
		    }
		    return transaction;
		    
		    
	    }
	    /**
		*  GetAmount method per requirements
		*
		*	When the user inputs “Deposit”, the program should prompt the user again for an amount with the text “Please enter an amount to deposit:”.
		*	Similarly, when the user inputs 
		*	Similarly, when the user inputs “Withdraw”, the program should prompt the user with the text “Please enter an amount to withdraw:”.
	          *    For our purposes here we do not know why we are called
	         *     we put a message up and send back answer
		*   Controller will determine when we are called
		*
		*  @param mydeug to provide diagnostic output information for testing purposes
		* @param mystring send in the prompt message to display
		*
	          *	@return amount the amount the user entered - Controller will determine correctness
		*/

	    public static String GetAmount(String mystring,boolean mydebug)
	    {
			String amount;
		        System.out.println(mystring);
		        amount = "0";
		        // for purposes here now keep working with amount as string
		    
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				amount = reader.readLine();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		   
		   return amount;
	   }
		    
}
    