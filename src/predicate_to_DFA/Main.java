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
					dfaMaker.getMaxScopeDepth());
			printer.printDOTFile(dfaReshaper.getStateList());

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

	// public void paint(Graphics g) {
	//
	// String[] input = new String[1];
	// input[0] = "input";
	// Reader reader = new Reader();
	// Lexer lexer = new Lexer();
	// DFAMaker dfaMaker = new DFAMaker();
	// DFAReshaper dfaReshaper = new DFAReshaper();
	//
	// try {
	//
	// reader.read(input);
	// lexer.lexe(reader.getInputLine());
	// dfaMaker.makeDFA(lexer.getToken());
	// dfaReshaper.reshapeDEA(dfaMaker.getStateList(),
	// dfaMaker.getMaxScopeDepth());
	//
	// int x;
	// int y;
	// ArrayList<State> stateList = dfaReshaper.getStateList();
	// int numberOfStates = stateList.size();
	// for (int i = 0; i < numberOfStates; i++) {
	// x = i * 100;
	// y = i * 100;
	// g.setColor(Color.BLACK);
	// g.setFont(new Font(Font.SERIF, Font.BOLD, 36));
	// g.drawString("q" + i, x + 30, y + 60);
	// g.drawOval(x + 10, y + 10, 80, 80);
	//
	// ArrayList<Transition> nextTransitions = stateList.get(i)
	// .getNextTransitions();
	// int numberOfTransitions = nextTransitions.size();
	// for (int j = 0; j < numberOfTransitions; j++) {
	// Transition nextTransition = nextTransitions.get(j);
	// int nextStateNumber = nextTransition.getNextState()
	// .getStateNumber();
	// int nx = nextStateNumber * 100;
	// int ny = nextStateNumber * 100;
	// int dsn = nextStateNumber - i;
	// String nextSymbol;
	// switch (nextTransition.getSymbolCase()) {
	// case SYMBOL:
	// nextSymbol = nextTransition.getSymbol();
	// break;
	// case OTHER:
	// nextSymbol = nextTransition.getOmittedSymbols().get()
	// .toString();
	// break;
	// default:
	// nextSymbol = nextTransition.getSymbolCase().toString();
	// }
	// switch (nextTransition.getSymbolCase()) {
	// case EOF:
	// g.drawOval(x + 15, y + 15, 70, 70);
	// break;
	// default:
	// g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
	// if (i < nextStateNumber) {
	// g.drawLine(x + 50 + dsn, y + 90, x + 50 + dsn, ny
	// + 50 + dsn);
	// g.drawLine(x + 50 + dsn, ny + 50 + dsn, nx + 10, ny
	// + 50 + dsn);
	// g.drawLine(nx + 10, ny + 50 + dsn, nx, ny + 40
	// + dsn);
	// g.drawLine(nx + 10, ny + 50 + dsn, nx, ny + 60
	// + dsn);
	// g.drawString(nextSymbol, x + 55 + dsn, ny + 45
	// + dsn);
	// } else if (i == nextStateNumber) {
	// g.drawLine(x + 50, y + 90, x + 50, y + 95);
	// g.drawLine(x + 50, y + 95, x + 95, y + 95);
	// g.drawLine(x + 95, y + 95, x + 95, y + 50);
	// g.drawLine(x + 95, y + 50, x + 90, y + 50);
	// g.drawLine(x + 90, y + 50, x + 100, y + 40);
	// g.drawLine(x + 90, y + 50, x + 100, y + 60);
	// g.drawString(nextSymbol, x + 100, y + 45);
	// } else {
	// g.drawLine(x + 50 + dsn, y + 10, x + 50 + dsn, ny
	// + 50 + dsn);
	// g.drawLine(x + 50 + dsn, ny + 50 + dsn, nx + 90, ny
	// + 50 + dsn);
	// g.drawLine(nx + 90, ny + 50 + dsn, nx + 100, ny
	// + 40 + dsn);
	// g.drawLine(nx + 90, ny + 50 + dsn, nx + 100, ny
	// + 60 + dsn);
	// g.drawString(nextSymbol, x + 55 + dsn, ny + 45
	// + dsn);
	// }
	// }
	// }
	// }
	//
	// } catch (IOException e) {
	// System.out.println(e + "(Input is invalid.)");
	// } catch (ComandLineArgumentException e) {
	// System.out.println(e);
	// } catch (FileReadException e) {
	// System.out.println(e);
	// } catch (NonDefinedTokenException e) {
	// System.out.println(e);
	// } catch (SyntaxErrorException e) {
	// System.out.println(e);
	// } catch (CodingErrorException e) {
	// System.out.println(e);
	// }
	// }
}