/** This program gets a system of equations from a 'txt' file and solves it, if there is a solution.
*** The number of equations must be equal to the number of variables.
*/

import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;

public class SystemOfEquations
{
	public static void main(String[] args)
	{
		int i, j, numVar = 0, count = 0;
		String str = new String();
		StringBuffer strBuf;
		ESystem syst = null;
		
		try
		{
			Scanner scan = new Scanner(new File("System.txt"));	// Reads the input file.
						
			str = scan.nextLine();
			
			if(str.charAt(0) != '#' || str.charAt(str.length() - 1) != '#')	// Tests file's data format.
			{
				System.out.print("\nThe file format is incorrect!\n");
				
				JOptionPane.showMessageDialog(null, "The file format is incorrect!");
				
				scan.close();
				
				System.exit(1);
			}
			
			str = str.substring(1, str.length() - 1);
			
			numVar = Integer.parseInt(str);
			
			syst = new ESystem(numVar, numVar + 1); 			// Instantiates the system of equations.
			
			for(i = 0; scan.hasNextDouble(); i++)
			{
				if(i == numVar)
				{
					System.out.print("\nThe file format is incorrect!\n");
					
					JOptionPane.showMessageDialog(null, "The file format is incorrect!");
					
					scan.close();
					
					System.exit(1);
				}
				
				for(j = 0; j < numVar + 1; j++)   				// Copies the file's values to the system.
				{
					if(scan.hasNextDouble())
					{
						syst.setElement(scan.nextDouble(), i, j);
						
						count++;
					}
					else
						break;
				}
			}
			
			if(count != numVar * (numVar + 1)) 					// Tests file's data format.
			{
				System.out.print("\nThe file format is incorrect!\n");
				
				JOptionPane.showMessageDialog(null, "The file format is incorrect!");
				
				scan.close();
				
				System.exit(1);
			}
			
			scan.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "File not found!");
			
			System.exit(1);
		}
		
		try  													// Writes the output file.
		{
			BufferedWriter bwrt = new BufferedWriter(new FileWriter("SystemOfEquationsOutput.txt"));
			
			str = "\nSystem:\n\n";
			
			bwrt.write(str);
			
			bwrt.flush();
			
			strBuf = new StringBuffer();
			
			for(i = 0; i < numVar; i++)
			{
				for(j = 0 ; j < numVar + 1; j++)
					strBuf.append(syst.getElement(i, j) + "\t");
				
				strBuf.append("\n");
				
				str = strBuf.toString();
				
				bwrt.write(str);
				
				bwrt.flush();
				
				strBuf.delete(0, strBuf.length());
			}
			
			str = "\n\nSolution:\n\n";
			
			bwrt.write(str);
			
			bwrt.flush();
			
			if(!syst.hasSolution()) 							// Writes the solution or a message of inexistent solution.
			{
				str = "There is no solution for the system.\n";
				
				bwrt.write(str);
				
				bwrt.flush();
			}
			else
			{
				double[] sol = new double[numVar];
				
				sol = syst.getSolution();
				
				for(i = 0; i < numVar; i++)
				{
					str = "x" + (i + 1) + ": " + sol[i] + "\n";
					
					bwrt.write(str);
					
					bwrt.flush();
				}
			}
			
			bwrt.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Problems creating output file!");
		}
		
		JOptionPane.showMessageDialog(null, "Done!");
	}
}
