package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Printer {

	public void printTransitions(ArrayList<State> stateList) {
		for (int i = 0; i < stateList.size(); i++) {
			State state = stateList.get(i);
			for (int j = 0; j < state.getNextTransitions().size(); j++) {
				Transition transition = state.getNextTransitions().get(j);
				// System.out.print(state.getStateNumber() + "--");
				// for dubug
				System.out.print(state.getCoStateNumber());
				System.out.print(state.getPredicateNumber() + "--");

				if (transition.getSymbolCase() == SymbolCase.SYMBOL) {
					System.out.print(transition.getSymbol() + "-->");
				} else if (transition.getSymbolCase() == SymbolCase.OTHER) {
					System.out.print(transition.getSymbolCase()
							+ transition.getSymbol() + "-->");
				} else {
					System.out.print(transition.getSymbolCase() + "-->");
				}
				// System.out.println(transition.getNextState().getStateNumber());
				// for debug
				System.out.print(transition.getNextState().getCoStateNumber());
				System.out.println(transition.getNextState()
						.getPredicateNumber());

			}
		}
	}
}
