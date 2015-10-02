package co.edu.uniandes.cabAndes.server.fachada;

import java.util.*;

import co.edu.uniandes.cabAndes.server.dao.*;
import co.edu.uniandes.cabAndes.shared.vos.*;

public class Fachada
{

  private static Fachada instancia=null;

  public static Fachada darInstancia()
  {
    if (instancia==null)
    {
      instancia=new Fachada();
    }
    return instancia;
  }

  private Fachada()
  {
  }

  // RF1 - dar existencias de productos
  public List<ProductoLugarAlmacValue> darExistenciasProductos(Integer idProducto, Integer idTipoProducto, Integer idPresentacion, Date fechaMinimaExpiracion, Date fechaMaximaExpiracion, Integer idLugarAlmacenamiento) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darExistenciasProductos(idProducto,idTipoProducto,idPresentacion,fechaMinimaExpiracion,fechaMaximaExpiracion,idLugarAlmacenamiento);
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

  // RF2 - dar pedidos de un usuario
  public List<PedidoValue> darPedidosUsuario(String direccionElectronicaUsuario, Boolean haSidoEntregado, Date fechaMinimaCreacion, Date fechaMaximaCreacion, Integer costoMinimo, Integer costoMaximo, Integer idProducto, Integer idPresentacion) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darPedidosUsuario(direccionElectronicaUsuario,haSidoEntregado,fechaMinimaCreacion,fechaMaximaCreacion,costoMinimo,costoMaximo,idProducto,idPresentacion);
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

  // RF2 - dar productos de un pedido
  public List<ProductoPedidoValue> darProductosPedido(Integer numeroPedido) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darProductosPedido(numeroPedido);
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

  // RF3a - entregar producto por parte de un proveedor
  public void entregarProductoPorParteDeProveedor(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEntrega) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.entregarProductoPorParteDeProveedor(numeroLicitacion,direccionElectronicaProveedor,fechaEntrega);
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

  // RF3b - enviar productos de una bodega a un local de ventas
  public void enviarProductosDeBodegaALocal(Integer numeroPedido) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.enviarProductosDeBodegaALocal(numeroPedido);
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

  // RF3c - vender un producto en un local de ventas
  public void venderProductoEnLocal(VentaValue venta) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.venderProductoEnLocal(venta);
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

  // RF3d - vender productos de una bodega a un comprador mayorista
  public void venderProductosBodegaCompradorMayorista(Integer numeroPedido) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.venderProductosBodegaCompradorMayorista(numeroPedido);
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

  // RF3e - eliminar productos que cumplieron con la fecha de expiración
  public void eliminarProductosExpirados(Date fechaActualDeCorte) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.eliminarProductosExpirados(fechaActualDeCorte);
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

  // RF4a - registrar pedido
  public void registrarPedido(PedidoValue pedido, List<ProductoPedidoValue> productosPedido) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.registrarPedido(pedido,productosPedido);
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

  // RF4b - satisfacer pedido
  public void satisfacerPedido(Integer numeroPedido) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.satisfacerPedido(numeroPedido);
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

  // RF5 - dar licitaciones cerradas sin asignar
  public List<LicitacionValue> darLicitacionesCerradasSinAsignar(Date fechaActualDeCorte) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darLicitacionesCerradasSinAsignar(fechaActualDeCorte);
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

  // RF5 - dar ofertas de los proveedores para una licitación
  public List<OfertaProveedorValue> darOfertasProveedores(Integer numeroLicitacion) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darOfertasProveedores(numeroLicitacion);
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

  // RF5 - escoger proveedor para una licitación
  public void escogerProveedorParaLicitacion(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEsperadaEntrega) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      dao.escogerProveedorParaLicitacion(numeroLicitacion,direccionElectronicaProveedor,fechaEsperadaEntrega);
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

  // RF6 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
  public List<ProductoMayorMovimientoValue> darProductosMayorMovimiento(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darProductosMayorMovimientoRF26(fechaMinimaMovimiento,fechaMaximaMovimiento);
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

  // RF7 - dar el local de ventas con el mayor número de ventas
  public List<LocalMayorNumeroVentasValue> darLocalesMayorNumeroVentas(Integer idTipoProducto, Integer idProducto) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darLocalesMayorNumeroVentas(idTipoProducto,idProducto);
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
  //RF8 -cerrar bodega
  public void cerrarBodega(Integer id, Integer textoMotivoCierre)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	  try
	    {
	      dao.cerrarBodega2(id, textoMotivoCierre);
	    }
	    catch (Throwable th)
	    {
	 th.printStackTrace();	    	
	      throw new Exception(th.getMessage());
	    }
	    finally
	    {
	      dao.cerrarConexion();
	    }
  }
  //RF9
  public void registrarBodega(Integer textoIdPBodega,Integer textoTipoProducto, String textoNombre, Double textoCapacidad) throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	  try
	    {
	      dao.registrarBodega(textoIdPBodega,textoTipoProducto,textoNombre,textoCapacidad);
	    }
	    catch (Throwable th)
	    {
	 th.printStackTrace();	    	
	      throw new Exception(th.getMessage());
	    }
	    finally
	    {
	      dao.cerrarConexion();
	    }
	  
  }
  //RF9
  public void ponerBodegaEnFuncionamiento(Integer id)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	  try
	    {
	      dao.ponerBodegaEnFuncionamiento(id);
	    }
	    catch (Throwable th)
	    {
th.printStackTrace();	    	
	      throw new Exception(th.getMessage());
	    }
	    finally
	    {
	      dao.cerrarConexion();
	    }
  }
  //RF10- dar productos de una bodega
  public List<ProductoLugarAlmacValue> darProductosBodega(Integer id) throws Exception
  {
    AdminDAO dao=new AdminDAO();
    try
    {
      return dao.darProductosBodega(id);
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
  //RF10 - dar pedidos de una bodega
  public List<BodegaPedidoValue> darPedidosBodega(Integer id) throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	      return dao.darPedidosBodega(id);
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
  //RF11 DAR LICITACIONES DE PROVEEDOR
  public List<LicitacionValue> darLicitacionesProveedor(String id) throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	      return dao.darLicitacionesProveedor(id);
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
  //RF11 DAR OFERTAS DE PROVEEDOR
  public List<OfertaProveedorValue> darOfertasProveedor(String id)throws Exception
  {
	  	AdminDAO dao=new AdminDAO();
	    try
	    {
	      return dao.darOfertasProveedor(id);
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
  
  public Map<String,UsuarioValue> darProvUsuario(String id)throws Exception
  {
	  	AdminDAOCatalogo dao=new AdminDAOCatalogo();
	    try
	    {
	      return dao.darProvUsuarios();
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
  //RF12
  public List<PedidoValue> darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima)throws Exception
  {
	  	AdminDAO dao=new AdminDAO();
	    try
	    {
	      return dao.darPedidosVigentesLocales(id,fechaMinima,fechaMaxima);
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
  
  //RF13
  public List<BodegaValue> darBodegasCapacidad(Double porcentaje,Integer idTipoProducto)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	      return dao.darBodegasCapacidad(porcentaje,idTipoProducto);
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
  
  //RF14
  public List<MovimientoValue> darMovmientos(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	    	return dao.darMovmientosRF25( cantidad,idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
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
  
  //RF15
  public List<MovimientoValue> darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	    	return dao.darMovmientosNo(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
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
  
  // RF23
  public void satisfacerPedidoRF23(Integer numeroPedido)throws Exception
  {
	  AdminDAO dao=new AdminDAO();
	    try
	    {
	    	dao.satisfacerPedido2(numeroPedido);
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
