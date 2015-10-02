package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.DatosBasicosValue;
import co.edu.uniandes.cabAndes.shared.vos.LugarAlmacenamientoValue;
import co.edu.uniandes.cabAndes.shared.vos.MovimientoValue;
import co.edu.uniandes.cabAndes.shared.vos.TipoProductoValue;

public class ModeloTablaMovimiento implements ModeloTabla<MovimientoValue>
{
	public String[] darNombreColumnas()
	  {
	    String[] nombreColumnas={"NUMERO","COD LUGAR ORIGEN","NOMBRE LUGAR ORIGEN","COD LUGAR DESTINO","NOMBRE LUGAR DESTINO","FECHA MOVIMIENTO","COD PRODUCTO","COD PRESENTACION","CANTIDAD"};
	    return nombreColumnas;
	  }
	
	public Object darCelda(MovimientoValue item, int columna)
	  {
	    switch (columna)
	    {
	      case 0:
	        return item.numero;
	      case 1:
	        return item.cod_lugar_origen;
	      case 2:
		    return item.nombre_lugar_origen;
	      case 3:
	        return item.cod_lugar_destino;
	      case 4:
		    return item.nombre_lugar_destino;
	      case 5:
	        return item.fecha_movimiento;
	      case 6:
	    	return item.cod_producto;
	      case 7:
	    	  return item.cod_presentacion;
	      case 8:
	    	  return item.cantidad;
	      default:
	        return null;
	    }
	  }

}
