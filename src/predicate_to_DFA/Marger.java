package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Marger {
	private ArrayList<State> stateList;

	public void margeTransition(ArrayList<State> stateList, int scopeDepth) {
		this.stateList = stateList;
		int i = 0;
		while (i < this.stateList.size()) {
			State state = this.stateList.get(i);
			if (state.getNextTransitions().size() > 1) {
				SymbolSet symbolSet = new SymbolSet();
				ArrayList<Transition> anyTransitions = new ArrayList<Transition>();
				ArrayList<SymbolSet> omittedSymbolSetList = new ArrayList<SymbolSet>();
				ArrayList<Transition> newTransitions = new ArrayList<Transition>();
				newTransitions.addAll(state.getNextTransitions());
				int j = 0;
				while (j < newTransitions.size()) {
					Transition transition = newTransitions.get(j);
					if (transition.getSymbolCase() == SymbolCase.SYMBOL) {
						symbolSet.add(transition.getSymbol());
					}
					j++;
				}
				if (!(symbolSet.isEmpty())) {
					j = 0;
					while (j < newTransitions.size()) {
						Transition transition = newTransitions.get(j);
						if (transition.getSymbolCase() == SymbolCase.OTHER) {
							omittedSymbolSetList.add(transition
									.getOmittedSymbols());
							anyTransitions.add(transition);
							newTransitions.remove(j);
						} else if (transition.getSymbolCase() == SymbolCase.ANY) {
							omittedSymbolSetList.add(new SymbolSet());
							anyTransitions.add(transition);
							newTransitions.remove(j);
						} else {
							j++;
						}
					}
				}
				if (!(anyTransitions.isEmpty())) {
					j = 0;
					while (j < anyTransitions.size()) {
						Transition anyTransition = anyTransitions.get(j);
						for (int k = 0; k < symbolSet.size(); k++) {
							boolean contain = false;
							if (anyTransition.getSymbolCase() == SymbolCase.OTHER) {
								for (String symbol : anyTransition
										.getOmittedSymbols().get()) {
									contain = symbolSet.get().get(k)
											.equals(symbol);
									if (contain) {
										break;
									}
								}
							}
							if (!contain) {
								Transition nextTransition = new Transition();
								nextTransition.setScopeDepth(anyTransition
										.getScopeDepth());
								nextTransition.setNextState(anyTransition
										.getNextState());
								nextTransition.setSymbolAndCase(symbolSet.get()
										.get(k), SymbolCase.SYMBOL);
								newTransitions.add(nextTransition);
							}
						}
						Transition nextTransition = new Transition();
						nextTransition.setScopeDepth(anyTransition
								.getScopeDepth());
						nextTransition.setNextState(anyTransition
								.getNextState());
						nextTransition.setSymbolCase(SymbolCase.OTHER);
						nextTransition.setOmittedSymbols(symbolSet);
						if (anyTransition.getSymbolCase() == SymbolCase.OTHER) {
							nextTransition.addAllOmittedSymbols(anyTransition
									.getOmittedSymbols());
						}
						int k = 0;
						while (k < nextTransition.getOmittedSymbols().size()) {
							int l = k + 1;
							while (l < nextTransition.getOmittedSymbols()
									.size()) {
								if (nextTransition
										.getOmittedSymbols()
										.get()
										.get(k)
										.equals(nextTransition
												.getOmittedSymbols().get()
												.get(l))) {
									nextTransition.getOmittedSymbols().get()
											.remove(l);
								} else {
									l++;
								}
							}
							k++;
						}
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
					Transition transitionB = state.getNextTransitions().get(k);
					if (transitionA.getNextState() == transitionB
							.getNextState()
							&& transitionA.getSymbolCase() == transitionB
									.getSymbolCase()
							&& ((transitionA.getSymbol() == null && transitionB
									.getSymbol() == null) || transitionA
									.getSymbol()
									.equals(transitionB.getSymbol()))) {
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
				if (state.getNextTransitions().get(j).getScopeDepth() >= scopeDepth) {
					Transition transitionA = state.getNextTransitions().get(j);
					int k = j + 1;
					while (k < state.getNextTransitions().size()) {
						if (state.getNextTransitions().get(k).getScopeDepth() >= scopeDepth) {
							Transition transitionB = state.getNextTransitions()
									.get(k);
							if (transitionA.getSymbolCase() == transitionB
									.getSymbolCase()
									&& ((transitionA.getSymbol() == null && transitionB
											.getSymbol() == null) || transitionA
											.getSymbol().equals(
													transitionB.getSymbol()))) {
								boolean contain = false;
								boolean equal = false;
								State newState = null;
								int predicateNumber = 0;
								if (transitionA.getNextState()
										.getPredicateNumber() > transitionB
										.getNextState().getPredicateNumber()) {
									predicateNumber = transitionA
											.getNextState()
											.getPredicateNumber();
								} else {
									predicateNumber = transitionB
											.getNextState()
											.getPredicateNumber();
								}
								ArrayList<Integer> costateNumbers = new ArrayList<Integer>();
								costateNumbers.addAll(transitionA
										.getNextState().getCoStateNumber());
								costateNumbers.addAll(transitionB
										.getNextState().getCoStateNumber());
								for (State checkState : stateList) {
									contain = checkState.getCoStateNumber()
											.containsAll(costateNumbers);
									equal = costateNumbers
											.containsAll(checkState
													.getCoStateNumber());
									if (contain
											&& equal
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
											.getNextState()
											.getNextTransitions());
									nextTransitions.addAll(transitionB
											.getNextState()
											.getNextTransitions());
									int l = 0;
									while (l < nextTransitions.size()) {
										Transition transition = nextTransitions
												.get(l);
										if (transition.getNextState()
												.getPredicateNumber() < predicateNumber) {
											Transition newTransition = new Transition();
											newTransition
													.setScopeDepth(transition
															.getScopeDepth());
											State predicateState;
											if (transition.getNextState() instanceof AcceptState) {
												predicateState = new AcceptState();
												this.stateList
														.add(predicateState);
											} else {
												predicateState = new State();
												this.stateList
														.add(predicateState);
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
											if (newTransition.getSymbolCase() == SymbolCase.OTHER) {
												newTransition
														.setOmittedSymbols(transition
																.getOmittedSymbols());
											}
											nextTransitions.add(newTransition);
											nextTransitions.remove(l);
										} else {
											l++;
										}
									}
									newState.setNextTransitions(nextTransitions);
								}
								Transition newTransition = new Transition();
								newTransition.setScopeDepth(transitionA
										.getScopeDepth());
								newTransition.setNextState(newState);
								newTransition.setSymbolAndCase(
										transitionA.getSymbol(),
										transitionA.getSymbolCase());
								newTransition.setOmittedSymbols(transitionA
										.getOmittedSymbols());
								ArrayList<Transition> newTransitions = state
										.getNextTransitions();
								newTransitions.remove(k);
								newTransitions.remove(j);
								newTransitions.add(newTransition);
								state.setNextTransitions(newTransitions);
								j = -1;
								break;
							}
						}
						k++;
					}
				}
				j++;
			}
			i++;
		}
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
