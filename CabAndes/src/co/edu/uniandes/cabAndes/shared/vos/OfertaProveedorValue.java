package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase OfertaProveedorValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class OfertaProveedorValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-4130645597542274846L;

  public Integer cod_licitacion;
  public String cod_proveedor;
  public Integer precio_total_ofrecido;
  public Integer precio_unitario;
  public Integer cantidad_piensa_proveer;
  public java.util.Date fecha_esperada_entrega;
  public Integer numero_lote;
  public java.util.Date fecha_expiracion_lote;

  public OfertaProveedorValue()
  {
  }

  public OfertaProveedorValue(Integer cod_licitacion, String cod_proveedor, Integer precio_total_ofrecido, Integer precio_unitario, Integer cantidad_piensa_proveer, java.util.Date fecha_esperada_entrega, Integer numero_lote, java.util.Date fecha_expiracion_lote)
  {
    this.cod_licitacion=cod_licitacion;
    this.cod_proveedor=cod_proveedor;
    this.precio_total_ofrecido=precio_total_ofrecido;
    this.precio_unitario=precio_unitario;
    this.cantidad_piensa_proveer=cantidad_piensa_proveer;
    this.fecha_esperada_entrega=fecha_esperada_entrega;
    this.numero_lote=numero_lote;
    this.fecha_expiracion_lote=fecha_expiracion_lote;
  }

}
