package co.edu.uniandes.cabAndes.TestCabAndes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.user.client.Window;

import co.edu.uniandes.cabAndes.server.dao.AdminDAO;
import co.edu.uniandes.cabAndes.server.dao.AdminDAOCatalogo;
import co.edu.uniandes.cabAndes.shared.vos.BodegaPedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.LicitacionValue;
import co.edu.uniandes.cabAndes.shared.vos.LocalMayorNumeroVentasValue;
import co.edu.uniandes.cabAndes.shared.vos.LugarAlmacenamientoValue;
import co.edu.uniandes.cabAndes.shared.vos.MovimientoValue;
import co.edu.uniandes.cabAndes.shared.vos.OfertaProveedorValue;
import co.edu.uniandes.cabAndes.shared.vos.PedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoLugarAlmacValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoMayorMovimientoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoPedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoValue;
import co.edu.uniandes.cabAndes.shared.vos.VentaValue;
import junit.framework.TestCase;

public class TestCabAndes extends TestCase
{
	private static final String ARCHIVO_CONEXION="conexion.properties";
	
	protected Connection conexion;
	
	public TestCabAndes() throws Exception
	  {

		System.out.println("Entra al constructor");
	    try
	    {
	      Properties properties=new Properties();
	      InputStream inputStream=AdminDAO.class.getResourceAsStream(ARCHIVO_CONEXION);
	      try
	      {
	        properties.load(inputStream);
	      }
	      finally
	      {
	        inputStream.close();
	      }
	      String cadenaConexion=properties.getProperty("url");
	      String usuario=properties.getProperty("usuario");
	      String clave=properties.getProperty("clave");
	      String driver=properties.getProperty("driver");
	      Class.forName(driver);
	      conexion=DriverManager.getConnection(cadenaConexion,usuario,clave);
	      conexion.setAutoCommit(false);
	    }
	    catch (Throwable th)
	    {
	      throw new SQLException("ERROR: AdminDAO obteniendo una conexión.");
	    }
	  }

	  public void commitConexion() throws Exception
	  {
	    conexion.commit();
	  }

	  public void rollbackConexion() throws Exception
	  {
	    conexion.rollback();
	  }

	  public void cerrarConexion() throws Exception
	  {
	    try
	    {
	      conexion.close();
	      conexion=null;
	    }
	    catch (Throwable th)
	    {
	      throw new SQLException("ERROR: AdminDAO cerrando una conexión.");
	    }
	  }

	  public void probarConexion() throws Exception
	  {
	    Statement statement=conexion.createStatement();
	    try
	    {
	      ResultSet resultSet=statement.executeQuery("SELECT * FROM USUARIO");
	      try
	      {
	        while (resultSet.next())
	        {
	          System.out.println(resultSet.getString("DIRECCION_ELECTRONICA"));
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      statement.close();
	    }
	  }
	  
	  //-----------------------------------------------------
	  // Escenarios de prueba
	  //-----------------------------------------------------
	  
	  /**
	   * Escenario de prueba para los usuarios
	   */
	  public void setUpEscenario1()
	  {
		  // Crea un usuario administrador de local
		  
		  String sql = "insert into USUARIO values('john@gmail.com','Johnathan','123654789','123','Bogotá','Cundinamarca','Colombiano','Cra 23 B # 42 - 86 Sur',4568,3569856,'Administrador de local','Persona natural')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  
		  // Crea un usuario Comprador Mayorista.
		  
		  sql = "insert into USUARIO values('john1@gmail.com','Johnathan1','132654789','123','Bogotá','Cundinamarca','Colombiano','Cra 23 B # 42 - 86 Sur',4568,3569856,'Comprador mayorista','Persona natural')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
		  
		  // Crea un usuario proveedor
		  
		  sql = "insert into USUARIO values('john2@gmail.com','Johnathan2','153654789','123','Bogotá','Cundinamarca','Colombiano','Cra 23 B # 42 - 86 Sur',4568,3569856,'Proveedor','Persona natural')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  

		  sql = "insert into PROVEEDOR values('john2@gmail.com',3,5)";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  
	  }
	  
	  public void undoEscenario1()
	  {
		  // Elimina el usuario administrador de local
		  
		  String sql = "delete from USUARIO where direccion_electronica =  'john@gmail.com'";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
		  
		  // Crea un usuario Comprador Mayorista.
		  
		  sql = "delete from USUARIO where direccion_electronica =  'john1@gmail.com'";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }

			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
		  
		  // Crea un usuario proveedor
		  
		  sql = "delete from PROVEEDOR where COD_USUARIO = 'john2@gmail.com'";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }

			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from USUARIO where direccion_electronica =  'john2@gmail.com'";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }

			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
	  
	  }
	  
	  public void setUpEscenario2()
	  {
		  String sql = "insert into PRODUCTO values(123456789,3,'Pescado feliz','20')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);			  
			  preparedStatement.execute();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "insert into PRODUCTO_LUGAR_ALMAC values(22,123456789,12,10,123456789,'01/01/2010',0.00)";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
	  }
	  
	  public void undoEscenario2()
	  {
		  
		  String sql = "delete from PRODUCTO_LUGAR_ALMAC where COD_PRODUCTO = 123456789";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from PRODUCTO where id = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  
	  }
	  
	  public void setUpEscenario3()
	  {
		  setUpEscenario1();
		  
		  String sql = "insert into PEDIDO values(123456789,'john@gmail.com','20/03/2014','14/04/2014',null,20000,'No','No')";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "insert into PEDIDO values(987654321,'john1@gmail.com','20/03/2014','14/04/2014',null,20000,'No','No')";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  
		  
	  }
	  
	  public void undoEscenario3()
	  {
		  String sql = "delete from PEDIDO where cod_usuario = 'john@gmail.com'";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from PEDIDO where cod_usuario = 'john1@gmail.com'";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);			  
			  preparedStatement.execute();
			  
			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
	  }
	  
	  public void setUpEscenario4()
	  {
		  setUpEscenario1();
		  setUpEscenario2();
		  
		  // Crea un usuario administrador de local
		  
		  String sql = "insert into lugar_almacenamiento values(123456789,1,'Local de margarita',5000000)";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  
		  // Crea un usuario Comprador Mayorista.
		  
		  sql = "insert into local values(123456789,'john@gmail.com')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
		  
		  // 
		  
		  sql = "insert into PRODUCTO_LUGAR_ALMAC values(123456789,123456789,12,50000,987654321,'15/04/2009',0.00)";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
	  }
	  
	  public void undoEscenario4()
	  {
		  
		  
		  String sql = "delete from producto_lugar_almac where numero_lote = 987654321";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.execute();

			  System.out.println("blblblb");
			  commitConexion();		

			  System.out.println("blblblb");
			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  // Crea un usuario Comprador Mayorista.
		  
		  sql = "delete from local where cod_administrador = 'john@gmail.com'";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  System.out.println(sql);
		  
		  // Crea un usuario administrador de local
		  
		  sql = "delete from lugar_almacenamiento where id = 123456789";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  undoEscenario2();
		  undoEscenario1();
		  
	  }
	  
