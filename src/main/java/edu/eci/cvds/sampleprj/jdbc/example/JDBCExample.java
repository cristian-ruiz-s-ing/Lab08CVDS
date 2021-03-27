/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {

    private static PreparedStatement prepare = null;
    
    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
            
            System.out.println("Valor total pedido 1:" + valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 1);
            
            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");

            int suCodigoECI=21544274;
            registrarNuevoProducto(con, suCodigoECI, "prueba 2", 21500);            
            con.commit();      
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo el código del producto
     * @param nombre el nombre del producto
     * @param precio el precio del producto
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre, int precio) throws SQLException{
        try{
            //Crear preparedStatement
            prepare = con.prepareStatement("insert into ORD_PRODUCTOS values (?,?,?)");
            //Asignar parámetros
            prepare.setInt(1, codigo);
            prepare.setString(2, nombre);
            prepare.setInt(3, precio);
            //Usar 'execute'
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
        List<String> np=new LinkedList<>();

        String query = "select nombre from ORD_DETALLE_PEDIDO as pe join ORD_PRODUCTOS as pd on (pe.producto_fk = pd.codigo) where pe.pedido_fk = ?;";
        try{
            //Crear preparedStatement
            prepare = con.prepareStatement(query);
            //Asignar parámetros
            prepare.setInt(1, codigoPedido);
            //Usar executeQuery
            ResultSet rs = prepare.executeQuery();
            //Sacar resultados del ResultSet
            while (rs.next()){
                //Llenar la lista y retornarla
                np.add(rs.getString(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido){
        
        String query = "SELECT SUM(pe.cantidad * pd.precio) FROM ORD_DETALLE_PEDIDO as pe join ORD_PRODUCTOS as pd on (pe.producto_fk = pd.codigo) WHERE pe.pedido_fk = ?";
        int total = 0;
        try{
            //Crear prepared statement
            prepare = con.prepareStatement(query);
            //Asignar parámetros
            prepare.setInt(1, codigoPedido);
            //Usar executeQuery
            ResultSet rs = prepare.executeQuery();
            //Sacar resultado del ResultSet
            while (rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } 
        return total;    
    }
}
