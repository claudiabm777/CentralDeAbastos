package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaProductosLugarAlmacenamiento implements ModeloTabla<ProductoLugarAlmacValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID LUGAR ALMACENAMIENTO","NOMBRE LUGAR ALMACENAMIENTO","PRODUCTO","PRESENTACIÓN","CANTIDAD","NÚMERO LOTE","FECHA EXPIRACIÓN LOTE","PROMOCIÓN LOTE"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(ProductoLugarAlmacValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    LugarAlmacenamientoValue lugarAlmacenamiento=datosBasicos.lugaresAlmacenamiento.get(item.cod_lugar_almac);
    ProductoValue producto=datosBasicos.productos.get(item.cod_producto);
    PresentacionValue presentacion=datosBasicos.presentaciones.get(item.cod_presentacion);
    switch (columna)
    {
      case 0:
        return item.cod_lugar_almac;
      case 1:
        return lugarAlmacenamiento.nombre;
      case 2:
        return producto.nombre;
      case 3:
        return presentacion.nombre;
      case 4:
        return item.cantidad;
      case 5:
        return item.numero_lote;
      case 6:
        return item.fecha_expiracion_lote;
      case 7:
        return item.promocion_lote;
      default:
        return null;
    }
  }

}
