package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProductoPrecioVentaValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProductoPrecioVentaValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=393784079586730241L;

  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer precio_al_por_mayor;
  public Integer precio_al_detal;

  public ProductoPrecioVentaValue()
  {
  }

  public ProductoPrecioVentaValue(Integer cod_producto, Integer cod_presentacion, Integer precio_al_por_mayor, Integer precio_al_detal)
  {
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.precio_al_por_mayor=precio_al_por_mayor;
    this.precio_al_detal=precio_al_detal;
  }

}
