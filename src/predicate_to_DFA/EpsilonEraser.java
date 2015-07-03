package predicate_to_DFA;

import java.util.ArrayList;

public class EpsilonEraser {

	public void eraseEpsilon(ArrayList<State> stateList) {
		for (int stateNumber = 0; stateNumber < stateList.size(); stateNumber++) {
			State state = stateList.get(stateNumber);
			int transitionNumber = 0;
			while (transitionNumber < state.getNextTransitions().size()) {
				Transition transition = state.getNextTransitions().get(
						transitionNumber);
				if (transition instanceof EpsilonTransition) {
					ArrayList<Transition> nextTransitions = state
							.getNextTransitions();
					nextTransitions.remove(transitionNumber);
					nextTransitions.addAll(transition.getNextState()
							.getNextTransitions());
					state.setNextTransitions(nextTransitions);
				} else {
					transitionNumber++;
				}
			}
		}
	}
}
