package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaLocalesMayorNumeroVentas implements ModeloTabla<LocalMayorNumeroVentasValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"ID LOCAL","TIPO PRODUCTO LOCAL","NOMBRE LOCAL","CAPACIDAD LOCAL","ADMINISTRADOR LOCAL","NUMERO VENTAS LOCAL"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(LocalMayorNumeroVentasValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    LugarAlmacenamientoValue lugarAlmacenamiento=datosBasicos.lugaresAlmacenamiento.get(item.cod_local);
    LocalValue local=datosBasicos.locales.get(item.cod_local);
    TipoProductoValue tipoProducto=datosBasicos.tiposProducto.get(lugarAlmacenamiento.cod_tipo_producto);
    switch (columna)
    {
      case 0:
        return local.id;
      case 1:
        return tipoProducto.nombre;
      case 2:
        return lugarAlmacenamiento.nombre;
      case 3:
        return lugarAlmacenamiento.capacidad;
      case 4:
        return local.cod_administrador;
      case 5:
        return item.numero_ventas;
      default:
        return null;
    }
  }

}
