package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaPresentaciones implements ModeloTabla<PresentacionValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID","NOMBRE","DESCRIPCIÓN","TIPO EMPAQUE","CAPACIDAD"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(PresentacionValue item, int columna)
  {
    switch (columna)
    {
      case 0:
        return item.id;
      case 1:
        return item.nombre;
      case 2:
        return item.descripcion;
      case 3:
        return item.tipo_empaque;
      case 4:
        return item.capacidad;
      default:
        return null;
    }
  }

}
