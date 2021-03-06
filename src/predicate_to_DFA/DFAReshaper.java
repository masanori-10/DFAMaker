package predicate_to_DFA;

import java.util.ArrayList;

public class DFAReshaper {
	private EOSSearcher eosSearcher;
	private EpsilonEraser epsilonEraser;
	private PredicateMarger predicateMarger;
	private Marger marger;
	private DummyEraser dummyEraser;
	private PredicateEraser predicateEraser;
	private ArrayList<State> stateList;

	public DFAReshaper() {
		eosSearcher = new EOSSearcher();
		epsilonEraser = new EpsilonEraser();
		predicateMarger = new PredicateMarger();
		marger = new Marger();
		dummyEraser = new DummyEraser();
		predicateEraser = new PredicateEraser();
	}

	public void reshapeDEA(ArrayList<State> stateList, int maxPredicateDepth)
			throws CodingErrorException {
		eosSearcher.searchEOS(stateList);
		while (maxPredicateDepth >= 0) {
			epsilonEraser.eraseEpsilon(stateList);
			predicateMarger.margePredicate(stateList, maxPredicateDepth);
			marger.margeTransition(predicateMarger.getStateList());
			dummyEraser.eraseDummy(marger.getStateList());
			predicateEraser.erasePredicate(dummyEraser.getStateList());
			maxPredicateDepth--;
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
