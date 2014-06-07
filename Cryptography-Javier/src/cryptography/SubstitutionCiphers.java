package cryptography;

public class SubstitutionCiphers extends Cryptography
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private int[]		letterCounts;
	private char[] 		descendingLetterOrder;
	private double[]	letterFrequencies;
	private double[]	descendingOrderLetterFrequencies;
	
	/*
	 * Class Constants
	 * 
	 */
	
		
	/*
	 * Getters
	 * 
	 */
	
	public int[] getLetterCounts()
	{
		return letterCounts;
	}
	
	public char[] getDescendingLetterOrder()
	{
		return descendingLetterOrder;
	}
	
	public double[] getLetterFrequencies()
	{
		return letterFrequencies;
	}
	
	public double[] getDescendingOrderLetterFrequencies()
	{
		return descendingOrderLetterFrequencies;
	}
	
	/*
	 * Constructor
	 * 
	 */
	
	public SubstitutionCiphers()
	{
		super();
	}
	
	/*
	 * Ceasar Cipher Encipher Method
	 * 
	 */
	
	public String ceasarEncipher(String text, int shift)
	{
		// Extract words consisting of only letters. Exclude all digits, punctuation and white space.
		// \\W means non-word character
		// \\d means digit
		// + means one or more than one such character
		// Pass to split the exclusion list.
				
		String[] words 		= text.split("[\\W\\d]+");
		String 	 clearText 	= "";
		
		// Convert the words to uppercase and string them together as the clear text
		
		for (int index = 0; index < words.length; index++)
		{
			clearText = clearText + words[index];
		}

		clearText = clearText.toUpperCase();
		
		// Save the clear text
		
		setClearText(clearText);
		
		String shiftedText 	= "";
		char   character 	= ' ';
				
		// Shift every letter in the clear text the number of letters indicated by the shift value.
		// Wrap around to A as needed.
		
		for (int index = 0; index < clearText.length(); index++)
		{
			character = (char) (clearText.charAt(index) + shift);
			
			if (character > 90)
			{
				character = (char)(65 + (character - 90) - 1);
			}
			
			shiftedText = shiftedText + String.valueOf(character);
		}
		
		// Save the cipher text
		
		setCipherText1(shiftedText);
		
		// Set the cipher text block size
		
		setCipherTextBlockSize1(CIPHER_TEXT_BLOCK_SIZE);
		
		return shiftedText;
	}
	
	/*
	 * Ceasar Cipher Decipher Method
	 * 
	 */
	
	public String ceasarDecipher(String text, int shift)
	{
		// Extract words consisting of only letters. Exclude all digits, punctuation and white space.
		// \\W means non-word character
		// \\d means digit
		// + means one or more than one such character
		// Pass to split the exclusion list.
				
		String[] words 		= text.split("[\\W\\d]+");	
		String cipherText 	= "";
		char   character 	= ' ';
		String shiftedText 	= "";
		
		// Convert the words to uppercase and string them together as the cipher text
		
		for (int index = 0; index < words.length; index++)
		{
			cipherText = cipherText + words[index];
		}

		cipherText = cipherText.toUpperCase();
						
		// Shift every letter in the cipher text the number of letters indicated by the shift value.
		// Wrap around to Z as needed.
		
		for (int index = 0; index < cipherText.length(); index++)
		{
			character = (char) (cipherText.charAt(index) - shift);
			
			if (character < 65)
			{
				character = (char)(90 - (65 - character) + 1);
			}
			
			shiftedText = shiftedText + String.valueOf(character);
		}
		
		return shiftedText;
	}
	
	/*
	 * Frequency Analysis Method
	 * 
	 */
	
	public void frequencyAnalysis(String text)
	{
		letterCounts						= new int[26];
		descendingOrderLetterFrequencies 	= new double[26];
		descendingLetterOrder 				= ENGLISH_LETTERS;
		
		String cipherText = text.toUpperCase();

		// Calculate letter counts
		
		for (int index = 0; index < cipherText.length(); index++)
		{
			letterCounts[65 - cipherText.charAt(index)]++;
		}
		
		// Calculate letter frequencies
		
		for (int index = 0; index < letterCounts.length; index++)
		{
			letterFrequencies[index] = letterCounts[index] / 26;
		}
		
		// Sort letter order and frequencies in descending order
		
		sortFrequencies();
	}
	
	/*
	 * Sort Frequencies Method
	 * 
	 * Insertion Sort algorithm
	 * 
	 * Stable Sort algorithm. It is called stable if it keeps elements with equal keys in the same relative order in the output as they were in the input.
	 * Other stable sorts: Bubble Sort, Merge Sort, Counting Sort.
	 * Selection Sort and most implementations of Quick Sort are not stable sorts.
	 * 
	 * Sort the numerical values of the keyword characters and parallel sort the keyword column #s in descending sequence.
	 * 
	 */
	
	public void sortFrequencies()
	{
		// Local variables
		
		int		maximum 		= 0;
		double 	temp 			= 0;
		char	tempChar		= ' ';

		// Sort the array
		
		for (int pass = 1; pass < letterFrequencies.length; pass++)
		{
			// Save the new maximum value index and the maximum column index
			
			maximum = pass;
			
			// The Compare and Swap Loop
			
			for (int compare = pass + 1; compare < letterFrequencies.length; compare++)
			{
				// Sort in descending order
				
				if (letterFrequencies[compare] > letterFrequencies[maximum])
				{
					// Save the new maximum value index
					
					maximum = compare;
				}
			}

			// Swap elements to bring the larger element to the top

			temp 							= letterFrequencies[pass];
			letterFrequencies[pass] 		= letterFrequencies[maximum];			
			letterFrequencies[maximum] 		= temp;
			
			// Sort the letters in descending order as well
			
			tempChar						= descendingLetterOrder[pass];
			descendingLetterOrder[pass]		= descendingLetterOrder[maximum];
			descendingLetterOrder[maximum]	= tempChar;
		}
	}
}
