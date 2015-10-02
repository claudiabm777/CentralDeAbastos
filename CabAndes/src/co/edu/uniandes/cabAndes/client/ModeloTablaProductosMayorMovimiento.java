package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaProductosMayorMovimiento implements ModeloTabla<ProductoMayorMovimientoValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID PRODUCTO","TIPO PRODUCTO","NOMBRE PRODUCTO","PRESENTACIÓN","PROMEDIO PESO","COSTO PROMEDIO","CANTIDAD DE VECES QUE HA SIDO PEDIDO","CANTIDAD DE VECES QUE HA SIDO VENDIDO"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(ProductoMayorMovimientoValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    ProductoValue producto=datosBasicos.productos.get(item.cod_producto);
    TipoProductoValue tipoProducto=datosBasicos.tiposProducto.get(producto.cod_tipo_producto);
    PresentacionValue presentacion=datosBasicos.presentaciones.get(item.cod_presentacion);
    switch (columna)
    {
      case 0:
        return producto.id;
      case 1:
        return tipoProducto.nombre;
      case 2:
        return producto.nombre;
      case 3:
        return presentacion.nombre;
      case 4:
        return item.promedio_peso;
      case 5:
        return item.costo_promedio;
      case 6:
        return item.veces_ha_sido_pedido;
      case 7:
        return item.veces_ha_sido_vendido;
      default:
        return null;
    }
  }

}
