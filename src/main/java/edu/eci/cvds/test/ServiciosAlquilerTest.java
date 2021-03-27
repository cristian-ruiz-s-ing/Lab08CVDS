package edu.eci.cvds.test;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.sql.Date;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }
    
    @Test
    public void validRegistrarCliente() {
    	try {
    		Cliente cliente = new Cliente("Lola", 3158119, "311234567", "calle 200", "loop@gmail.com", false, null);
    		serviciosAlquiler.registrarCliente(cliente);

			Cliente clienteCheck = serviciosAlquiler.consultarCliente(cliente.getDocumento());
			assertEquals(cliente.getDocumento(), clienteCheck.getDocumento());
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void invalidRegistrarCliente() {
    	boolean r = false;
    	try {
    		Cliente cliente = new Cliente("Lola", 3158200, "311234567", "calle 200", "looping@gmail.com", false, null);
    		serviciosAlquiler.registrarCliente(cliente);
    		
    		Cliente cliente2 = new Cliente("pepe", 3158200, "311234567", "calle 201", "pol@gmail.com", false, null);
    		serviciosAlquiler.registrarCliente(cliente2);
    		
		} catch (ExcepcionServiciosAlquiler e) {
			r = true;
		}
    	Assert.assertTrue(r);
    }
    
    @Test
    public void validConsultarCliente() {
    	try {
			Cliente cliente = serviciosAlquiler.consultarCliente(2158119);
			assertEquals(2158119, cliente.getDocumento());
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void invalidConsultarCliente() {
    	try {
			Cliente cliente = serviciosAlquiler.consultarCliente(2158110);
			Assert.assertNull(cliente);
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void validConsultarClientes() {
    	try {
			List<Cliente> clientes = serviciosAlquiler.consultarClientes();
			Assert.assertTrue(clientes.size() > 0);
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void validRegistrarItem() {
    	try {
    		Date fecha2 = new Date(120, 11, 25);
    		TipoItem tipo2 = new TipoItem(91, "Belico");
    		Item item = new Item(tipo2, 14  , "The pacific" , "serie", fecha2, 140, "dvd", "Belico");
    		serviciosAlquiler.registrarItem(item);
    		
    		Item itemCheck = serviciosAlquiler.consultarItem(item.getId());
			assertEquals(item.getId(), itemCheck.getId());
    		
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void invalidRegistrarItem() {
    	boolean r = false;
    	try {
    		Date fecha2 = new Date(120, 11, 25);
    		TipoItem tipo2 = new TipoItem(91, "Belico");
    		Item item = new Item(tipo2, 15  , "The pacific" , "serie", fecha2, 140, "dvd", "Belico");
    		serviciosAlquiler.registrarItem(item);
    		
    		Item item2 = new Item(tipo2, 15  , "Ocean" , "serie", fecha2, 140, "dvd", "Belico");
    		serviciosAlquiler.registrarItem(item2);
    		
		} catch (ExcepcionServiciosAlquiler e) {
			r = true;
		}
    	Assert.assertTrue(r);
    }
    
    @Test
    public void validConsultarItem() {
    	try {
			Item item = serviciosAlquiler.consultarItem(93);
			assertEquals(93, item.getId());
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void invalidConsultarItem() {
    	try {
    		Item item = serviciosAlquiler.consultarItem(0);
			Assert.assertNull(item);
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void validConsultarItemsDisponibles() {
    	try {
    		
    		
			List<Item> items = serviciosAlquiler.consultarItemsDisponibles();
			Assert.assertTrue(items.size() > 0);
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void validVetarCliente() {
    	try {
			serviciosAlquiler.vetarCliente(2158119, true);
			Cliente cliente = serviciosAlquiler.consultarCliente(2158119);
			assertEquals(true, cliente.isVetado());
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void validRegistrarAlquiler() {
    	try { 
    		//Se busca el item disponible
            Date fecharegistro = java.sql.Date.valueOf("2020-10-10");
    	    List<Item> itDis = serviciosAlquiler.consultarItemsDisponibles();
    	    Item it = itDis.get(0);
    	    //Se alquila
    	    Cliente cl = serviciosAlquiler.consultarCliente(2158119);
    	    serviciosAlquiler.registrarAlquilerCliente(fecharegistro, cl.getDocumento(), it, 10);
	    	
		} catch (ExcepcionServiciosAlquiler e) {
			Assert.assertFalse(false);
		}
    }
    
    @Test
    public void registrarAlquilerSinItem() {
    	boolean r = false;
    	try { 
    		//Crea item
    		Date fecharegistro = java.sql.Date.valueOf("2020-10-11");
            Date fecha = java.sql.Date.valueOf("2020-10-10");
    	    TipoItem tipo2 = new TipoItem(91, "Belico");
    	    Item item = new Item(tipo2, 16  , "pororo" , "serie", fecha, 140, "dvd", "Belico");
    	    
    	    //Intenta hacer alquiler
    	    serviciosAlquiler.registrarAlquilerCliente(fecharegistro, 2158119, item, 15);
	    	
		} catch ( ExcepcionServiciosAlquiler e) {
			r = true;
		}
    	Assert.assertTrue(r);
    }
    
}