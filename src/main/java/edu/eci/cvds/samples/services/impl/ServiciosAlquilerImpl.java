package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import java.sql.Date;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   
   @Inject
   private ItemRentadoDAO itemRentadoDAO;
   
   @Inject
   private ClienteDAO clienteDAO;

   @Override
   public int valorMultaRetrasoxDia(int itemId) throws PersistenceException {
       return itemDAO.valorMultaXDia(itemId);
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try {
           return clienteDAO.load(docu);
       } catch (PersistenceException ex) {
    	   throw new ExcepcionServiciosAlquiler("Error al consultar el cliente "+docu,ex);
       }
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
	   try {
           return clienteDAO.consultarItemsCliente(idcliente);
       } catch (PersistenceException ex) {
    	   throw new ExcepcionServiciosAlquiler("Error al consultar el cliente "+idcliente,ex);
       }
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
	   try {
           return clienteDAO.consultarClientes();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar clientes",ex);
       }
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
	   try {
           return itemDAO.consultarItemsDisponibles();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los items ",ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler { 
	   itemRentadoDAO.registrarAlquilerCliente(docu, item, date, numdias);
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try {
    	   clienteDAO.save(c);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registrar el cliente con documento: "+c.getDocumento(),ex);
       }
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler, PersistenceException {
	   return itemDAO.costoAlquiler(iditem, numdias);
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
	   try {
           itemDAO.save(i);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registarar el item "+i.getId(),ex);
       }
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
	   try {
           clienteDAO.vetarCliente(docu, estado);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al vetar Cliente con doc: "+docu,ex);
       }
   }
}