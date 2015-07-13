package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;

public class Marger {
	private ArrayList<State> stateList;
	private boolean isCompleted;

	public boolean margeTransition(ArrayList<State> stateList) {
		this.stateList = stateList;
		this.isCompleted = true;
		int stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			State currentState = this.stateList.get(stateNumber);
			if (currentState.getNextTransitions().size() > 1) {
				SymbolSet symbolOnSymbolTransitions = new SymbolSet();
				for (int transitionNumber = 0; transitionNumber < currentState
						.getNextTransitions().size(); transitionNumber++) {
					Transition currentTransition = currentState
							.getNextTransitions().get(transitionNumber);
					if (currentTransition.getSymbolCase() == SymbolCase.SYMBOL) {
						symbolOnSymbolTransitions.add(currentTransition
								.getSymbol());
					}
				}
				if (!(symbolOnSymbolTransitions.isEmpty())) {
					ArrayList<Transition> anyOrOtherTransitions = new ArrayList<Transition>();
					int transitionNumber = 0;
					while (transitionNumber < currentState.getNextTransitions()
							.size()) {
						Transition currentTransition = currentState
								.getNextTransitions().get(transitionNumber);
						switch (currentTransition.getSymbolCase()) {
						case OTHER:
							anyOrOtherTransitions.add(currentTransition);
							currentState.getNextTransitions().remove(
									transitionNumber);
							break;
						case ANY:
							anyOrOtherTransitions.add(currentTransition);
							currentState.getNextTransitions().remove(
									transitionNumber);
							break;
						default:
							transitionNumber++;
						}
					}
					if (!(anyOrOtherTransitions.isEmpty())) {
						this.isCompleted = false;
						transitionNumber = 0;
						while (transitionNumber < anyOrOtherTransitions.size()) {
							Transition currentAnyOrOtherTransition = anyOrOtherTransitions
									.get(transitionNumber);
							for (int symbolNumber = 0; symbolNumber < symbolOnSymbolTransitions
									.size(); symbolNumber++) {
								boolean contain = false;
								if (currentAnyOrOtherTransition.getSymbolCase() == SymbolCase.OTHER) {
									for (String symbol : currentAnyOrOtherTransition
											.getOmittedSymbols().get()) {
										contain = symbolOnSymbolTransitions
												.get().get(symbolNumber)
												.equals(symbol);
										if (contain) {
											break;
										}
									}
								}
								if (!contain) {
									Transition newSymbolTransition = new Transition();
									newSymbolTransition
											.setNextState(currentAnyOrOtherTransition
													.getNextState());
									newSymbolTransition.setSymbolAndCase(
											symbolOnSymbolTransitions.get()
													.get(symbolNumber),
											SymbolCase.SYMBOL);
									currentState.getNextTransitions().add(
											newSymbolTransition);
								}
							}
							Transition newOtherTransition = new Transition();
							SymbolSet currentOmittedSymbols = new SymbolSet();
							currentOmittedSymbols
									.addAll(symbolOnSymbolTransitions.get());
							newOtherTransition
									.setNextState(currentAnyOrOtherTransition
											.getNextState());
							newOtherTransition.setSymbolCase(SymbolCase.OTHER);
							newOtherTransition
									.setOmittedSymbols(currentOmittedSymbols);
							if (currentAnyOrOtherTransition.getSymbolCase() == SymbolCase.OTHER) {
								currentOmittedSymbols
										.addAll(currentAnyOrOtherTransition
												.getOmittedSymbols().get());
							}
							int omittedSymbolNumberA = 0;
							while (omittedSymbolNumberA < currentOmittedSymbols
									.size()) {
								int omittedSymbolNumberB = omittedSymbolNumberA + 1;
								while (omittedSymbolNumberB < currentOmittedSymbols
										.size()) {
									if (currentOmittedSymbols
											.get()
											.get(omittedSymbolNumberA)
											.equals(currentOmittedSymbols.get()
													.get(omittedSymbolNumberB))) {
										currentOmittedSymbols.get().remove(
												omittedSymbolNumberB);
									} else {
										omittedSymbolNumberB++;
									}
								}
								omittedSymbolNumberA++;
							}
							currentState.getNextTransitions().add(
									newOtherTransition);
							transitionNumber++;
						}
					}
				}
				int transitionNumberA = 0;
				while (transitionNumberA < currentState.getNextTransitions()
						.size()) {
					Transition currentTransitionA = currentState
							.getNextTransitions().get(transitionNumberA);
					int transitionNumberB = transitionNumberA + 1;
					while (transitionNumberB < currentState
							.getNextTransitions().size()) {
						Transition currentTransitionB = currentState
								.getNextTransitions().get(transitionNumberB);
						if (currentTransitionA.getNextState() == currentTransitionB
								.getNextState()
								&& currentTransitionA.getSymbolCase() == currentTransitionB
										.getSymbolCase()) {
							switch (currentTransitionA.getSymbolCase()) {
							case SYMBOL:
								if ((currentTransitionA.getSymbol() == null && currentTransitionB
										.getSymbol() == null)
										|| currentTransitionA.getSymbol()
												.equals(currentTransitionB
														.getSymbol())) {
									this.isCompleted = false;
									currentState.getNextTransitions().remove(
											transitionNumberB);
								} else {
									transitionNumberB++;
								}
								break;
							case OTHER:
								if (currentTransitionA
										.getOmittedSymbols()
										.get()
										.containsAll(
												currentTransitionB
														.getOmittedSymbols()
														.get())
										&& currentTransitionB
												.getOmittedSymbols()
												.get()
												.containsAll(
														currentTransitionA
																.getOmittedSymbols()
																.get())) {
									this.isCompleted = false;
									currentState.getNextTransitions().remove(
											transitionNumberB);
								} else {
									transitionNumberB++;
								}
								break;
							default:
								this.isCompleted = false;
								currentState.getNextTransitions().remove(
										transitionNumberB);
							}
						} else {
							transitionNumberB++;
						}
					}
					transitionNumberA++;
				}
				transitionNumberA = 0;
				while (transitionNumberA < currentState.getNextTransitions()
						.size()) {
					Transition currentTransitionA = currentState
							.getNextTransitions().get(transitionNumberA);
					int transitionNumberB = transitionNumberA + 1;
					while (transitionNumberB < currentState
							.getNextTransitions().size()) {
						Transition currentTransitionB = currentState
								.getNextTransitions().get(transitionNumberB);
						boolean equalAAndB = false;
						if (currentTransitionA.getSymbolCase() == currentTransitionB
								.getSymbolCase()) {
							switch (currentTransitionA.getSymbolCase()) {
							case SYMBOL:
								if ((currentTransitionA.getSymbol() == null && currentTransitionB
										.getSymbol() == null)
										|| currentTransitionA.getSymbol()
												.equals(currentTransitionB
														.getSymbol())) {
									equalAAndB = true;
								}
								break;
							case OTHER:
								if (currentTransitionA
										.getOmittedSymbols()
										.get()
										.containsAll(
												currentTransitionB
														.getOmittedSymbols()
														.get())
										&& currentTransitionB
												.getOmittedSymbols()
												.get()
												.containsAll(
														currentTransitionA
																.getOmittedSymbols()
																.get())) {
									equalAAndB = true;
								}
								break;
							default:
								equalAAndB = true;
							}
						}
						if (equalAAndB) {
							this.isCompleted = false;
							boolean predefined = false;
							State margedState = null;
							ArrayList<Integer> costateNumbers = new ArrayList<Integer>();
							costateNumbers.addAll(currentTransitionA
									.getNextState().getCoStateNumber());
							costateNumbers.addAll(currentTransitionB
									.getNextState().getCoStateNumber());
							for (State checkState : stateList) {
								predefined = checkState.getCoStateNumber()
										.containsAll(costateNumbers);
								predefined = predefined
										&& costateNumbers
												.containsAll(checkState
														.getCoStateNumber());
								if (predefined) {
									margedState = checkState;
									break;
								}
							}
							if (margedState == null) {
								margedState = new State();
								if (currentTransitionA.getNextState().isEOP()
										|| currentTransitionB.getNextState()
												.isEOP()) {
									margedState.setEOP();
								}
								margedState.setCoStateNumber(costateNumbers);
								this.stateList.add(margedState);
								ArrayList<Transition> newTransitions = new ArrayList<Transition>();
								newTransitions.addAll(currentTransitionA
										.getNextState().getNextTransitions());
								newTransitions.addAll(currentTransitionB
										.getNextState().getNextTransitions());
								margedState.setNextTransitions(newTransitions);
							}
							Transition margedTransition = new Transition();
							margedTransition.setNextState(margedState);
							margedTransition.setSymbolAndCase(
									currentTransitionA.getSymbol(),
									currentTransitionA.getSymbolCase());
							margedTransition
									.setOmittedSymbols(currentTransitionA
											.getOmittedSymbols());
							currentState.getNextTransitions().remove(
									transitionNumberB);
							currentState.getNextTransitions().remove(
									transitionNumberA);
							currentState.getNextTransitions().add(
									margedTransition);
							transitionNumberA = -1;
							break;
						}
						transitionNumberB++;
					}
					transitionNumberA++;
				}
			}
			stateNumber++;
		}
		return this.isCompleted;
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
