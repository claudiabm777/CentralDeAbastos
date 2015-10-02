package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaProveedores implements ModeloTabla<ProveedorValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"USUARIO","TIPO PRODUCTO","CALIFICACIÓN"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(ProveedorValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    TipoProductoValue tipoProducto=datosBasicos.tiposProducto.get(item.cod_tipo_producto);
    switch (columna)
    {
      case 0:
        return item.cod_usuario;
      case 1:
        return tipoProducto.nombre;
      case 2:
        return item.calificacion;
      default:
        return null;
    }
  }
}
