package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaBodegas implements ModeloTabla<BodegaValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID","TIPO PRODUCTO","NOMBRE","CAPACIDAD","ESTADO"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(BodegaValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    LugarAlmacenamientoValue lugarAlmacenamiento=datosBasicos.lugaresAlmacenamiento.get(item.id);
    TipoProductoValue tipoProducto=datosBasicos.tiposProducto.get(lugarAlmacenamiento.cod_tipo_producto);
    switch (columna)
    {
      case 0:
        return item.id;
      case 1:
        return tipoProducto.nombre;
      case 2:
        return lugarAlmacenamiento.nombre;
      case 3:
        return lugarAlmacenamiento.capacidad;
      case 4:
    	return item.estado;
      default:
        return null;
    }
  }

}
