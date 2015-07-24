package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Printer {
	// private int stateNumber = 0;
	// private int transitionNumber = 0;

	public void printDOTFile(ArrayList<State> stateList) {
		System.out.println("digraph DFA {");
		for (int i = 0; i < stateList.size(); i++) {
			State state = stateList.get(i);
			// if (!(state.getStateNumber() == -1)) {
			// this.stateNumber++;
			// }
			for (int j = 0; j < state.getNextTransitions().size(); j++) {
				System.out.print("	");
				Transition transition = state.getNextTransitions().get(j);
				if (transition.getSymbolCase() == SymbolCase.EOF) {
					System.out.println("q" + state.getStateNumber() + " [peripheries = 2];");
				} else {
					System.out.print("q" + state.getStateNumber() + " -> ");
					// for dubug
					// System.out.print(state.getCoStateNumber());
					// System.out.print(state.getPredicateNumber() + "--");
					System.out.print("q" + transition.getNextState().getStateNumber());
					// for debug
					// System.out.print(transition.getNextState().getCoStateNumber());
					// System.out.println(transition.getNextState()
					// .getPredicateNumber());

					System.out.print(" [label = \"");
					if (transition.getSymbolCase() == SymbolCase.SYMBOL) {
						System.out.print(transition.getSymbol());
					} else if (transition.getSymbolCase() == SymbolCase.OTHER) {
						String omittedSymbols = null;
						int lengthCounter = 0;
						for (String symbol : transition.getOmittedSymbols().get()) {
							if (omittedSymbols == null) {
								System.out.print("_");
								omittedSymbols = symbol;
							} else {
								if (lengthCounter == 2) {
									System.out.print("_");
									lengthCounter = 0;
								}
								System.out.print("_");
								omittedSymbols += ",";
								omittedSymbols += symbol;
							}
							lengthCounter++;
						}
						System.out.print("\\n");
						System.out.print(omittedSymbols);
					} else {
						System.out.print(transition.getSymbolCase());
					}
					System.out.println("\"];");
				}
			}
		}
		System.out.println("}");
		// for print style
		// System.out.println("Number of States is " + this.stateNumber + ".");
		// System.out.println("Number of Transitions is " +
		// this.transitionNumber
		// + ".");
	}
}
