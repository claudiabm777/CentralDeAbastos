package co.edu.uniandes.cabAndes.client;

import java.util.*;

import co.edu.uniandes.cabAndes.shared.vos.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("servlet")
public interface CabAndesServlet extends RemoteService
{

  // dar datos básicos
  DatosBasicosValue darDatosBasicos() throws Exception;

  // RF1 - dar existencias de productos
  List<ProductoLugarAlmacValue> darExistenciasProductos(Integer idProducto, Integer idTipoProducto, Integer idPresentacion, Date fechaMinimaExpiracion, Date fechaMaximaExpiracion, Integer idLugarAlmacenamiento) throws Exception;

  // RF2 - dar pedidos de un usuario
  List<PedidoValue> darPedidosUsuario(String direccionElectronicaUsuario, Boolean haSidoEntregado, Date fechaMinimaCreacion, Date fechaMaximaCreacion, Integer costoMinimo, Integer costoMaximo, Integer idProducto, Integer idPresentacion) throws Exception;

  // RF2 - dar productos de un pedido
  List<ProductoPedidoValue> darProductosPedido(Integer numeroPedido) throws Exception;

  // RF3a - entregar producto por parte de un proveedor
  void entregarProductoPorParteDeProveedor(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEntrega) throws Exception;

  // RF3b - enviar productos de una bodega a un local de ventas
  void enviarProductosDeBodegaALocal(Integer numeroPedido) throws Exception;

  // RF3c - vender un producto en un local de ventas
  void venderProductoEnLocal(VentaValue venta) throws Exception;

  // RF3d - vender productos de una bodega a un comprador mayorista
  void venderProductosBodegaCompradorMayorista(Integer numeroPedido) throws Exception;

  // RF3e - eliminar productos que cumplieron con la fecha de expiración
  void eliminarProductosExpirados(Date fechaActualDeCorte) throws Exception;

  // RF4a - registrar pedido
  void registrarPedido(PedidoValue pedido, List<ProductoPedidoValue> productosPedido) throws Exception;

  // RF4b - satisfacer pedido
  void satisfacerPedido(Integer numeroPedido) throws Exception;

  // RF5 - dar licitaciones cerradas sin asignar
  List<LicitacionValue> darLicitacionesCerradasSinAsignar(Date fechaActualDeCorte) throws Exception;

  // RF5 - dar ofertas de los proveedores para una licitación
  List<OfertaProveedorValue> darOfertasProveedores(Integer numeroLicitacion) throws Exception;

  // RF5 - escoger proveedor para una licitación
  void escogerProveedorParaLicitacion(Integer numeroLicitacion, String direccionElectronicaProveedor, Date fechaEsperadaEntrega) throws Exception;

  // RF6 - dar los productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)
  List<ProductoMayorMovimientoValue> darProductosMayorMovimiento(Date fechaMinimaMovimiento, Date fechaMaximaMovimiento) throws Exception;

  // RF7 - dar el local de ventas con el mayor número de ventas
  List<LocalMayorNumeroVentasValue> darLocalesMayorNumeroVentas(Integer idTipoProducto, Integer idProducto) throws Exception;
  
  //RF8 -cerrar bodega
  void cerrarBodega(Integer id, Integer textoMotivoCierre)throws Exception;
  
  //RF9 - realizar el registro de una nueva bodega
  void registrarBodega(Integer textoIdPBodega,Integer textoTipoProducto, String textoNombre, Double textoCapacidad) throws Exception;
  
  //RF9 - poner bodega en funcionamiento
  void ponerBodegaEnFuncionamiento(Integer id)throws Exception;
  
  //RF10 - dar productos de una bodega
  List<ProductoLugarAlmacValue> darProductosBodega(Integer id) throws Exception;
  
  //RF10 - dar pedidos de una bodega
  List<BodegaPedidoValue> darPedidosBodega(Integer id) throws Exception;
  
  //RF11 - dar licitaciones de un proveedor
  List<LicitacionValue> darLicitacionesProveedor(String id) throws Exception;
  
  //RF11 - dar ofertas de proveedor
  List<OfertaProveedorValue> darOfertasProveedor(String id)throws Exception;

  //RF11
  Map<String,UsuarioValue> darProvUsuario(String id)throws Exception;
  
  //RF12
  List<PedidoValue> darPedidosVigentesLocales(Integer id, Date fechaMinima, Date fechaMaxima)throws Exception;
  
  //RF13
  List<BodegaValue> darBodegasCapacidad(Double porcentaje,Integer idTipoProducto)throws Exception;
  
  //RF14
  List<MovimientoValue> darMovmientos(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception;
  
  //RF15
  List<MovimientoValue> darMovmientosNo(Integer cantidad,Integer idProducto,Integer idPresentacion,Integer idBodegaOrigen,Integer idLugarDestino,Date fechaMinima,Date fechaMaxima)throws Exception;
 
  // RF23
  void satisfacerPedidoRF23(Integer numeroPedido)throws Exception;


}
