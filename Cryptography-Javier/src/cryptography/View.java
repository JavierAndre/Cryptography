package cryptography;

import java.util.Scanner;

public class View
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private Cryptography 	cipher;
	
	private Scanner			keyboard;
	private String 			clearText;
	private String 			cipherText;
	
	/*
	 * Class Constants
	 * 
	 */
	
	private final String	CLEAR_TEXT_MESSAGE						= "Clear Text Message";
	private final String	CIPHER_TEXT_MESSAGE						= "Cipher Text Message";
		
	private final String	SUBSTITUTION_CIPHERS					= "1";
	private final String	TRANSPOSITION_CIPHERS					= "2";
	private final String	CRYPTANALYSIS							= "3";
	private final String	MONO_ALPHABETIC_SUBSTITUTION_CIPHERS	= "1";
	private final String	POLY_ALPHABETIC_SUBSTITUTION_CIPHERS	= "2";
	private final String	CEASAR_CIPHER_ENCIPHER 					= "1";
	private final String	CEASAR_CIPHER_DECIPHER 					= "2";
	private final String	SUBSTITUTION_CIPHER_FREQUENCY_ANALYSIS	= "3";
	private final String	CUMMULATIVE_LETTER_FREQUENCIES			= "4";
	private final String	COLUMNAR_TRANSPOSITION_ENCIPHER			= "1";
	private final String	COLUMNAR_TRANSPOSITION_DECIPHER			= "2";
	private final String	DOUBLE_COLUMNAR_TRANSPOSITION_ENCIPHER	= "3";
	private final String	DOUBLE_COLUMNAR_TRANSPOSITION_DECIPHER	= "4";
	private final String	TRANSPOSITION_CODE_BREAKING				= "5";
	private final String	HELP									= "H";
	private final String	EXIT 									= "E";
	private final int		NULL_KEYWORD							= 1;
	private final int		NON_NULL_KEYWORD						= 2;
	
	/*
	 * Constructor
	 * 
	 */
	
	public View()
	{
		
	}
	
	/*
	 * Display Main Menu Method
	 * 
	 */
	
	public void displayMainMenu()
	{
		String menuOption 	= "";
		keyboard 			= new Scanner(System.in);
		
		// Main Menu
		
		while (!menuOption.equalsIgnoreCase(EXIT))
		{
			System.out.println("----------------------------");
			System.out.println("| Welcome to Cryptography! |");
			
			System.out.println("|      < " + Cryptography.CRYPTOGRAPHY_VERSION + " >     |");
			
			System.out.println("----------------------------" + "\n");
			
			System.out.println("(1) Substitution Ciphers");
			System.out.println("(2) Transposition Ciphers");
			System.out.println("(3) Cryptanalysis");
			
			System.out.println("\n" + "(E) Exit the Cryptography App" + "\n");
			
			System.out.print("Choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase())
			{
				case SUBSTITUTION_CIPHERS:
				{
					displaySubstitutionCiphersMenu();
										
					break;
				}
				case TRANSPOSITION_CIPHERS:
				{
					displayTranspositionCiphersMenu();
										
					break;
				}
				case CRYPTANALYSIS:
				{
					displayCryptanalysisMenu();
										
					break;
				}
				case EXIT:
				{
					System.out.println("\n" + "Thank you for using the Cryptography App." + "\n");
					
					break;
				}
				default:
				{
					System.out.println("\n" + "Please enter a valid Menu option" + "\n");
					
					break;
				}
			}
		}
	}
	
	/*
	 * Display Substitution Ciphers Method
	 * 
	 */
	
	private void displaySubstitutionCiphersMenu()
	{
		String	menuOption = "";
	
		// Create SubstitutionCiphers instance
		
		cipher = new SubstitutionCiphers();
		
		// Main Menu
		
		while (!menuOption.equalsIgnoreCase(EXIT))
		{
			System.out.println("\n" + "-----------------------------");
			System.out.println("| Substitution Ciphers Menu |");
			System.out.println("-----------------------------" + "\n");
			
			System.out.println("(1) Nono-Alphabetic Substitution Ciphers");
			System.out.println("(2) Poly-Alphabetic Substitution Ciphers");
			
			System.out.println("\n" + "(E) Exit Menu" + "\n");
			
			System.out.print("Choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase())
			{
				case MONO_ALPHABETIC_SUBSTITUTION_CIPHERS:
				{
					displayMonoAlhabeticSubstitutionCiphersMenu();
					
					break;
				}
				case POLY_ALPHABETIC_SUBSTITUTION_CIPHERS:
				{
					System.out.println("\n" + "Under Construction");
					
					break;
				}
				case EXIT:
				{
					System.out.println();
					
					break;
				}
				default:
				{
					System.out.println("\n" + "Please enter a valid Menu option");
					
					break;
				}
			}
		}
	}
	
	/*
	 * Display Mono-Alphabetic Substitution Ciphers Method
	 * 
	 */
	
	private void displayMonoAlhabeticSubstitutionCiphersMenu()
	{
		String	menuOption 			= "";
		int		shiftValue			= 0;
		int		cipherTextBlockSize = 0;
		
		// Create SubstitutionCiphers instance
		
		cipher = new SubstitutionCiphers();
		
		/*
		 *  Mono-Alphabetic Substitution Ciphers Menu
		 */
		
		while (!menuOption.equalsIgnoreCase(EXIT))
		{
			System.out.println("\n" + "---------------------------------------------");
			System.out.println("| Mono-Alphabetic Substitution Ciphers Menu |");
			System.out.println("---------------------------------------------" + "\n");
			
			System.out.println("(1) Ceasar Cipher Encipher");
			System.out.println("(2) Ceasar Cipher Decipher");
			
			System.out.println("\n" + "(E) Exit Menu" + "\n");
			
			System.out.print("Choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase())
			{
				case CEASAR_CIPHER_ENCIPHER:
				{
					System.out.println("\n" + "Ceasar Cipher Encipher");
					System.out.println("----------------------");
					
					clearText = getText(CLEAR_TEXT_MESSAGE);
					
					if (clearText.length() > 0)
					{
						shiftValue			= getShiftValue();					
						cipherText 			= ((SubstitutionCiphers) cipher).ceasarEncipher(clearText, shiftValue);
						cipherTextBlockSize = cipher.getCipherTextBlockSize1();
					
						displayCipherText(cipherText, cipherTextBlockSize);
					}
					
					break;
				}
				case CEASAR_CIPHER_DECIPHER:
				{
					System.out.println("\n" + "Ceasar Cipher Decipher");
					System.out.println("----------------------");
					
					cipherText = getText(CIPHER_TEXT_MESSAGE);
					
					if (cipherText.length() > 0)
					{
						shiftValue			= getShiftValue();				
						clearText 			= ((SubstitutionCiphers) cipher).ceasarDecipher(cipherText, shiftValue);
					
						displayClearText(clearText);
					}
					
					break;
				}
				case EXIT:
				{
					break;
				}
				default:
				{
					System.out.println("\n" + "Please enter a valid Menu option");
					
					break;
				}
			}
		}
	}
	
	/*
	 * Display Transposition Ciphers Menu Method
	 * 
	 */
	
	private void displayTranspositionCiphersMenu()
	{
		String	menuOption 			= "";
		String	keyword1			= "";
		String	keyword2			= "";
		int		cipherTextBlockSize = 0;
		int 	cipherTextType		= Cryptography.IRREGULAR_CIPHER_TEXT;
		
		// Create TranspositionCiphers instance
		
		cipher = new TranspositionCiphers();
		
		/*
		 * Transposition Ciphers Menu
		 */
					
		while (!menuOption.equalsIgnoreCase(EXIT))
		{
			System.out.println("\n" + "------------------------------");
			System.out.println("| Transposition Ciphers Menu |");
			System.out.println("------------------------------" + "\n");
			
			System.out.println("(1) Columnar Transposition Encipher");
			System.out.println("(2) Columnar Transposition Decipher");
			System.out.println("(3) Double Columnar Transposition Encipher");
			System.out.println("(4) Double Columnar Transposition Decipher");
			System.out.println("(5) Transposition Code Breaking");
			
			System.out.println("\n" + "(H) Help");
			
			System.out.println("\n" + "(E) Exit Menu" + "\n");
			
			System.out.print("Choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase())
			{
				/*
				 * Columnar Transposition Encipher
				 * 
				 */
		
				case COLUMNAR_TRANSPOSITION_ENCIPHER:
				{
					System.out.println("\n" + "Columnar Transposition Encipher");
					System.out.println("-------------------------------");
					
					clearText = getText(CLEAR_TEXT_MESSAGE);
					
					if (clearText.length() > 0)
					{
						keyword1 = getKeyword(1, NON_NULL_KEYWORD);
						
						cipherTextType = getCipherTextType();
							
						/*
						 * Column Transposition
						 * 
						 */
						
						cipherText 			= ((TranspositionCiphers) cipher).columnarTranspositionEncipher(clearText, keyword1, cipherTextType);
						cipherTextBlockSize = cipher.getCipherTextBlockSize1();
		
						// Display the transposition matrix

						if (getShowDetails())
						{
							char[][] transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix1();
							int[] 	 keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword1LetterOrder();
							
							System.out.println("\n" + "Columnar Transposition - Write Clear Text on the transposition matrix in row order");
			
							// Display the transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword1, keywordLetterOrder);
							
							// Display the cipher text
							
							System.out.println("\n" + "Read Cipher Text from the transposition matrix in column order");
						}
						else
						{
							// Display the cipher text
							
							System.out.println("\n" + "Cipher Text");
							System.out.println("-----------");
						}
						
						displayCipherText(cipherText, cipherTextBlockSize);
					}

					break;
				}
				
				/*
				 * Columnar Transposition Decipher
				 * 
				 */
		
				case COLUMNAR_TRANSPOSITION_DECIPHER:
				{
					System.out.println("\n" + "Columnar Transposition Decipher");
					System.out.println("-------------------------------");
					
					cipherText = getText(CIPHER_TEXT_MESSAGE);
					
					if (cipherText.length() > 0)
					{					
						keyword1	= getKeyword(1, NON_NULL_KEYWORD);
						clearText 	= ((TranspositionCiphers) cipher).columnarTranspositionDecipher(cipherText, keyword1);
						
						/*
						 * Column Transposition
						 * 
						 */
						
						// Display the transposition matrix
						
						if (getShowDetails())
						{
							char[][] transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix1();
							int[] 	 keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword1LetterOrder();
							
							System.out.println("\n" + "Columnar Transposition - Write Clear Text on the transposition matrix in column order");
		
							// Display the transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword1, keywordLetterOrder);
							
							// Display the clear text
							
							System.out.println("\n" + "Read Clear Text from the transposition matrix in row order");
						}
						else
						{
							// Display the clear text
							
							System.out.println("\n" + "Clear Text");
							System.out.println("----------");
						}
						
						displayClearText(clearText);
					}

					break;
				}
			
				/*
				 * Double Columnar Transposition Encipher
				 * 
				 */
			
				case DOUBLE_COLUMNAR_TRANSPOSITION_ENCIPHER:
				{
					System.out.println("\n" + "Double Columnar Transposition Encipher");
					System.out.println("--------------------------------------");
					
					clearText = getText(CLEAR_TEXT_MESSAGE);
					
					if (clearText.length() > 0)
					{					
						keyword1		= getKeyword(1, NON_NULL_KEYWORD);
						keyword2		= getKeyword(2, NON_NULL_KEYWORD);
					
						cipherTextType 	= getCipherTextType();

						((TranspositionCiphers) cipher).doubleColumnarTranspositionEncipher(clearText, keyword1, keyword2, cipherTextType);
	
						/*
						 * First Column Transposition
						 * 
						 */
							
						// Display the first transposition matrix
						
						if (getShowDetails())
						{
							char[][] transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix1();
							int[] 	 keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword1LetterOrder();
							
							System.out.println("\n" + "First Columnar Transposition - Write Clear Text on the first transposition matrix in row order");
		
							// Display the first transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword1, keywordLetterOrder);
							
							// Get and display the intermediate cipher text
							
							cipherText 			= cipher.getCipherText1();
							cipherTextBlockSize = cipher.getCipherTextBlockSize1();
							
							System.out.println("\n" + "Read Intermediate Cipher Text from the first transposition matrix in column order");
							
							displayCipherText(cipherText, cipherTextBlockSize);
							
							/*
							 * Second Column Transposition
							 * 
							 */
							
							// Display the second transposition matrix
							
							transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix2();
							keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword2LetterOrder();
							
							System.out.println("\n" + "Second Columnar Transposition - Write Intermediate Cipher Text on the second transposition matrix in row order");
		
							// Display the second transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword2, keywordLetterOrder);
							
							System.out.println("\n" + "Read final Cipher Text from the second transposition matrix in column order");
						}
						else
						{
							// Display the final cipher text
							
							System.out.println("\n" + "Cipher Text");
							System.out.println("-----------");
						}
							
						// Get and display the final cipher text
						
						cipherText 			= cipher.getCipherText2();
						cipherTextBlockSize = cipher.getCipherTextBlockSize2();		
						
						displayCipherText(cipherText, cipherTextBlockSize);
					}
										
					break;
				}
				
				/*
				 * Double Columnar Transposition Decipher
				 * 
				 */
				
				case DOUBLE_COLUMNAR_TRANSPOSITION_DECIPHER:
				{
					System.out.println("\n" + "Double Columnar Transposition Decipher");
					System.out.println("--------------------------------------");
					
					cipherText = getText(CIPHER_TEXT_MESSAGE);
					
					if (cipherText.length() > 0)
					{
						keyword1			= getKeyword(1, NON_NULL_KEYWORD);
						keyword2			= getKeyword(2, NON_NULL_KEYWORD);
						clearText 		   	= ((TranspositionCiphers) cipher).doubleColumnarTranspositionDecipher(cipherText, keyword1, keyword2);
						
						/*
						 * First Column Transposition
						 * 
						 */
						
						// Display the first transposition matrix
						
						if (getShowDetails())
						{
							char[][] transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix2();
							int[] 	 keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword2LetterOrder();
							
							System.out.println("\n" + "First Columnar Transposition - Write Clear Text on the second transposition matrix in column order");
		
							// Display the first transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword2, keywordLetterOrder);
							
							// Get and display the intermediate cipher text
							
							cipherText 			= cipher.getCipherText2();
							cipherTextBlockSize = cipher.getCipherTextBlockSize2();
							
							System.out.println("\n" + "Read Intermediate Cipher Text from the second transposition matrix in row order");
							
							displayCipherText(cipherText, cipherTextBlockSize);
		
							/*
							 * Second Column Transposition
							 * 
							 */
							
							// Display the second transposition matrix
							
							transpositionMatrix = ((TranspositionCiphers) cipher).getTranspositionMatrix1();
							keywordLetterOrder  = ((TranspositionCiphers) cipher).getKeyword1LetterOrder();
							
							System.out.println("\n" + "Second Columnar Transposition - Write Intermediate Cipher Text on the first transposition matrix in column order");
		
							// Display the second transposition matrix
							
							displayTranspositionMatrix(transpositionMatrix, keyword1, keywordLetterOrder);
							
							// Display clear text
							
							System.out.println("\n" + "Read Clear Text from the first transposition matrix in row order");
						}
						else
						{
							// Display clear text
							
							System.out.println("\n" + "Clear Text");
							System.out.println("----------");
						}
						
						// Get and display clear text
						
						clearText = cipher.getClearText();				
											
						displayClearText(clearText);
					}

					break;
				}
				
				/*
				 * Transposition Code Breaking
				 * 
				 */
				
				case TRANSPOSITION_CODE_BREAKING:
				{	
					transpositionCodeBreaking(cipherText);
					
					break;
				}
				case HELP:
				{	
					help();
					
					break;
				}
				case EXIT:
				{
					System.out.println();
					
					break;
				}
				default:
				{
					System.out.println("\n" + "Please enter a valid Menu option");
					
					break;
				}
			}
		}
	}
	
	/*
	 * Display Cryptanalysis Method
	 * 
	 */
	
	private void displayCryptanalysisMenu()
	{
		String	menuOption 			= "";
		int		shiftValue			= 0;
		int		cipherTextBlockSize = 0;
		
		// Create Cryptanalysis instance
		
		Cryptanalysis cryptanalysis = new Cryptanalysis();
		
		/*
		 *  Cryptanalysis Menu
		 */
		
		while (!menuOption.equalsIgnoreCase(EXIT))
		{
			System.out.println("\n" + "---------------------");
			System.out.println("| Cryptanalysis Menu |");
			System.out.println("---------------------" + "\n");
			
			System.out.println("(1) Cummulative Asceding Order English Letter Frequencies");
			
			System.out.println("\n" + "(E) Exit Menu" + "\n");
			
			System.out.print("Choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase())
			{
				case SUBSTITUTION_CIPHER_FREQUENCY_ANALYSIS:
				{
					//((SubstitutionCiphers) cipher).frequencyAnalysis(cipherText);
					
					break;
				}
				case CUMMULATIVE_LETTER_FREQUENCIES:
				{
					cummulativeLetterFrequencies();
					
					break;
				}
				case EXIT:
				{
					break;
				}
				default:
				{
					System.out.println("\n" + "Please enter a valid Menu option");
					
					break;
				}
			}
		}
	}
	
	/*
	 * Get Text Method
	 * 
	 */
	
	private String getText(String type)
	{
		String 	text 	= "";
		String  input 	= "";
		boolean done 	= false;
		
		System.out.println("\n" + "Enter each line of the " + type + " below and press Enter. Press Enter again when you are finished:" + "\n");
		
		while (!done)
		{
			System.out.print("> ");
			input = keyboard.nextLine();

			input = cipher.stripNonAlphabetic(input);
			
			if (input.length() > 0)
			{
				text = text + input;
			}
			else
			{
				if (text.length() > 0)
				{
					System.out.println("\n" + "Text input finished.");
				}
				else
				{
					System.out.println("\n" + "Text input cancelled.");
				}
				
				done = true;
			}
		}

		text = text.toUpperCase();
		
		return text;
	}

	/*
	 * Get Keyword Method
	 * 
	 */
	
	private String getKeyword(int number, int nullKeywordOK)
	{
		boolean finished 	= false;
		String 	keyword 	= "";

		while (!finished)
		{
			System.out.print("\n" + "Enter keyword #" + number + " with at least " + Cryptography.MINIMUM_KEYWORD_LENGTH + " alphabetic characters: ");
		
			keyword = keyboard.nextLine();

			// Strip invalid characters (non-alphabetic)
			
			keyword = cipher.stripNonAlphabetic(keyword);
			
			if (validateKeyword(keyword) && (keyword.length() >= Cryptography.MINIMUM_KEYWORD_LENGTH && keyword.length() <= Cryptography.MAXIMUM_KEYWORD_LENGTH) || nullKeywordOK == NULL_KEYWORD)
			{
				finished = true;
			}
			else
			{
				System.out.println("\n" + "Keywords cannot be part of the clear text. Keywords must have at least " + Cryptography.MINIMUM_KEYWORD_LENGTH + " and no more than " + Cryptography.MAXIMUM_KEYWORD_LENGTH + " alphabetic characters");
			}
		}

		keyword = keyword.toUpperCase();
		
		return keyword;
	}

	/*
	 * Validate Keyword Method
	 * 
	 */
	
	public boolean validateKeyword(String keyword)
	{
		// Returns true if the keyword is valid (text does not contain the keyword)
		
		return !clearText.contains(keyword);
	}
	
	/*
	 * Get Show Details Method
	 * 
	 */
	
	private boolean getShowDetails()
	{
		boolean showDetails = false; 
		String	details 	= " ";
		
		while (details.length() > 0 && !(details.equalsIgnoreCase("Y") || details.equalsIgnoreCase("N")))
		{
			System.out.print("\n" + "Show details? (Press Enter for Y, Y or N): ");
			
			details = keyboard.nextLine();
		}
		
		if (details.length() == 0 || details.equalsIgnoreCase("Y"))
		{
			showDetails = true;
		}
		
		return showDetails;
	}
	
	/*
	 * Get Cipher Text Type Method
	 * 
	 */
	
	private int getCipherTextType()
	{
		int 	cipherTextType 	= Cryptography.IRREGULAR_CIPHER_TEXT; 
		String	regular 		= " ";
		
		while (regular.length() > 0 && !(regular.equalsIgnoreCase("Y") || regular.equalsIgnoreCase("N")))
		{
			System.out.print("\n" + "Create regular (padded) cipher text? (Press Enter for Y, Y or N): ");
			
			regular = keyboard.nextLine();
		}
		
		if (regular.length() == 0 || regular.equalsIgnoreCase("Y"))
		{
			cipherTextType = Cryptography.REGULAR_CIPHER_TEXT;
		}
		
		return cipherTextType;
	}
	
	/*
	 * Get Shift Value Method
	 * 
	 */
	
	private int getShiftValue()
	{
		int shiftValue = 0;
		
		while (shiftValue <= 0 || shiftValue >= 26)
		{
			System.out.print("\n" + "Enter a shift value greater than zero and less than 26: ");
			
			shiftValue = keyboard.nextInt();
			
			// Clear the CR/LF from the keyboard buffer
			
			keyboard.nextLine();
		}
		
		return shiftValue;
	}
	
	/*
	 * Display Clear Text
	 * 
	 */
	
	private void displayClearText(String clearText)
	{
		String 	text 				= "";
		int		numberOfCharacters 	= 0;
		
		System.out.println();
		
		for (int index = 0; index < clearText.length(); index++)
		{
			if (numberOfCharacters < 100)
			{
				text = text + clearText.substring(index, index + 1) + " ";
				
				numberOfCharacters = numberOfCharacters + 2;
			}
			else
			{
				System.out.println(text);
				
				text = clearText.substring(index, index + 1) + " ";
				
				numberOfCharacters = 2;
			}
		}
		
		if (text.length() > 0)
		{
			System.out.print(text);
		}

		System.out.println();
	}
	
	/*
	 * Display Cipher Text
	 * 
	 */
	
	private void displayCipherText(String cipherText, int cipherTextBlockSize)
	{
		String cipherTextBlock 	= "";
		int	   numberOfBlocks	= 0;
		
		System.out.println();
		
		for (int index = 0; index < cipherText.length(); index++)
		{			
			if (cipherTextBlock.length() < cipherTextBlockSize)
			{
				cipherTextBlock = cipherTextBlock + cipherText.substring(index, index + 1);
			}
			else
			{
				// Display the current cipher text block
				
				if (numberOfBlocks < 10)
				{
					System.out.print(cipherTextBlock + " ");
					
					numberOfBlocks++;
				}
				else
				{					
					System.out.println(cipherTextBlock + " ");
					
					numberOfBlocks = 0;
				}

				cipherTextBlock = "";
				cipherTextBlock = cipherTextBlock + cipherText.substring(index, index + 1);
			}
		}
		
		if (cipherTextBlock.length() > 0)
		{
			if (numberOfBlocks <= 10)
			{
				System.out.print(cipherTextBlock);
			}
			else
			{
				System.out.print("\n" + cipherTextBlock);
			}
		}
				
		System.out.println();
	}
	
	/*
	 * Display Transposition Matrix Method
	 * 
	 */
	
	public void displayTranspositionMatrix(char[][] transpositionMatrix, String keyword, int[] keywordLetterOrder)
	{
		System.out.println("\n" + "Transposition Matrix" + "\n");

		String topLetterOrderLine 		= "";
		String bottomLetterOrderLine 	= "";
		String keywordUpperCase			= keyword.toUpperCase();
		String dashLine					= "";
		
		// Display the top line of the letter order numbers (the 10th line)
		
		for (int index = 0; index < keywordLetterOrder.length; index++)
		{
			if (keywordLetterOrder[index] > 9)
			{
				topLetterOrderLine 		= topLetterOrderLine + "1 ";
				bottomLetterOrderLine 	= bottomLetterOrderLine + (keywordLetterOrder[index] - 10 + " ");
			}
			else
			{
				bottomLetterOrderLine	= bottomLetterOrderLine + keywordLetterOrder[index] + " ";
				topLetterOrderLine 		= topLetterOrderLine + "  ";
			}
		}
		
		for (int index = 0; index < keywordUpperCase.length(); index++)
		{
			System.out.print(keywordUpperCase.substring(index, index + 1) + " ");
			
			dashLine = dashLine + "--";
		}
		
		System.out.println();
		
		if (topLetterOrderLine.length() > 0)
		{
			System.out.println(topLetterOrderLine);
		}
		
		System.out.println(bottomLetterOrderLine);
		
		System.out.println(dashLine + "\n");
		
		for (int row = 0; row < transpositionMatrix.length; row++)
		{
			for (int col = 0; col < transpositionMatrix[row].length; col++)
			{
				System.out.print(transpositionMatrix[row][col] + " ");
			}
			
			System.out.println();
		}
	}
	
	/*
	 * Help Method
	 * 
	 */
	
	private void help()
	{
		System.out.println("\n" + "---------------------------------------------");
		System.out.println("How to Encipher Double Columnar Transposition");
		System.out.println("---------------------------------------------" + "\n");
		System.out.println("You will need two keywords from " + Cryptography.MINIMUM_KEYWORD_LENGTH + " to " + Cryptography.MAXIMUM_KEYWORD_LENGTH + " ALPHABETIC characters in length.");
		System.out.println("The keywords cannot be part of the clear text message.");
		System.out.println("You will need to write the alphabetical column sequence numbers below the keywords." + "\n");
		System.out.println("1. Write the clear text in row order on the first transposition matrix.");
		System.out.println("   If the last row is incomplete you will need to decide whether to pad it (regular cipher text) or not (irregular cipher text).");
		System.out.println("2. Read the intermediate cipher text from the first transposition matrix in column order in alphabetical column sequence.");
		System.out.println("3. Write the Intermediate Cipher Text on the second transposition matrix in row order.");
		System.out.println("   If the last row is incomplete you will need to decide whether to pad it (regular cipher text) or not (irregular cipher text).");
		System.out.println("4. Read the final cipher text from the second transposition matrix in column order.");

		System.out.println("\n" + "---------------------------------------------");
		System.out.println("How to Decipher Double Columnar Transposition");
		System.out.println("---------------------------------------------" + "\n");
		System.out.println("You will need the two keywords used to encipher the clear message. The keywords must be entered in the same Encipher order.");
		System.out.println("You will need to write the alphabetical column sequence numbers below the keywords." + "\n");
		System.out.println("1. Write cipher text on the second transposition matrix in column order in alphabetical column sequence.");
		System.out.println("2. Read the intermediate cipher text from the second transposition matrix in row order.");
		System.out.println("3. Write the intermediate cipher text on the first transposition matrix in column order in alphabetical column sequence.");
		System.out.println("4. Read the clear text from the first transposition matrix in row order.");
	}
	
	/*
	 * Cummulative Letter Frequencies Method
	 * 
	 */
	
	public void cummulativeLetterFrequencies()
	{
		System.out.print("\n" + Cryptography.ASCENDING_ENGLISH_LETTER_FREQUENCIES[0] + ", ");
		
		double sum = Cryptography.ASCENDING_ENGLISH_LETTER_FREQUENCIES[0];
		
		for (int index = 1; index < Cryptography.ASCENDING_ENGLISH_LETTER_FREQUENCIES.length; index++)
		{
			sum = sum + Cryptography.ASCENDING_ENGLISH_LETTER_FREQUENCIES[index];
			
			// Round to 3 decimal places (number of zeroes indicates the number of decimal places)
			
			sum = (double)Math.round(sum * 1000) / 1000;
			
			System.out.print(sum + ", ");
		}
		
		System.out.println();
	}
	
	/*
	 * Transposition Code Breaking Method
	 * 
	 */
	
	private void transpositionCodeBreaking(String cipherText)
	{
		//double[] letterFrequencyDifferences = ((TranspositionCiphers) cipher).codeBreaking(cipherText);
		
		
	}
}
