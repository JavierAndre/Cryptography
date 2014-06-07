package cryptographyTests;

import cryptography.Cryptanalysis;
import cryptography.Cryptography;
import cryptography.TranspositionCiphers;

public class CryptanalysisTests
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Cryptanalysis	cryptanalysis;
	
	/*
	 * Constructor
	 * 
	 */
	
	public CryptanalysisTests()
	{
		cryptanalysis = new Cryptanalysis();
	}
	
	/*
	 * Rull all tests
	 * 
	 */
	
	public void run()
	{
		testIsTransposition();
	}
	
	/*
	 * Test isTransposition Method and its helpers
	 * 
	 */
	
	public void testIsTransposition()
	{
		String cipherText 	= "Bergdahl, the only remaining U.S. soldier captured during the conflicts in Iraq and Afghanistan, was recovered by U.S. special operations forces without incident about 10:30 a.m. ET at a pick-up point in eastern Afghanistan, near the border with Pakistan, a senior Department of Defense official told CNN. There were 18 armed Taliban members present.";

		// Create TranspositionCiphers instance
		
		Cryptography cipher = new TranspositionCiphers();
		
		// Strip non-alphabetic characters
		
		cipherText = cipher.stripNonAlphabetic(cipherText);
		
		// Check if the cipher text is from a Transposition Cipher
		
		cryptanalysis.isTransposition(cipherText);
		
		int[] 	 letterFrequencies				= cryptanalysis.getLetterFrequencies();
		double[] letterFrequencyPercentages		= cryptanalysis.getLetterFrequencyPercentages();
		double[] letterFrequencyDifferences		= cryptanalysis.getLetterFrequencyDifferences();
		
		// Display Letter frequencies

		System.out.println("Character Frequencies" + "\n");
		
		for (int index = 0; index < letterFrequencies.length; index++)
		{
			System.out.println((char)(index + 65) + ": " + letterFrequencies[index]);
		}
		
		// Display Letter frequencies

		System.out.println("Character Frequency Percentages compared to English Letter Frequencies" + "\n");
		
		for (int index = 0; index < letterFrequencies.length; index++)
		{
			System.out.println((char)(index + 65) + ": " + letterFrequencyPercentages[index] + "\t\t" + ": " + Cryptography.ENGLISH_LETTER_FREQUENCIES[index]);
		}
	}
}
