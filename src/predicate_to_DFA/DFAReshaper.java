package predicate_to_DFA;

import java.util.ArrayList;

public class DFAReshaper {
	public EpsilonEraser epsilonEraser;
	public PredicateMarger predicateMarger;
	public Marger marger;
	public DummyEraser dummyEraser;
	public PredicateEraser predicateEraser;
	public ArrayList<State> stateList;

	public DFAReshaper() {
		epsilonEraser = new EpsilonEraser();
		predicateMarger = new PredicateMarger();
		marger = new Marger();
		dummyEraser = new DummyEraser();
		predicateEraser = new PredicateEraser();
	}

	public void reshapeDEA(ArrayList<State> stateList, int maxPredicateNumber) {
		epsilonEraser.eraseEpsilon(stateList);
		for (int predicateNumber = maxPredicateNumber; predicateNumber > 0; predicateNumber--) {

			marger.margeTransition(stateList, predicateNumber);
			dummyEraser.eraseDummy(marger.getStateList());
			predicateEraser.erasePredicate(dummyEraser.getStateList());
		}
		this.stateList = predicateEraser.getStateList();
		this.renumberingState();
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}

	public void renumberingState() {
		int stateNumber = 0;
		while (stateNumber < this.stateList.size()) {
			if (this.stateList.get(stateNumber).getStateNumber() != -1) {
				this.stateList.get(stateNumber).setStateNumber(stateNumber);
				stateNumber++;
			} else {
				this.stateList.remove(stateNumber);
			}
		}
	}
}
