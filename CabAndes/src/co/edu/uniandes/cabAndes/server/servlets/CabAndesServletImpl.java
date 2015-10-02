package co.edu.uniandes.cabAndes.server.servlets;

import java.util.*;

import co.edu.uniandes.cabAndes.client.*;
import co.edu.uniandes.cabAndes.server.fachada.*;
import co.edu.uniandes.cabAndes.server.jms.ColaMensajes;
import co.edu.uniandes.cabAndes.server.jms.RecibidorMensajes;
import co.edu.uniandes.cabAndes.shared.vos.*;

import com.google.gwt.user.server.rpc.*;

@SuppressWarnings("serial")
public class CabAndesServletImpl extends RemoteServiceServlet implements CabAndesServlet
{
	
	  static
	  {
		 System.out.println("Creando recibidor de mensajes del grupo 9");
		 RecibidorMensajes recibidorMensajes=new RecibidorMensajes();
		 try
		 {
			 recibidorMensajes.recibirMensajes(ColaMensajes.COLA_PETICIONES_GRUPO9);
			 System.out.println("Creado recibidor de mensajes del grupo 9");
		 }
		 catch (Throwable th)
		 {
			 th.printStackTrace();
		 }
	  }

  // dar datos básicos
  @Override
  public DatosBasicosValue darDatosBasicos() throws Exception
  {
    return FachadaAdmin.darInstancia().darDatosBasicos();
  }

  // RF1 - dar existencias de productos
  @Override
  public List<ProductoLugarAlmacValue> darExistenciasProductos(Integer idProducto, Integer idTipoProducto, Integer idPresentacion, Date fechaMinimaExpiracion, Date fechaMaximaExpiracion, Integer idLugarAlmacenamiento) throws Exception
  {
    return Fachada.darInstancia().darExistenciasProductos(idProducto,idTipoProducto,idPresentacion,fechaMinimaExpiracion,fechaMaximaExpiracion,idLugarAlmacenamiento);
  }

  // RF2 - dar pedidos de un usuario
  @Override
  public List<PedidoValue> darPedidosUsuario(String direccionElectronicaUsuario, Boolean haSidoEntregado, Date fechaMinimaCreacion, Date fechaMaximaCreacion, Integer costoMinimo, Integer costoMaximo, Integer idProducto, Integer idPresentacion) throws Exception
  {
    return Fachada.darInstancia().darPedidosUsuario(direccionElectronicaUsuario,haSidoEntregado,fechaMinimaCreacion,fechaMaximaCreacion,costoMinimo,costoMaximo,idProducto,idPresentacion);
  }

  // RF2 - dar productos de un pedido
  @Override
  public List<ProductoPedidoValue> darProductosPedido(Integer numeroPedido) throws Exception
  {
    return Fachada.darInstancia().darProductosPedido(numeroPedido);
  }

  // RF3a - entregar producto por parte de un proveedor
  @Override
  public void entregarProductoPorParteDeProveedor(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEntrega) throws Exception
  {
    Fachada.darInstancia().entregarProductoPorParteDeProveedor(numeroLicitacion,direccionElectronicaProveedor,fechaEntrega);
  }

  // RF3b - enviar productos de una bodega a un local de ventas
  @Override
  public void enviarProductosDeBodegaALocal(Integer numeroPedido) throws Exception
  {
    Fachada.darInstancia().enviarProductosDeBodegaALocal(numeroPedido);
  }

  // RF3c - vender un producto en un local de ventas
  @Override
  public void venderProductoEnLocal(VentaValue venta) throws Exception
  {
    Fachada.darInstancia().venderProductoEnLocal(venta);
  }

  // RF3d - vender productos de una bodega a un comprador mayorista
  @Override
  public void venderProductosBodegaCompradorMayorista(Integer numeroPedido) throws Exception
  {
    Fachada.darInstancia().venderProductosBodegaCompradorMayorista(numeroPedido);
  }

  // RF3e - eliminar productos que cumplieron con la fecha de expiración
  @Override
  public void eliminarProductosExpirados(Date fechaActualDeCorte) throws Exception
  {
    Fachada.darInstancia().eliminarProductosExpirados(fechaActualDeCorte);
  }

  // RF4a - registrar pedido
  @Override
  public void registrarPedido(PedidoValue pedido, List<ProductoPedidoValue> productosPedido) throws Exception
  {
    Fachada.darInstancia().registrarPedido(pedido,productosPedido);
  }

