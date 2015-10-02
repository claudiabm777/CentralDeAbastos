package co.edu.uniandes.cabAndes.server.dao;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import com.google.gwt.user.client.Window;

import co.edu.uniandes.cabAndes.server.jms.ColaMensajes;
import co.edu.uniandes.cabAndes.server.jms.EnviadorMensajes;
import co.edu.uniandes.cabAndes.server.jms.Formateador;
import co.edu.uniandes.cabAndes.server.jms.RecibidorMensajes;
import co.edu.uniandes.cabAndes.shared.vos.*;

public class AdminDAO
{

  private static final String ARCHIVO_CONEXION="conexion.properties";
  

  protected Connection conexion;

  public AdminDAO() throws Exception
  {
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
    String sql="SELECT * FROM USUARIO us WHERE us.DIRECCION_ELECTRONICA=? AND (us.ROL='Comprador mayorista' OR us.ROL='Administrador de local')";
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
    // Ver RF23
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

  // RF26 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
  public List<ProductoMayorMovimientoValue> darProductosMayorMovimientoRF26(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento) throws Exception
  {
	  List<ProductoMayorMovimientoValue> cosas=darProductosMayorMovimiento(fechaMinimaMovimiento, fechaMaximaMovimiento);
	  EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
	  String peticion="RF26";
	  peticion+="\r\n"+Formateador.formatearFecha(fechaMinimaMovimiento);
	  peticion+="\r\n"+Formateador.formatearFecha(fechaMaximaMovimiento);
	  enviadorMensajes.enviarMensaje(ColaMensajes.COLA_PETICIONES_GRUPO5,peticion);
	  RecibidorMensajes recibidorMensajes=new RecibidorMensajes();
	  String respuesta=recibidorMensajes.recibirMensajeYa(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO9);
	  BufferedReader reader=new BufferedReader(new StringReader(respuesta));
	  String rf=reader.readLine();
	  if (rf.equals("RF26"))
	  {
		  while (true)
		  {
		     String linea=reader.readLine();
		     if (linea==null||linea.trim().isEmpty()) break;
		     String[] pedazos=linea.split("\\;");
		     Integer cod_producto=Formateador.leerEntero(pedazos[0]);
		     Integer cod_presentacion=Formateador.leerEntero(pedazos[1]);
		     Double promedio_peso=Formateador.leerFlotante(pedazos[2]);
		     Double costo_promedio=Formateador.leerFlotante(pedazos[3]);
		     Integer veces_ha_sido_pedido=Formateador.leerEntero(pedazos[4]);
		     Integer veces_ha_sido_vendido=Formateador.leerEntero(pedazos[5]);
		     cosas.add(new ProductoMayorMovimientoValue(cod_producto, cod_presentacion, promedio_peso, costo_promedio, veces_ha_sido_pedido, veces_ha_sido_vendido));
		  }
	  }
	  return cosas;
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
  
  
  //RF24 CERRAR BODEGA 2
  private String cerrarBodegaP2(Integer id, Integer textoMotivoCierre) throws Exception
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
	  
	  List<Integer>listaBodegasSuplentes=new ArrayList<Integer>();
	  
	  String loDelOtro="RF24";
	  loDelOtro+="\r\n"+cod_tipo_producto;
	  while(resultSet.next())
	  {
		  System.out.println("000000000000000000000000000000099999999999999900000000000");
		  Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
	      Double total_almacenado=resultSet.getDouble("TOTAL_ALMACENADO");
	      Double capacidad_total=resultSet.getDouble("CAPACIDAD");
	      Double capacidad_disponible=capacidad_total-total_almacenado;
	      
	      if(cod_lugar_almac!=id)
	      {
	    	  PreparedStatement preparedStatement1=conexion.prepareStatement(sql1);
	    	  
	    	  preparedStatement1.setInt(1,id);
	    	  ResultSet resultSet1=preparedStatement1.executeQuery();

	    	  while(resultSet1.next()&&capacidad_disponible>=0)
	    	  {
	    		  	Integer cod_lugar_almac_producto=id;
	    	  		Integer cod_producto=resultSet1.getInt("COD_PRODUCTO");
	    	  		Integer cod_presentacion=resultSet1.getInt("COD_PRESENTACION");
	    	  		Integer cantidadToda=resultSet1.getInt("CANTIDAD");
	    	  		Integer numero_lote=resultSet1.getInt("NUMERO_LOTE");
	    	  		Date fecha_expiracion_lote=resultSet1.getDate("FECHA_EXPIRACION_LOTE");
	    	  		Double promocion_lote=resultSet1.getDouble("PROMOCION_LOTE");
	    	  		
	    	  		Integer cantidadMia=(cantidadToda+1)/2,cantidadOtro=cantidadToda-cantidadMia;
	    	  		loDelOtro+="\r\n"+cod_producto+";"+cod_presentacion+";"+cantidadOtro+";"+numero_lote+";"+Formateador.formatearFecha(fecha_expiracion_lote);
	    	  		
	    	  		
	    	  		Integer cantidad=cantidadMia;
	    	  		////////////////
	    	  		Double capacidad=resultSet1.getDouble("CAPACIDAD");
	    	  		Double peso_producto=capacidad*cantidad;
	    	  		if(capacidad_disponible-peso_producto>=0)
	    	  		{
	    	  			capacidad_disponible-=peso_producto;
	    	  			String sql3="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	      	  			PreparedStatement preparedStatement3=conexion.prepareStatement(sql3);
	      	  			preparedStatement3.setInt(1, cod_lugar_almac_producto);
	      	  			preparedStatement3.setInt(2, cod_producto);
	      	  			preparedStatement3.setInt(3, cod_presentacion);
	      	  			preparedStatement3.setInt(4, numero_lote);
	      	  			preparedStatement3.setDate(5,resultSet1.getDate("FECHA_EXPIRACION_LOTE"));
	      	  			
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
	    	  resultSet1.close();
	    	  preparedStatement1.close();
	      }
	  }
	  PreparedStatement preparedStatement1=conexion.prepareStatement(sql1);
	  
	  preparedStatement1.setInt(1,id);
	  ResultSet resultSet1=preparedStatement1.executeQuery();
	  if(resultSet1.next())
	  {
		  resultSet.close();
		  resultSet1.close();
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

	  return loDelOtro;
	  
  }

  //RF24 CERRAR BODEGA 3
  private void cerrarBodegaP3(Integer cod_tipo_producto, List<ProductoLugarAlmacValue> cosas) throws Exception
  {	  	  
	  
	  AdminDAOCatalogo catalogo=new AdminDAOCatalogo();
	  Map<Integer,PresentacionValue> map=catalogo.darPresentaciones();
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
	  	  
	  List<Integer>listaBodegasSuplentes=new ArrayList<Integer>();
	  
	  while(resultSet.next())
	  {
		  System.out.println("000000000000000000000000000000099999999999999900000000000");
		  Integer cod_lugar_almac=resultSet.getInt("COD_LUGAR_ALMAC");
	      Double total_almacenado=resultSet.getDouble("TOTAL_ALMACENADO");
	      Double capacidad_total=resultSet.getDouble("CAPACIDAD");
	      Double capacidad_disponible=capacidad_total-total_almacenado;
	      
	      {
	    	  System.out.println("FALTAN "+cosas.size());
	    	  Iterator<ProductoLugarAlmacValue> iterator=cosas.iterator();

	    	  while(iterator.hasNext()&&capacidad_disponible>=0)
	    	  {
	    		  ProductoLugarAlmacValue cosa=iterator.next();
	    	  		Integer cod_producto=cosa.cod_producto;
	    	  		Integer cod_presentacion=cosa.cod_presentacion;
	    	  		Integer cantidad=cosa.cantidad;
	    	  		Integer numero_lote=cosa.numero_lote;
	    	  		Date fecha_expiracion_lote=cosa.fecha_expiracion_lote;
	    	  		Double promocion_lote=cosa.promocion_lote;
	    	  		
	    	  		PresentacionValue presentacion=map.get(cod_presentacion);
	    	  		////////////////
	    	  		Double capacidad=presentacion!=null?presentacion.capacidad:0.0;
	    	  		Double peso_producto=capacidad*cantidad;
	    	  		if(capacidad_disponible-peso_producto>=0)
	    	  		{
	    	  			capacidad_disponible-=peso_producto;
	    	  			
	    	  			iterator.remove();
	    	  			System.out.println("DESPACHANDO OTRO");
	    	  			
	    	  			try {
	      	  				String sql4="INSERT INTO PRODUCTO_LUGAR_ALMAC VALUES(?,?,?,?,?,?,?)";
	      	  				PreparedStatement preparedStatement4=conexion.prepareStatement(sql4);
	      	  				preparedStatement4.setInt(1, cod_lugar_almac);
	      	  				preparedStatement4.setInt(2, cod_producto);
	      	  				preparedStatement4.setInt(3, cod_presentacion);
	      	  				preparedStatement4.setInt(4, 0);
	      	  				preparedStatement4.setInt(5, numero_lote);
	      	  				preparedStatement4.setDate(6,new java.sql.Date(fecha_expiracion_lote.getTime()));
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
	     				preparedStatement5.setDate(6,new java.sql.Date(fecha_expiracion_lote.getTime()));
	     				preparedStatement5.executeUpdate();
	    	  			preparedStatement5.close();
	    	  			listaBodegasSuplentes.add(cod_lugar_almac);
	    	  		}

	    	  }
	      }
	  }
	  resultSet.close();
	  preparedStatement.close();	  
  }

  public void cerrarBodega2(Integer id, Integer textoMotivoCierre)throws Exception
  {

	  try
	    {
	     String loDelOtro=cerrarBodegaP2(id,textoMotivoCierre);
	      commitConexion();
          EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
          enviadorMensajes.enviarMensaje(ColaMensajes.COLA_PETICIONES_GRUPO5,loDelOtro);

	      
		} 
	    catch (Exception excepcion) 
		{
			rollbackConexion();
			throw excepcion;
		} finally {

		}
	  
  }

  public void cerrarBodega3(Integer cod_tipo_producto, List<ProductoLugarAlmacValue> cosas)throws Exception
  {

	  try
	    {
	      cerrarBodegaP3(cod_tipo_producto,cosas);
	  System.out.println("P3 OK");
	      
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
  
  //RF12
  public List<PedidoValue> darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima)throws Exception
  {
	  	if (id==null) throw new Exception("Debe dar el id del local.");
	    List<PedidoValue> pedidos=new ArrayList<PedidoValue>();
	    String sql="SELECT pd.NUMERO,pd.COD_USUARIO,pd.FECHA_CREACION,pd.FECHA_ESPERADA_ENTREGA, pd.FECHA_ENTREGA,pd.PRECIO_CABANDES,pd.HA_SIDO_CANCELADO,pd.HA_SIDO_ENTREGADO FROM PEDIDO pd INNER JOIN LOCAL lc ON pd.COD_USUARIO=lc.COD_ADMINISTRADOR WHERE lc.ID=? AND ";
	    
	    if (fechaMinima!=null) sql+="(pd.FECHA_CREACION>=? OR pd.FECHA_ENTREGA>=?) AND ";
	    if (fechaMaxima!=null) sql+="(pd.FECHA_CREACION<=? OR pd.FECHA_ENTREGA<=?)";
	    sql=sql.replaceAll(" AND $","").replaceAll(" WHERE $","");
	    PreparedStatement preparedStatement=conexion.prepareStatement(sql); try
	    {
	        int indiceParametro=1;
	        preparedStatement.setInt(indiceParametro++,id);
	        if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
	        if (fechaMinima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMinima.getTime()));
	        if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
	        if (fechaMaxima!=null) preparedStatement.setDate(indiceParametro++,new java.sql.Date(fechaMaxima.getTime()));
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
	  ResultSet resultSet=preparedStatement.executeQuery();
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
	      ResultSet resultSet=preparedStatement.executeQuery();
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
	         System.out.println("1111111111111111111");
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
	    System.out.println("3333333333333333333333333333:    "+mov.isEmpty());
	    return mov;
	      
  }
  
  //RF14-RF25
  public List<MovimientoValue> darMovmientosRF25(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
  {
	  List<MovimientoValue> cosas=darMovmientos(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
	  EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
	  String peticion="RF25";
	  peticion+="\r\n"+Formateador.formatearEntero(cantidad);
	  peticion+="\r\n"+Formateador.formatearEntero(idProducto);
	  peticion+="\r\n"+Formateador.formatearEntero(idPresentacion);
	  peticion+="\r\n"+Formateador.formatearEntero(idBodegaOrigen);
	  peticion+="\r\n"+Formateador.formatearEntero(idLugarDestino);
	  peticion+="\r\n"+Formateador.formatearFecha(fechaMinima);
	  peticion+="\r\n"+Formateador.formatearFecha(fechaMaxima);
	  enviadorMensajes.enviarMensaje(ColaMensajes.COLA_PETICIONES_GRUPO5,peticion);
	  RecibidorMensajes recibidorMensajes=new RecibidorMensajes();
	  String respuesta=recibidorMensajes.recibirMensajeYa(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO9);
	  BufferedReader reader=new BufferedReader(new StringReader(respuesta));
	  String rf=reader.readLine();
	  if (rf.equals("RF25"))
	  {
		  while (true)
		  {
		     String linea=reader.readLine();
		     if (linea==null||linea.trim().isEmpty()) break;
		     String[] pedazos=linea.split("\\;");
		     Integer numero=Formateador.leerEntero(pedazos[0]);
		     Integer cod_lugar_origen=Formateador.leerEntero(pedazos[1]);
		     String nombre_lugar_origen=!pedazos[2].equals("null")?pedazos[2]:"";
		     Integer cod_lugar_destino=Formateador.leerEntero(pedazos[3]);
		     String nombre_lugar_destino=!pedazos[4].equals("null")?pedazos[4]:"";
		     java.util.Date fecha_movimiento=Formateador.leerFecha(pedazos[5]);
		     Integer cod_producto=Formateador.leerEntero(pedazos[6]);
		     Integer cod_presentacion=Formateador.leerEntero(pedazos[7]);
		     Integer cantidad2=Formateador.leerEntero(pedazos[8]);

		     
		     cosas.add(new MovimientoValue(numero, cod_lugar_origen, nombre_lugar_origen, cod_lugar_destino, nombre_lugar_destino, fecha_movimiento, cod_producto, cod_presentacion, cantidad2));
		  }
	  }
	  return cosas;
	      
  }

	  //RF15 
  public List<MovimientoValue> darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
  {
	  AdminDAOCatalogo catalogo=new AdminDAOCatalogo();
	  Map<Integer,LugarAlmacenamientoValue> lugares=catalogo.darLugaresAlmacenamiento();
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
	    System.out.println(sql);
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
	      ResultSet resultSet=preparedStatement.executeQuery();
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
  
  // RF23 - METODO DE AYUDA QUE DA LA INFORMACION DE UN PEDIDO
  private PedidoValue darPedido(Integer numeroPedido) throws Exception
  {
    if (numeroPedido==null) throw new Exception("Debe dar el número de un pedido.");
    System.out.println("RF23 - Consultando pedido "+numeroPedido);
    PedidoValue pedido=null;
    String sql="SELECT * FROM PEDIDO pd WHERE pd.NUMERO=?";
    PreparedStatement preparedStatement=conexion.prepareStatement(sql);
    try
    {
      preparedStatement.setInt(1,numeroPedido);
      ResultSet resultSet=preparedStatement.executeQuery();
      try
      {
        if (resultSet.next())
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
          pedido=new PedidoValue(numero,cod_usuario,fecha_creacion,fecha_esperada_entrega,fecha_entrega,precio_cabandes,ha_sido_cancelado,ha_sido_entregado);
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
    if (pedido==null)  throw new Exception("No existe un pedido con el número "+numeroPedido);
    return pedido;
  }

  // RF23 - SATISFACER PEDIDO
  private String satisfacerPedidoP2(Integer numeroPedido) throws Exception
  {
	  PedidoValue pedido=darPedido(numeroPedido);
	  List<ProductoPedidoValue> productosPedido=darProductosPedido(numeroPedido);
	  String loDelOtro="RF23";
	  for (ProductoPedidoValue productoPedido:productosPedido)
	  {
		  System.out.println("RF23 - Intentando satisfacer pedido "+numeroPedido+" Producto:"+productoPedido.cod_producto+" Presentación:"+productoPedido.cod_presentacion+" Cantidad: "+productoPedido.cantidad_solicitada);
		  int cantidad_solicitada=productoPedido.cantidad_solicitada;
		  int cantidad_faltante=cantidad_solicitada;
		  int cantidad_entregada=0;
		  if (cantidad_solicitada>0)
		  {
			  String sql="SELECT * FROM PRODUCTO_LUGAR_ALMAC pl INNER JOIN BODEGA bo ON bo.ID=pl.COD_LUGAR_ALMAC WHERE pl.COD_PRODUCTO=? AND pl.COD_PRESENTACION=?";
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  preparedStatement.setInt(1, productoPedido.cod_producto);
			  preparedStatement.setInt(2, productoPedido.cod_presentacion);
			  ResultSet resultSet=preparedStatement.executeQuery();
			  while(resultSet.next())
			  {
				  int cantidad=resultSet.getInt("CANTIDAD");
				  if (cantidad_faltante>=cantidad)
				  {
	    	  			String sql3="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND CANTIDAD=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	      	  			PreparedStatement preparedStatement3=conexion.prepareStatement(sql3);
	      	  			preparedStatement3.setInt(1, resultSet.getInt("COD_LUGAR_ALMAC"));
	      	  			preparedStatement3.setInt(2, resultSet.getInt("COD_PRODUCTO"));
	      	  			preparedStatement3.setInt(3, resultSet.getInt("COD_PRESENTACION"));
	      	  			preparedStatement3.setInt(4, resultSet.getInt("CANTIDAD"));
	      	  			preparedStatement3.setInt(5, resultSet.getInt("NUMERO_LOTE"));
	      	  			preparedStatement3.setDate(6,resultSet.getDate("FECHA_EXPIRACION_LOTE"));	      	  			
	      	  			preparedStatement3.executeUpdate();
	    	  			preparedStatement3.close();
					    cantidad_entregada+=cantidad;
					    cantidad_faltante-=cantidad;
				  }
				  else // if (cantidad_faltante<cantidad)
				  {
	    	  			String sql4="UPDATE PRODUCTO_LUGAR_ALMAC SET CANTIDAD=CANTIDAD-? WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	     				PreparedStatement preparedStatement5=conexion.prepareStatement(sql4);
	     				preparedStatement5.setInt(1, cantidad_faltante);
	      	  			preparedStatement5.setInt(2, resultSet.getInt("COD_LUGAR_ALMAC"));
	      	  			preparedStatement5.setInt(3, resultSet.getInt("COD_PRODUCTO"));
	      	  			preparedStatement5.setInt(4, resultSet.getInt("COD_PRESENTACION"));
	      	  			preparedStatement5.setInt(5, resultSet.getInt("CANTIDAD"));
	      	  			preparedStatement5.setInt(6, resultSet.getInt("NUMERO_LOTE"));
	      	  			preparedStatement5.setDate(7,resultSet.getDate("FECHA_EXPIRACION_LOTE"));	      	  			
	     				preparedStatement5.executeUpdate();
	    	  			preparedStatement5.close();
					    cantidad_entregada+=cantidad_faltante;
						cantidad_faltante=0;
				  }
			  }
			  System.out.println("RF23 - Cantidad entregada: "+cantidad_entregada+" Cantidad faltante: "+cantidad_faltante);

			  resultSet.close();
			  preparedStatement.close();

	  		  String sql6="UPDATE PRODUCTO_PEDIDO SET CANTIDAD_ENTREGADA=? WHERE COD_PEDIDO=? AND COD_PRODUCTO=? AND COD_PRESENTACION=?";
  	  		  PreparedStatement preparedStatement6=conexion.prepareStatement(sql6);
  	  	      preparedStatement6.setInt(1, cantidad_entregada);
  	  		  preparedStatement6.setInt(2, productoPedido.cod_pedido);
  	  		  preparedStatement6.setInt(3, productoPedido.cod_producto);
  	  		  preparedStatement6.setInt(4, productoPedido.cod_presentacion);
  	  		  preparedStatement6.executeUpdate();
	  		  preparedStatement6.close();
	  		  
	  		  if (cantidad_faltante>0)
	  		  {
	  			 loDelOtro+="\r\n"+productoPedido.cod_producto+";"+productoPedido.cod_presentacion+";"+cantidad_faltante;
	  		  }
		  }
	  }
	  return loDelOtro;
  }
  
  // RF23 - SATISFACER PEDIDO ENVIANDO AL OTRO CABANDES LO QUE FALTE
  public void satisfacerPedido2(Integer numeroPedido)throws Exception
  {
	  try
	    {
	     String loDelOtro=satisfacerPedidoP2(numeroPedido);
	     System.out.println("RF 23: Al otro grupo se le envió el mensaje "+loDelOtro);
	      commitConexion();
          EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
          enviadorMensajes.enviarMensaje(ColaMensajes.COLA_PETICIONES_GRUPO5,loDelOtro);
		} 
	    catch (Exception excepcion) 
		{
	    	excepcion.printStackTrace();
			rollbackConexion();
			throw excepcion;
		}
	    finally {
		}	  
  }

  //RF23 SATISFACER PEDIDO DE PRODUCTOS NO SATISFECHOS POR EL OTRO CABANDES
  private void satisfacerPedidoP3(List<ProductoPedidoValue> cosas) throws Exception
  {	  	  	  
	  int max_numero_licitacion=1;
	  String sql1="SELECT MAX(NUMERO) FROM LICITACION";
	  Statement statement1=conexion.createStatement();
	  ResultSet resultSet1=statement1.executeQuery(sql1);
	  if (resultSet1.next())
	  {
		  max_numero_licitacion=resultSet1.getInt(1);
	  }
	  System.out.println("Máximo número licitación: "+max_numero_licitacion);
	  List<LicitacionValue> lo_que_falto=new ArrayList<LicitacionValue>();
	  for (ProductoPedidoValue productoPedido:cosas)
	  {
		  System.out.println("RF23 - Intentando satisfacer del otro CabAndes Producto:"+productoPedido.cod_producto+" Presentación:"+productoPedido.cod_presentacion+" Cantidad: "+productoPedido.cantidad_solicitada);
		  int cantidad_solicitada=productoPedido.cantidad_solicitada;
		  int cantidad_faltante=cantidad_solicitada;
		  int cantidad_entregada=0;
		  if (cantidad_solicitada>0)
		  {
			  String sql="SELECT * FROM PRODUCTO_LUGAR_ALMAC pl INNER JOIN BODEGA bo ON bo.ID=pl.COD_LUGAR_ALMAC WHERE pl.COD_PRODUCTO=? AND pl.COD_PRESENTACION=?";
			  PreparedStatement preparedStatement=conexion.prepareStatement(sql);
			  preparedStatement.setInt(1, productoPedido.cod_producto);
			  preparedStatement.setInt(2, productoPedido.cod_presentacion);
			  ResultSet resultSet=preparedStatement.executeQuery();
			  while(resultSet.next())
			  {
				  int cantidad=resultSet.getInt("CANTIDAD");
				  if (cantidad_faltante>=cantidad)
				  {
	    	  			String sql3="DELETE FROM PRODUCTO_LUGAR_ALMAC WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND CANTIDAD=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	      	  			PreparedStatement preparedStatement3=conexion.prepareStatement(sql3);
	      	  			preparedStatement3.setInt(1, resultSet.getInt("COD_LUGAR_ALMAC"));
	      	  			preparedStatement3.setInt(2, resultSet.getInt("COD_PRODUCTO"));
	      	  			preparedStatement3.setInt(3, resultSet.getInt("COD_PRESENTACION"));
	      	  			preparedStatement3.setInt(4, resultSet.getInt("CANTIDAD"));
	      	  			preparedStatement3.setInt(5, resultSet.getInt("NUMERO_LOTE"));
	      	  			preparedStatement3.setDate(6,resultSet.getDate("FECHA_EXPIRACION_LOTE"));	      	  			
	      	  			preparedStatement3.executeUpdate();
	    	  			preparedStatement3.close();
					    cantidad_entregada+=cantidad;
					    cantidad_faltante-=cantidad;
				  }
				  else // if (cantidad_faltante<cantidad)
				  {
	    	  			String sql4="UPDATE PRODUCTO_LUGAR_ALMAC SET CANTIDAD=CANTIDAD-? WHERE COD_LUGAR_ALMAC=? AND COD_PRODUCTO=? AND COD_PRESENTACION=? AND NUMERO_LOTE=? AND FECHA_EXPIRACION_LOTE=?";
	     				PreparedStatement preparedStatement5=conexion.prepareStatement(sql4);
	     				preparedStatement5.setInt(1, cantidad_faltante);
	      	  			preparedStatement5.setInt(2, resultSet.getInt("COD_LUGAR_ALMAC"));
	      	  			preparedStatement5.setInt(3, resultSet.getInt("COD_PRODUCTO"));
	      	  			preparedStatement5.setInt(4, resultSet.getInt("COD_PRESENTACION"));
	      	  			preparedStatement5.setInt(5, resultSet.getInt("CANTIDAD"));
	      	  			preparedStatement5.setInt(6, resultSet.getInt("NUMERO_LOTE"));
	      	  			preparedStatement5.setDate(7,resultSet.getDate("FECHA_EXPIRACION_LOTE"));	      	  			
	     				preparedStatement5.executeUpdate();
	    	  			preparedStatement5.close();
					    cantidad_entregada+=cantidad_faltante;
						cantidad_faltante=0;
				  }
			  }
			  resultSet.close();
			  preparedStatement.close();

			  System.out.println("RF23 - Cantidad entregada al otro grupo: "+cantidad_entregada+" Cantidad faltante: "+cantidad_faltante);

			  if (cantidad_faltante>0)
			  {
				  max_numero_licitacion++;
				  Integer numero_licitacion=max_numero_licitacion;
				  Integer cod_producto_licitacion=productoPedido.cod_producto;
				  Integer cod_presentacion_licitacion=productoPedido.cod_presentacion;
				  Integer cantidad_solicitada_licitacion=cantidad_faltante;
				  Integer cantidad_recibida_licitacion=0;
				  String cod_proveedor_elegido_licitacion=null;
				  java.util.Date fecha_creacion_licitacion=new java.util.Date(System.currentTimeMillis());
				  java.util.Date fecha_maxima_propuestas_licitacion=new java.util.Date(System.currentTimeMillis()+7*24*60*60*1000); // + 1 semana
				  java.util.Date fecha_esperada_entrega_licitacion=null;
				  java.util.Date fecha_entrega_licitacion=null;
				  Integer calificacion_entrega_licitacion=null;
				  Boolean ha_sido_elegida_licitacion=false;
				  Boolean ha_sido_satisfecha_licitacion=false;
				  LicitacionValue licitacion=new LicitacionValue(numero_licitacion,cod_producto_licitacion,cod_presentacion_licitacion,cantidad_solicitada_licitacion,cantidad_recibida_licitacion,cod_proveedor_elegido_licitacion,fecha_creacion_licitacion,fecha_maxima_propuestas_licitacion,fecha_esperada_entrega_licitacion,fecha_entrega_licitacion,calificacion_entrega_licitacion,ha_sido_elegida_licitacion,ha_sido_satisfecha_licitacion);
				  lo_que_falto.add(licitacion);
			  }
		  }
	  }
	  for (LicitacionValue licitacion:lo_que_falto)
	  {
		  System.out.println("creando licitación número "+licitacion.numero+" sobre el producto "+licitacion.cod_producto+", presentacion "+licitacion.cod_presentacion+", cantidad "+licitacion.cantidad_solicitada);
			String sql4="INSERT INTO LICITACION VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	  		PreparedStatement preparedStatement4=conexion.prepareStatement(sql4);
	  		preparedStatement4.setInt(1,licitacion.numero); // NUMERO
	  		preparedStatement4.setInt(2,licitacion.cod_producto); // COD_PRODUCTO
	  		preparedStatement4.setInt(3,licitacion.cod_presentacion); // COD_PRESENTACION
	  		preparedStatement4.setInt(4,licitacion.cantidad_solicitada); // CANTIDAD_SOLICITADA
	  		preparedStatement4.setInt(5,licitacion.cantidad_recibida); // CANTIDAD_RECIBIDA
	  		preparedStatement4.setString(6,null); // COD_PROVEEDOR_ELEGIDO
	  		preparedStatement4.setDate(7,new java.sql.Date(licitacion.fecha_creacion.getTime())); // FECHA_CREACION
	  		preparedStatement4.setTimestamp(8,new java.sql.Timestamp(licitacion.fecha_maxima_propuestas.getTime())); // FECHA_MAXIMA_PROPUESTAS
	  		preparedStatement4.setDate(9,null); // FECHA_ESPERADA_ENTREGA
	  		preparedStatement4.setDate(10,null); // FECHA_ENTREGA
	  		preparedStatement4.setObject(11,null); // CALIFICACION_ENTREGA
	  		preparedStatement4.setString(12,"No"); // HA_SIDO_ELEGIDA
	  		preparedStatement4.setString(13,"No"); // HA_SIDO_SATISFECHA	  		
	  		preparedStatement4.executeUpdate();
		  	preparedStatement4.close();
	  }
  }

  public void satisfacerPedido3(List<ProductoPedidoValue> cosas) throws Exception
  {
	  try
	    {
	      satisfacerPedidoP3(cosas);
	  System.out.println("P3 OK");
	      commitConexion();
		} 
	    catch (Exception excepcion) 
		{
	    	excepcion.printStackTrace();
			rollbackConexion();
			throw excepcion;
		} finally {

		}	  
  }


  
  
  
  
  
  // método main para probar que la conexión funciona
  public static void main(String[] args) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.probarConexion();
    }
    finally
    {
      dao.cerrarConexion();
    }
  }

}
