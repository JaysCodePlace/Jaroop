
import java.io.*;
import java.util.*;




/* Jay Harkins */
/* 2-22-17   */
/* Jaroop exercise project */

		
    class JaroopExercise
{
	    
	    
	static File out;
	static File inListDir;
	static FileWriter fw;
	static File targetcopy;
	static PrintWriter pw;

	private static String commandLineFile;
    
    public static void main(String[] args) {
	String targetDir;   
	File out;
	File targetcopy;	    
	String commandLineMode;
	//commandLineMode = args[0];
	    
	//out = new File("mynew.html");
	//fw = new FileWriter(out);
	FileOutputStream fout;    
	//fw = new FileWriter("mynew.html");
	//pw = new PrintWriter(fw);
	//pw.println("something");	
	try 
	{
		fout = new FileOutputStream("mynew.html");
		//out = new File("another.html");
		//fw = new FileWriter(out);
		out = new File("another.html");
		fw = new FileWriter(out);
		pw = new PrintWriter(fw);	
		//pw = new PrintWriter(fw);
		new PrintStream(fout).println("hellowanother world!");
		//pw.close();
		fout.close();
		FileReader fr = new FileReader("log.html");
		BufferedReader in = new BufferedReader(fr);
		//ArrayList currentFile;	
		//currentFile = new ArrayList(5);
		ArrayList<String> currentFile = new ArrayList<String>();
		String line;
		currentFile.clear();
		while ((line = in.readLine()) != null)
		{
			//pw.println(line);
			currentFile.add(line);
		}
		for (int j =0; j<currentFile.size(); j++)
		{	
			if (currentFile.get(j).indexOf("</tbody>")!=-1)
			{
				pw.println("<new line>");
			}
			pw.println(currentFile.get(j));
		}
		pw.close();
	}
	catch (IOException e)
	{
		System.err.println("Unable to write to file");
		System.exit(-1);
	}
	
	
	System.out.println("were doing something");
    }
}
    
    	
	

