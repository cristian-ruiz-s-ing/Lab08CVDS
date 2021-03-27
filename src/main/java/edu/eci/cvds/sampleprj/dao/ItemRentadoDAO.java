package edu.eci.cvds.sampleprj.dao;

import java.sql.Date;

import org.apache.ibatis.exceptions.PersistenceException;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.entities.Item;

public interface ItemRentadoDAO {

    public long consultarMultaAlquiler(int itemId, Date fechaDevolucion) throws PersistenceException;

    public void registrarAlquilerCliente(long doc, Item item, Date fechainicio, int numDias) throws ExcepcionServiciosAlquiler;

}