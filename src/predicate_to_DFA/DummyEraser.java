package predicate_to_DFA;

import java.util.ArrayList;

public class DummyEraser {
	private ArrayList<State> stateList;
	private boolean isCompleted;
	private ArrayList<State> implementedStates;

	public boolean eraseDummy(ArrayList<State> stateList) {
		this.stateList = stateList;
		this.isCompleted = true;
		this.implementedStates = new ArrayList<State>();
		int stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			if (this.stateList.get(stateNumber).getStateNumber() == 0) {
				State startState = this.stateList.get(stateNumber);
				this.implementedStates.add(startState);
				this.checkNextTransition(startState);
				break;
			}
			stateNumber++;
		}
		stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			if (!(this.implemented(this.stateList.get(stateNumber)))) {
				this.isCompleted = false;
				this.stateList.remove(stateNumber);
			} else {
				stateNumber++;
			}
		}
		return this.isCompleted;
	}

	private void checkNextTransition(State startState) {
		for (int transitionNumber = 0; transitionNumber < startState
				.getNextTransitions().size(); transitionNumber++) {
			State implementedState = startState.getNextTransitions()
					.get(transitionNumber).getNextState();
			if (!(this.implemented(implementedState))) {
				this.implementedStates.add(implementedState);
				this.checkNextTransition(implementedState);
			}
		}
	}

	private boolean implemented(State state) {
		for (int implementedStateNumber = 0; implementedStateNumber < implementedStates
				.size(); implementedStateNumber++) {
			if (implementedStates.get(implementedStateNumber) == state) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
