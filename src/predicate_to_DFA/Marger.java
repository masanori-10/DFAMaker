package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Marger {
	private ArrayList<State> stateList;

	public void margeTransition(ArrayList<State> stateList) {
		this.stateList = stateList;
		for (int x = 0; x < 1; x++) {
			int i = 0;
			while (i < this.stateList.size()) {
				State state = this.stateList.get(i);
				if (state.getNextTransitions().size() > 1) {
					ArrayList<String> symbolList = new ArrayList<String>();
					ArrayList<State> anyNextStates = new ArrayList<State>();
					ArrayList<Transition> newTransitions = new ArrayList<Transition>();
					newTransitions.addAll(state.getNextTransitions());
					int j = 0;
					while (j < newTransitions.size()) {
						Transition transition = newTransitions.get(j);
						if (transition.getSymbolCase() == SymbolCase.SYMBOL) {
							symbolList.add(transition.getSymbol());
						}
						j++;
					}
					if (!(symbolList.isEmpty())) {
						j = 0;
						while (j < newTransitions.size()) {
							Transition transition = newTransitions.get(j);
							boolean contain = false;
							if (transition.getSymbolCase() == SymbolCase.OTHER) {
								for (String symbol : symbolList) {
									contain = transition.getSymbol().contains(
											symbol);
									if (contain) {
										break;
									}
								}
							}
							if (transition.getSymbolCase() == SymbolCase.ANY
									|| contain) {
								anyNextStates.add(transition.getNextState());
								newTransitions.remove(j);
							} else {
								j++;
							}
						}
					}
					if (!(anyNextStates.isEmpty())) {
						j = 0;
						while (j < anyNextStates.size()) {
							State nextState = anyNextStates.get(j);
							String symbols = null;
							for (int k = 0; k < symbolList.size(); k++) {
								Transition nextTransition = new Transition();
								nextTransition.setNextState(nextState);
								nextTransition.setSymbolAndCase(
										symbolList.get(k), SymbolCase.SYMBOL);
								newTransitions.add(nextTransition);
								if (symbols == null) {
									symbols = symbolList.get(k);
								} else {
									symbols += symbolList.get(k);
								}
							}
							Transition nextTransition = new Transition();
							nextTransition.setNextState(nextState);
							nextTransition.setSymbolAndCase(symbols,
									SymbolCase.OTHER);
							newTransitions.add(nextTransition);
							state.setNextTransitions(newTransitions);
							j++;
						}
					}
				}
				int j = 0;
				while (j < state.getNextTransitions().size()) {
					Transition transitionA = state.getNextTransitions().get(j);
					int k = j + 1;
					while (k < state.getNextTransitions().size()) {
						Transition transitionB = state.getNextTransitions()
								.get(k);
						if (transitionA.getNextState() == transitionB
								.getNextState()
								&& transitionA.getSymbolCase() == transitionB
										.getSymbolCase()
								&& ((transitionA.getSymbol() == null && transitionB
										.getSymbol() == null) || transitionA
										.getSymbol().equals(
												transitionB.getSymbol()))) {
							ArrayList<Transition> newTransitions = state
									.getNextTransitions();
							newTransitions.remove(k);
							state.setNextTransitions(newTransitions);
							j = 0;
							transitionA = state.getNextTransitions().get(j);
							k = j + 1;
						} else {
							k++;
						}
					}
					j++;
				}
				j = 0;
				while (j < state.getNextTransitions().size()) {
					Transition transitionA = state.getNextTransitions().get(j);
					int k = j + 1;
					while (k < state.getNextTransitions().size()) {
						Transition transitionB = state.getNextTransitions()
								.get(k);
						if (transitionA.getSymbolCase() == transitionB
								.getSymbolCase()
								&& ((transitionA.getSymbol() == null && transitionB
										.getSymbol() == null) || transitionA
										.getSymbol().equals(
												transitionB.getSymbol()))) {
							boolean containA = false;
							boolean containB = false;
							State newState = null;
							int predicateNumber = 0;
							if (transitionA.getNextState().getPredicateNumber() > transitionB
									.getNextState().getPredicateNumber()) {
								predicateNumber = transitionA.getNextState()
										.getPredicateNumber();
							} else {
								predicateNumber = transitionB.getNextState()
										.getPredicateNumber();
							}
							for (State checkState : stateList) { // TODO equal
								containA = checkState.getCoStateNumber()
										.containsAll(
												transitionA.getNextState()
														.getCoStateNumber());
								containB = checkState.getCoStateNumber()
										.containsAll(
												transitionB.getNextState()
														.getCoStateNumber());
								if (containA
										&& containB
										&& predicateNumber == checkState
												.getPredicateNumber()) {
									newState = checkState;
									break;
								}
							}
							if (newState == null) {
								newState = new State();
								newState.setCoStateNumber(transitionA
										.getNextState().getCoStateNumber(),
										transitionB.getNextState()
												.getCoStateNumber());
								newState.setPredicateNumber(predicateNumber);
								this.stateList.add(newState);
								ArrayList<Transition> nextTransitions = new ArrayList<Transition>();
								nextTransitions.addAll(transitionA
										.getNextState().getNextTransitions());
								nextTransitions.addAll(transitionB
										.getNextState().getNextTransitions());
								int l = 0;
								while (l < nextTransitions.size()) {
									Transition transition = nextTransitions
											.get(l);
									if (transition.getNextState()
											.getPredicateNumber() < predicateNumber) {
										Transition newTransition = new Transition();
										State predicateState;
										if (transition.getNextState() instanceof AcceptState) {
											predicateState = new AcceptState();
											this.stateList.add(predicateState);
										} else {
											predicateState = new State();
											this.stateList.add(predicateState);
											predicateState
													.setNextTransitions(transition
															.getNextState()
															.getNextTransitions());
										}
										predicateState
												.setPredicateNumber(predicateNumber);
										predicateState
												.setCoStateNumber(transition
														.getNextState()
														.getCoStateNumber());
										newTransition
												.setNextState(predicateState);
										newTransition.setSymbolAndCase(
												transition.getSymbol(),
												transition.getSymbolCase());
										nextTransitions.add(newTransition);
										nextTransitions.remove(l);
									} else {
										l++;
									}
								}

								newState.setNextTransitions(nextTransitions);
							}
							Transition newTransition = new Transition();
							newTransition.setNextState(newState);
							newTransition.setSymbolAndCase(
									transitionA.getSymbol(),
									transitionA.getSymbolCase());
							ArrayList<Transition> newTransitions = state
									.getNextTransitions();
							newTransitions.remove(k);
							newTransitions.remove(j);
							newTransitions.add(newTransition);
							state.setNextTransitions(newTransitions);
							j = 0;
							transitionA = state.getNextTransitions().get(j);
							k = j + 1;
						} else {
							k++;
						}
					}
					j++;
				}
				i++;
			}
		}
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