	  public void setUpEscenario5()
	  {
		  setUpEscenario2();
		  
		  String sql = "insert into LUGAR_ALMACENAMIENTO values(123456789,3,'Bodega Juanita',500000)";

		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "insert into BODEGA values(123456789,'En funcionamiento')";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "insert into PRODUCTO_LUGAR_ALMAC values(123456789,123456789,12,50000,123456789,'20/03/2015',0.0)";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }

	  }
	  
	  public void undoEscenario5()
	  {
		  
		  String sql = "delete from producto_lugar_almac where COD_PRODUCTO = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from bodega where id = 123456789";

		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from producto_lugar_almac where COD_LUGAR_ALMAC = 987654321";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from producto_lugar_almac where COD_PRODUCTO = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from producto_lugar_almac where COD_LUGAR_ALMAC = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  sql = "delete from LUGAR_ALMACENAMIENTO where ID = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  undoEscenario2();		  
		  
	  }
	  
	  //-------------------------------------------------------------------------------------
	  // Pruebas de Los requerimientos funcionales
	  //-------------------------------------------------------------------------------------
	  
	  /**
	   * 
	   */
	  public void testReqF1()
	  {
		  setUpEscenario2();
		  
		  // Analiza el escenario en que debe encontrar algo.
		  try
		  {
			  List<ProductoLugarAlmacValue> lista = darExistenciasProductos(123456789,3,12,null, null, 22);
			  
			  System.out.println("Tamaño = " + lista.size());
			  for(int i = 0; i < lista.size(); i++)
			  {
				  System.out.println("Contenido = " + lista.get(i).cod_producto);
			  }
			  
			  if(lista.size() == 0)			  
				  fail("Debería encontrar el producto solicitado por parámetros");			  
			  else
			  {
				  if(lista.get(0).cod_producto != 123456789)
					  fail("El codigo del producto deberia ser 123456789");
			  }
		  }catch(Exception e)
		  {
			  fail("No debería lanzar una exception acá");
		  }
		  
		  // Analiza el esceario donde no debería encontrar nada.
		  try
		  {
			  List<ProductoLugarAlmacValue> lista = darExistenciasProductos(12345689,3,12,null, null, 22);
			  
			  if(lista.size() != 0)			  
				  fail("No debería encontrar el producto solicitado por parámetros");
		  }catch(Exception e)
		  {
			  fail("No debería lanzar una exception acá");
		  }
		  
		  undoEscenario2();
		  
	  }
	  
	  /**
	   * 
	   */
	  public void testReqF2()
	  {
		  setUpEscenario3();
		  
		  try {
			List<PedidoValue> lista = darPedidosUsuario("john@gmail.com", false, null, null, 1, 10000000, null, null);
			
			if(lista.size() == 0)			  
				  fail("Debería encontrar el producto solicitado por parámetros");			  
			  else
			  {
				  if(!lista.get(0).cod_usuario.equals("john@gmail.com"))
					  fail("El codigo del producto deberia ser 123456789");
			  }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("No debería lanzar esta exception");
			e.printStackTrace();
		}
		
		try {
			List<PedidoValue> lista = darPedidosUsuario("john1@gmail.com", false, null, null, 1, 10000000, null, null);
			
			if(lista.size() == 0)			  
				  fail("Debería encontrar el producto solicitado por parámetros");			  
			  else
			  {
				  if(!lista.get(0).cod_usuario.equals("john1@gmail.com"))
					  fail("El codigo del producto deberia ser 123456789");
			  }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("No debería lanzar esta exception");
			e.printStackTrace();
		}
		
		undoEscenario3();
		undoEscenario1();
	  }
	  
	  /**
	   * Desarrolla el test que prueba la disminución en las existencias de producto en un local.
	   */
	  public void testReqF3c()
	  {
		  setUpEscenario4();
		  
		  try
		  {
			  VentaValue venta = new VentaValue();
			  
			  venta.cantidad = 30000;
			  venta.cod_local = 123456789;
			  venta.cod_producto = 123456789;
			  venta.cod_presentacion = 12;
			  venta.numero = 4456789;
			  venta.fecha = new Date(2014, 5, 5);
			  venta.precio = 40000;
			  
			  venderProductoEnLocal(venta);
			  
			  String sql = "select * from venta where cod_local = 123456789";
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  System.out.println(sql);
			  
			  ResultSet resultSet = preparedStatement.executeQuery();
			  
			  while(resultSet.next())
			  {
				  String c = resultSet.getString("COD_LOCAL");
				  System.out.println(c);
				  if(c == null || c.equals(""))
					  fail("No encontró nada");
				  else if(!c.equals("123456789"))
					  fail("No encontró lo que debía encontrar");
			  }
			  
			  resultSet.close();
			 
		  }catch(Exception e)
		  {
			  fail("No debería lanzar una exception acá");
		  }
		  
		  String sql = "delete from venta where cod_local = 123456789";
		  
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();		

			  preparedStatement.close();
			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  undoEscenario4();
		  
	  }

	  /**
	   * Desarrolla el test que prueba el correcto 
	   */
	  public void testReqF3e()
	  {
		  setUpEscenario2();
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		  try {
			Date fecha = sdf.parse("14-02-2010");
//			  String sql = "SELECT * FROM PRODUCTO_LUGAR_ALMAC WHERE FECHA_EXPIRACION_LOTE<=?";
//			  PreparedStatement preparedStatement;
//			try {
//				preparedStatement = conexion.prepareStatement(sql);
//				 try
//				    {
//					 System.out.println(fecha.getTime());
//					  System.out.println("---" + new java.sql.Date(fecha.getTime()));
//				      preparedStatement.setDate(1,new java.sql.Date(fecha.getTime()));
//				      ResultSet resultado = preparedStatement.executeQuery();
//				      while(resultado.next())
//				      {
//				    	  System.out.println(resultado.getString(6));
//				      }
//					  System.out.println("----" + new java.sql.Date(fecha.getTime()));
//
//				    }
//				    catch (Exception excepcion)
//				    {
//				    	excepcion.printStackTrace();
//				    }
//				    finally
//				    {
//				      preparedStatement.close();
//				    }
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		undoEscenario2();	  
		  
	  }
	  
	  
	  public void testReqF4a()
	  {
		  setUpEscenario4();
		  
		  PedidoValue pedido = new PedidoValue();
		  
		  pedido.cod_usuario = "john@gmail.com";
		  pedido.fecha_creacion = new Date(2014,5,4);
		  pedido.fecha_entrega = null;
		  pedido.fecha_esperada_entrega = null;
		  pedido.ha_sido_cancelado = false;
		  pedido.ha_sido_entregado = false;
		  pedido.numero = 123456789;
		  
		  List<ProductoPedidoValue> lista = new ArrayList<ProductoPedidoValue>();
		  
		  ProductoPedidoValue nuevo = new ProductoPedidoValue();
		  
		  nuevo.cantidad_solicitada = 30000;
		  nuevo.cantidad_entregada = 0;
		  nuevo.cod_pedido = 123456789;
		  nuevo.cod_producto = 123456789;
		  nuevo.cod_presentacion = 12;
		  
		  lista.add(nuevo);
		  
		  try {
			registrarPedido(pedido, lista);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("No debería lanzar ninguna excepcion acá");
			e.printStackTrace();
			
		}
		
		try {
			  
			  String sql = "select * from pedido where COD_USUARIO = 'john@gmail.com'";
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  System.out.println(sql);
			  
			  ResultSet resultSet = preparedStatement.executeQuery();
			  
			  String codigoPedido = "";
			  
			  while(resultSet.next())
			  {
					  String c = resultSet.getString("COD_USUARIO");
					  System.out.println(c);
					  if(c == null || c.equals(""))
						  fail("No encontró nada");
					  else if(!c.equals("john@gmail.com"))
					  {
						  fail("No encontró lo que debía encontrar");
					  }
					  else
					  {
						  codigoPedido = resultSet.getString("NUMERO");
					  }
					  
				  
			  }
			  
			  sql = "select * from PRODUCTO_PEDIDO where COD_PRODUCTO = 123456789";	
			  
			  PreparedStatement preparedStatement1=conexion.prepareStatement(sql);
			  System.out.println(sql);
			  
			  ResultSet resultSet1 = preparedStatement1.executeQuery();
			  
			  while(resultSet1.next())
			  {
					  String c = resultSet1.getString("COD_PEDIDO");
					  System.out.println(c);
					  if(c == null || c.equals(""))
						  fail("No encontró nada");
					  else if(!c.equals(codigoPedido))
						  fail("No encontró lo que debía encontrar");
				  
			  }
			  
			  
			  preparedStatement.close();
			  preparedStatement1.close();
			  resultSet.close();
			  resultSet1.close();
			  
		  } catch (Exception e) {
			  // TODO Auto-generated catch block
			  System.out.println(e.getMessage());
			  fail("No debería lanzarse ninguna excepcion");
			  e.printStackTrace();
			  
		  }
		  
		  try {
			  
			  
			  String sql = "delete from PRODUCTO_PEDIDO where COD_PRODUCTO = 123456789";
			  
			  PreparedStatement preparedStatement1=conexion.prepareStatement(sql);
			  System.out.println(sql);
			  
			  preparedStatement1.executeUpdate();	  
			  
			  sql = "delete from pedido where COD_USUARIO = 'john@gmail.com'";
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  System.out.println(sql);
			  
			  preparedStatement.executeUpdate();	  
			  
			  

			  			  
			  preparedStatement.close();
			  preparedStatement1.close();
			  
		  } catch (Exception e) {
			  
			  System.out.println(e.getMessage());
			  // TODO Auto-generated catch block
			  fail("No debería lanzarse ninguna excepcion");
			  e.printStackTrace();
			  
		  }
		  
		  undoEscenario4();
	  }
	  
	  public void testReqF5()
	  {
		  setUpEscenario1();
		  setUpEscenario2();
		  
		  String sql = "insert into LICITACION values(123456789,123456789,12,50000,0,null,'20/04/08','14/05/08','20/06/08',null,null,'No','No')";
		  System.out.println(sql);
		  try {
			  
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  
			  preparedStatement.executeUpdate();

			  commitConexion();
			  
			  preparedStatement.close();
			  
			  List<LicitacionValue> lista = darLicitacionesCerradasSinAsignar(new Date(2008, 12, 12));
			  
			  if(lista.size() == 0)
				  fail("Debería retornar algo");
			  
			  int numero = 0;
			  for(int i = 0; i < lista.size(); i++)
			  {
				  LicitacionValue l = lista.get(i);
				  if(l.numero == 123456789)
					  numero = l.numero;
			  }
			  
			  sql = "insert into OFERTA_PROVEEDOR values(123456789,'john2@gmail.com',20000,20,50000,'20/06/2010',123456789,'20/06/2011')";
			  
			  System.out.println(sql);
			  PreparedStatement preparedStatement1=conexion.prepareStatement(sql);
			  
			  preparedStatement1.executeUpdate();

			  commitConexion();
			  
			  preparedStatement1.close();
			  
			  List<OfertaProveedorValue> lista1 = darOfertasProveedores(123456789);
			  
			  if(lista1.size() == 0)
				  fail("Debería retornar algo");
			  
			  System.out.println(lista.get(0).numero);
			  System.out.println(lista1.get(0).cod_licitacion);
			  
			  if(!(numero == (lista1.get(0).cod_licitacion)))
				  fail("Los números de licitación deberían coincidir");
			  
			  escogerProveedorParaLicitacion(123456789, "john2@gmail.com", lista1.get(0).fecha_esperada_entrega);
			  
			  sql = "select * from licitacion where numero = 123456789";
			  
			  System.out.println(sql);
			  PreparedStatement preparedStatement2=conexion.prepareStatement(sql);
			  
			  ResultSet rs = preparedStatement2.executeQuery();
			  
			  while(rs.next())
			  {
				  String a = rs.getString("NUMERO");
				  if(a.equals("") || a == null)
					  fail("No encontró nada");
				  else if(!a.equals("123456789"))
					  fail("No encontró el valor esperado");
			  }

			  rs.close();
			  preparedStatement2.close();
			  
			  sql = "delete from OFERTA_PROVEEDOR where COD_PROVEEDOR = 'john2@gmail.com'";
			  
			  System.out.println(sql);
			  PreparedStatement preparedStatement4=conexion.prepareStatement(sql);
			  
			  preparedStatement4.executeUpdate();

			  commitConexion();
			  
			  preparedStatement4.close();
			  
			  sql = "delete from licitacion where numero = 123456789";
			  
			  System.out.println(sql);
			  PreparedStatement preparedStatement3=conexion.prepareStatement(sql);
			  
			  preparedStatement3.executeUpdate();

			  commitConexion();
			  
			  preparedStatement3.close();
			  

			    
		  } catch (Exception e) {				
			  try {					
				  rollbackConexion();				
			  } catch (Exception e1) {				
				  fail("No debería lanzar una excepcion");
					
				  e1.printStackTrace();
				
			  }
			  
			  System.out.println(e.getMessage());
			  fail("No debería lanzar una excepcion");
				
			  e.printStackTrace();
		  }
		  
		  undoEscenario2();
		  undoEscenario1();
		  
	  }
	  
	  public void testReqF8()
	  {
		  setUpEscenario5();
		  
		  // El caso en que la bodega ya existe
		  
		  try {
			registrarBodega(123456789, 3, "Bodega de juana", 500000.0);
			
			fail("No debería poder agregar la bodega");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		// El caso en que la bodega no exista
		
		  
		  // Se observará que se cierre adecuadamente la bodega.
		  
		  try {
			cerrarBodega(123456789, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("No debería fallar al momento de cerrar la bodega");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "select * from bodega where id = 123456789";
		
		System.out.println(sql);
		
		try {
			PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				String c = resultSet.getString("ESTADO");
				System.out.println(c);
				if( c == null || c.equals(""))
					fail("No se obtuvo respuesta");
				else if(!c.equals("En ampliacion") && !c.equals("En mantenimiento"))
					fail("La bodega no se cerró correctamente");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		try {
			ponerBodegaEnFuncionamiento(123456789);
		} catch (Exception e) {
			
			fail("No debería lanzar una excepción");
			
			e.printStackTrace();
		}
		
		sql = "select * from bodega where id = 123456789";
		
		System.out.println(sql);
		
		try {
			PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				String c = resultSet.getString("ESTADO");
				if( c == null || c.equals(""))
					fail("No se obtuvo respuesta");
				else if(c.equals("En ampliacion") || c.equals("En mantenimiento"))
					fail("La bodega no se reabrió correctamente");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		undoEscenario5();
		  
	  }
	  
	  //---------------------------------------------------------
	  // Tests itetración 4
	  //---------------------------------------------------------	
	  
	  /**
	   * Test que observa el requerimiento funcional Visualizar movimientos de productos 1
	   */
	  public void testMovimientosProductos1()
	  {
		  System.out.println("--------------- Test Movimientos Productos ------------------");

		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		  try {
			  for(int i = 0; i < 50; i++)
			  {
				  Integer cantidad = null;
				  Integer idProducto = null;
				  Integer idPresentacion = null;
				  Integer idBodegaOrigen = 1;
				  Integer idLugarDestino = null;
				  Date fechaMinima = sdf.parse("14-02-2010");
				  Date fechaMaxima = sdf.parse("14-02-2012"); 
				  
				  System.out.println("- Caso " + (i+1) + ": Bodega Origen: " + (idBodegaOrigen+i) + ", Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima );
				  
				  List<MovimientoValue> lista = darMovmientos(cantidad, idProducto, idPresentacion, idBodegaOrigen+i, idLugarDestino, fechaMinima, fechaMaxima);
				  
				  System.out.println("--Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
				  System.out.println("Fecha: 2014");
				  System.out.println(" ");
				  
				  cantidad = null;
				  idProducto = null;
				  idPresentacion = null;
				  idBodegaOrigen = 1;
				  idLugarDestino = null;
				  fechaMinima = sdf.parse("14-02-2010");
				  fechaMaxima = sdf.parse("14-02-2014"); 
				  
				  System.out.println("- Caso " + (i+2) + ": Bodega Origen: " + (idBodegaOrigen+i) + ", Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima );
				  
				  lista = darMovmientos(cantidad, idProducto, idPresentacion, idBodegaOrigen+i, idLugarDestino, fechaMinima, fechaMaxima);
				  
				  System.out.println("--Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
				  System.out.println("Fecha: 2016");
				  System.out.println(" ");
				  
				  cantidad = null;
				  idProducto = null;
				  idPresentacion = null;
				  idBodegaOrigen = 1;
				  idLugarDestino = null;
				  fechaMinima = sdf.parse("14-02-2010");
				  fechaMaxima = sdf.parse("14-02-2016"); 
				  
				  System.out.println("- Caso " + (i+3) + ": Bodega Origen: " + (idBodegaOrigen+i) + ", Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima );
				  
				  lista = darMovmientos(cantidad, idProducto, idPresentacion, idBodegaOrigen + i, idLugarDestino, fechaMinima, fechaMaxima);
				  
				  System.out.println("--Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
			  
			  }
		  } catch (Exception e) {
			  
			  e.printStackTrace();
			  
		  }
	  }
	  
	  /**
	   * Test que observa el requerimiento funcional Visualizar movimientos de productos 1
	   */
	  public void testMovimientosProductos2()
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		  try {
			  
			  System.out.println("---------------------------- Test movimientos productos 2 ------------------");
			  
			  for(int i = 1; i < 3; i++)
			  {
				  
					  System.out.println("2012");
					  System.out.println("");
					  
					  Integer cantidad = null;
					  Integer idProducto = null;
					  Integer idPresentacion = null;
					  Integer idBodegaOrigen = i;
					  Integer idLugarDestino = null;
					  Date fechaMinima = sdf.parse("14-02-2010");
					  Date fechaMaxima = sdf.parse("14-02-2012"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  List<MovimientoValue> lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size());
					  
					  System.out.println(" ");
					  System.out.println("2014");
					  System.out.println("");
					  
					  cantidad = null;
					  idProducto = null;
					  idPresentacion = null;
					  idBodegaOrigen = i;
					  idLugarDestino = null;
					  fechaMinima = sdf.parse("14-02-2010");
					  fechaMaxima = sdf.parse("14-02-2014"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size()); 
					  
					  System.out.println(" ");
					  System.out.println("2016");
					  System.out.println("");
					  
					  cantidad = null;
					  idProducto = null;
					  idPresentacion = null;
					  idBodegaOrigen = i;
					  idLugarDestino = null;
					  fechaMinima = sdf.parse("14-02-2010");
					  fechaMaxima = sdf.parse("14-02-2016"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size());
					  
					  for(int j = 1; j < 6; j++)
					  {
					  System.out.println("");
					  System.out.println("2012");
					  System.out.println("");
					  
					  cantidad = null;
					  idProducto = j;
					  idPresentacion = null;
					  idBodegaOrigen = i;
					  idLugarDestino = null;
					  fechaMinima = sdf.parse("14-02-2010");
					  fechaMaxima = sdf.parse("14-02-2012"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size());
					  
					  System.out.println(" ");
					  System.out.println("2014");
					  System.out.println("");
					  
					  cantidad = null;
					  idProducto = j;
					  idPresentacion = null;
					  idBodegaOrigen = i;
					  idLugarDestino = null;
					  fechaMinima = sdf.parse("14-02-2010");
					  fechaMaxima = sdf.parse("14-02-2014"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size());
					  
					  System.out.println(" ");
					  System.out.println("2016");
					  System.out.println("");
					  
					  cantidad = null;
					  idProducto = j;
					  idPresentacion = null;
					  idBodegaOrigen = i;
					  idLugarDestino = null;
					  fechaMinima = sdf.parse("14-02-2010");
					  fechaMaxima = sdf.parse("14-02-2016"); 
					  
					  System.out.println("Datos: Bodega origen: " + idBodegaOrigen + ", idProducto: " + idProducto);
					  lista = darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
//					  System.out.println("Tamaño de la respuesta: " + lista.size());
				  }
				  
				  
				  
			  }
		  } catch (Exception e) {
			  
			  e.printStackTrace();
			  
		  }
	  }
	  
	  /**
	   * Test que observa el método visualizar bodegas v2
	   */
	  public void testVisualizarBodegas2()
	  {
		  System.out.println("--------------- Test Visualizar Bodegas 2 -----------------");
		  try {
			  
			  Double porcentaje = 1.0;
			  Integer codTipoProducto = 1;
			  System.out.println("- Caso 1: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  List<BodegaValue> lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 5.0;
			  codTipoProducto = 1;
			  System.out.println("- Caso 2: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 25.0;
			  codTipoProducto = 1;
			  System.out.println("- Caso 3: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 50.0;
			  codTipoProducto = 1;
			  System.out.println("- Caso 4: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 75.0;
			  codTipoProducto = 1;
			  System.out.println("- Caso 5: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());

			  System.out.println(" ");
			  System.out.println("--- Tipo de producto 2");
			  System.out.println(" ");
			  
			  porcentaje = 1.0;
			  codTipoProducto = 2;
			  System.out.println("- Caso 1: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 5.0;
			  codTipoProducto = 2;
			  System.out.println("- Caso 2: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 25.0;
			  codTipoProducto = 2;
			  System.out.println("- Caso 3: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 50.0;
			  codTipoProducto = 2;
			  System.out.println("- Caso 4: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 75.0;
			  codTipoProducto = 2;
			  System.out.println("- Caso 5: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  System.out.println("--- Tipo de producto 3");
			  System.out.println(" ");
			  
			  porcentaje = 1.0;
			  codTipoProducto = 3;
			  System.out.println("- Caso 1: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 5.0;
			  codTipoProducto = 3;
			  System.out.println("- Caso 2: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 25.0;
			  codTipoProducto = 3;
			  System.out.println("- Caso 3: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 50.0;
			  codTipoProducto = 3;
			  System.out.println("- Caso 4: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  
			  System.out.println(" ");
			  
			  porcentaje = 75.0;
			  codTipoProducto = 3;
			  System.out.println("- Caso 5: Porcentaje = " + porcentaje + ", Tipo Producto = " + codTipoProducto);
			  lista = darBodegasCapacidad(porcentaje, codTipoProducto);
			  System.out.println("Tamaño de la respuesta: " + lista.size());
			  

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	  }
	  	  
	  /**
	   * Test que observa el requerimiento funcional que pide los productos de los locales.
	   */
	  public void testVisualizarLocales()
	  { 
		  System.out.println("-------------------- Test Pedidos Vigentes Locales ----------------");
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		  try {
			  for(int i = 0; i < 25; i++)
			  {
				  System.out.println("");
				  System.out.println("2011");
				  System.out.println("");
				  Date fechaMinima = sdf.parse("14-02-2010");
				  Date fechaMaxima = sdf.parse("14-02-2011");
				  Integer idLocal = 4501;
				  System.out.println("- Caso " + (i+1) + ": Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima + ", Id Local: " + (idLocal + i));			  
				  List<PedidoValue> lista = darPedidosVigentesLocales(idLocal + i, fechaMinima, fechaMaxima);
				  System.out.println("Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
				  System.out.println("Fecha final: 2012");
				  System.out.println(" ");
				  
				  fechaMinima = sdf.parse("14-02-2010");
				  fechaMaxima = sdf.parse("14-02-2012");
				  idLocal = 4501;
				  System.out.println("- Caso " + (i+2) + ": Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima + ", Id Local: " + (idLocal + i));			  
				  lista = darPedidosVigentesLocales(idLocal + i, fechaMinima, fechaMaxima);
				  System.out.println("Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
				  System.out.println("Fecha final: 2014");
				  System.out.println(" ");
				  
				  fechaMinima = sdf.parse("14-02-2010");
				  fechaMaxima = sdf.parse("14-02-2014");
				  idLocal = 4501;
				  System.out.println("- Caso " + (i+2) + ": Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima + ", Id Local: " + (idLocal + i));			  
				  lista = darPedidosVigentesLocales(idLocal + i, fechaMinima, fechaMaxima);
				  System.out.println("Tamaño de la respuesta: " + lista.size());
				  
				  System.out.println(" ");
				  System.out.println("Fecha final: 2016");
				  System.out.println(" ");
				  
				  fechaMinima = sdf.parse("14-02-2010");
				  fechaMaxima = sdf.parse("14-02-2016");
				  idLocal = 4501;
				  System.out.println("- Caso " + (i+2) + ": Fecha Minima: " + fechaMinima + ", Fecha Máxima: " + fechaMaxima + ", Id Local: " + (idLocal + i));			  
				  lista = darPedidosVigentesLocales(idLocal + i, fechaMinima, fechaMaxima);
				  System.out.println("Tamaño de la respuesta: " + lista.size());
			  }
			  
			 
			  
			  
		  } catch (Exception e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			  
		  }
		  
	  }
	  
	  // RF1 - dar existencias de productos
	  public List<ProductoLugarAlmacValue> darExistenciasProductos(Integer idProducto, Integer idTipoProducto, Integer idPresentacion, Date fechaMinimaExpiracion, Date fechaMaximaExpiracion, Integer idLugarAlmacenamiento) throws Exception
	  {
	    List<ProductoLugarAlmacValue> existencias=new ArrayList<ProductoLugarAlmacValue>();
	    String sql="SELECT * FROM PRODUCTO_LUGAR_ALMAC pl INNER JOIN PRODUCTO pr ON pr.ID=pl.COD_PRODUCTO WHERE ";
	    if (idProducto!=null) sql+="pl.COD_PRODUCTO=? AND ";
	    if (idTipoProducto!=null) sql+="pr.COD_TIPO_PRODUCTO=? AND ";
	    if (idPresentacion!=null) sql+="pl.COD_PRESENTACION=? AND ";
	    if (fechaMinimaExpiracion!=null) sql+="pl.FECHA_EXPIRACION_LOTE>=? AND ";
	    if (fechaMaximaExpiracion!=null) sql+="pl.FECHA_EXPIRACION_LOTE<=? AND ";
	    if (idLugarAlmacenamiento!=null) sql+="pl.COD_LUGAR_ALMAC=? AND ";
	    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      int indiceParametro=1;
	      if (idProducto!=null) preparedStatement.setInt(indiceParametro++,idProducto);
	      if (idTipoProducto!=null) preparedStatement.setInt(indiceParametro++,idTipoProducto);
	      if (idPresentacion!=null) preparedStatement.setInt(indiceParametro++,idPresentacion);
	      if (fechaMinimaExpiracion!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinimaExpiracion.getTime()));
	      if (fechaMaximaExpiracion!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaximaExpiracion.getTime()));
	      if (idLugarAlmacenamiento!=null) preparedStatement.setInt(indiceParametro++,idLugarAlmacenamiento);
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
	          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
	          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
	          Integer cantidad=resultSet.getInt("CANTIDAD");
	          Integer numero_lote=resultSet.getInt("NUMERO_LOTE");
	          Date fecha_expiracion_lote=resultSet.getDate("FECHA_EXPIRACION_LOTE");
	          Double promocion_lote=resultSet.getDouble("PROMOCION_LOTE");
	          ProductoLugarAlmacValue existencia=new ProductoLugarAlmacValue(cod_lugar_almac,cod_producto,cod_presentacion,cantidad,numero_lote,fecha_expiracion_lote,promocion_lote);
	          existencias.add(existencia);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return existencias;
	  }

	  // RF2 - método privado para ver si el usuario es comprador mayorista o administrador de local
	  private boolean esCompradorMayoristaOAdministradorDeLocal(String direccionElectronicaUsuario) throws Exception
	  {
	    if (direccionElectronicaUsuario==null) throw new Exception("Debe dar la dirección de correo electrónico de un usuario.");
	    String sql="z";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setString(1,direccionElectronicaUsuario);
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        if (resultSet.next())
	        {
	          return true;
	        }
	        else
	        {
	          return false;
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	  }

	  // RF2 - dar pedidos de un usuario
	  public List<PedidoValue> darPedidosUsuario(String direccionElectronicaUsuario, Boolean haSidoEntregado, Date fechaMinimaCreacion, Date fechaMaximaCreacion, Integer costoMinimo, Integer costoMaximo, Integer idProducto, Integer idPresentacion) throws Exception
	  {
	    if (direccionElectronicaUsuario==null) throw new Exception("Debe dar la dirección de correo electrónico de un usuario.");
	    if (!esCompradorMayoristaOAdministradorDeLocal(direccionElectronicaUsuario)) throw new Exception("No existe un comprador mayorista o administrador de local con la dirección de correo electrónico dada.");
	    List<PedidoValue> pedidos=new ArrayList<PedidoValue>();
	    String sql="SELECT * FROM PEDIDO pd WHERE pd.COD_USUARIO=? AND ";
	    if (haSidoEntregado!=null) sql+="pd.HA_SIDO_ENTREGADO=? AND ";
	    if (fechaMinimaCreacion!=null) sql+="pd.FECHA_CREACION>=? AND ";
	    if (fechaMaximaCreacion!=null) sql+="pd.FECHA_CREACION<=? AND ";
	    if (costoMinimo!=null) sql+="pd.PRECIO_CABANDES>=? AND ";
	    if (costoMaximo!=null) sql+="pd.PRECIO_CABANDES<=? AND ";
	    if (idProducto!=null) sql+="? IN (SELECT pi.COD_PRODUCTO FROM PRODUCTO_PEDIDO pi WHERE pi.COD_PEDIDO=pd.NUMERO) AND ";
	    if (idPresentacion!=null) sql+="? IN (SELECT pi.COD_PRESENTACION FROM PRODUCTO_PEDIDO pi WHERE pi.COD_PEDIDO=pd.NUMERO) AND ";
	    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      int indiceParametro=1;
	      preparedStatement.setString(indiceParametro++,direccionElectronicaUsuario);
	      if (haSidoEntregado!=null) preparedStatement.setString(indiceParametro++,haSidoEntregado?"Si":"No");
	      if (fechaMinimaCreacion!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinimaCreacion.getTime()));
	      if (fechaMaximaCreacion!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaximaCreacion.getTime()));
	      if (costoMinimo!=null) preparedStatement.setInt(indiceParametro++,costoMinimo);
	      if (costoMaximo!=null) preparedStatement.setInt(indiceParametro++,costoMaximo);
	      if (idProducto!=null) preparedStatement.setInt(indiceParametro++,idProducto);
	      if (idPresentacion!=null) preparedStatement.setInt(indiceParametro++,idPresentacion);
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer numero=resultSet.getInt("NUMERO");
	          String cod_usuario=resultSet.getString("COD_USUARIO");
	          Date fecha_creacion=resultSet.getDate("FECHA_CREACION");
	          Date fecha_esperada_entrega=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
	          Date fecha_entrega=resultSet.getDate("FECHA_ENTREGA");
	          Integer precio_cabandes=resultSet.getInt("PRECIO_CABANDES");
	          if (resultSet.wasNull())
	          {
	            precio_cabandes=null;
	          }
	          Boolean ha_sido_cancelado=resultSet.getString("HA_SIDO_CANCELADO").equals("Si");
	          Boolean ha_sido_entregado=resultSet.getString("HA_SIDO_ENTREGADO").equals("Si");
	          PedidoValue pedido=new PedidoValue(numero,cod_usuario,fecha_creacion,fecha_esperada_entrega,fecha_entrega,precio_cabandes,ha_sido_cancelado,ha_sido_entregado);
	          pedidos.add(pedido);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return pedidos;
	  }

	  // RF2 - dar productos de un pedido
	  public List<ProductoPedidoValue> darProductosPedido(Integer numeroPedido) throws Exception
	  {
	    if (numeroPedido==null) throw new Exception("Debe dar el número de pedido.");
	    List<ProductoPedidoValue> productosPedido=new ArrayList<ProductoPedidoValue>();
	    String sql="SELECT * FROM PRODUCTO_PEDIDO pi WHERE pi.COD_PEDIDO=?";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setInt(1,numeroPedido);
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_pedido=resultSet.getInt("COD_PEDIDO");
	          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
	          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
	          Integer cantidad_solicitada=resultSet.getInt("CANTIDAD_SOLICITADA");
	          Integer cantidad_entregada=resultSet.getInt("CANTIDAD_ENTREGADA");
	          ProductoPedidoValue productoPedido=new ProductoPedidoValue(cod_pedido,cod_producto,cod_presentacion,cantidad_solicitada,cantidad_entregada);
	          productosPedido.add(productoPedido);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return productosPedido;
	  }

	  // RF3a - entregar producto por parte de un proveedor
	  public void entregarProductoPorParteDeProveedor(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEntrega) throws Exception
	  {
	    // Falta
	  }

	  // RF3b - enviar productos de una bodega a un local de ventas
	  public void enviarProductosDeBodegaALocal(Integer numeroPedido) throws Exception
	  {
	    // Falta
	  }

	  // RF3c - método privado para disminuir las existencias de un producto en una presentación dentro de un local
	  private void disminuirExistenciasProductoEnLocal(int idProducto, int idPresentacion, int idLocal, int cantidad) throws Exception
	  {
	    int cantidadQueFaltaPorDar=cantidad;
	    List<ProductoLugarAlmacValue> existencias=darExistenciasProductos(idProducto,null,idPresentacion,null,null,idLocal);
	    for (ProductoLugarAlmacValue existencia:existencias)
	    {
	      if (cantidadQueFaltaPorDar>0)
	      {
	        if (existencia.cantidad>cantidadQueFaltaPorDar)
	        {
	          // hay que reducir la cantidad del producto en la presentación dentro del local
	          String sql="UPDATE PRODUCTO_LUGAR_ALMAC SET CANTIDAD=? WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	          PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	          try
	          {
	            preparedStatement.setInt(1,existencia.cantidad-cantidadQueFaltaPorDar);
	            preparedStatement.setInt(2,existencia.cod_lugar_almac);
	            preparedStatement.setInt(3,existencia.cod_producto);
	            preparedStatement.setInt(4,existencia.cod_presentacion);
	            preparedStatement.setInt(5,existencia.numero_lote);
	            preparedStatement.setDate(6,new java.sql.Date(existencia.fecha_expiracion_lote.getTime()));
	            preparedStatement.executeUpdate();
	            cantidadQueFaltaPorDar=0;
	          }
	          finally
	          {
	            preparedStatement.close();
	          }
	        }
	        else
	        {
	          // hay que eliminar la existencia del producto en la presentación dentro del local
	          String sql="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	          PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	          try
	          {
	            preparedStatement.setInt(1,existencia.cod_lugar_almac);
	            preparedStatement.setInt(2,existencia.cod_producto);
	            preparedStatement.setInt(3,existencia.cod_presentacion);
	            preparedStatement.setInt(4,existencia.numero_lote);
	            preparedStatement.setDate(5,new java.sql.Date(existencia.fecha_expiracion_lote.getTime()));
	            preparedStatement.executeUpdate();
	            cantidadQueFaltaPorDar=cantidadQueFaltaPorDar-existencia.cantidad;
	          }
	          finally
	          {
	            preparedStatement.close();
	          }
	        }
	      }
	    }
	    if (cantidadQueFaltaPorDar>0)
	    {
	      throw new Exception("En el local no hay disponibles "+cantidad+" de esos productos en esa presentación.");
	    }
	    // El commit lo hace quien llama el método
	  }

	  // RF3c - vender un producto en un local de ventas
	  public void venderProductoEnLocal(VentaValue venta) throws Exception
	  {
	    if (venta.numero==null) throw new Exception("Debe dar el número de venta.");
	    if (venta.cod_local==null) throw new Exception("Debe dar el local de venta.");
	    if (venta.cod_producto==null) throw new Exception("Debe dar el producto vendido.");
	    if (venta.cod_presentacion==null) throw new Exception("Debe dar la presentación del producto vendido.");
	    if (venta.cantidad==null) throw new Exception("Debe dar la cantidad vendida.");
	    if (venta.fecha==null) throw new Exception("Debe dar la fecha de venta.");
	    if (venta.precio==null) throw new Exception("Debe dar el precio de venta.");
	    String sql="INSERT INTO VENTA VALUES(?,?,?,?,?,?,?)";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setInt(1,venta.numero);
	      preparedStatement.setInt(2,venta.cod_local);
	      preparedStatement.setInt(3,venta.cod_producto);
	      preparedStatement.setInt(4,venta.cod_presentacion);
	      preparedStatement.setInt(5,venta.cantidad);
	      preparedStatement.setDate(6,new java.sql.Date(venta.fecha.getTime()));
	      preparedStatement.setInt(7,venta.precio);
	      preparedStatement.executeUpdate();
	      disminuirExistenciasProductoEnLocal(venta.cod_producto,venta.cod_presentacion,venta.cod_local,venta.cantidad);
	      commitConexion();
	    }
	    catch (Exception excepcion)
	    {
	      rollbackConexion();
	      throw excepcion;
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	  }

	  // RF3d - vender productos de una bodega a un comprador mayorista
	  public void venderProductosBodegaCompradorMayorista(Integer numeroPedido) throws Exception
	  {
	    // Falta
	  }

	  // RF3e - eliminar productos que cumplieron con la fecha de expiración
	  public void eliminarProductosExpirados(Date fechaActualDeCorte) throws Exception
	  {
	    if (fechaActualDeCorte==null) throw new Exception("Debe dar una fecha actual de corte.");
	    String sql="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE FECHA_EXPIRACION_LOTE<=?";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setDate(1,new java.sql.Date(fechaActualDeCorte.getTime()));
	      preparedStatement.executeUpdate();
	      commitConexion();
	    }
	    catch (Exception excepcion)
	    {
	      rollbackConexion();
	      throw excepcion;
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	  }

	  // RF4a - registrar pedido
	  public void registrarPedido(PedidoValue pedido, List<ProductoPedidoValue> productosPedido) throws Exception
	  {
	    if (pedido.numero==null) throw new Exception("Debe dar el número de pedido.");
	    if (pedido.cod_usuario==null) throw new Exception("Debe dar el correo electrónico del usuario.");
	    if (pedido.fecha_creacion==null) throw new Exception("Debe dar la fecha de creación del pedido.");
	    if (productosPedido.isEmpty()) throw new Exception("Debe dar por lo menos un producto.");
	    if (!esCompradorMayoristaOAdministradorDeLocal(pedido.cod_usuario)) throw new Exception("No existe un comprador mayorista o administrador de local con la dirección de correo electrónico dada.");
	    String sql="INSERT INTO PEDIDO VALUES(?,?,?,NULL,NULL,NULL,'No','No')";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setInt(1,pedido.numero);
	      preparedStatement.setString(2,pedido.cod_usuario);
	      preparedStatement.setDate(3,new java.sql.Date(pedido.fecha_creacion.getTime()));
	      preparedStatement.executeUpdate();
	      for (ProductoPedidoValue productoPedido:productosPedido)
	      {
	        if (productoPedido.cod_producto==null) throw new Exception("Debe dar todos los productos.");
	        if (productoPedido.cod_presentacion==null) throw new Exception("Debe dar todas las presentaciones.");
	        if (productoPedido.cantidad_solicitada==null) throw new Exception("Debe dar todas las cantidades solicitadas.");
	        String sql2="INSERT INTO PRODUCTO_PEDIDO VALUES(?,?,?,?,0)";
	        PreparedStatement preparedStatement2=conexion.prepareStatement(sql2);
	        try
	        {
	          preparedStatement2.setInt(1,pedido.numero);
	          preparedStatement2.setInt(2,productoPedido.cod_producto);
	          preparedStatement2.setInt(3,productoPedido.cod_presentacion);
	          preparedStatement2.setInt(4,productoPedido.cantidad_solicitada);
	          preparedStatement2.executeUpdate();
	        }
	        finally
	        {
	          preparedStatement2.close();
	        }
	      }
	      commitConexion();
	    }
	    catch (Exception excepcion)
	    {
	      rollbackConexion();
	      throw excepcion;
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	  }

	  // RF4b - satisfacer pedido
	  public void satisfacerPedido(Integer numeroPedido) throws Exception
	  {
	    // Falta
	  }

	  // RF5 - dar licitaciones cerradas sin asignar
	  public List<LicitacionValue> darLicitacionesCerradasSinAsignar(Date fechaActualDeCorte) throws Exception
	  {
	    if (fechaActualDeCorte==null) throw new Exception("Debe dar una fecha actual de corte.");
	    List<LicitacionValue> licitaciones=new ArrayList<LicitacionValue>();
	    String sql="SELECT * FROM LICITACION li WHERE li.HA_SIDO_ELEGIDA=? AND HA_SIDO_SATISFECHA=? AND li.FECHA_MAXIMA_PROPUESTAS<=?";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setString(1,"No");
	      preparedStatement.setString(2,"No");
	      preparedStatement.setDate(3,new java.sql.Date(fechaActualDeCorte.getTime()));
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer numero=resultSet.getInt("NUMERO");
	          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
	          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
	          Integer cantidad_solicitada=resultSet.getInt("CANTIDAD_SOLICITADA");
	          Integer cantidad_recibida=resultSet.getInt("CANTIDAD_RECIBIDA");
	          String cod_proveedor_elegido=resultSet.getString("COD_PROVEEDOR_ELEGIDO");
	          Date fecha_creacion=resultSet.getDate("FECHA_CREACION");
	          Date fecha_maxima_propuestas=resultSet.getTimestamp("FECHA_MAXIMA_PROPUESTAS");
	          Date fecha_esperada_entrega=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
	          Date fecha_entrega=resultSet.getDate("FECHA_ENTREGA");
	          Integer calificacion_entrega=resultSet.getInt("CALIFICACION_ENTREGA");
	          if (resultSet.wasNull())
	          {
	            calificacion_entrega=null;
	          }
	          Boolean ha_sido_elegida=resultSet.getString("HA_SIDO_ELEGIDA").equals("Si");
	          Boolean ha_sido_satisfecha=resultSet.getString("HA_SIDO_SATISFECHA").equals("Si");
	          LicitacionValue licitacion=new LicitacionValue(numero,cod_producto,cod_presentacion,cantidad_solicitada,cantidad_recibida,cod_proveedor_elegido,fecha_creacion,fecha_maxima_propuestas,fecha_esperada_entrega,fecha_entrega,calificacion_entrega,ha_sido_elegida,ha_sido_satisfecha);
	          licitaciones.add(licitacion);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return licitaciones;
	  }

	  // RF5 - dar ofertas de los proveedores para una licitación
	  public List<OfertaProveedorValue> darOfertasProveedores(Integer numeroLicitacion) throws Exception
	  {
	    if (numeroLicitacion==null) throw new Exception("Debe dar un número de licitacion.");
	    List<OfertaProveedorValue> ofertasProveedores=new ArrayList<OfertaProveedorValue>();
	    String sql="SELECT * FROM OFERTA_PROVEEDOR op WHERE op.COD_LICITACION=?";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setInt(1,numeroLicitacion);
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_licitacion=resultSet.getInt("COD_LICITACION");
	          String cod_proveedor=resultSet.getString("COD_PROVEEDOR");
	          Integer precio_total_ofrecido=resultSet.getInt("PRECIO_TOTAL_OFRECIDO");
	          Integer precio_unitario=resultSet.getInt("PRECIO_UNITARIO");
	          Integer cantidad_piensa_proveer=resultSet.getInt("CANTIDAD_PIENSA_PROVEER");
	          Date fecha_esperada_entrega=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
	          Integer numero_lote=resultSet.getInt("NUMERO_LOTE");
	          Date fecha_expiracion_lote=resultSet.getDate("FECHA_EXPIRACION_LOTE");
	          OfertaProveedorValue ofertaProveedor=new OfertaProveedorValue(cod_licitacion,cod_proveedor,precio_total_ofrecido,precio_unitario,cantidad_piensa_proveer,fecha_esperada_entrega,numero_lote,fecha_expiracion_lote);
	          ofertasProveedores.add(ofertaProveedor);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return ofertasProveedores;
	  }

	  // RF5 - escoger proveedor para una licitación
	  public void escogerProveedorParaLicitacion(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEsperadaEntrega) throws Exception
	  {
	    if (numeroLicitacion==null) throw new Exception("Debe dar un número de licitacion.");
	    if (direccionElectronicaProveedor==null) throw new Exception("Debe dar la dirección electrónica del proveedor.");
	    if (fechaEsperadaEntrega==null) throw new Exception("Debe dar una fecha esperada de entrega.");
	    String sql="UPDATE LICITACION SET COD_PROVEEDOR_ELEGIDO=?,FECHA_ESPERADA_ENTREGA=?,HA_SIDO_ELEGIDA=?,HA_SIDO_SATISFECHA=? WHERE NUMERO=?";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setString(1,direccionElectronicaProveedor);
	      preparedStatement.setDate(2,new java.sql.Date(fechaEsperadaEntrega.getTime()));
	      preparedStatement.setString(3,"Si");
	      preparedStatement.setString(4,"No");
	      preparedStatement.setInt(5,numeroLicitacion);
	      preparedStatement.executeUpdate();
	      commitConexion();
	    }
	    catch (Exception excepcion)
	    {
	      rollbackConexion();
	      throw excepcion;
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	  }

	  // RF6 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
	  public List<ProductoMayorMovimientoValue> darProductosMayorMovimiento(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento) throws Exception
	  {
	    if (fechaMinimaMovimiento==null) fechaMinimaMovimiento=new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
	    if (fechaMaximaMovimiento==null) fechaMaximaMovimiento=new SimpleDateFormat("yyyy-MM-dd").parse("2100-01-01");
	    List<ProductoMayorMovimientoValue> productosMayorMovimiento=new ArrayList<ProductoMayorMovimientoValue>();
	    String sql="";
	    sql+=" WITH";
	    sql+="   VECES_PEDIDO AS (";
	    sql+="     SELECT pi.COD_PRODUCTO, pi.COD_PRESENTACION, COUNT(*) VECES";
	    sql+="     FROM PRODUCTO_PEDIDO pi";
	    sql+="     INNER JOIN PEDIDO pd ON pd.NUMERO=pi.COD_PEDIDO";
	    sql+="     WHERE pd.FECHA_CREACION>=? AND pd.FECHA_CREACION<=?";
	    sql+="     GROUP BY pi.COD_PRODUCTO,pi.COD_PRESENTACION";
	    sql+="   ),";
	    sql+="   VECES_VENDIDO AS (";
	    sql+="     SELECT ve.COD_PRODUCTO, ve.COD_PRESENTACION, COUNT(*) VECES";
	    sql+="     FROM VENTA ve";
	    sql+="     WHERE ve.FECHA>=? AND ve.FECHA<=?";
	    sql+="     GROUP BY ve.COD_PRODUCTO,ve.COD_PRESENTACION";
	    sql+="   )";
	    sql+=" SELECT pr.ID COD_PRODUCTO, pv.COD_PRESENTACION COD_PRESENTACION, pr.PESO PROMEDIO_PESO, pv.PRECIO_AL_DETAL COSTO_PROMEDIO, vp.VECES VECES_HA_SIDO_PEDIDO, vv.VECES VECES_HA_SIDO_VENDIDO";
	    sql+=" FROM PRODUCTO_PRECIO_VENTA pv";
	    sql+=" INNER JOIN PRODUCTO pr ON pr.ID=pv.COD_PRODUCTO";
	    sql+=" LEFT JOIN VECES_PEDIDO vp ON vp.COD_PRODUCTO=pv.COD_PRODUCTO AND vp.COD_PRESENTACION=pv.COD_PRESENTACION";
	    sql+=" LEFT JOIN VECES_VENDIDO vv ON vv.COD_PRODUCTO=pv.COD_PRODUCTO AND vv.COD_PRESENTACION=pv.COD_PRESENTACION";
	    sql+=" WHERE (vp.VECES=(SELECT MAX(vp2.VECES) FROM VECES_PEDIDO vp2) OR vv.VECES=(SELECT MAX(vv2.VECES) FROM VECES_VENDIDO vv2))";
	    sql+=" ORDER BY pr.COD_TIPO_PRODUCTO ASC, pr.NOMBRE ASC";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      preparedStatement.setDate(1,new java.sql.Date(fechaMinimaMovimiento.getTime()));
	      preparedStatement.setDate(2,new java.sql.Date(fechaMaximaMovimiento.getTime()));
	      preparedStatement.setDate(3,new java.sql.Date(fechaMinimaMovimiento.getTime()));
	      preparedStatement.setDate(4,new java.sql.Date(fechaMaximaMovimiento.getTime()));
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
	          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
	          Double promedio_peso=resultSet.getDouble("PROMEDIO_PESO");
	          Double costo_promedio=resultSet.getDouble("COSTO_PROMEDIO");
	          Integer veces_ha_sido_pedido=resultSet.getInt("VECES_HA_SIDO_PEDIDO");
	          if (resultSet.wasNull())
	          {
	            veces_ha_sido_pedido=0; // si era null, dejarlo en cero
	          }
	          Integer veces_ha_sido_vendido=resultSet.getInt("VECES_HA_SIDO_VENDIDO");
	          if (resultSet.wasNull())
	          {
	            veces_ha_sido_vendido=0; // si era null, dejarlo en cero
	          }
	          ProductoMayorMovimientoValue productoMayorMovimiento=new ProductoMayorMovimientoValue(cod_producto,cod_presentacion,promedio_peso,costo_promedio,veces_ha_sido_pedido,veces_ha_sido_vendido);
	          productosMayorMovimiento.add(productoMayorMovimiento);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return productosMayorMovimiento;
	  }

	  // RF7 - dar el local de ventas con el mayor número de ventas
	  public List<LocalMayorNumeroVentasValue> darLocalesMayorNumeroVentas(Integer idTipoProducto, Integer idProducto) throws Exception
	  {
	    if (idTipoProducto!=null&&idProducto!=null) throw new Exception("Debe escoger un tipo de producto o un producto, pero no ambos.");
	    List<LocalMayorNumeroVentasValue> localesMayorNumeroVentas=new ArrayList<LocalMayorNumeroVentasValue>();
	    String sql="";
	    sql+=" WITH";
	    sql+="   VECES_VENDIO AS (";
	    sql+="     SELECT ve.COD_LOCAL, COUNT(*) NUMERO_VENTAS";
	    sql+="     FROM VENTA ve";
	    sql+="     INNER JOIN PRODUCTO pr ON pr.ID=ve.COD_PRODUCTO";
	    if (idTipoProducto!=null)
	    {
	      sql+="     WHERE pr.COD_TIPO_PRODUCTO=?";
	    }
	    else if (idProducto!=null)
	    {
	      sql+="     WHERE pr.ID=?";
	    }
	    sql+="     GROUP BY ve.COD_LOCAL";
	    sql+="   )";
	    sql+=" SELECT vv.COD_LOCAL, vv.NUMERO_VENTAS";
	    sql+=" FROM VECES_VENDIO vv";
	    sql+=" WHERE vv.NUMERO_VENTAS=(SELECT MAX(vv2.NUMERO_VENTAS) FROM VECES_VENDIO vv2)";
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
	    try
	    {
	      if (idTipoProducto!=null)
	      {
	        preparedStatement.setInt(1,idTipoProducto);
	      }
	      else if (idProducto!=null)
	      {
	        preparedStatement.setInt(1,idProducto);
	      }
	      ResultSet resultSet=preparedStatement.executeQuery();
	      try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_local=resultSet.getInt("COD_LOCAL");
	          Integer numero_ventas=resultSet.getInt("NUMERO_VENTAS");
	          if (resultSet.wasNull())
	          {
	            numero_ventas=0; // si era null, dejarlo en cero
	          }
	          LocalMayorNumeroVentasValue localMayorNumeroVentas=new LocalMayorNumeroVentasValue(cod_local,numero_ventas);
	          localesMayorNumeroVentas.add(localMayorNumeroVentas);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return localesMayorNumeroVentas;
	  }
	  //RF9 - Registrar Bodega
	  public void registrarBodega(Integer textoIdPBodega,Integer textoTipoProducto, String textoNombre, Double textoCapacidad)throws Exception
	  {
		  LugarAlmacenamientoValue lugarAlm=new LugarAlmacenamientoValue(textoIdPBodega,textoTipoProducto,textoNombre,textoCapacidad);
		  BodegaValue bodega=new BodegaValue(textoIdPBodega,"En funcionamiento");
		  if (lugarAlm.nombre==null) throw new Exception("Debe dar el nombre de la bodega.");
		    if (lugarAlm.id==null) throw new Exception("Debe dar el id de la bodega.");
		    if (lugarAlm.cod_tipo_producto==null) throw new Exception("Debe dar el codigo del tipo de producto.");
		    if (lugarAlm.capacidad==null) throw new Exception("Debe dar la capacidad de la bodega.");
		    String sql="INSERT INTO LUGAR_ALMACENAMIENTO VALUES(?,?,?,?)";
		    String sql1="INSERT INTO BODEGA VALUES(?,?)";
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    PreparedStatement preparedStatement1=conexion.prepareStatement(sql1);
		    try
		    {
		      preparedStatement.setInt(1,lugarAlm.id);
		      preparedStatement.setInt(2,lugarAlm.cod_tipo_producto);
		      preparedStatement.setString(3,lugarAlm.nombre);
		      preparedStatement.setDouble(4,lugarAlm.capacidad);
		      preparedStatement.executeUpdate();
		      
		     preparedStatement1.setInt(1,lugarAlm.id);
		     preparedStatement1.setString(2,"En funcionamiento");
		     preparedStatement1.executeUpdate();
		     quitarProductosABodegas(lugarAlm.id);
		      commitConexion();
		      
		      
			} 
		    catch (Exception excepcion) 
			{
				rollbackConexion();
				throw excepcion;
			} finally {
				preparedStatement.close();
				//preparedStatement1.close();
			}
		}
	  
	  private void cerrarBodegaP(Integer id, Integer textoMotivoCierre) throws Exception
	  {
		  
		  if (id==null) throw new Exception("Debe dar el id de la bodega.");
		  if (textoMotivoCierre==null) throw new Exception("Debe seleccionar el motivo del cierre.");
		  
		  String sql13="SELECT * FROM LUGAR_ALMACENAMIENTO WHERE ID=?";
		  PreparedStatement preparedStatement13=conexion.prepareStatement(sql13);
		  preparedStatement13.setInt(1, id);
		  ResultSet resultSet13=preparedStatement13.executeQuery();
		  resultSet13.next();
		  Integer cod_tipo_producto=resultSet13.getInt("COD_TIPO_PRODUCTO");
		  resultSet13.close();
		  preparedStatement13.close();
		  
		  String sql="WITH TAB AS(";
		  sql+=" SELECT B.COD_LUGAR_ALMAC, B.COD_PRODUCTO, B.COD_PRESENTACION, B.CANTIDAD_TOTAL * A.CAPACIDAD AS OCUPACION";
		  sql+=" FROM (select COD_LUGAR_ALMAC,COD_PRODUCTO, COD_PRESENTACION, sum(CANTIDAD) AS CANTIDAD_TOTAL";
		  sql+=" from PRODUCTO_LUGAR_ALMAC GROUP BY  COD_LUGAR_ALMAC, COD_PRODUCTO, COD_PRESENTACION)B";
		  sql+=" INNER JOIN PRESENTACION A";
		  sql+=" ON A.ID = B.COD_PRESENTACION),";
		  sql+=" TABL AS(";
		  sql+=" SELECT TAB.COD_LUGAR_ALMAC, SUM(TAB.OCUPACION) AS TOTAL_ALMACENADO";
		  sql+=" FROM TAB";
		  sql+=" GROUP BY TAB.COD_LUGAR_ALMAC ORDER BY TAB.COD_LUGAR_ALMAC)";
		  sql+=" SELECT ti.COD_LUGAR_ALMAC, ti.TOTAL_ALMACENADO, li.CAPACIDAD FROM TABL ti INNER JOIN LUGAR_ALMACENAMIENTO li ON ti.COD_LUGAR_ALMAC=li.ID INNER JOIN BODEGA bi ON bi.ID=li.ID WHERE bi.ESTADO='En funcionamiento' AND li.COD_TIPO_PRODUCTO= "+cod_tipo_producto;
		  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		  
		  ResultSet resultSet=preparedStatement.executeQuery();
		  
		  String sql1="SELECT * FROM PRODUCTO_LUGAR_ALMAC INNER JOIN PRESENTACION ON PRESENTACION.ID=PRODUCTO_LUGAR_ALMAC.COD_PRESENTACION WHERE COD_LUGAR_ALMAC=?";
		  PreparedStatement preparedStatement1=conexion.prepareStatement(sql1);
		  
		  preparedStatement1.setInt(1,id);
		  ResultSet resultSet1=preparedStatement1.executeQuery();
		  
		  List<Integer>listaBodegasSuplentes=new ArrayList<Integer>();
		  while(resultSet.next())
		  {
			  System.out.println("000000000000000000000000000000099999999999999900000000000");
			  Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
		      Double total_almacenado=resultSet.getDouble("TOTAL_ALMACENADO");
		      Double capacidad_total=resultSet.getDouble("CAPACIDAD");
		      Double capacidad_disponible=capacidad_total-total_almacenado;
		      
		      if(cod_lugar_almac!=id)
		      {
		    	  while(resultSet1.next()&&capacidad_disponible>=0)
		    	  {
		    		  	Integer cod_lugar_almac_producto=resultSet1.getInt("COD_LUGAR_ALMAC");
		    	  		Integer cod_producto=resultSet1.getInt("COD_PRODUCTO");
		    	  		Integer cod_presentacion=resultSet1.getInt("COD_PRESENTACION");
		    	  		Integer cantidad=resultSet1.getInt("CANTIDAD");
		    	  		Integer numero_lote=resultSet1.getInt("NUMERO_LOTE");
		    	  		Date fecha_expiracion_lote=resultSet1.getDate("FECHA_EXPIRACION_LOTE");
		    	  		Double promocion_lote=resultSet1.getDouble("PROMOCION_LOTE");
		    	  		
		    	  		////////////////
		    	  		Double capacidad=resultSet1.getDouble("CAPACIDAD");
		    	  		Double peso_producto=capacidad*cantidad;
		    	  		if(capacidad_disponible-peso_producto>=0)
		    	  		{
		    	  			capacidad_disponible-=peso_producto;
		    	  			String sql3="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND CANTIDAD=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
		      	  			PreparedStatement preparedStatement3=conexion.prepareStatement(sql3);
		      	  			preparedStatement3.setInt(1, cod_lugar_almac_producto);
		      	  			preparedStatement3.setInt(2, cod_producto);
		      	  			preparedStatement3.setInt(3, cod_presentacion);
		      	  			preparedStatement3.setInt(4, cantidad);
		      	  			preparedStatement3.setInt(5, numero_lote);
		      	  			preparedStatement3.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
		      	  			
		      	  			preparedStatement3.executeUpdate();
		    	  			preparedStatement3.close();
		    	  			
		    	  			try {
		      	  				String sql4="INSERT INTO PRODUCTO_LUGAR_ALMAC VALUES(?,?,?,?,?,?,?)";
		      	  				PreparedStatement preparedStatement4=conexion.prepareStatement(sql4);
		      	  				preparedStatement4.setInt(1, cod_lugar_almac);
		      	  				preparedStatement4.setInt(2, cod_producto);
		      	  				preparedStatement4.setInt(3, cod_presentacion);
		      	  				preparedStatement4.setInt(4, 0);
		      	  				preparedStatement4.setInt(5, numero_lote);
		      	  				preparedStatement4.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
		      	  				preparedStatement4.setDouble(7,promocion_lote);
		      	  				preparedStatement4.executeUpdate();
		      		  			preparedStatement4.close();
		      	  			}
		      	  			catch (SQLException ex) {
		      	  			}
		    	  			String sql4="UPDATE PRODUCTO_LUGAR_ALMAC SET CANTIDAD=CANTIDAD+? WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";

		     				PreparedStatement preparedStatement5=conexion.prepareStatement(sql4);
		     				preparedStatement5.setInt(1, cantidad);
		     				preparedStatement5.setInt(2, cod_lugar_almac);
		     				preparedStatement5.setInt(3, cod_producto);
		     				preparedStatement5.setInt(4, cod_presentacion);
		     				preparedStatement5.setInt(5, numero_lote);
		     				preparedStatement5.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
		     				preparedStatement5.executeUpdate();
		    	  			preparedStatement5.close();
		    	  			listaBodegasSuplentes.add(cod_lugar_almac);
		    	  		}

		    	  }
		      }
		  }
		  if(resultSet1.next())
		  {
			  throw new Exception("No hay suficiente capacidad en las demas bodegas para almacenar los productos de la bodega que se va a cerrar, por lo tanto no se puede cerrar");
		  }
		  resultSet1.close();
		  resultSet.close();
		  preparedStatement1.close();
		  preparedStatement.close();
		  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		  String sql6="SELECT * FROM BODEGA_PEDIDO ni INNER JOIN PEDIDO ci ON ni.COD_PEDIDO=ci.NUMERO WHERE ci.HA_SIDO_ENTREGADO='No' AND ci.HA_SIDO_CANCELADO='No' AND ni.COD_BODEGA=?";
		  PreparedStatement preparedStatement6=conexion.prepareStatement(sql6);
		  preparedStatement6.setInt(1, id);
		  ResultSet resultSet6=preparedStatement6.executeQuery();
		  
		  while(resultSet6.next())
		  {
			  try{
			  String cod_usuario=resultSet6.getString("COD_USUARIO");
			  Integer cod_pedido_p=resultSet6.getInt("COD_PEDIDO");
			  String sql10="INSERT INTO NOTIFICACION_BODEGA_PED VALUES(?,?,?,?)";
			  PreparedStatement preparedStatement10=conexion.prepareStatement(sql10);
			  preparedStatement10.setInt(1, id);
			  preparedStatement10.setInt(2, cod_pedido_p);
			  preparedStatement10.setString(3, "Cierre Bodega");
			  preparedStatement10.setString(4, "Una de las bodegas de las cuales dependía su pedido ha sido cerrada por cuestiones de mantenimiento y/o remodelacion");
			  preparedStatement10.executeUpdate();
			  preparedStatement10.close();
			  }
			  catch(Exception t){
				  
			  }
			  //MANDAR EL CORREO AL USUARIO
			  
		  }
		  resultSet6.close();
		  preparedStatement6.close();
		  String sql7="SELECT * FROM BODEGA_PEDIDO WHERE COD_BODEGA=?";
		  PreparedStatement preparedStatement7=conexion.prepareStatement(sql7);
		  preparedStatement7.setInt(1, id);
		  ResultSet resultSet7=preparedStatement7.executeQuery();
		  
		  while(resultSet7.next())
		  {
			  for(int i=0;i<listaBodegasSuplentes.size();i++)
			  {
				  try
				  {
					  Integer cod_bod=listaBodegasSuplentes.get(i);
					  String sql8="INSERT INTO BODEGA_PEDIDO VALUES(?,?)";
					  PreparedStatement preparedStatement8=conexion.prepareStatement(sql8);
					  preparedStatement8.setInt(1, cod_bod);
					  preparedStatement8.setInt(2, resultSet7.getInt("COD_PEDIDO"));
					  preparedStatement8.executeUpdate();
					  preparedStatement8.close();
				  }
				  catch(Exception e)
				  {

				  }
			  }
		  }
		  resultSet7.close();
		  preparedStatement7.close();
		  System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

		 /* String sql12="DELETE FROM NOTIFICACION_BODEGA_PED WHERE COD_BODEGA=?";
		  PreparedStatement preparedStatement12=conexion.prepareStatement(sql12);
		  preparedStatement12.setInt(1, id);
		  preparedStatement12.executeUpdate();
		  preparedStatement12.close();*/
		  
		  String sql9="DELETE FROM BODEGA_PEDIDO WHERE COD_BODEGA=?";
		  PreparedStatement preparedStatement9=conexion.prepareStatement(sql9);
		  preparedStatement9.setInt(1, id);
		  preparedStatement9.executeUpdate();
		  preparedStatement9.close();
		  System.out.println("cccccccccccccccccccccccccccccccccccccc");
		  String sql11="UPDATE BODEGA SET ESTADO=? WHERE ID=?";
		  PreparedStatement preparedStatement11=conexion.prepareStatement(sql11);
		  System.out.println(textoMotivoCierre);
		 String aaa="";
		  if(textoMotivoCierre==1)
		  {
			  aaa="En ampliacion";
		  }
		  else
		  {
			  aaa="En mantenimiento";
		  }
		  preparedStatement11.setString(1, aaa);
		  preparedStatement11.setInt(2, id);
		  preparedStatement11.executeUpdate();
		  preparedStatement11.close();
		  System.out.println("dddddddddddddddddddddddddddddddddddddddddddd");

		  
		  
	  }
	  
	  public void cerrarBodega(Integer id, Integer textoMotivoCierre)throws Exception
	  {

		  try
		    {
		     cerrarBodegaP(id,textoMotivoCierre);
		      commitConexion();
		      
		      
			} 
		    catch (Exception excepcion) 
			{
				rollbackConexion();
				throw excepcion;
			} finally {

			}
		  
	  }
	  
	  //RF9- PONER BODEGA EN FUNCIONAMIENTO
	  public void ponerBodegaEnFuncionamiento(Integer id)throws Exception
	  {
		  if (id==null) throw new Exception("Debe dar el id de la bodega.");
		  String sql="UPDATE BODEGA SET ESTADO='En funcionamiento' WHERE ID=?";
		  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		  try
		    {
		     preparedStatement.setInt(1,id);
		     preparedStatement.executeUpdate();
		     quitarProductosABodegas(id);
		      commitConexion();
		      
		      
			} 
		    catch (Exception excepcion) 
			{
				rollbackConexion();
				throw excepcion;
			} finally {
				preparedStatement.close();
				//preparedStatement1.close();
			}
		  
	  }
	  
	  //RF9
	  private void quitarProductosABodegas(Integer id_bodega_mantenimiento) throws SQLException
	  {
		  String sql="WITH TAB AS(";
		  sql+=" SELECT B.COD_LUGAR_ALMAC, B.COD_PRODUCTO, B.COD_PRESENTACION, B.CANTIDAD_TOTAL * A.CAPACIDAD AS OCUPACION";
		  sql+=" FROM (select COD_LUGAR_ALMAC,COD_PRODUCTO, COD_PRESENTACION, sum(CANTIDAD) AS CANTIDAD_TOTAL";
		  sql+=" from PRODUCTO_LUGAR_ALMAC GROUP BY  COD_LUGAR_ALMAC, COD_PRODUCTO, COD_PRESENTACION)B";
		  sql+=" INNER JOIN PRESENTACION A";
		  sql+=" ON A.ID = B.COD_PRESENTACION),";
		  sql+=" TABL AS(";
		  sql+=" SELECT TAB.COD_LUGAR_ALMAC, SUM(TAB.OCUPACION) AS TOTAL_ALMACENADO";
		  sql+=" FROM TAB";
		  sql+=" GROUP BY TAB.COD_LUGAR_ALMAC ORDER BY TAB.COD_LUGAR_ALMAC)";
		  sql+=" SELECT COD_LUGAR_ALMAC,TOTAL_ALMACENADO FROM TABL WHERE TOTAL_ALMACENADO>=(SELECT AVG(TOTAL_ALMACENADO) FROM TABL)";
		  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		  ResultSet resultSet=preparedStatement.executeQuery();
		  String sql2="SELECT * FROM LUGAR_ALMACENAMIENTO ni WHERE ni.ID=?";
		  PreparedStatement preparedStatement2=conexion.prepareStatement(sql2);
	      preparedStatement2.setInt(1,id_bodega_mantenimiento);
	      ResultSet resultSet2=preparedStatement2.executeQuery();
	      resultSet2.next();
	      Double capacidad_bodega_mantenimiento=resultSet2.getDouble("CAPACIDAD");
	      Integer cod_tipo_producto=resultSet2.getInt("COD_TIPO_PRODUCTO");
	      Double maximo_a_llenar=capacidad_bodega_mantenimiento*0.6;
		  while (resultSet.next()&&capacidad_bodega_mantenimiento>=maximo_a_llenar)
	      {
	        Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
	        Double total_almacenado=resultSet.getDouble("TOTAL_ALMACENADO");
	        String sql1="SELECT * FROM PRODUCTO_LUGAR_ALMAC INNER JOIN PRESENTACION ON PRESENTACION.ID=PRODUCTO_LUGAR_ALMAC.COD_PRESENTACION INNER JOIN PRODUCTO ON PRODUCTO_LUGAR_ALMAC.COD_PRODUCTO=PRODUCTO.ID WHERE PRODUCTO.COD_TIPO_PRODUCTO= "+cod_tipo_producto+" AND PRODUCTO_LUGAR_ALMAC.COD_LUGAR_ALMAC=?";
	        PreparedStatement preparedStatement1=conexion.prepareStatement(sql1);
	        preparedStatement1.setInt(1,cod_lugar_almac);
	  	  	ResultSet resultSet1=preparedStatement1.executeQuery();
	  	  	
	  	  	Double maximo_a_quitar=0.3*total_almacenado;
	  	  	while (resultSet1.next()&&total_almacenado>=total_almacenado-maximo_a_quitar&&capacidad_bodega_mantenimiento>=maximo_a_llenar)
	  	  	{
	  	  		Integer cod_lugar_almac_producto=resultSet1.getInt("COD_LUGAR_ALMAC");
	  	  		Integer cod_producto=resultSet1.getInt("COD_PRODUCTO");
	  	  		Integer cod_presentacion=resultSet1.getInt("COD_PRESENTACION");
	  	  		Integer cantidad=resultSet1.getInt("CANTIDAD");
	  	  		Integer numero_lote=resultSet1.getInt("NUMERO_LOTE");
	  	  		Date fecha_expiracion_lote=resultSet1.getDate("FECHA_EXPIRACION_LOTE");
	  	  		Double promocion_lote=resultSet1.getDouble("PROMOCION_LOTE");
	  	  		
	  	  		////////////////
	  	  		Double capacidad=resultSet1.getDouble("CAPACIDAD");
	  	  		Double peso_producto=capacidad*cantidad;
	  	  		if(capacidad_bodega_mantenimiento-peso_producto>=0 &&total_almacenado-peso_producto>=0)
	  	  		{
	  	  			capacidad_bodega_mantenimiento-=peso_producto;
	  	  			total_almacenado-=peso_producto;
	  	  			String sql3="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND CANTIDAD=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	  	  			PreparedStatement preparedStatement3=conexion.prepareStatement(sql3);
	  	  			preparedStatement3.setInt(1, cod_lugar_almac_producto);
	  	  			preparedStatement3.setInt(2, cod_producto);
	  	  			preparedStatement3.setInt(3, cod_presentacion);
	  	  			preparedStatement3.setInt(4, cantidad);
	  	  			preparedStatement3.setInt(5, numero_lote);
	  	  			preparedStatement3.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
	  	  			
	  	  			preparedStatement3.executeUpdate();
		  			preparedStatement3.close();
	  	  			//if()
	  	  			//{
	  	  				
	  	  			//}
	  	  			//else
	  	  			//{
	  	  			try {
	  	  				String sql4="INSERT INTO PRODUCTO_LUGAR_ALMAC VALUES(?,?,?,?,?,?,?)";
	  	  				PreparedStatement preparedStatement4=conexion.prepareStatement(sql4);
	  	  				preparedStatement4.setInt(1, id_bodega_mantenimiento);
	  	  				preparedStatement4.setInt(2, cod_producto);
	  	  				preparedStatement4.setInt(3, cod_presentacion);
	  	  				preparedStatement4.setInt(4, 0);
	  	  				preparedStatement4.setInt(5, numero_lote);
	  	  				preparedStatement4.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
	  	  				preparedStatement4.setDouble(7,promocion_lote);
	  	  				preparedStatement4.executeUpdate();
	  		  			preparedStatement4.close();
	  	  			}
	  	  			catch (SQLException ex) {
	  	  			}
		  			String sql4="UPDATE PRODUCTO_LUGAR_ALMAC SET CANTIDAD=CANTIDAD+? WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";

	 				PreparedStatement preparedStatement5=conexion.prepareStatement(sql4);
	 				preparedStatement5.setInt(1, cantidad);
	 				preparedStatement5.setInt(2, id_bodega_mantenimiento);
	 				preparedStatement5.setInt(3, cod_producto);
	 				preparedStatement5.setInt(4, cod_presentacion);
	 				preparedStatement5.setInt(5, numero_lote);
	 				preparedStatement5.setDate(6,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
	 				preparedStatement5.executeUpdate();
		  			preparedStatement5.close();
	  	  			//}
	  	  			
		  			
	  	  		
	  	  		}
	  	  		
	  	  	}
	  	  	resultSet1.close();
	  	  	preparedStatement1.close();
	      }
		  resultSet2.close();
		  resultSet.close();
		  
		  preparedStatement.close();
		  preparedStatement2.close();
		  
	  }
	  //RF10- DAR PRODUCTOS BODEGA
	  public List<ProductoLugarAlmacValue>darProductosBodega(Integer id)throws Exception
	  {
		  if (id==null) throw new Exception("Debe dar el identificador de la bodega.");
		    List<ProductoLugarAlmacValue> productosPedido=new ArrayList<ProductoLugarAlmacValue>();
		    String sql="SELECT * FROM PRODUCTO_LUGAR_ALMAC pi WHERE pi.COD_LUGAR_ALMAC=?";
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    try
		    {
		      preparedStatement.setInt(1,id);
		      ResultSet resultSet=preparedStatement.executeQuery();
		      try
		      {
		        while (resultSet.next())
		        {
		          Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
		          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
		          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
		          Integer cantidad=resultSet.getInt("CANTIDAD");
		          Integer numero_lote=resultSet.getInt("NUMERO_LOTE");
		          Date fecha_expiracion_lote=resultSet.getDate("FECHA_EXPIRACION_LOTE");
		          Double promocion_lote=resultSet.getDouble("PROMOCION_LOTE");
		          ProductoLugarAlmacValue producto=new ProductoLugarAlmacValue(cod_lugar_almac,cod_producto,cod_presentacion,cantidad,numero_lote,fecha_expiracion_lote,promocion_lote);
		          productosPedido.add(producto);
		        }
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return productosPedido;
	  }

	  //RF10- DAR PEDIDOS BODEGA
	  public List<BodegaPedidoValue>darPedidosBodega(Integer id)throws Exception
	  {
		  if (id==null) throw new Exception("Debe dar el identificador de la bodega.");
		    List<BodegaPedidoValue> productosPedido=new ArrayList<BodegaPedidoValue>();
		    String sql="SELECT * FROM BODEGA_PEDIDO pi WHERE pi.COD_BODEGA=?";
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    try
		    {
		      preparedStatement.setInt(1,id);
		      ResultSet resultSet=preparedStatement.executeQuery();
		      try
		      {
		        while (resultSet.next())
		        {
		          Integer cod_lugar_almac=resultSet.getInt("COD_BODEGA");
		          Integer cod_pedido=resultSet.getInt("COD_PEDIDO");
		          
		          BodegaPedidoValue bodPedido=new BodegaPedidoValue(cod_lugar_almac,cod_pedido);
		          productosPedido.add(bodPedido);
		        }
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return productosPedido;
	  }
	  
	  //RF11 - DAR LICITACIONES PROVEEDOR
	  public List<LicitacionValue> darLicitacionesProveedor(String id) throws Exception
	  {
		  if (id==null) throw new Exception("Debe dar el identificador del proveedor.");
		    List<LicitacionValue> licitaciones=new ArrayList<LicitacionValue>();
		    String sql="SELECT * FROM LICITACION pi WHERE pi.COD_PROVEEDOR_ELEGIDO=?";
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    try
		    {
		      preparedStatement.setString(1,id);
		      ResultSet resultSet=preparedStatement.executeQuery();
		      try
		      {
		    	  if(resultSet==null)
		    	  {
		    		  Window.alert("El proveedor seleccionado no tiene licitaciones");
		    	  }
		        while (resultSet.next())
		        {
		          Integer numero=resultSet.getInt("NUMERO");
		          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
		          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
		          Integer cantidad_solicitada=resultSet.getInt("CANTIDAD_SOLICITADA");
		          Integer cantidad_recibida=resultSet.getInt("CANTIDAD_RECIBIDA");
		          String cod_proveedor_eleg=resultSet.getString("COD_PROVEEDOR_ELEGIDO");
		          if (resultSet.wasNull())
		          {
		        	  cod_proveedor_eleg=null;
		          }
		          Date fecha_creacion=resultSet.getDate("FECHA_CREACION");
		          Date fecha_maxima_propuesta=resultSet.getTimestamp("FECHA_MAXIMA_PROPUESTAS");
		          Date fecha_esperada_entrega=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
		          if (resultSet.wasNull())
		          {
		        	  fecha_esperada_entrega=null;
		          }
		          Date fecha_entrega=resultSet.getDate("FECHA_ENTREGA");
		          if (resultSet.wasNull())
		          {
		        	  fecha_entrega=null;
		          }
		          Integer calificacion_entrega=resultSet.getInt("CALIFICACION_ENTREGA");
		          if (resultSet.wasNull())
		          {
		        	  calificacion_entrega=null;
		          }
		          String ha_sido_ele=resultSet.getString("HA_SIDO_ELEGIDA");
		          Boolean ha_sido_elegido=false;
		          if(ha_sido_ele.equals("Si"))
		          {
		        	  ha_sido_elegido=true;
		          }
		          String ha_sido_sat=resultSet.getString("HA_SIDO_SATISFECHA");
		          Boolean ha_sido_satisfecho=false;
		          if(ha_sido_sat.equals("Si"))
		          {
		        	  ha_sido_satisfecho=true;
		          }
		          LicitacionValue licitacion=new LicitacionValue(numero,cod_producto,cod_presentacion,cantidad_solicitada,cantidad_recibida,cod_proveedor_eleg,fecha_creacion,fecha_maxima_propuesta,fecha_esperada_entrega,fecha_entrega,calificacion_entrega,ha_sido_elegido,ha_sido_satisfecho);
		          licitaciones.add(licitacion);
		        }
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return licitaciones;
	  }
	  
	  public List<OfertaProveedorValue> darOfertasProveedor(String id)throws Exception
	  {
		  if (id==null) throw new Exception("Debe dar el identificador del proveedor.");
		    List<OfertaProveedorValue> ofertas=new ArrayList<OfertaProveedorValue>();
		    String sql="SELECT * FROM OFERTA_PROVEEDOR pi WHERE pi.COD_PROVEEDOR=?";
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    try
		    {
		      preparedStatement.setString(1,id);
		      ResultSet resultSet=preparedStatement.executeQuery();
		      try
		      {
		    	  if(resultSet==null)
		    	  {
		    		  Window.alert("El proveedor seleccionado no tiene ofertas");
		    	  }
		        while (resultSet.next())
		        {
		          Integer cod_licitacion=resultSet.getInt("COD_LICITACION");
		          String cod_proveedor=resultSet.getString("COD_PROVEEDOR");
		          Integer precio_tot=resultSet.getInt("PRECIO_TOTAL_OFRECIDO");
		          Integer precio_unit=resultSet.getInt("PRECIO_UNITARIO");
		          Integer cantidad_piensa=resultSet.getInt("CANTIDAD_PIENSA_PROVEER");
		          Date fecha_esperada_ent=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
		          Integer num_lote=resultSet.getInt("NUMERO_LOTE");
		          
		          Date fecha_exp_lote=resultSet.getTimestamp("FECHA_EXPIRACION_LOTE");
		          
		          OfertaProveedorValue oferta=new OfertaProveedorValue(cod_licitacion,cod_proveedor,precio_tot,precio_unit,cantidad_piensa,fecha_esperada_ent,num_lote,fecha_exp_lote);
		          ofertas.add(oferta);
		        }
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return ofertas;
	  }
	  
	  //-------------------------------------------
	  // Métodos iteracion 4
	  //-------------------------------------------
	  
	  
	  
	  //RF12
	  public List<PedidoValue> darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima)throws Exception
	  {
		  	String planConsulta = "explain plan for ";
		  	if (id==null) throw new Exception("Debe dar el id del local.");
		    List<PedidoValue> pedidos=new ArrayList<PedidoValue>();
		    String sql="SELECT pd.NUMERO,pd.COD_USUARIO,pd.FECHA_CREACION,pd.FECHA_ESPERADA_ENTREGA, pd.FECHA_ENTREGA,pd.PRECIO_CABANDES,pd.HA_SIDO_CANCELADO,pd.HA_SIDO_ENTREGADO FROM PEDIDO pd INNER JOIN LOCAL lc ON pd.COD_USUARIO=lc.COD_ADMINISTRADOR WHERE lc.ID=? AND ";
		    
		    if (fechaMinima!=null) sql+="(pd.FECHA_CREACION>=? OR pd.FECHA_ENTREGA>=?) AND ";
		    if (fechaMaxima!=null) sql+="(pd.FECHA_CREACION<=? OR pd.FECHA_ENTREGA<=?)";
		    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql); 
		    try
		    {
		        int indiceParametro=1;
		        preparedStatement.setInt(indiceParametro++,id);
		        if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
		        if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
		        if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
		        if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
		        long tInicio = System.currentTimeMillis();
		        ResultSet resultSet=preparedStatement.executeQuery();
		        long tFinal = System.currentTimeMillis();
			      long resultado = tFinal - tInicio;
			      System.out.println("-- SQL: " + sql);
			      System.out.println("-- Tiempo: " + resultado + " ms");
		        try
		        {
		          while (resultSet.next())
		          {
		            Integer numero=resultSet.getInt("NUMERO");
		            String cod_usuario=resultSet.getString("COD_USUARIO");
		            Date fecha_creacion=resultSet.getDate("FECHA_CREACION");
		            Date fecha_esperada_entrega=resultSet.getDate("FECHA_ESPERADA_ENTREGA");
		            Date fecha_entrega=resultSet.getDate("FECHA_ENTREGA");
		            Integer precio_cabandes=resultSet.getInt("PRECIO_CABANDES");
		            if (resultSet.wasNull())
		            {
		              precio_cabandes=null;
		            }
		            Boolean ha_sido_cancelado=resultSet.getString("HA_SIDO_CANCELADO").equals("Si");
		            Boolean ha_sido_entregado=resultSet.getString("HA_SIDO_ENTREGADO").equals("Si");
		            PedidoValue pedido=new PedidoValue(numero,cod_usuario,fecha_creacion,fecha_esperada_entrega,fecha_entrega,precio_cabandes,ha_sido_cancelado,ha_sido_entregado);
		            pedidos.add(pedido);
		          }
		        }
		        finally
		        {
		          resultSet.close();
		        }
		      }
		      finally
		      {
		        preparedStatement.close();
		      }
		      return pedidos;
		    
	  }
	  //RF13
	  public List<BodegaValue> darBodegasCapacidad(Double porcentaje,Integer idTipoProducto)throws Exception
	  {
		  List<BodegaValue> bodegas=new ArrayList<BodegaValue>();
		  Double porc=porcentaje/100.0;
		  System.out.println(porc);
		  String sql="WITH TAB AS (";
		  sql+="    SELECT pl.COD_LUGAR_ALMAC, pl.CANTIDAD*pe.CAPACIDAD AS OCUPACION";
		  sql+="    FROM PRODUCTO_LUGAR_ALMAC pl";
		  sql+="    INNER JOIN PRESENTACION pe ON pe.ID=pl.COD_PRESENTACION";
		  sql+="    INNER JOIN LUGAR_ALMACENAMIENTO li ON li.ID=pl.COD_LUGAR_ALMAC";
		  sql+="    INNER JOIN BODEGA bi ON bi.ID=li.ID";
		  sql+="    WHERE li.COD_TIPO_PRODUCTO=?";
		  sql+=" ),";
		  sql+=" TABL AS (";
		  sql+="    SELECT ta.COD_LUGAR_ALMAC, sum(ta.OCUPACION) AS TOTAL_ALMACENADO";
		  sql+="    FROM TAB ta";
		  sql+="    GROUP BY ta.COD_LUGAR_ALMAC";
		  sql+=" )";
		  sql+=" SELECT ti.COD_LUGAR_ALMAC, ti.TOTAL_ALMACENADO, li.CAPACIDAD, bi.ESTADO FROM TABL ti INNER JOIN LUGAR_ALMACENAMIENTO li ON ti.COD_LUGAR_ALMAC=li.ID INNER JOIN BODEGA bi ON bi.ID=li.ID WHERE (ti.TOTAL_ALMACENADO >= li.CAPACIDAD *"+porc+" ) AND li.COD_TIPO_PRODUCTO= ? ";
		  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		  preparedStatement.setInt(1,idTipoProducto);
		  preparedStatement.setInt(2,idTipoProducto);
		  long tInicio = System.currentTimeMillis();
		  ResultSet resultSet=preparedStatement.executeQuery();
		  long tFinal = System.currentTimeMillis();
	      long resultado = tFinal - tInicio;
	      System.out.println("-- SQL: " + sql);
	      System.out.println("-- Tiempo: " + resultado + " ms");
		  try{
		  
		  try
	      {
	        while (resultSet.next())
	        {
	          Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
	          String estado=resultSet.getString("ESTADO");
	          

	          BodegaValue bodega=new BodegaValue(cod_lugar_almac,estado);
	          bodegas.add(bodega);
	        }
	      }
	      finally
	      {
	        resultSet.close();
	      }
	    }
	    finally
	    {
	      preparedStatement.close();
	    }
	    return bodegas;
	  
		  
	  }
	  //RF14
	  public List<MovimientoValue> darMovmientos(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
	  {
		  AdminDAOCatalogo catalogo=new AdminDAOCatalogo();
		  Map<Integer,LugarAlmacenamientoValue> lugares=catalogo.darLugaresAlmacenamiento();
		  List<MovimientoValue> mov=new ArrayList<MovimientoValue>();
		    String sql="SELECT * FROM MOVIMIENTO mo WHERE ";
		    if (idProducto!=null) sql+="mo.COD_PRODUCTO=? AND ";
		    if (cantidad!=null) sql+="mo.CANTIDAD=? AND ";
		    if (idPresentacion!=null) sql+="mo.COD_PRESENTACION=? AND ";
		    if (fechaMinima!=null) sql+="mo.FECHA_MOVIMIENTO>=? AND ";
		    if (fechaMaxima!=null) sql+="mo.FECHA_MOVIMIENTO<=? AND ";
		    if (idBodegaOrigen!=null) sql+="mo.COD_LUGAR_ORIGEN=? AND ";
		    if (idLugarDestino!=null) sql+="mo.COD_LUGAR_DESTINO=? AND ";
		    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
		    System.out.println("RF14  "+sql);
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    try
		    {
		      int indiceParametro=1;
		      if (idProducto!=null) preparedStatement.setInt(indiceParametro++,idProducto);
		      if (cantidad!=null) preparedStatement.setInt(indiceParametro++,cantidad);
		      if (idPresentacion!=null) preparedStatement.setInt(indiceParametro++,idPresentacion);
		      if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
		      if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
		      if (idBodegaOrigen!=null) preparedStatement.setInt(indiceParametro++,idBodegaOrigen);
		      if (idLugarDestino!=null) preparedStatement.setInt(indiceParametro++,idLugarDestino);
		      long tInicio = System.currentTimeMillis();
		      ResultSet resultSet=preparedStatement.executeQuery();
		      long tFinal = System.currentTimeMillis();
		      long resultado = tFinal - tInicio;
		      System.out.println("-- SQL: " + sql);
		      System.out.println("-- Tiempo: " + resultado + " ms");
		      try
		      {
		        while (resultSet.next())
		        {
		          Integer numero=resultSet.getInt("NUMERO");
		          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
		          Integer rescantidad=resultSet.getInt("CANTIDAD");
		          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
		          Date fecha_movimiento=resultSet.getDate("FECHA_MOVIMIENTO");
		          Integer id_bodega_origen=resultSet.getInt("COD_LUGAR_ORIGEN");
		          Integer id_lugar_destino=resultSet.getInt("COD_LUGAR_DESTINO");
		          
		          LugarAlmacenamientoValue bodega_origen=lugares.get(id_bodega_origen);
		          LugarAlmacenamientoValue lugar_destino=lugares.get(id_lugar_destino);
		          String nombre_bodega_origen=bodega_origen!=null?bodega_origen.nombre:"";
		          String nombre_lugar_destino=lugar_destino!=null?lugar_destino.nombre:"";

		          MovimientoValue existencia=new MovimientoValue(numero,id_bodega_origen,nombre_bodega_origen,id_lugar_destino,nombre_lugar_destino,fecha_movimiento,cod_producto,cod_presentacion,rescantidad);
		          mov.add(existencia);
		        }
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return mov;
		      
	  }
		  //RF15 
	  public List<MovimientoValue> darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
	  {
		  System.out.println("entro!!!!!!!!!!!!!!!!!!!!!!!!!!");
		  List<MovimientoValue> mov=new ArrayList<MovimientoValue>();
		    String sql="SELECT * FROM MOVIMIENTO mo WHERE ";
		    if (idProducto!=null) sql+="mo.COD_PRODUCTO<>? AND ";
		    if (cantidad!=null) sql+="mo.CANTIDAD<>? AND ";
		    if (idPresentacion!=null) sql+="mo.COD_PRESENTACION<>? AND ";
		    if (fechaMinima!=null && fechaMaxima==null) sql+="mo.FECHA_MOVIMIENTO<? AND ";
		    if (fechaMinima==null && fechaMaxima!=null) sql+="mo.FECHA_MOVIMIENTO>? AND ";
		    if (fechaMinima!=null && fechaMaxima!=null) sql+="(mo.FECHA_MOVIMIENTO<? OR mo.FECHA_MOVIMIENTO>?) AND ";
		    if (idBodegaOrigen!=null) sql+="mo.COD_LUGAR_ORIGEN<>? AND ";
		    if (idLugarDestino!=null) sql+="mo.COD_LUGAR_DESTINO<>? AND ";
		    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
		    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
		    
		    try
		    {
		      int indiceParametro=1;
		      if (idProducto!=null) preparedStatement.setInt(indiceParametro++,idProducto);
		      if (cantidad!=null) preparedStatement.setInt(indiceParametro++,cantidad);
		      if (idPresentacion!=null) preparedStatement.setInt(indiceParametro++,idPresentacion);
		      if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
		      if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
		      if (idBodegaOrigen!=null) preparedStatement.setInt(indiceParametro++,idBodegaOrigen);
		      if (idLugarDestino!=null) preparedStatement.setInt(indiceParametro++,idLugarDestino);
		      long tInicio = System.currentTimeMillis();
		      ResultSet resultSet=preparedStatement.executeQuery();
		      long tFinal = System.currentTimeMillis();
		      long resultado = tFinal - tInicio;
		      System.out.println("-- SQL: " + sql);
		      System.out.println("-- Tiempo: " + resultado + " ms");
		      if(resultado > 800)
		    	  throw new Exception("La sentencia sql: " + sql + " posee un tiempo mayor a 8 segundos");
		      try
		      {
		    	  int i = 0;
		        while (resultSet.next())
		        {
		        	i++;
//		          Integer numero=resultSet.getInt("NUMERO");
//		          Integer cod_producto=resultSet.getInt("COD_PRODUCTO");
//		          Integer rescantidad=resultSet.getInt("CANTIDAD");
//		          Integer cod_presentacion=resultSet.getInt("COD_PRESENTACION");
//		          Date fecha_movimiento=resultSet.getDate("FECHA_MOVIMIENTO");
//		          Integer bodega_origen=resultSet.getInt("COD_LUGAR_ORIGEN");
//		          Integer lugar_destino=resultSet.getInt("COD_LUGAR_DESTINO");
//		          
//		          MovimientoValue existencia=new MovimientoValue(numero,bodega_origen,lugar_destino,fecha_movimiento,cod_producto,cod_presentacion,rescantidad);
//		          mov.add(existencia);
		        }
		        System.out.println("Tamaño respuesta: " + i);
		      }
		      finally
		      {
		        resultSet.close();
		      }
		    }
		    finally
		    {
		      preparedStatement.close();
		    }
		    return mov;
		      
	  }
	

}
