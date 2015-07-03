package predicate_to_DFA;

import java.util.ArrayList;

public class PredicateEraser {
	private ArrayList<State> stateList;

	public void erasePredicate(ArrayList<State> stateList) {
		this.stateList = stateList;
		int i = 0;
		while (i < this.stateList.size()) {
			if (this.stateList.get(i).getPredicateNumber() % 2 == 1) {
				this.stateList.remove(i);
			} else {
				i++;
			}
		}
		i = 0;
		while (i < this.stateList.size()) {
			int j = 0;
			State state = this.stateList.get(i);
			while (j < state.getNextTransitions().size()) {
				if (state.getNextTransitions().get(j).getNextState()
						.getPredicateNumber() % 2 == 1) {
					state.getNextTransitions().remove(j);
				} else {
					j++;
				}
			}
			i++;
		}

	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}
