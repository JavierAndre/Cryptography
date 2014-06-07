package cryptographyTests;

import java.text.DecimalFormat;

import cryptography.Cryptography;
import cryptography.TranspositionCiphers;

public class TranspositionCipherTests
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Cryptography	cipher;
	
	/*
	 * Constructor
	 * 
	 */
	
	public TranspositionCipherTests()
	{
		cipher = new Cryptography();
	}
	
	/*
	 * Rull all tests
	 * 
	 */
	
	public void run()
	{
		testGetNull();
	}
	
	/*
	 * Test Weighted Random Character Padding Method
	 * 
	 */
	
	public void testGetNull()
	{
		char		characterGenerated					= ' ';
		char[] 		weightedRandomCharacters 	  		= new char[100000];
		int[]		weightedRandomCharacterCounts 		= new int[26];			// Upper case letters only
		double[]	weightedRandomCharacterFrequencies 	= new double[26];
		
		// Generate random characters and count them
		
		for (int index = 0; index < weightedRandomCharacters.length; index++)
		{
			characterGenerated = cipher.getNull();
			
			weightedRandomCharacters[index] = characterGenerated;
			
			weightedRandomCharacterCounts[characterGenerated - 65]++;
		}
		
		// Calculate the generated character frequencies
		
		for (int index = 0; index < weightedRandomCharacterCounts.length; index++)
		{
			if (weightedRandomCharacterCounts[index] > 0)
			{
				// Round to nearest three decimal places
				
				weightedRandomCharacterFrequencies[index] = ((double)weightedRandomCharacterCounts[index] / (double)weightedRandomCharacters.length) * 100.0d;
				weightedRandomCharacterFrequencies[index] = Math.round(weightedRandomCharacterFrequencies[index] * 1000.0d) / 1000.0d;
			}
		}
		
		// Display the generated character frequencies and compare them to the English letter frequencies

		System.out.println("Generated Weighted Character Frequencies compared to English Letter Frequencies" + "\n");
		
		for (int index = 0; index < weightedRandomCharacterFrequencies.length; index++)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.000");
			System.out.println((char)(index + 65) + ": " + decimalFormat.format(weightedRandomCharacterFrequencies[index]) + "\t\t" + ": " + decimalFormat.format(Cryptography.ENGLISH_LETTER_FREQUENCIES[index]));
		}
	}
}
