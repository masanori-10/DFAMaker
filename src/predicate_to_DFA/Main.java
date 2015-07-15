package predicate_to_DFA;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {

		String[] input = new String[1];
		input[0] = "input";
		Reader reader = new Reader();
		Lexer lexer = new Lexer();
		DFAMaker dfaMaker = new DFAMaker();
		DFAReshaper dfaReshaper = new DFAReshaper();
		Printer printer = new Printer();

		try {

			reader.read(input);
			lexer.lexe(reader.getInputLine());
			dfaMaker.makeDFA(lexer.getToken());
			dfaReshaper.reshapeDEA(dfaMaker.getStateList(),
					dfaMaker.getMaxPredicateDepth());
			printer.printDOTFile(dfaReshaper.getStateList(),
					reader.getInputLine());

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