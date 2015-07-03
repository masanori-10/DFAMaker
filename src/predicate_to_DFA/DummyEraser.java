package predicate_to_DFA;

import java.util.ArrayList;

public class DummyEraser {
	private ArrayList<State> stateList;
	private ArrayList<State> implementedStates;

	public void eraseDummy(ArrayList<State> stateList) {
		this.stateList = stateList;
		this.implementedStates = new ArrayList<State>();
		int i = 0;
		while (i < this.stateList.size()) {
			if (this.stateList.get(i).getStateNumber() == 0) {
				State startState = this.stateList.get(i);
				this.implementedStates.add(startState);
				this.checkNextTransition(startState);
				break;
			}
			i++;
		}
		i = 0;
		while (i < this.stateList.size()) {
			if (!(this.implemented(this.stateList.get(i)))) {
				this.stateList.remove(i);
			} else {
				i++;
			}
		}
	}

	private void checkNextTransition(State startState) {
		for (int i = 0; i < startState.getNextTransitions().size(); i++) {
			State implementedState = startState.getNextTransitions().get(i)
					.getNextState();
			if (!(this.implemented(implementedState))) {
				this.implementedStates.add(implementedState);
				this.checkNextTransition(implementedState);
			}
		}
	}

	private boolean implemented(State state) {
		for (int i = 0; i < implementedStates.size(); i++) {
			if (implementedStates.get(i) == state) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
