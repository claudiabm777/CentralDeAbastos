package co.edu.uniandes.cabAndes.server.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class AdminDAOCatalogo extends AdminDAO
{

  public AdminDAOCatalogo() throws Exception
  {
    super();
  }

  // dar datos básicos - dar usuarios
  public Map<String,UsuarioValue> darUsuarios() throws Exception
  {
    Map<String,UsuarioValue> usuarios=new TreeMap<String,UsuarioValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM USUARIO");
      try
      {
        while (resultSet.next())
        {
          String direccion_electronica=resultSet.getString("DIRECCION_ELECTRONICA");
          String nombre=resultSet.getString("NOMBRE");
          String doc_identificacion=resultSet.getString("DOC_IDENTIFICACION");
          String palabra_clave=resultSet.getString("PALABRA_CLAVE");
          String ciudad=resultSet.getString("CIUDAD");
          String departamento=resultSet.getString("DEPARTAMENTO");
          String nacionalidad=resultSet.getString("NACIONALIDAD");
          String direccion_fisica=resultSet.getString("DIRECCION_FISICA");
          Integer codigo_postal=resultSet.getInt("CODIGO_POSTAL");
          if (resultSet.wasNull())
          {
            codigo_postal=null;
          }
          String telefono=resultSet.getString("TELEFONO");
          String rol=resultSet.getString("ROL");
          String tipo=resultSet.getString("TIPO");
          UsuarioValue usuario=new UsuarioValue(direccion_electronica,nombre,doc_identificacion,palabra_clave,ciudad,departamento,nacionalidad,direccion_fisica,codigo_postal,telefono,rol,tipo);
          usuarios.put(usuario.direccion_electronica,usuario);
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
    return usuarios;
  }

  // dar datos básicos - dar tipos de producto
  public Map<Integer,TipoProductoValue> darTiposProducto() throws Exception
  {
    Map<Integer,TipoProductoValue> tiposProducto=new TreeMap<Integer,TipoProductoValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM TIPO_PRODUCTO");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String nombre=resultSet.getString("NOMBRE");
          String descripcion=resultSet.getString("DESCRIPCION");
          TipoProductoValue tipoProducto=new TipoProductoValue(id,nombre,descripcion);
          tiposProducto.put(tipoProducto.id,tipoProducto);
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
    return tiposProducto;
  }

  // dar datos básicos - dar productos
  public Map<Integer,ProductoValue> darProductos() throws Exception
  {
    Map<Integer,ProductoValue> productos=new TreeMap<Integer,ProductoValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM PRODUCTO");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          Integer cod_tipo_producto=resultSet.getInt("COD_TIPO_PRODUCTO");
          String nombre=resultSet.getString("NOMBRE");
          Double peso=resultSet.getDouble("PESO");
          ProductoValue producto=new ProductoValue(id,cod_tipo_producto,nombre,peso);
          productos.put(producto.id,producto);
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
    return productos;
  }

  // dar datos básicos - dar presentaciones
  public Map<Integer,PresentacionValue> darPresentaciones() throws Exception
  {
    Map<Integer,PresentacionValue> presentaciones=new TreeMap<Integer,PresentacionValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM PRESENTACION");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String nombre=resultSet.getString("NOMBRE");
          String descripcion=resultSet.getString("DESCRIPCION");
          String tipo_empaque=resultSet.getString("TIPO_EMPAQUE");
          Double capacidad=resultSet.getDouble("CAPACIDAD");
          PresentacionValue presentacion=new PresentacionValue(id,nombre,descripcion,tipo_empaque,capacidad);
          presentaciones.put(presentacion.id,presentacion);
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
    return presentaciones;
  }

  // dar datos básicos - dar lugares de almacenamiento
  public Map<Integer,LugarAlmacenamientoValue> darLugaresAlmacenamiento() throws Exception
  {
    Map<Integer,LugarAlmacenamientoValue> lugaresAlmacenamiento=new TreeMap<Integer,LugarAlmacenamientoValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM LUGAR_ALMACENAMIENTO");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          Integer cod_tipo_producto=resultSet.getInt("COD_TIPO_PRODUCTO");
          String nombre=resultSet.getString("NOMBRE");
          Double capacidad=resultSet.getDouble("CAPACIDAD");
          LugarAlmacenamientoValue lugarAlmacenamiento=new LugarAlmacenamientoValue(id,cod_tipo_producto,nombre,capacidad);
          lugaresAlmacenamiento.put(lugarAlmacenamiento.id,lugarAlmacenamiento);
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
    return lugaresAlmacenamiento;
  }

  // dar datos básicos - dar bodegas mantenimiento
  public Map<Integer,BodegaValue> darBodegasMantenimiento() throws Exception
  {
    Map<Integer,BodegaValue> bodegas=new TreeMap<Integer,BodegaValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM BODEGA  WHERE ESTADO= 'En mantenimiento'");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String estado=resultSet.getString("ESTADO");
          BodegaValue bodega=new BodegaValue(id,estado);
          bodegas.put(bodega.id,bodega);
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
    return bodegas;
  }
  
//dar datos básicos - dar bodegas mantenimiento
  public Map<Integer,BodegaValue> darBodegasFuncionamiento() throws Exception
  {
    Map<Integer,BodegaValue> bodegas=new TreeMap<Integer,BodegaValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM BODEGA  WHERE ESTADO= 'En funcionamiento'");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String estado=resultSet.getString("ESTADO");
          BodegaValue bodega=new BodegaValue(id,estado);
          bodegas.put(bodega.id,bodega);
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
    return bodegas;
  }
  
  public Map<Integer,BodegaValue> darBodegas() throws Exception
  {
    Map<Integer,BodegaValue> bodegas=new TreeMap<Integer,BodegaValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM BODEGA");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String estado=resultSet.getString("ESTADO");
          BodegaValue bodega=new BodegaValue(id,estado);
          bodegas.put(bodega.id,bodega);
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
    return bodegas;
  }

  // dar datos básicos - dar locales
  public Map<Integer,LocalValue> darLocales() throws Exception
  {
    Map<Integer,LocalValue> locales=new TreeMap<Integer,LocalValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM LOCAL");
      try
      {
        while (resultSet.next())
        {
          Integer id=resultSet.getInt("ID");
          String cod_administrador=resultSet.getString("COD_ADMINISTRADOR");
          LocalValue local=new LocalValue(id,cod_administrador);
          locales.put(local.id,local);
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
    return locales;
  }

  // dar datos básicos - dar proveedores
  public Map<String,ProveedorValue> darProveedores() throws Exception
  {
    Map<String,ProveedorValue> proveedores=new TreeMap<String,ProveedorValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM PROVEEDOR");
      try
      {
        while (resultSet.next())
        {
          String cod_usuario=resultSet.getString("COD_USUARIO");
          Integer cod_tipo_producto=resultSet.getInt("COD_TIPO_PRODUCTO");
          Double calificacion=resultSet.getDouble("CALIFICACION");
          if (resultSet.wasNull())
          {
            calificacion=null;
          }
          ProveedorValue proveedor=new ProveedorValue(cod_usuario,cod_tipo_producto,calificacion);
          proveedores.put(proveedor.cod_usuario,proveedor);
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
    return proveedores;
  }
  
  public Map<Integer,PedidoValue> darPedidos() throws Exception
  {
    Map<Integer,PedidoValue> pedidos=new TreeMap<Integer,PedidoValue>();
    Statement statement=conexion.createStatement();
    try
    {
      ResultSet resultSet=statement.executeQuery("SELECT * FROM PEDIDO");
      try
      {
        while (resultSet.next())
        {
          Integer numero=resultSet.getInt("NUMERO");
          String cod_usuario=resultSet.getString("COD_USUARIO");
          Date fecha_cracion=resultSet.getDate("FECHA_CREACION");
          
          Date fecha_esperada_entrega =resultSet.getDate("FECHA_ESPERADA_ENTREGA");
          if(resultSet.wasNull())
          {
        	  fecha_esperada_entrega=null;
          }
          Date fecha_entrega =resultSet.getDate("FECHA_ENTREGA");
          if (resultSet.wasNull())
          {
        	  fecha_entrega =null;
          }
          Integer precio_cabandes=resultSet.getInt("PRECIO_CABANDES");
          if (resultSet.wasNull())
          {
        	  precio_cabandes =null;
          }
          String ha_sido_cancel=resultSet.getString("HA_SIDO_CANCELADO");
          Boolean ha_sido_cancelado=false;
          if(ha_sido_cancel.equals("Si"))
          {
        	  ha_sido_cancelado=true;
          }
          String ha_sido_ent=resultSet.getString("HA_SIDO_ENTREGADO");
          Boolean ha_sido_entregado=false;
          if(ha_sido_ent.equals("Si"))
          {
        	  ha_sido_entregado=true;
          }
          PedidoValue pedido=new PedidoValue(numero,cod_usuario,fecha_cracion,fecha_esperada_entrega,fecha_entrega,precio_cabandes,ha_sido_cancelado,ha_sido_entregado);
          pedidos.put(pedido.numero,pedido);
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
    return pedidos;
  }

  public Map<String,UsuarioValue> darProvUsuarios() throws Exception
  {
    Map<String,UsuarioValue> usuarios=new TreeMap<String,UsuarioValue>();
   
    PreparedStatement preparedStatement=conexion.prepareStatement("SELECT * FROM USUARIO us WHERE us.ROL='Proveedor'");
    try
    {
      ResultSet resultSet=preparedStatement.executeQuery();
      
      try
      {
        while (resultSet.next())
        {
          String direccion_electronica=resultSet.getString("DIRECCION_ELECTRONICA");
          String nombre=resultSet.getString("NOMBRE");
          String doc_identificacion=resultSet.getString("DOC_IDENTIFICACION");
          String palabra_clave=resultSet.getString("PALABRA_CLAVE");
          String ciudad=resultSet.getString("CIUDAD");
          String departamento=resultSet.getString("DEPARTAMENTO");
          String nacionalidad=resultSet.getString("NACIONALIDAD");
          String direccion_fisica=resultSet.getString("DIRECCION_FISICA");
          Integer codigo_postal=resultSet.getInt("CODIGO_POSTAL");
          if (resultSet.wasNull())
          {
            codigo_postal=null;
          }
          String telefono=resultSet.getString("TELEFONO");
          String rol=resultSet.getString("ROL");
          String tipo=resultSet.getString("TIPO");
          UsuarioValue usuario=new UsuarioValue(direccion_electronica,nombre,doc_identificacion,palabra_clave,ciudad,departamento,nacionalidad,direccion_fisica,codigo_postal,telefono,rol,tipo);
          usuarios.put(usuario.direccion_electronica,usuario);
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
    return usuarios;
  }
}
