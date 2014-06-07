package cryptography;

/*
 * Cryptanalysis Class
 * 
 */

public class Cryptanalysis extends Cryptography
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private int[] 	 letterFrequencies;
	private	double[] letterFrequencyPercentages;
	private double[] letterFrequencyDifferences;
	
	/*
	 * Class Constants
	 * 
	 */

	
	
	/*
	 * Getters and Setters
	 * 
	 */
	
	public int[] getLetterFrequencies()
	{
		return letterFrequencies;
	}

	public double[] getLetterFrequencyPercentages()
	{
		return letterFrequencyPercentages;
	}

	public double[] getLetterFrequencyDifferences()
	{
		return letterFrequencyDifferences;
	}
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public Cryptanalysis()
	{
		
	}
	
	/*
	 * isTransposition Method
	 * 
	 */
	
	public int isTransposition(String cipherText)
	{
		int transposition = -1;		// No

		/*
		 * Is it a Transposition Cipher?
		 * 
		 * Calculate cipher text letter frequencies and compare to English letter frequencies.
		 * 
		 */
		
		letterFrequencies 		   = countLetterFrequencies(cipherText);
		
		letterFrequencyPercentages = calculateLetterFrequencies(letterFrequencies);

		letterFrequencyDifferences = compareLetterFrequencies(letterFrequencyPercentages);
		
		return transposition;
	}
	
	/*
	 * Count Letter Frequencies Method
	 * 
	 */
	
	private int[] countLetterFrequencies(String cipherText)
	{
		// Convert cipher text to upper case
		
		cipherText = cipherText.toUpperCase();
		
		// Count the cipher text letter frequencies
		
		int[] letterFrequencies = new int[26];
		
		for (int index = 0; index < letterFrequencies.length; index++)
		{
			letterFrequencies[index] = 0;
		}
		
		for (int index = 0; index < cipherText.length(); index++)
		{
			letterFrequencies[cipherText.charAt(index) - 65]++;
		}
		
		return letterFrequencies;
	}
	
	/*
	 * Calculate Letter Frequency Percentages Method
	 * 
	 */
	
	private double[] calculateLetterFrequencies(int[] letterFrequencies)
	{
		double[] letterFrequencyPercentages = new double[26];
		
		for (int index = 0; index < letterFrequencyPercentages.length; index++)
		{
			letterFrequencyPercentages[index] = 0;
		}
		
		// Calculate the letter frequency percentages
		
		for (int index = 0; index < letterFrequencies.length; index++)
		{
			if (letterFrequencies[index] > 0)
			{
				// Round to nearest three decimal places
				
				letterFrequencyPercentages[index] = ((double)letterFrequencies[index] / (double)letterFrequencies.length) * 100.0d;
				letterFrequencyPercentages[index] = Math.round(letterFrequencyPercentages[index] * 1000.0d) / 1000.0d;
			}
		}
		
		return letterFrequencyPercentages;
	}
	
	/*
	 * Compare letter frequency percentages to English letter frequency percentages Method
	 * 
	 */
	
	private double[] compareLetterFrequencies(double[] letterFrequencyPercentages)
	{
		double[] letterFrequencyDifferences = new double[26];
		
		for (int index = 0; index < letterFrequencyDifferences.length; index++)
		{
			letterFrequencyDifferences[index] = 0;
		}
		
		for (int index = 0; index < letterFrequencyPercentages.length; index++)
		{			
			// Calculate the percentage difference
			
			letterFrequencyDifferences[index] = ENGLISH_LETTER_FREQUENCIES[index] - letterFrequencyPercentages[index];
			
			// Calculate the difference percentage
			
			letterFrequencyDifferences[index] = Math.abs((letterFrequencyDifferences[index] / ENGLISH_LETTER_FREQUENCIES[index]) * 100.0d);
			letterFrequencyDifferences[index] = Math.round(letterFrequencyDifferences[index] * 1000.0d) / 1000.0d;
		}
		
		return letterFrequencyDifferences;
	}
}
