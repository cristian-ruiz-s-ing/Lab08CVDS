package edu.eci.cvds.sampleprj.dao.mybatis;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.google.inject.Inject;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.guice.transactional.Transactional;

import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO {
    @Inject
    private ItemRentadoMapper itemRentadoMapper;
    @Inject
    private ItemMapper itemMapper;

    @Override
    public long consultarMultaAlquiler(int itemId, Date fechaDevolucion) throws PersistenceException {
        try {
            //obtenemos item
        	ItemRentado itemRentado = itemRentadoMapper.consultarItemRentado(itemId);
            
        	//Calculamos multa
            LocalDate fechafinRenta = itemRentado.getFechafinrenta().toLocalDate();
            LocalDate fechadevolucion = fechaDevolucion.toLocalDate();
            long dias = ChronoUnit.DAYS.between(fechafinRenta, fechadevolucion);
            long valor = itemRentado.getItem().getTarifaxDia();
            
            return dias * valor;
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar la multa del item:  " + itemId, e);
        }
    }

    @Override
    @Transactional
    public void registrarAlquilerCliente(long doc, Item item, Date fechaInicio, int numDias) throws ExcepcionServiciosAlquiler {
        LocalDate fechaInicial = fechaInicio.toLocalDate();
        LocalDate fechaFinal = fechaInicial.plusDays(numDias);
    	
        Item it = itemMapper.consultarItem(item.getId());
        if (numDias > 1) {
    		try {
    			itemRentadoMapper.registrarAlquilerCliente(doc, it.getId(), fechaInicio, Date.valueOf(fechaFinal));
    		}catch (NullPointerException e) {
    			throw new ExcepcionServiciosAlquiler("Error al registrar alquiler");    
            }
    	}
    }
}