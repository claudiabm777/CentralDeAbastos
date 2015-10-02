package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaOfertasProveedores implements ModeloTabla<OfertaProveedorValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"LICITACIÓN","PROVEEDOR","CALIFICACIÓN PROVEEDOR","PRECIO TOTAL OFRECIDO","PRECIO UNITARIO","CANTIDAD PIENSA PROVEER","FECHA ESPERADA ENTREGA","NÚMERO LOTE","FECHA EXPIRACIÓN LOTE"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(OfertaProveedorValue item, int columna)
  {
    DatosBasicosValue datosBasicos=CabAndes.datosBasicos;
    ProveedorValue proveedor=datosBasicos.proveedores.get(item.cod_proveedor);
    switch (columna)
    {
      case 0:
        return item.cod_licitacion;
      case 1:
        return item.cod_proveedor;
      case 2:
        return proveedor.calificacion;
      case 3:
        return item.precio_total_ofrecido;
      case 4:
        return item.precio_unitario;
      case 5:
        return item.cantidad_piensa_proveer;
      case 6:
        return item.fecha_esperada_entrega;
      case 7:
        return item.numero_lote;
      case 8:
        return item.fecha_expiracion_lote;
      default:
        return null;
    }
  }

}