  // RF4b - satisfacer pedido
  @Override
  public void satisfacerPedido(Integer numeroPedido) throws Exception
  {
    Fachada.darInstancia().satisfacerPedido(numeroPedido);
  }

  // RF5 - dar licitaciones cerradas sin asignar
  @Override
  public List<LicitacionValue> darLicitacionesCerradasSinAsignar(Date fechaActualDeCorte) throws Exception
  {
    return Fachada.darInstancia().darLicitacionesCerradasSinAsignar(fechaActualDeCorte);
  }

  // RF5 - dar ofertas de los proveedores para una licitación
  @Override
  public List<OfertaProveedorValue> darOfertasProveedores(Integer numeroLicitacion) throws Exception
  {
    return Fachada.darInstancia().darOfertasProveedores(numeroLicitacion);
  }

  // RF5 - escoger proveedor para una licitación
  @Override
  public void escogerProveedorParaLicitacion(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEsperadaEntrega) throws Exception
  {
    Fachada.darInstancia().escogerProveedorParaLicitacion(numeroLicitacion,direccionElectronicaProveedor,fechaEsperadaEntrega);
  }

  // RF6 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
  @Override
  public List<ProductoMayorMovimientoValue> darProductosMayorMovimiento(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento) throws Exception
  {
    return Fachada.darInstancia().darProductosMayorMovimiento(fechaMinimaMovimiento,fechaMaximaMovimiento);
  }

  // RF7 - dar el local de ventas con el mayor número de ventas
  @Override
  public List<LocalMayorNumeroVentasValue> darLocalesMayorNumeroVentas(Integer idTipoProducto, Integer idProducto) throws Exception
  {
    return Fachada.darInstancia().darLocalesMayorNumeroVentas(idTipoProducto,idProducto);
  }
//RF8 -cerrar bodega
  public void cerrarBodega(Integer id, Integer textoMotivoCierre)throws Exception
  {
	  Fachada.darInstancia().cerrarBodega(id, textoMotivoCierre);
  }
  
//RF9 - realizar el registro de una nueva bodega
  public void registrarBodega(Integer textoIdPBodega,Integer textoTipoProducto, String textoNombre, Double textoCapacidad) throws Exception
  {
	 Fachada.darInstancia().registrarBodega(textoIdPBodega, textoTipoProducto, textoNombre,  textoCapacidad);
  }
//RF9 -PONER BODEGA EN FUNCIONAMIENTO
  public void ponerBodegaEnFuncionamiento(Integer id)throws Exception
  {
	  Fachada.darInstancia().ponerBodegaEnFuncionamiento(id);
  }
@Override
//RF10 - dar productos de una bodega
public List<ProductoLugarAlmacValue> darProductosBodega(Integer id)throws Exception 
{
	return Fachada.darInstancia().darProductosBodega(id);
}

//RF10 - dar pedidos de una bodega
public List<BodegaPedidoValue> darPedidosBodega(Integer id) throws Exception
{
	return Fachada.darInstancia().darPedidosBodega(id);
}

//RF11 - dar licitaciones de un proveedor
public List<LicitacionValue> darLicitacionesProveedor(String id) throws Exception
{
	return Fachada.darInstancia().darLicitacionesProveedor(id);
}

//RF11 - dar ofertas de proveedor
public List<OfertaProveedorValue> darOfertasProveedor(String id)throws Exception
{
	return Fachada.darInstancia().darOfertasProveedor(id);
}
//rf11
public Map<String,UsuarioValue> darProvUsuario(String id)throws Exception
{
	return Fachada.darInstancia().darProvUsuario(id);
}

//RF12
public List<PedidoValue> darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima)throws Exception
{
	return Fachada.darInstancia().darPedidosVigentesLocales(id,fechaMinima,fechaMaxima);
}

//RF13
public List<BodegaValue> darBodegasCapacidad(Double porcentaje,Integer idTipoProducto)throws Exception
{
	return Fachada.darInstancia().darBodegasCapacidad( porcentaje, idTipoProducto);
}
//RF14
public List<MovimientoValue> darMovmientos(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
{
	return Fachada.darInstancia().darMovmientos( cantidad,idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
}
//RF15
public List<MovimientoValue> darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception
{
	return Fachada.darInstancia().darMovmientosNo( cantidad,idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
}

//RF15
public void satisfacerPedidoRF23(Integer numeroPedido)throws Exception
{
	Fachada.darInstancia().satisfacerPedidoRF23(numeroPedido);
}

}
