package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaProductos implements ModeloTabla<ProductoValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID","TIPO PRODUCTO","NOMBRE","PESO"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(ProductoValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    TipoProductoValue tipoProducto=datosBasicos.tiposProducto.get(item.cod_tipo_producto);
    switch (columna)
    {
      case 0:
        return item.id;
      case 1:
        return tipoProducto.nombre;
      case 2:
        return item.nombre;
      case 3:
        return item.peso;
      default:
        return null;
    }
  }

}
