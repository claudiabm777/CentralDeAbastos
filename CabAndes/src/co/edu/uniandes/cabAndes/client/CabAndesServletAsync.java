package co.edu.uniandes.cabAndes.client;

import java.util.*;

import co.edu.uniandes.cabAndes.shared.vos.*;

import com.google.gwt.user.client.rpc.*;

public interface CabAndesServletAsync
{

  // dar datos básicos
  void darDatosBasicos(AsyncCallback<DatosBasicosValue> callback);

  // RF1 - dar existencias de productos
  void darExistenciasProductos(Integer idProducto, Integer idTipoProducto, Integer idPresentacion, Date fechaMinimaExpiracion, Date fechaMaximaExpiracion, Integer idLugarAlmacenamiento, AsyncCallback<List<ProductoLugarAlmacValue>> callback);

  // RF2 - dar pedidos de un usuario
  void darPedidosUsuario(String direccionElectronicaUsuario, Boolean haSidoEntregado, Date fechaMinimaCreacion, Date fechaMaximaCreacion, Integer costoMinimo, Integer costoMaximo, Integer idProducto, Integer idPresentacion, AsyncCallback<List<PedidoValue>> callback);

  // RF2 - dar productos de un pedido
  void darProductosPedido(Integer numeroPedido, AsyncCallback<List<ProductoPedidoValue>> callback);

  // RF3a - entregar producto por parte de un proveedor
  void entregarProductoPorParteDeProveedor(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEntrega, AsyncCallback<Void> callback);

  // RF3b - enviar productos de una bodega a un local de ventas
  void enviarProductosDeBodegaALocal(Integer numeroPedido, AsyncCallback<Void> callback);

  // RF3c - vender un producto en un local de ventas
  void venderProductoEnLocal(VentaValue venta, AsyncCallback<Void> callback);

  // RF3d - vender productos de una bodega a un comprador mayorista
  void venderProductosBodegaCompradorMayorista(Integer numeroPedido, AsyncCallback<Void> callback);

  // RF3e - eliminar productos que cumplieron con la fecha de expiración
  void eliminarProductosExpirados(Date fechaActualDeCorte, AsyncCallback<Void> callback);

  // RF4a - registrar pedido
  void registrarPedido(PedidoValue pedido, List<ProductoPedidoValue> productosPedido, AsyncCallback<Void> callback);

  // RF4b - satisfacer pedido
  void satisfacerPedido(Integer numeroPedido, AsyncCallback<Void> callback);

  // RF5 - dar licitaciones cerradas sin asignar
  void darLicitacionesCerradasSinAsignar(Date fechaActualDeCorte, AsyncCallback<List<LicitacionValue>> callback);

  // RF5 - dar ofertas de los proveedores para una licitación
  void darOfertasProveedores(Integer numeroLicitacion, AsyncCallback<List<OfertaProveedorValue>> callback);

  // RF5 - escoger proveedor para una licitación
  void escogerProveedorParaLicitacion(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEsperadaEntrega, AsyncCallback<Void> callback);

  // RF6 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
  void darProductosMayorMovimiento(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento, AsyncCallback<List<ProductoMayorMovimientoValue>> callback);

  // RF7 - dar el local de ventas con el mayor número de ventas
  void darLocalesMayorNumeroVentas(Integer idTipoProducto, Integer idProducto, AsyncCallback<List<LocalMayorNumeroVentasValue>> callback);
  
  // RF8 -cerrar bodega
  void cerrarBodega(Integer id, Integer textoMotivoCierre, AsyncCallback<Void> callback);

  // RF9 - realizar el registro de una nueva bodega
  void registrarBodega(Integer textoIdPBodega,Integer textoTipoProducto, String textoNombre, Double textoCapacidad, AsyncCallback<Void> callback);
  
  //RF9 - PONER BODEGA EN FUNCIONAMIENTO
  void ponerBodegaEnFuncionamiento(Integer id,AsyncCallback<Void> callback);
  
  //RF10 - dar productos de una bodega
  void darProductosBodega(Integer id,AsyncCallback <List<ProductoLugarAlmacValue>> callback);
  
  //RF10 - dar pedidos de una bodega
  void darPedidosBodega(Integer id,AsyncCallback <List<BodegaPedidoValue>> callback);
  
  //RF11 - dar licitaciones de un proveedor
  void darLicitacionesProveedor(String id,AsyncCallback <List<LicitacionValue>> callback);
  
  //RF11 - dar ofertas de proveedor
  void darOfertasProveedor(String id,AsyncCallback <List<OfertaProveedorValue>> callback);
  
  //RF11
  void  darProvUsuario(String id, AsyncCallback <Void> callback);
  
  //RF12
  void darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima,AsyncCallback <List<PedidoValue>> callback);
  
  //RF13
  void darBodegasCapacidad(Double porcentaje,Integer idTipoProducto,AsyncCallback <List<BodegaValue>> callback);
  
  //RF14
  void darMovmientos(Integer cantidad ,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima,AsyncCallback <List<MovimientoValue>> callback);
  
  //RF15
  void darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima,AsyncCallback <List<MovimientoValue>> callback);

  // RF23
  void satisfacerPedidoRF23(Integer numeroPedido, AsyncCallback<Void> callback);


}
