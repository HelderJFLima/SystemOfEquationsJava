/** Class that represents a system of equations of 'n' variables and 'n' equations.
 * 
 * @author HelderJFLima
 *
 */

public class ESystem
{
	private int rows, columns;
	private double[][] sysmatrix;
	private double[] solution;
	private boolean tested = false;
	private boolean hasSolution = false;
	
	
	public void setElement(double element, int row, int column)
	{
		this.sysmatrix[row][column] = element;
	}
	
	
	public double getElement(int row, int column)
	{
		return this.sysmatrix[row][column];
	}
	
	
	/** Returns the solution of the system if it exists or 'null' if doesn't.
	 * 
	 * @return double[]
	 */
	public double[] getSolution()
	{
		if(!tested)
			this.solveSystem();
		
		if(hasSolution)
			return solution;
		else
			return null;
	}
	
	
	/** Returns the number of equations (or variables) of the system.
	 * 
	 * @return int
	 */
	public int numberOfEquations()
	{
		return this.sysmatrix.length;
	}
	
	
	/** Returns 'true' if the system has solution or 'false' otherwise.
	 * 
	 * @return boolean
	 */
	public boolean hasSolution()
	{
		if(!tested)
			this.solveSystem();
		
		return this.hasSolution;
	}
	
	
	/** Swaps two rows of a matrix.
	 * 
	 * @param mat - matrix to swap rows
	 * @param a - one line to swap
	 * @param b - the other line to swap
	 */
	private void swapRows(double[][] mat, int a, int b)
	{
		int j;
		double temp;
		
		for(j = 0; j < mat[0].length; j++)
		{
			temp = mat[a][j];
			
			mat[a][j] = mat[b][j];
			
			mat[b][j] = temp;
		}
	}
	
	
	/** Transforms the matrix of coefficients into an upper triangular matrix.
	 * 
	 * @param mat - matrix of coefficients
	 */
	private void gaussianElimination(double[][] mat)
	{
		int i, j, k;
	    double factor;

	    for (i = 0; i < mat.length; i++)
	    {
	        if (mat[i][i] == 0 && i < mat.length - 1)	// Swaps rows, if necessary, to better organize the matrix.
	        {
	            for (k = i + 1; k < mat.length; k++)
	            {
	                if (mat[k][i] != 0)
	                {
	                    this.swapRows(mat, i, k);

	                    break;
	                }
	            }
	        }

	        if (mat[i][i] != 0 && i < mat.length - 1)
	        {
	            for (k = i + 1; k < mat.length; k++)	// Transforms into a triangular matrix.
	            {
	                if (mat[k][i] == 0)
	                    continue;
	                
	                factor = - mat[k][i] / mat[i][i];
	                
	                for (j = i + 1; j < mat[0].length; j++)
	                	mat[k][j] += factor * mat[i][j];
	                
	                mat[k][i] = 0;
	            }
	        }
	    }
	}
	
	
	/** Solves the system of equations if it's possible.
	 * 
	 */
	private void solveSystem()
	{
		int i, j;
		boolean independentSystem = true;
		double[][] tempmat = new double[this.rows][this.columns];
		
		for(i = 0; i < this.rows; i++)  				// Copies the system's matrix.
		{
			for(j = 0; j < this.columns; j++)
				tempmat[i][j] = this.sysmatrix[i][j];
		}
		
		this.gaussianElimination(tempmat);
				
		for (i = 0; i < this.rows; i++) 				// Tests if the system is independent or not.
		{
			if(tempmat[i][i] == 0)
			{
				independentSystem = false;
				
				break;
			}
		}
		
		if(independentSystem)
		{
			this.solution = new double[this.rows];

	        for (i = this.rows - 1; i >= 0; i--)      	// Solves the system by simple substitution.
	        {
	            solution[i] = tempmat[i][this.columns - 1];
	            
	            for (j = i + 1; j < this.columns - 1; j++)
	            	solution[i] -= tempmat[i][j] * solution[j];
	            
	            solution[i] /= tempmat[i][i];
	        }
	        
	        this.hasSolution = true;
		}
		
		this.tested = true;
	}
	
	
	ESystem(int rows, int columns)
	{
		this.rows = rows;
		
		this.columns =  columns;
		
		if(rows <= 0 || columns <= 0)
			throw new IllegalArgumentException("Nonpositive value informed for the system of equations.");
		
		if(rows != columns - 1)
			throw new IllegalArgumentException("Incompatible numbers of variables and equations.");
		
		this.sysmatrix = new double[rows][columns];
	}
}
