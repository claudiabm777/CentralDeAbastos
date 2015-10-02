package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaTiposProducto implements ModeloTabla<TipoProductoValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID","NOMBRE","DESCRIPCIÓN"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(TipoProductoValue item, int columna)
  {
    switch (columna)
    {
      case 0:
        return item.id;
      case 1:
        return item.nombre;
      case 2:
        return item.descripcion;
      default:
        return null;
    }
  }

}
