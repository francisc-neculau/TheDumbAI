package game.ion;

import java.util.SortedSet;
import java.util.TreeSet;

public class RiverState {
	
	private SortedSet<RiverRole> westShore;
	private SortedSet<RiverRole> eastShore;
	private Location 		     boatLocation;
	
	public RiverState(SortedSet<RiverRole> eastShore, SortedSet<RiverRole> westShore, Location boatLocation ) {
		this.eastShore = new TreeSet<>(eastShore);
		this.westShore = new TreeSet<>(westShore);
		this.boatLocation = boatLocation;
	}
	
	public SortedSet<RiverRole> getWestShore() {
		return new TreeSet<RiverRole>(westShore);
	}

	public SortedSet<RiverRole> getEastShore() {
		return new TreeSet<RiverRole>(eastShore);
	}
	
	public Location getBoatLocation() {
		return this.boatLocation;
	}
	
	@Override
    public String toString() {
        return "State{" + westShore + ", " + eastShore + ", boatLocation=" + boatLocation + "}";
    }
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof RiverState)) return false;
		RiverState state = (RiverState)obj;
		
		if(this.boatLocation != state.boatLocation) return false;
		if(westShore.size() != state.westShore.size()) return false;
		for (RiverRole role : westShore) {
			if(!state.westShore.contains(role)) return false;
		}
			
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * containsKey checks the hashCode and then goes to equals
	 */
	@Override
	public int hashCode() {
        int result;
        result = westShore.hashCode();
        result = 31 * result + eastShore.hashCode();
        result = 31 * result + boatLocation.hashCode();
        return result;
    }
	
}
