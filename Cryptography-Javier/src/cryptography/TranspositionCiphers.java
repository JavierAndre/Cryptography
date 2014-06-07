package cryptography;

import java.util.Random;

public class TranspositionCiphers extends Cryptography
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private char[][] 		transpositionMatrix1;
	private char[][] 		transpositionMatrix2;
	private int[] 			keyword1LetterOrder;
	private int[] 			keyword2LetterOrder;
	
	/*
	 * Class Constants
	 * 
	 */
	
	public static final int	COLUMNAR_TRANSPOSITION			= 1;
	public static final int	DOUBLE_COLUMNAR_TRANSPOSITION	= 2;
	
	/*
	 * Getters
	 * 
	 */
	
	public char[][] getTranspositionMatrix1()
	{
		return transpositionMatrix1;
	}
	
	public char[][] getTranspositionMatrix2()
	{
		return transpositionMatrix2;
	}
	
	public int[] getKeyword1LetterOrder()
	{
		return keyword1LetterOrder;
	}
	
	public int[] getKeyword2LetterOrder()
	{
		return keyword2LetterOrder;
	}
	
	/*
	 * Constructor
	 * 
	 */
	
	public TranspositionCiphers()
	{
		
	}
	
	/*
	 * Columnar Transposition Cipher Encipher Method
	 * 
	 * Columnar Transposition uses a keyword.
	 * The keywords must meet a minimum and maximum length.
	 * Keywords cannot be part of clear text;
	 * If a keyword is invalid an empty cipher text string is returned.
	 * 
	 * Select between regular (padded) and irregular (unpadded) cipher text.
	 * 
	 */
	
	public String columnarTranspositionEncipher(String clearText, String keyword, int cipherTextType)
	{
		String 	 cipherText 				= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int		 clearTextPosition 			= 0;
		int		 numberOfRandomCharacters	= 0;
		Random 	 randomCharacter			= new Random();
		char 	 character					= ' ';
		boolean	 finished					= false;
				
		/*
		 * First Columnar Transposition
		 * 
		 * Write the clear text in rows on the keyword #1 transposition matrix
		 * 
		 */
		
		columns 		= keyword.length();
		rows			= clearText.length() / columns;
		rowsRemainder	= clearText.length() % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}

		// Initialize the keyword #1 transposition matrix
		
		char[] 	 randomCharacters 	= new char[keyword.length()];
		transpositionMatrix1 		= new char[rows][columns];
		
		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
	
		// Write the clear text in rows, padding the last short row as needed if regular cipher text was selected
		
		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				if (clearTextPosition < clearText.length())
				{
					transpositionMatrix1[row][col] = clearText.charAt(clearTextPosition);
				
					clearTextPosition++;
				}
				else
				{
					// Regular cipher text: pad the clear text with a random high-frequency letter. Avoid repeating random characters.
					
					if (cipherTextType == REGULAR_CIPHER_TEXT)
					{
						finished = false;
						
						while (!finished)
						{
							character = getNull();
							
							if (validateRandomCharacter(randomCharacters, character))
							{
								// Save the random character
								
								randomCharacters[numberOfRandomCharacters] = character;
								numberOfRandomCharacters++;
								
								transpositionMatrix1[row][col] = character;
								
								finished = true;
							}
						}
					}
				}
			}
		}
			
		// Get the numerical values of the letters of keyword #1 as offsets from A (65)
		
		int[] keywordNumericalValues = new int[keyword.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #1 characters in ascending sequence. Parallel sort the keyword #1 column order numbers.
		
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #1 column order into word order
		
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
			
		// Read the clear text in column order according to the sorted numerical values of each keyword #1 character.
		// If this is an irregular cipher text, there may be an incomplete last row with one or more matrix cols still set to the initialization character.
		// If so, the initialization characters will be skipped.
		
		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != INITIALIZATION_CHARACTER)
				{
					cipherText = cipherText + transpositionMatrix1[row][keywordColumnOrder[col]];
				}
			}
		}
		
		// Save the intermediate cipher text
		
		setCipherText1(cipherText);
		
		// Set the cipher text block size
		
		setCipherTextBlockSize1(CIPHER_TEXT_BLOCK_SIZE);

		// Return the final cipher text
		
		return cipherText;
	}
	
	/*
	 * Columnar Transposition Decipher Method
	 * 
	 * Columnar Transposition uses a keyword.
	 * The keyword must meet a minimum and maximum length.
	 * Keywords cannot be part of cipher text.
	 * If a keyword is invalid an empty clear text string is returned.
	 * 
	 * The cipher text may be regular (padded) or irregular (unpadded).
	 * 
	 */
	
	public String columnarTranspositionDecipher(String cipherText, String keyword)
	{
		String 	 clearText 					= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int 	 cipherTextPosition			= 0;

		/*
		 * Columnar Transposition
		 * 
		 * Calculate the number of complete transposition matrix rows.
		 * Write the cipher text in cols on the transposition matrix.
		 * Read the clear text in row order from the transposition matrix.
		 * 
		 */

		// Calculate the transposition matrix dimension
		
		columns 		= keyword.length();
		rows			= cipherText.length() / columns;
		rowsRemainder	= cipherText.length() % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}

		// Initialize the transposition matrix to the initialization character. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row will be blocked off by initializing them to the block off character.
		
		transpositionMatrix1 = new char[rows][columns];

		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
		
		if (rowsRemainder > 0)
		{
			for (int col = rowsRemainder; col < transpositionMatrix1[rows - 1].length; col++)
			{
				transpositionMatrix1[rows - 1][col] = BLOCKED_OFF_CHARACTER;
			}
		}
		
		// Get the numerical values of the letters of the keyword as offsets from A (65)
		
		int[] keywordNumericalValues = new int[keyword.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword characters in ascending sequence. Parallel sort the keyword column order numbers.
		
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword column order into word order
		
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Write the cipher text in col order on the transposition matrix.
		// If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row are blocked off by initializing them to the block off character.
		// Skip the blocked off cols.
		
		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != BLOCKED_OFF_CHARACTER)
				{				
					if (cipherTextPosition < cipherText.length())
					{
						transpositionMatrix1[row][keywordColumnOrder[col]] = cipherText.charAt(cipherTextPosition);
					
						cipherTextPosition++;
					}
				}
			}
		}
		
		// Read the clear text in row order from the transposition matrix

		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				if (transpositionMatrix1[row][col] != BLOCKED_OFF_CHARACTER)
				{
					clearText = clearText + transpositionMatrix1[row][col];
				}
			}
		}

		// Save the clear text
		
		setClearText(clearText);
		
		// Return the clear text
		
		return clearText;
	}
	
	/*
	 * Double Columnar Transposition Cipher Encipher Method
	 * 
	 * Double Columnar Transposition uses two keywords.
	 * The keywords must meet a minimum and maximum length.
	 * Keywords cannot be part of clear text;
	 * If a keyword is invalid an empty cipher text string is returned.
	 * 
	 * Select between regular (padded) and irregular (unpadded) cipher text.
	 *
	 */
	
	public String doubleColumnarTranspositionEncipher(String clearText, String keyword1, String keyword2, int cipherTextType)
	{
		String 	 cipherText 				= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int		 clearTextPosition 			= 0;
		int		 numberOfRandomCharacters	= 0;
		Random 	 randomCharacter			= new Random();
		char 	 character					= ' ';
		boolean	 finished					= false;
		int 	 cipherTextLength			= 0;
		int 	 cipherTextPosition			= 0;
						
		/*
		 * First Columnar Transposition
		 * 
		 * Write the clear text in rows on the keyword #1 transposition matrix 
		 * 
		 */
		
		columns 		= keyword1.length();
		rows			= clearText.length() / columns;
		rowsRemainder	= clearText.length() % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}

		// Initialize the keyword #1 transposition matrix
		
		char[] 	 randomCharacters 	= new char[keyword1.length()];
		transpositionMatrix1 		= new char[rows][columns];
		
		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
	
		// Write the clear text in rows, padding the last short row if needed if regular cipher text was selected
		
		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				if (clearTextPosition < clearText.length())
				{
					transpositionMatrix1[row][col] = clearText.charAt(clearTextPosition);
				
					clearTextPosition++;
				}
				else
				{
					// Regular cipher text: pad the clear text with a random high-frequency letter. Avoid repeating random characters!
					
					if (cipherTextType == REGULAR_CIPHER_TEXT)
					{
						finished = false;
						
						while (!finished)
						{
							//character = Cryptography.mostFrequentLettersEnglish[randomCharacter.nextInt(Cryptography.mostFrequentLettersEnglish.length)];
	
							character = getNull();
							
							if (validateRandomCharacter(randomCharacters, character))
							{
								// Save the random character
								
								randomCharacters[numberOfRandomCharacters] = character;
								numberOfRandomCharacters++;
								
								//transpositionMatrix1[row][col] = Cryptography.mostFrequentLettersEnglish[randomCharacter.nextInt(Cryptography.mostFrequentLettersEnglish.length)];
								
								transpositionMatrix1[row][col] = character;
								
								finished = true;
							}
						}
					}
				}
			}
		}
			
		// Get the numerical values of the letters of keyword #1 as offsets from A (65)
		
		int[] keywordNumericalValues = new int[keyword1.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword1.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #1 characters in ascending sequence. Parallel sort the keyword #1 column order numbers.
		
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #1 column order into word order
		
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Read the clear text in column order according to the sorted numerical values of each keyword #1 character.
		// If this is an irregular cipher text, there may be an incomplete last row with one or more matrix cols still set to the initialization character.
		// If so, the initialization characters will be skipped. Write the intermediate cipher text.
		
		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != INITIALIZATION_CHARACTER)
				{
					cipherText = cipherText + transpositionMatrix1[row][keywordColumnOrder[col]];
				}
			}
		}
		
		// Save the intermediate cipher text
		
		setCipherText1(cipherText);
			
		// Set the cipher text block size
		
		setCipherTextBlockSize1(CIPHER_TEXT_BLOCK_SIZE);
		
		/*
		 * Second Columnar Transposition
		 * 
		 * Read the intermediate cipher text in column order according to the column order of each keyword #1 character.
		 * Write the intermediate cipher text in row order on the keyword #2 transposition matrix.
		 * 
		 */

		// Get the length of the intermediate cipher text
		
		cipherTextLength  	= cipherText.length();

		// Calculate the number of keyword #2 transposition matrix columns
		
		columns 			= keyword2.length();

		// Calculate the keyword #2 transposition matrix rows
		
		rows				= cipherTextLength / columns;
		rowsRemainder		= cipherTextLength % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}
			
		randomCharacters 	 		= new char[keyword2.length()];
		numberOfRandomCharacters	= 0;
		transpositionMatrix2 		= new char[rows][columns];
	
		// Initialize the keyword #2 transposition matrix
		
		for (int row = 0; row < transpositionMatrix2.length; row++)
		{
			for (int col = 0; col < transpositionMatrix2[row].length; col++)
			{
				transpositionMatrix2[row][col] = INITIALIZATION_CHARACTER;
			}
		}
			
		// Read the intermediate cipher text in column order according to the column order of each keyword #1 character.
		// Write the intermediate cipher text in row order on the keyword #2 transposition matrix, padding the last short row if needed.
		
		for (int row = 0; row < transpositionMatrix2.length; row++)
		{
			for (int col = 0; col < transpositionMatrix2[row].length; col++)
			{
				if (cipherTextPosition < cipherTextLength)
				{
					transpositionMatrix2[row][col] = cipherText.charAt(cipherTextPosition);
				
					cipherTextPosition++;
				}
				else
				{
					// Regular cipher text: pad the clear text with a random high-frequency letter. Avoid repeating random characters!
					
					if (cipherTextType == -1)
					{
						finished = false;
						
						while (!finished)
						{
							//character = Cryptography.mostFrequentLettersEnglish[randomCharacter.nextInt(Cryptography.mostFrequentLettersEnglish.length)];
	
							character = getNull();
							
							if (validateRandomCharacter(randomCharacters, character))
							{
								// Save the random character
								
								randomCharacters[numberOfRandomCharacters] = character;
								numberOfRandomCharacters++;
								
								//transpositionMatrix2[row][col] = Cryptography.mostFrequentLettersEnglish[randomCharacter.nextInt(Cryptography.mostFrequentLettersEnglish.length)];
								
								transpositionMatrix2[row][col] = character;
								
								finished = true;
							}
						}
					}
				}
			}
		}

		// Get the numerical values of the letters of keyword #2 as offsets from A (65)
		
		keywordNumericalValues = new int[keyword2.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword2.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #2 characters in ascending sequence. Parallel sort the keyword #2 column order numbers.
		
		keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #2 column order into word order
		
		keyword2LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword2LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Read the cipher text in column order according to the sorted numerical values of each keyword #2 character.
		// If this is an irregular cipher text, there may be an incomplete last row with one or more matrix cols still set to the initialization character.
		// If so, the initialization characters will be skipped. Write the final cipher text.
		
		cipherText = "";
								
		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix2[row][keywordColumnOrder[col]] != INITIALIZATION_CHARACTER)
				{
					cipherText = cipherText + transpositionMatrix2[row][keywordColumnOrder[col]];
				}
			}
		}

		// Save the final cipher text
		
		setCipherText2(cipherText);
		
		// Set the cipher text block size
		
		setCipherTextBlockSize2(CIPHER_TEXT_BLOCK_SIZE);
		
		// Return the final cipher text
		
		return cipherText;
	}
	
	/*
	 * Double Columnar Transposition Decipher Method
	 * 
	 * Double Columnar Transposition uses two keywords.
	 * The keywords must meet a minimum and maximum length.
	 * Keywords cannot be part of cipher text.
	 * If a keyword is invalid an empty clear text string is returned.
	 * 
	 * The cipher text may be regular (padded) or irregular (unpadded).
	 * 
	 */
	
	public String doubleColumnarTranspositionDecipher(String cipherText, String keyword1, String keyword2)
	{
		String 	 clearText 					= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int 	 cipherTextLength			= cipherText.length();
		int 	 cipherTextPosition			= 0;

		/*
		 * First Columnar Transposition
		 * 
		 * Write the final cipher text in cols on the keyword #2 transposition matrix.
		 * Read the intermediate cipher text in row order from the keyword #2 transposition matrix.
		 * 
		 */
		
		// Calculate the keyword #2 transposition matrix dimension
		
		columns 		= keyword2.length();
		rows			= cipherText.length() / columns;
		rowsRemainder	= cipherText.length() % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}

		// Initialize the keyword #2 transposition matrix to the initialization character. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row will be blocked off by initializing them to the block off character.
		
		transpositionMatrix2 = new char[rows][columns];

		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < transpositionMatrix2[row].length; col++)
			{
				transpositionMatrix2[row][col] = INITIALIZATION_CHARACTER;
			}
		}
		
		if (rowsRemainder > 0)
		{
			for (int col = rowsRemainder; col < transpositionMatrix2[rows - 1].length; col++)
			{
				transpositionMatrix2[rows - 1][col] = BLOCKED_OFF_CHARACTER;
			}
		}
		
		// Get the numerical values of the letters of keyword #2 as offsets from A (65)
		
		int[] keywordNumericalValues = new int[keyword2.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword2.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #2 characters in ascending sequence. Parallel sort the keyword #2 column order numbers.
		
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #2 column order into word order
		
		keyword2LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword2LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Write the cipher text in col order on the transposition matrix.
		// If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row are blocked off by initializing them to the block off character.
		// Skip the blocked off cols.
		
		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix2[row][keywordColumnOrder[col]] != BLOCKED_OFF_CHARACTER)
				{				
					if (cipherTextPosition < cipherText.length())
					{
						transpositionMatrix2[row][keywordColumnOrder[col]] = cipherText.charAt(cipherTextPosition);
					
						cipherTextPosition++;
					}
				}
			}
		}
		
		// Read the intermediate cipher text in row order from the keyword #2 transposition matrix

		cipherText = "";
		
		for (int row = 0; row < transpositionMatrix2.length; row++)
		{
			for (int col = 0; col < transpositionMatrix2[row].length; col++)
			{
				if (transpositionMatrix2[row][col] != BLOCKED_OFF_CHARACTER)
				{
					cipherText = cipherText + transpositionMatrix2[row][col];
				}
			}
		}
		
		// Save the intermediate cipher text
		
		setCipherText2(cipherText);
		
		// Set the cipher text block size
		
		setCipherTextBlockSize2(CIPHER_TEXT_BLOCK_SIZE);
		
		/*
		 * Second Columnar Transposition
		 * 
		 * Write the intermediate cipher text in col order on the keyword #1 transposition matrix.
		 * 
		 */
		
		// Get the intermediate cipher text length
		
		cipherTextLength 	= cipherText.length();
		
		// Calculate the number of keyword #1 transposition matrix columns
		
		columns 			= keyword1.length();

		// Calculate the keyword #1 transposition matrix rows
		
		rows				= cipherTextLength / columns;
		rowsRemainder		= cipherTextLength % columns;
		
		if (rowsRemainder > 0)
		{
			rows++;
		}

		// Initialize the keyword #1 transposition matrix to the initialization character. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row will be blocked off by initializing them to the block off character.
		
		transpositionMatrix1 = new char[rows][columns];

		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
		
		if (rowsRemainder > 0)
		{
			for (int col = rowsRemainder; col < transpositionMatrix1[rows - 1].length; col++)
			{
				transpositionMatrix1[rows - 1][col] = BLOCKED_OFF_CHARACTER;
			}
		}
		
		// Get the numerical values of the letters of keyword #1 as offsets from A (65)
		
		keywordNumericalValues = new int[keyword1.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword1.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #1 characters in ascending sequence. Parallel sort the keyword #1 column order numbers.
		
		keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #1 column order into word order
		
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++)
		{
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Write the cipher text in col order on the transposition matrix.
		// If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row are blocked off by initializing them to the block off character.
		// Skip the blocked off cols.
		
		cipherTextPosition 	 = 0;

		for (int col = 0; col < keywordColumnOrder.length; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != BLOCKED_OFF_CHARACTER)
				{				
					if (cipherTextPosition < cipherText.length())
					{
						transpositionMatrix1[row][keywordColumnOrder[col]] = cipherText.charAt(cipherTextPosition);
					
						cipherTextPosition++;
					}
				}
			}
		}
		
		// Read the cipher text in row order from the keyword #1 transposition matrix.
		// Write the clear text.
		
		for (int row = 0; row < transpositionMatrix1.length; row++)
		{
			for (int col = 0; col < transpositionMatrix1[row].length; col++)
			{
				if (transpositionMatrix1[row][col] != BLOCKED_OFF_CHARACTER)
				{
					clearText = clearText + transpositionMatrix1[row][col];
				}
			}
		}
			
		// Save the clear text
		
		setClearText(clearText);

		// Return the clear text
		
		return clearText;
	}
	
	/*
	 * Sort Keyword Columns Method
	 * 
	 * Insertion Sort algorithm
	 * 
	 * Stable Sort algorithm. It is called stable if it keeps elements with equal keys in the same relative order in the output as they were in the input.
	 * Other stable sorts: Bubble Sort, Merge Sort, Counting Sort.
	 * Selection Sort and most implementations of Quick Sort are not stable sorts.
	 * 
	 * Sort the numerical values of the keyword characters and parallel sort the keyword column order #s in ascending sequence.
	 * 
	 */
	
	public int[] sortKeywordColumns(int[] keywordNumericalValues)
	{
		// Local variables
		
		int	minimum 		= 0;
		int	compare			= 0;
		int minimumIndex	= 0;

		int[] columnOrder	= new int[keywordNumericalValues.length];
		
		for (int index = 0; index < columnOrder.length; index++)
		{
			columnOrder[index] = index;
		}
		
		// Sort the array
		
		for (int pass = 1; pass < keywordNumericalValues.length; pass++)
		{
			// Save the new minimum value and the minimum column index
			
			minimum = keywordNumericalValues[pass];
			
			minimumIndex = pass;
			
			// The Compare and Swap Loop
			
			for (compare = pass - 1; compare >= 0 && minimum < keywordNumericalValues[compare]; compare--)
			{
				// Move the larger value down and save its column index
				
				keywordNumericalValues[compare + 1] = keywordNumericalValues[compare];
				
				columnOrder[compare + 1] = columnOrder[compare];
			}

			// Move the smaller value found to the top and save its column index
			
			keywordNumericalValues[compare + 1] = minimum;
			
			columnOrder[compare + 1] = minimumIndex;
		}
		
		// Return the sorted Column Order array
		
		return columnOrder;	
	}
	
	/*
	 * Code Breaking Method
	 * 
	 */
	
	public String codeBreaking(String cipherText)
	{
		String clearText = "";
		
		/*
		 * Is it a Transposition Cipher?
		 * 
		 * Calculate cipher text letter frequencies and compare to English letter frequencies.
		 * 
		 */
		
		//int isTransposition = isTransposition(cipherText);

		
		
		
		
		return clearText;
	}
}
