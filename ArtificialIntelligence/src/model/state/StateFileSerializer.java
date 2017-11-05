package model.state;

import java.io.IOException;

public interface StateFileSerializer<S extends State>
{
	public void writeState(S state, String filePath) throws IOException;
	
	public S readState(String filePath) throws IOException;
}
