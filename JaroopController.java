import java.io.*;
import java.util.*;
// Java libraries for input and output of command line 
// and opening files and operations within those files
/**
		* This is intended to be my Controller tier of the Model View Controller 
		* design pattern for this Jaroop exercise
		* 
		*	Ideally the controller would contain the actions, events handlers/listers and access to data
		*	in terms of program data. It would echange with the view in terms 
		*   of processing what the user has entered in.
		*   It would also exchange with the Model tier in terms of 
		* calling the methods and setting program data to carry out what
		*  the user wants to do
		*
		*   Which for this excersise user can Exit, Withdraw, Balance or Deposit
		*
		*	Balance would be a sum of all transactions in the HTML file
		*	Withdraw or Depoist - also requires user to enter an amount
		*	user is required to enter a positive value limited to 2 decimal 
		*        places anythng else is consider invalid and the user is "trapped"
		*       once selecting withdraw and deposit per requirement.
		*
		*	The requirement was for command line development
		*  so alot of debug statements and settings.
		*
		* to make the project from the windows command line
		*  use the makejaroop.bat  or javac JaroopController.java JaroopView.java JaroopModel.java
		* then java JaroopController 
		* and you should be able to start making banking transactions
		*
		*
		*  @author   Jay Harkins
		* @version   V .75   February 24, 2017
		*
		*/
 public class JaroopController
{		
		
		static String helpdecimals;
		// found late in testing if user has shorted us decimals xx. or xx.n still valid
		// so determine what needs to be appended to make pleasing display here
		public static void main(String[] args) 
		{
			
			String transaction;
			String amount;
			boolean amountok;
			boolean mydebug = false;
			String msg;
			// controller loop
			// while the view/ interface does not get an exit
			// when working with command line I usually just set as comand line argument but for purposes here left as setting
			// it here......or not.
			//mydebug = true;
				do
				{
					
					boolean validtransaction;
					transaction = JaroopView.GetTransaction(false);
					switch (transaction.toUpperCase())
					{
						case "BALANCE":
							// for purposes here set amount to ok
							// we don't need amount for balance case
							float balance;
							balance = JaroopModel.BalanceTransaction(false);
							//System.out.println("The current balance is $"+Float.toString(balance));
						System.out.println("The current balance is $"+String.format("%.2f",balance));
							amountok=true;
							break;
						case "DEPOSIT":
						case "WITHDRAW":
							if (transaction.toUpperCase().equals("DEPOSIT"))
							{
								msg = "Please enter an amount to deposit:";
							}
							else
							{
								msg = "Please enter an amount to withdraw:";
							}
							amountok=false;
							do 
							{
								
								amount = JaroopView.GetAmount(msg, false);
								// late find maybe short decimal points still valid but need help for nice display
								// chcekamount signals if we need help or not intialize to yes
								// then let CheckAmount set it or set No if NA
								amountok=CheckAmount(amount,false);
								if(helpdecimals.length()==1 || helpdecimals.length()==2 || helpdecimals.length()==3)
								{
									amount=amount+helpdecimals;
								}
								if(mydebug)
								{
									System.out.println("amountok is "+Boolean.toString(amountok));
								}
								if (amountok)
								{
									JaroopModel.transType = transaction.toUpperCase();
									JaroopModel.amount = amount;
									JaroopModel. StoreTransactionData(false);
								}
						
							}while (!amountok);
						// process deposit or withdrawl
							break;
						// default: no default if were something above process or while not Exit
						// otherwise loop
					}
			    
			}while (!transaction.toUpperCase().equals("EXIT"));
			// as documented valid transaction Balance,, Withdraw, Deposot, Exit
			// Exit handled here to maintain loop until Exit or handle valid cases above
		    
		}
		 /**
		*  Check Amount
		*
		*
		*  For deposits and withdrawals, users should be only allowed to enter positive decimal amounts. If an invalid amount is entered, the user should be prompted again to input an amount.
		*  Any numbers with more than two decimal places shall be regarded as invalid
		*   Any negative numbers should be invalid
		*   determine if we need to helps decimals. My interpretation is that the user may have skipped a decimal, only a decimal point and then 1 or 2 places after
		*   massage amount here as string so that it is pleasing to eye and a standard currency value
		* 
		*  @param myAmount local copy of what the user has entered as an amount 
		*  @param mydeug to provide diagnostic output information for testing purposes
		*/
		static boolean CheckAmount(String myAmount, boolean mydebug)
		{
			boolean myOk=false;
			int finddecimal;
			int amountlength;
			int numberofdecimals;
			try
			{
				float f = Float.valueOf(myAmount.trim()).floatValue();
				if(f>0)
				{
					myOk =true;
				}
			}
			catch (NumberFormatException nfe)
			{
				myOk = false;
			}
			if(mydebug)
			{
				System.out.println("myok is "+Boolean.toString(myOk));
			}
			// reason for me keeping it as string is to check for the decimal point
			// and not only check for it but determine if too many digits or not
			finddecimal = myAmount.indexOf(".");
			// indexof will be zerobased so first position would be 0 if like entered .99
			// if we are false because we don't have a float check for decimals doesn't matter
			//if ((finddecimal != -1) && myOk)			
			if ((finddecimal != -1) && myOk)
			{
				amountlength=myAmount.length();
				if(mydebug)
				{
					System.out.println("We have a decimal!!!!");
					System.out.println("decimal is at"+Integer.toString(finddecimal));
					System.out.println("length is "+amountlength);
					System.out.println("myok is "+Boolean.toString(myOk));
					// we have a decimal poiint
				}
				numberofdecimals = amountlength-finddecimal-1;
				if(mydebug)
				{
						System.out.println("decimal places"+Integer.toString(numberofdecimals));
						
						
				}
				if (numberofdecimals>2)
				{
					if(mydebug)
					{
						System.out.println("We have too many decimal places");
						
						
					}
					// we have 2 or more decimals
					// we only came here if myOk is true we know there is a float 
					// but if more than 2 decimals then we need to turn off myOk because were not ok
					myOk = false;
				}
				else
				{
					// found in testing best place is probably here for ashetically pleasing 
					// if user has shorted us decimals still valid but append in proper amount for pleasing output
					//  Could be more graceful than to put in global but found late in testing
					switch(numberofdecimals)
					{
						case 0:
							// this is the case where the user entered a decimal but no places.
							helpdecimals = "00";
							break;
						case 1:
							// note is they only enter 9.2 we already have a decimal
							helpdecimals ="0";
							break;
						default:
							// determine that we do not need to help decimals
						        // and make a really big long string to signal not to use this
							helpdecimals = "nothingrequired";
					}
				}
				
				
					
			}
			else
			{
				// we found no decimals
				// but don't turn on myOK if float it is true and is a number as well so leave it alone
				// this is the case where the user entered no decimals
				// so help out with 2
				helpdecimals = ".00";
				
			}
			return myOk;
		}

}
    
	    
	    
		    