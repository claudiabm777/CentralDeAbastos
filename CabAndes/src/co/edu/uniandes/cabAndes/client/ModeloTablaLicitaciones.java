package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaLicitaciones implements ModeloTabla<LicitacionValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"NÚMERO","PRODUCTO","PRESENTACION","CANTIDAD SOLICITADA","CANTIDAD RECIBIDA","PROVEEDOR ELEGIDO","FECHA CREACIÓN","FECHA MÁXIMA PROPUESTAS","FECHA ESPERADA ENTREGA","FECHA ENTREGA","CALIFICACIÓN ENTREGA","HA SIDO ELEGIDA","HA SIDO SATISFECHA"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(LicitacionValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    ProductoValue producto=datosBasicos.productos.get(item.cod_producto);
    PresentacionValue presentacion=datosBasicos.presentaciones.get(item.cod_presentacion);
    switch (columna)
    {
      case 0:
        return item.numero;
      case 1:
        return producto.nombre;
      case 2:
        return presentacion.nombre;
      case 3:
        return item.cantidad_solicitada;
      case 4:
        return item.cantidad_recibida;
      case 5:
        return item.cod_proveedor_elegido;
      case 6:
        return item.fecha_creacion;
      case 7:
        return item.fecha_maxima_propuestas;
      case 8:
        return item.fecha_esperada_entrega;
      case 9:
        return item.fecha_entrega;
      case 10:
        return item.calificacion_entrega;
      case 11:
        return item.ha_sido_elegida;
      case 12:
        return item.ha_sido_satisfecha;
      default:
        return null;
    }
  }

}
