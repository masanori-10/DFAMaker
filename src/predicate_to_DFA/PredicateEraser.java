package predicate_to_DFA;

import java.util.ArrayList;

public class PredicateEraser {
	private ArrayList<State> stateList;
	private boolean isCompeted;

	public boolean erasePredicate(ArrayList<State> stateList) {
		this.stateList = stateList;
		this.isCompeted = true;
		int stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			if (this.stateList.get(stateNumber).isEOP()) {
				this.stateList.remove(stateNumber);
			} else {
				stateNumber++;
			}
		}
		stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			int transitionNumber = 0;
			State currentState = this.stateList.get(stateNumber);
			while (transitionNumber < currentState.getNextTransitions().size()) {
				if (currentState.getNextTransitions().get(transitionNumber)
						.getNextState().isEOP()) {
					currentState.getNextTransitions().remove(transitionNumber);
				} else {
					transitionNumber++;
				}
			}
			stateNumber++;
		}
		return this.isCompeted;
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
