package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaProductosPedido implements ModeloTabla<ProductoPedidoValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"PEDIDO","PRODUCTO","PRESENTACIÓN","CANTIDAD SOLICITADA","CANTIDAD ENTREGADA"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(ProductoPedidoValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    ProductoValue producto=datosBasicos.productos.get(item.cod_producto);
    PresentacionValue presentacion=datosBasicos.presentaciones.get(item.cod_presentacion);
    switch (columna)
    {
      case 0:
        return item.cod_pedido;
      case 1:
        return producto.nombre;
      case 2:
        return presentacion.nombre;
      case 3:
        return item.cantidad_solicitada;
      case 4:
        return item.cantidad_entregada;
      default:
        return null;
    }
  }

}
