package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProductoMayorMovimientoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProductoMayorMovimientoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=6199879721342098878L;

  public Integer cod_producto;
  public Integer cod_presentacion;
  public Double promedio_peso;
  public Double costo_promedio;
  public Integer veces_ha_sido_pedido;
  public Integer veces_ha_sido_vendido;

  public ProductoMayorMovimientoValue()
  {
  }

  public ProductoMayorMovimientoValue(Integer cod_producto, Integer cod_presentacion, Double promedio_peso, Double costo_promedio, Integer veces_ha_sido_pedido, Integer veces_ha_sido_vendido)
  {
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.promedio_peso=promedio_peso;
    this.costo_promedio=costo_promedio;
    this.veces_ha_sido_pedido=veces_ha_sido_pedido;
    this.veces_ha_sido_vendido=veces_ha_sido_vendido;
  }

}
