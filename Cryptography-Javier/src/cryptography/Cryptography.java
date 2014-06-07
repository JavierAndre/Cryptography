package cryptography;

import java.util.Random;

public class Cryptography
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private String	clearText;
	private String	cipherText1;
	private String	cipherText2;
	private int		cipherTextBlockSize1;
	private int		cipherTextBlockSize2;

	/*
	 * Class Constants
	 * 
	 */
	
	public static final char[]		ENGLISH_LETTERS										= { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public static final char[]		MOST_FREQUENT_ENGLISH_LETTERS						= { 'E', 'T', 'A', 'O', 'I', 'N', 'S', 'H', 'R', 'D', 'L', 'C', 'U' };
	
	public static final char[]		DESCENDING_ENGLISH_LETTER_ORDER 					= { 'E', 'T', 'A', 'O', 'I', 'N', 'S', 'R', 'H', 'L', 'D', 'C', 'U', 'M', 'F', 'P', 'G', 'W', 'Y', 'B', 'V', 'K', 'X', 'J', 'Q', 'Z' };

	// Letter Frequencies From A to Z:										  	  	  		  A		 B		 C	    D	   E	  F		 G		H	   I	  J		 K		L	   M	  N		 O		P	   Q	  R		 S		T	   U	 V		W		X	   Y	  Z	
	
	public static final double[]	ENGLISH_LETTER_FREQUENCIES 							= { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074 };

	// Letter Frequencies in Descending Order:								  	  	  		  E		  T		 A		O	   I	  N		 S		H      R	  D		 L		C	   U	  M		 W	    F	   G	  P	  	 Y		B	   V	  K		 J		X	   Q	  Z
	
	public static final double[]	DESCENDING_ENGLISH_LETTER_FREQUENCIES 				= { 12.702, 9.056, 8.167, 7.507, 6.966, 6.749, 6.327, 6.094, 5.987, 4.253, 4.025, 2.782, 2.758, 2.406, 2.360, 2.228, 2.015, 1.929, 1.974, 1.492, 0.978, 0.772, 0.153, 0.150, 0.095, 0.074 };

	// Letter Frequencies in Ascending Order:								  	  	  		  Z		 Q		 X	    J	   K	  V 	 B	    Y	   P	  G		 F		W	   M	  U		 C	    L	   D	 R		H		S	   N	  I		O		A	   T	  E
	
	public static final double[]	ASCENDING_ENGLISH_LETTER_FREQUENCIES 				= { 0.074, 0.095, 0.150, 0.153, 0.772, 0.978, 1.492, 1.974, 1.929, 2.015, 2.228, 2.360, 2.406, 2.758, 2.782, 4.025, 4.253, 5.987, 6.094, 6.327, 6.749, 6.966, 7.507, 8.167, 9.056, 12.702 };
	
	// Cummulative Letters in Ascending Order
	
	public static final char[]		CUMMULATIVE_ASCENDING_ENGLISH_LETTER_FREQUENCIES	= {'Z', 'Q', 'X', 'J', 'K', 'V', 'B', 'Y', 'P', 'G', 'F', 'W', 'M', 'U', 'C', 'L', 'D', 'R', 'H', 'S', 'N', 'I', 'O', 'A', 'T', 'E'	};
	
	// Weighted Letter Frequencies From A to Z:										  	  	  A		 B		 C	     D	    E	    F		 G		H	    I	    J		 K		L	     M	    N		 O		P	   Q	   R		S		T	   U	   V		W		X	   Y	   Z	
	
	public static final double[]	WEIGHTED_ENGLISH_LETTER_FREQUENCIES 				= { 8.167, 9.659, 12.441, 16.694, 29.396, 31.624, 33.639, 39.733, 46.699, 46.852, 47.624, 51.649, 54.055, 60.804, 68.311, 70.24, 70.335, 76.322, 82.649, 91.705, 94.463, 95.441, 97.801, 97.951, 99.925, 99.999 };
	
	public static final int			CIPHER_TEXT_BLOCK_SIZE								= 4;
	public static final int			MINIMUM_KEYWORD_LENGTH								= 6;
	public static final int			MAXIMUM_KEYWORD_LENGTH								= MOST_FREQUENT_ENGLISH_LETTERS.length;
	
	public static final int			ASCENDING_SEQUENCE									= 1;
	public static final int			DESCENDING_SEQUENCE									= 2;
	public static final int			INDEX_NOT_FOUND										= -1;

	public static final int			INITIALIZATION_CHARACTER							= ' ';		// ASCII 32 decimal
	public static final int			BLOCKED_OFF_CHARACTER								= '\0';		// ASCII 0 decimal
	
	public static final int			REGULAR_CIPHER_TEXT									= 1;
	public static final int			IRREGULAR_CIPHER_TEXT								= 2;
	
	public static final String		CRYPTOGRAPHY_VERSION								= "Version 0.3";

	/*
	 * Getters
	 * 
	 */
	
	public String getClearText()
	{
		return clearText;
	}
	
	public String getCipherText1()
	{
		return cipherText1;
	}
	
	public String getCipherText2()
	{
		return cipherText2;
	}
	
	public int getCipherTextBlockSize1()
	{
		return cipherTextBlockSize1;
	}
	
	public int getCipherTextBlockSize2()
	{
		return cipherTextBlockSize2;
	}
	
	/*
	 * Setters
	 * 
	 */
	
	public void setClearText(String text)
	{
		clearText = text;
	}
	
	public void setCipherText1(String text)
	{
		cipherText1 = text;
	}
	
	public void setCipherText2(String text)
	{
		cipherText2 = text;
	}
	
	public void setCipherTextBlockSize1(int size)
	{
		cipherTextBlockSize1 = size;
	}
	
	public void setCipherTextBlockSize2(int size)
	{
		cipherTextBlockSize2 = size;
	}
	
	/*
	 * Constructor
	 * 
	 */
	
	public Cryptography()
	{
		clearText	= "";
		cipherText1 = "";
		cipherText2 = "";
	}
	
	/*
	 * Validate Random Character Method
	 * 
	 */
	
	public boolean validateRandomCharacter(char[] randomCharacters, char character)
	{
		boolean isValid = true;
		
		for (int index = 0; index < randomCharacters.length; index++)
		{
			if (randomCharacters[index] == character)
			{
				isValid = false;
				break;
			}
		}
		
		return isValid;
	}
	
	/*
	 * Get Null Method
	 * 
	 * Randomly selects a letter from the English alphabet according to its weighted letter frequency.
	 * Nulls are used in regular cipher text to pad short rows.
	 * 
	 */
	
	public char getNull()
	{
		Random 	 random					= new Random();
		double	 randomCummulativeValue	= 0;
		char 	 randomCharacter		= ' ';
		
		// Randomly select a character from the cummulative ascending letter frequencies. nextDouble() returns a number from 0.0 (inclusive) to 1.0 (exclusive).
		// Multiplying it by 100.0 gives a value from 0.0 to 0.999999...
		
		randomCummulativeValue = random.nextDouble() * 100.0d;

		// Find the letter with the closest weighted frequency
		
		randomCharacter = ENGLISH_LETTERS[search(randomCummulativeValue)];
		
		return randomCharacter;
	}
	
	/*
	 * Search Method
	 * 
	 * Don't do Binary Search! It does not guarantee find the CLOSEST value greater than the search value!
	 * 
	 */
	
	public int search(double searchValue)
	{
		// Local variables
		
		int	indexFound = 0;
		
		for (int index = 0; index < WEIGHTED_ENGLISH_LETTER_FREQUENCIES.length; index++)
		{
			// Quit searching when a frequency greater or equal than the search value is found
			
			if (WEIGHTED_ENGLISH_LETTER_FREQUENCIES[index] >= searchValue)
			{
				indexFound = index;
				
				break;
			}			
		}
		
		return indexFound;
	}
	
	/*
	 * Strip NonAlphabetic Characters Method
	 * 
	 */
	
	public String stripNonAlphabetic(String text)
	{
		// Strip non-alphabetic characters
		
		String[] words = text.split("[\\W\\d]+");
		
		text = "";
		
		for (int index = 0; index < words.length; index++)
		{
			text = text + words[index];
		}
		
		return text;
	}
	
	/*
	 * Matrices Method
	 * 
	 */
	
	private int[][] matrices(int number)
	{
		// The square root of the number represents all the values that when squared are less than the number.
		// Ignore (square root + 1), as it is the mirror image of the square root (e.g., if number = 72 and limit = 8,
		// then 8 x 9 = 72 and 9 * 8 = 72).
		
		int limit = (int) Math.sqrt(number);
		
		int[][] matrices = new int[2][limit];
		
		// Ignore 1 as a trivial factor
		
		for (int factor = 2; factor <= limit; factor++)
		{
			// Find all the values less than or equal to the limit than evenly divide the number
			
			if (number % factor == 0)
			{
				// For example, if the number is 72:
				// matrices[0][0] = 2
				// matrices[1][0] = 36
				// matrices[0][1] = 3
				// matrices[1][1] = 24
				
				matrices[factor - 2][factor - 2] = factor;
				matrices[factor - 1][factor - 2] = number / factor;
			}
		}
		
		return matrices;
	}
}
