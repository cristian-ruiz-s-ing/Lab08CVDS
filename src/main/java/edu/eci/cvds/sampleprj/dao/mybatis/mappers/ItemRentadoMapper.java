package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;
import edu.eci.cvds.samples.entities.ItemRentado;

public interface ItemRentadoMapper {

	public void registrarAlquilerCliente(@Param("idcl")long doc, @Param("idit") int id, @Param("fechainicio") Date fechaInicio, @Param("fechafin")Date fechafin);
	public ItemRentado consultarItemRentado(@Param("idit")int itemId);

}
