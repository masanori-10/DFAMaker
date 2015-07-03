package predicate_to_DFA;

import java.io.IOException;

public class Main {
	private static boolean toNext;

	public static void main(String[] args) {

		Reader reader = new Reader();
		Lexer lexer = new Lexer();
		DFAMaker dfaMaker = new DFAMaker();
		DFAReshaper dfaReshaper = new DFAReshaper();
		Printer printer = new Printer();

		try {

			do {
				toNext = reader.read(args);
				lexer.lexe(reader.getInputLine());
				dfaMaker.makeDFA(lexer.getToken());
				dfaReshaper.reshapeDEA(dfaMaker.getStateList());
				printer.printTransitions(dfaReshaper.getStateList());
			} while (toNext);

		} catch (IOException e) {
			System.out.println(e + "(Input is invalid.)");
		} catch (ComandLineArgumentException e) {
			System.out.println(e);
		} catch (FileReadException e) {
			System.out.println(e);
		} catch (NonDefinedTokenException e) {
			System.out.println(e);
		} catch (SyntaxErrorException e) {
			System.out.println(e);
		} catch (CodingErrorException e) {
			System.out.println(e);
		}
	}
}