package edu.eci.cvds.sampleprj.dao;

public class PersistenceException extends Exception{
	
	public PersistenceException (String mensaje, org.apache.ibatis.exceptions.PersistenceException e) {
		super(mensaje);
	}
	
}
