package co.edu.uniandes.cabAndes.server.fachada;

import java.util.*;
import co.edu.uniandes.cabAndes.server.dao.*;
import co.edu.uniandes.cabAndes.shared.vos.*;

public class FachadaAdmin
{

  private static FachadaAdmin instancia=null;

  public static FachadaAdmin darInstancia()
  {
    if (instancia==null)
    {
      instancia=new FachadaAdmin();
    }
    return instancia;
  }

  private FachadaAdmin()
  {
  }

  // dar datos básicos
  public DatosBasicosValue darDatosBasicos() throws Exception
  {
    AdminDAOCatalogo dao=new AdminDAOCatalogo();
    try
    {
      Map<String,UsuarioValue> usuarios=dao.darUsuarios();
      Map<Integer,TipoProductoValue> tiposProducto=dao.darTiposProducto();
      Map<Integer,ProductoValue> productos=dao.darProductos();
      Map<Integer,PresentacionValue> presentaciones=dao.darPresentaciones();
      Map<Integer,LugarAlmacenamientoValue> lugaresAlmacenamiento=dao.darLugaresAlmacenamiento();
      Map<Integer,BodegaValue> bodegas=dao.darBodegas();
      Map<Integer,LocalValue> locales=dao.darLocales();
      Map<String,ProveedorValue> proveedores=dao.darProveedores();
      Map<Integer,PedidoValue>pedidos=dao.darPedidos();
      Map<String,UsuarioValue> proveedorUsuario=dao.darProvUsuarios();
      Map<Integer,BodegaValue> bodegasMantenimiento=dao.darBodegasMantenimiento();
      Map<Integer,BodegaValue> bodegasFuncionamiento=dao.darBodegasFuncionamiento();
      
      DatosBasicosValue datosBasicos=new DatosBasicosValue(usuarios,tiposProducto,productos,presentaciones,lugaresAlmacenamiento,bodegas,locales,proveedores,pedidos,proveedorUsuario,bodegasMantenimiento,bodegasFuncionamiento);
      return datosBasicos;
    }
    catch (Throwable th)
    {
      throw new Exception(th.getMessage());
    }
    finally
    {
      dao.cerrarConexion();
    }
  }

}
