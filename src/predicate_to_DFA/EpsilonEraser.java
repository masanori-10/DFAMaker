package predicate_to_DFA;

import java.util.ArrayList;

public class EpsilonEraser {
	private boolean isCompleted;

	public boolean eraseEpsilon(ArrayList<State> stateList) {
		this.isCompleted = true;
		for (int stateNumber = 0; stateNumber < stateList.size(); stateNumber++) {
			State currentState = stateList.get(stateNumber);
			int transitionNumber = 0;
			while (transitionNumber < currentState.getNextTransitions().size()) {
				Transition currentTransition = currentState
						.getNextTransitions().get(transitionNumber);
				if (currentTransition instanceof EpsilonTransition) {
					this.isCompleted = false;
					ArrayList<Transition> nextTransitions = currentState
							.getNextTransitions();
					nextTransitions.remove(transitionNumber);
					nextTransitions.addAll(currentTransition.getNextState()
							.getNextTransitions());
					currentState.setNextTransitions(nextTransitions);
				} else {
					transitionNumber++;
				}
			}
		}
		return this.isCompleted;
	}
}
