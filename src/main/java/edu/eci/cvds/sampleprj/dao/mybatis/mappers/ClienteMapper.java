package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {

    public Cliente consultarCliente( @Param("idcli") int id );
    
 
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'id' y relacionado con el item identificado con 'idit'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("idcli")int id, @Param("idit")int idit, @Param("fechainicio")Date fechainicio,@Param("fechafin")Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();

    /**
     * Registrar un cliente
     * @return 
     */
	public void insertarCliente(@Param("cliente") Cliente c);


	public void vetarCliente(@Param("idcli")int id,@Param("estado") boolean vetado);


	public List<ItemRentado> consultarItemsCliente(@Param("idcli")long idcliente);
	
	public int valorMultaRetrasoxDia(@Param("idItem")int itemId);
    
}
