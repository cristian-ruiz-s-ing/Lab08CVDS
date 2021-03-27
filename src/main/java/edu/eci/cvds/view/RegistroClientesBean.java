package edu.eci.cvds.view;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.sql.Date;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;

@ManagedBean(name="AlquilerItemsBean")
@SessionScoped
public class RegistroClientesBean extends BasePageBean{
    private Cliente cliente = null;
    private List<Cliente> clientes= null;
    private List<ItemRentado> items= null;
    private Item item = null;
    private long precio = 0;

    @Inject
    private ServiciosAlquiler servicios;
    @Inject
    private Cliente clienteUsado;

    public Cliente consultarCliente (long docu){
        try{
        cliente = servicios.consultarCliente(docu);
        }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler ){
        }
        return cliente;
    }

    public List<Cliente> consultaClientes () {
        try{
        clientes =servicios.consultarClientes();
        }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){

        }
        return clientes;
    }

    public void registrarCliente(String nombre, long documento, String telefono, String direccion, String email){
        try{
        cliente = new Cliente(nombre,documento,telefono,direccion,email);
        servicios.registrarCliente(cliente);
        }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){}

        }
        
    public List<ItemRentado> consultarItems (long id){
        try{
                items = servicios.consultarItemsCliente(id);
                }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){}
                return items;
            }

            public Long consultarItem (int id){
                try{
                        item = servicios.consultarItem(id);
                        precio=item.getTarifaxDia();
                        }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){}
                        return precio;
            
                    }

    
            
    public void RegistrarItems(int itemRegistrado, int dias){
            try{
                Date fecha = new Date(System.currentTimeMillis()); 
                item =  servicios.consultarItem(itemRegistrado); 
                servicios.registrarAlquilerCliente(fecha, clienteUsado.getDocumento() , item, dias);
                }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){}           
                }

    public long consultarmulta(int iditem){
                    try{
                        Date fecha = new Date(System.currentTimeMillis()); 
                        precio=servicios.consultarMultaAlquiler(iditem, fecha);
                        }catch(ExcepcionServiciosAlquiler excepcionServiciosAlquiler){}  
                        return precio;         
                        }
                        
    public Cliente getclienteUsado(){
        return clienteUsado;
    }

    public void setclienteUsado(Cliente usado){
        this.clienteUsado=usado;

    }

}

