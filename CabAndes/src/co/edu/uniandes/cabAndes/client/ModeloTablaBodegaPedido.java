package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaPedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.DatosBasicosValue;
import co.edu.uniandes.cabAndes.shared.vos.LugarAlmacenamientoValue;
import co.edu.uniandes.cabAndes.shared.vos.PedidoValue;

public class ModeloTablaBodegaPedido implements ModeloTabla<BodegaPedidoValue>
{
	@Override
	  public String[] darNombreColumnas()
	  {
	    String[] nombreColumnas={"ID BODEGA","NUMERO PEDIDO","USUARIO PEDIDO","FECHA CREACIÓN PEDIDO","FECHA ESPERADA ENTREGA","FECHA ENTREGA","PRECIO CABANDES","HA SIDO CANCELADO","HA SIDO ENTREGADO"};
	    return nombreColumnas;
	  }

	  public Object darCelda(BodegaPedidoValue item, int columna)
	  {
		  DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
		  PedidoValue pedido=datosBasicos.pedidos.get(item.cod_pedido); 
		  LugarAlmacenamientoValue lugar=datosBasicos.lugaresAlmacenamiento.get(item.cod_bodega); 
	    switch (columna)
	    {
	      case 0:
	        return lugar.id;
	      case 1:
	        return pedido.numero;
	      case 2:
	    	  return pedido.cod_usuario;
	      case 3:
	    	  return pedido.fecha_creacion;
	      case 4:
	    	  return pedido.fecha_esperada_entrega;
	      case 5:
	    	  return pedido.fecha_entrega;
	      case 6:
	    	  return pedido.precio_cabandes;
	      case 7:
	    	  return pedido.ha_sido_cancelado;
	      case 8:
	    	  return pedido.ha_sido_entregado;
	      default:
	        return null;
	    }
	  }

}
