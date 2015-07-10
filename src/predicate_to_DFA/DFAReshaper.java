package predicate_to_DFA;

import java.util.ArrayList;

public class DFAReshaper {
	private EpsilonEraser epsilonEraser;
	private PredicateMarger predicateMarger;
	private Marger marger;
	private DummyEraser dummyEraser;
	private PredicateEraser predicateEraser;
	private ArrayList<State> stateList;
	private boolean isCompleted;

	public DFAReshaper() {
		epsilonEraser = new EpsilonEraser();
		predicateMarger = new PredicateMarger();
		marger = new Marger();
		dummyEraser = new DummyEraser();
		predicateEraser = new PredicateEraser();
	}

	public void reshapeDEA(ArrayList<State> stateList) {
		while (!(this.isCompleted)) {
			this.isCompleted = epsilonEraser.eraseEpsilon(stateList);
			this.isCompleted = this.isCompleted
					&& predicateMarger.margePredicate(stateList);
			this.isCompleted = this.isCompleted
					&& marger.margeTransition(predicateMarger.getStateList());
			this.isCompleted = this.isCompleted
					&& dummyEraser.eraseDummy(marger.getStateList());
			this.isCompleted = this.isCompleted
					&& predicateEraser.erasePredicate(dummyEraser
							.getStateList());
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
