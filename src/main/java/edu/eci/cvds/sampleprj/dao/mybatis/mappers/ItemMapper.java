package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.Item;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {     
    
    public Item consultarItem(@Param("idit")int id);
    
    public void insertarItem(@Param("item") Item it);

	public List<Item> consultarItemsDisponibles();

        
}
