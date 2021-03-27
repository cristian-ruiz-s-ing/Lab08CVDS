package edu.eci.cvds.samples.services;

import edu.eci.cvds.sampleprj.dao.PersistenceException;

public class ExcepcionServiciosAlquiler extends Exception{

	public ExcepcionServiciosAlquiler(String mensaje) {
		super(mensaje);
	}
	
	public ExcepcionServiciosAlquiler (String mensaje, PersistenceException ex) {
		super(mensaje);
	}

}
