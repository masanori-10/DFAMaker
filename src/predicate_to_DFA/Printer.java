package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Printer {
	private int stateNumber = 0;
	private int transitionNumber = 0;

	public void printTransitions(ArrayList<State> stateList) {
		for (int i = 0; i < stateList.size(); i++) {
			State state = stateList.get(i);
			if (!(state.getStateNumber() == -1)) {
				this.stateNumber++;
			}
			for (int j = 0; j < state.getNextTransitions().size(); j++) {
				Transition transition = state.getNextTransitions().get(j);
				if (!(transition.getSymbolCase() == SymbolCase.EOF)) {
					this.transitionNumber++;
				}
				System.out.print(state.getStateNumber() + "--");
				// for dubug
				// System.out.print(state.getCoStateNumber());
				// System.out.print(state.getPredicateNumber() + "--");

				if (transition.getSymbolCase() == SymbolCase.SYMBOL) {
					System.out.print(transition.getSymbol() + "-->");
				} else if (transition.getSymbolCase() == SymbolCase.OTHER) {
					System.out.print(transition.getSymbolCase());
					System.out.print(transition.getOmittedSymbols().get());
					System.out.print("-->");
				} else {
					System.out.print(transition.getSymbolCase() + "-->");
				}
				System.out.println(transition.getNextState().getStateNumber());
				// for debug
				// System.out.print(transition.getNextState().getCoStateNumber());
				// System.out.println(transition.getNextState()
				// .getPredicateNumber());

			}
		}
		System.out.println("Number of States is " + this.stateNumber + ".");
		System.out.println("Number of Transitions is " + this.transitionNumber
				+ ".");
	}
}
