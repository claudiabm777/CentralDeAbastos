package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaPedidos implements ModeloTabla<PedidoValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"NÚMERO","USUARIO","FECHA CREACIÓN","FECHA ESPERADA ENTREGA","FECHA ENTREGA","PRECIO CABANDES","HA SIDO CANCELADO","HA SIDO ENTREGADO"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(PedidoValue item, int columna)
  {
    switch (columna)
    {
      case 0:
        return item.numero;
      case 1:
        return item.cod_usuario;
      case 2:
        return item.fecha_creacion;
      case 3:
        return item.fecha_esperada_entrega;
      case 4:
        return item.fecha_entrega;
      case 5:
        return item.precio_cabandes;
      case 6:
        return item.ha_sido_cancelado;
      case 7:
        return item.ha_sido_entregado;
      default:
        return null;
    }
  }

}
