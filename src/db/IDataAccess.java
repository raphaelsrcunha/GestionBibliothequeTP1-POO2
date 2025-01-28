package db;

public interface IDataAccess {

	void connect() throws Exception; 
    void disconnect() throws Exception;
	
}
