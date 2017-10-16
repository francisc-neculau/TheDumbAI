package queens;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class QueenProblem {

	private final Integer N;
	private List<List<Integer>> solutions;
	private DomainList domainList;
	private List<Integer> solution;
	
	
	public QueenProblem(Integer dimension) {
		this.N = dimension;
		this.domainList = new DomainList(dimension);
		this.solution = new ArrayList<>(dimension);
	}
	
	public void solve() {
		FC(0);
	}
	
	private void FC(Integer column) {
		Dom domain = domainList.getDomain(column);
		for (Integer row : domain) {

			domain.setSelectedValue(row);

			if(column == N-1) {
				System.out.println(domainList);
			} else {
				if (checkForward(column, row))
					FC(column + 1);
				restoreDomainList(column);
			}
				
		}
	}

	private boolean checkForward(Integer column, Integer row) {
		for (int i = column+1; i < N; i++) {
			domainList.getDomain(i).removeInconsistentValues(column, row);
			if(domainList.getDomain(i).isEmpty()) {
				domainList.getDomain(i).setRestoreBoundry(true);
				return false;
			}
		}
		return true;
	}
	
	private void restoreDomainList(Integer domainIndex) {
		for (int i = domainIndex + 1; i < N; i++) {
			// restore until reach the empty or also said unconsistent state :)
			if(domainList.getDomain(i).isRestoreBoundry()) {
				domainList.getDomain(i).restoreDomainConsistency();
				domainList.getDomain(i).setRestoreBoundry(false);
				break;
			}
			domainList.getDomain(i).restoreDomainConsistency();
		}
	}
	
}

class Dom implements Iterable<Integer>{
	
	private List<Integer> values;
	private Integer domainColumnIndex;
	private Deque<List<Integer>> recoveryValuesStack;
	private Integer selectedValue;
	private boolean restoreBoundry = false;
	
	public Dom(Integer bound, Integer domainColumnIndex) {
		this.recoveryValuesStack = new LinkedList<>();
		this.domainColumnIndex = domainColumnIndex;
		this.values = new ArrayList<>();
		for (int i = 0; i < bound; i++) {
			this.values.add(i);
		}
	}


	public void removeInconsistentValues(Integer column, Integer row) {
		// cache Domain previous consistent state for recovery
		this.recoveryValuesStack.push(new ArrayList<>(this.values));
		// remove for row
		if(this.values.contains(row))
			this.values.remove(row);
		// remove for up-diagonal 
		if(this.values.contains(row - Math.abs(domainColumnIndex - column)))
			this.values.remove(new Integer(row - Math.abs(domainColumnIndex - column)));
		// remove for down-diagonal
		if(this.values.contains(row + Math.abs(domainColumnIndex - column)))
			this.values.remove(new Integer(row + Math.abs(domainColumnIndex - column)));
	}

	public void restoreDomainConsistency() {
		if(recoveryValuesStack.peek() != null)
			this.values = this.recoveryValuesStack.pop();
		
	}
	
	public boolean isEmpty() {
		return this.values.isEmpty();
	}
	

	@Override
	public Iterator<Integer> iterator() {
		return this.values.iterator();
	}

	public Integer getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Integer selectedValue) {
		this.selectedValue = selectedValue;
	}


	public Integer getDomainColumnIndex() {
		return domainColumnIndex;
	}
	
	public void setDomainColumnIndex(Integer domainColumnIndex) {
		this.domainColumnIndex = domainColumnIndex;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer value : values) {
			sb.append(value);
			sb.append(" ");
		}
		return sb.toString();
	}

	public boolean isRestoreBoundry() {
		return restoreBoundry;
	}
	
	public void setRestoreBoundry(boolean restoreBoundry) {
		this.restoreBoundry = restoreBoundry;
	}

}

class DomainList {
	
	private List<Dom> domanins;

	public DomainList(Integer bound){
		this.domanins = new ArrayList<>();
		for (int i = 0; i < bound; i++) {
			this.domanins.add(new Dom(bound, i));
		}
	}

	public Dom getDomain(Integer index) {
		return this.domanins.get(index);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Dom dom : domanins) {
			sb.append(dom.getSelectedValue());
			sb.append(" ");
		}
		return sb.toString();
	}
}
