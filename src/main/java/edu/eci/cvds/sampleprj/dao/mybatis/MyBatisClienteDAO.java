package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

public class MyBATISClienteDAO implements ClienteDAO{
	
	@Inject
	private ClienteMapper clienteMapper;    

	@Override
	@Transactional
	public void save(Cliente c) throws PersistenceException {
		try{
		      clienteMapper.insertarCliente(c);
		}
		catch(org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al registrar el cliente ",e);
		}
	}
	
	@Override
	public Cliente load(long documento) throws PersistenceException {
		try{
		      return clienteMapper.consultarCliente((int) documento);
		}
		catch(org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al consultar el cliente "+documento,e);
		}
	}

	@Override
	public List<Cliente> consultarClientes() throws PersistenceException {
		try{
		      return clienteMapper.consultarClientes();
		}
		catch(org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al consultar clientes ",e);
		}
	}

	@Override
	@Transactional
	public void vetarCliente(long id, boolean vetado) throws PersistenceException {
		try{
		      clienteMapper.vetarCliente((int) id, vetado);
		}
		catch(org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al consultar clientes ",e);
		}	
	}

	@Override
	public List<ItemRentado> consultarItemsCliente(long idcliente) throws PersistenceException {
		try{
		     return clienteMapper.consultarItemsCliente(idcliente);
		}
		catch(org.apache.ibatis.exceptions.PersistenceException e){
			throw new PersistenceException("Error al consultar items del cliente",e);
		}
	}

}
