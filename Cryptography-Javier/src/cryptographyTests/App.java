package cryptographyTests;

public class App
{
	public static void main(String[] args)
	{
		TranspositionCipherTests transpositionCipherTests = new TranspositionCipherTests();
		
		System.out.println("Transposition Cipher Tests" + "\n");
		
		transpositionCipherTests.run();
		
		CryptanalysisTests cryptanalysysTests = new CryptanalysisTests();
		
		System.out.println("Cryptanalysis Tests" + "\n");
		
		cryptanalysysTests.run();
	}
}
