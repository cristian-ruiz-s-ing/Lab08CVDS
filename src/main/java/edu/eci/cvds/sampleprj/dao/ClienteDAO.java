package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;

public interface ClienteDAO {
	
	public void save(Cliente c) throws PersistenceException;
	
	public List<Cliente> consultarClientes() throws PersistenceException;
	
	public Cliente load(long documento) throws PersistenceException;
	
	public void vetarCliente( long docu, boolean vetado) throws PersistenceException;

	public List<ItemRentado> consultarItemsCliente(long idcliente) throws PersistenceException;

}