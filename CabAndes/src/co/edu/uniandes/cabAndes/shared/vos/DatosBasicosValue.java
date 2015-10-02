package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import java.util.*;

import com.google.gwt.user.client.rpc.*;

/**
 * Clase DatosBasicosValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class DatosBasicosValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=7477095767226219592L;

  public Map<String,UsuarioValue> usuarios;
  public Map<Integer,TipoProductoValue> tiposProducto;
  public Map<Integer,ProductoValue> productos;
  public Map<Integer,PresentacionValue> presentaciones;
  public Map<Integer,LugarAlmacenamientoValue> lugaresAlmacenamiento;
  public Map<Integer,BodegaValue> bodegas;
  public Map<Integer,LocalValue> locales;
  public Map<String,ProveedorValue> proveedores;
  public Map<Integer,PedidoValue> pedidos;
  public Map<String,UsuarioValue> provUsuarios;
  public Map<Integer,BodegaValue> bodegasMantenimiento;
  public Map<Integer,BodegaValue> bodegasFuncionamiento;

  public DatosBasicosValue()
  {
  }

  public DatosBasicosValue(Map<String,UsuarioValue> usuarios, Map<Integer,TipoProductoValue> tiposProducto, Map<Integer,ProductoValue> productos, Map<Integer,PresentacionValue> presentaciones, Map<Integer,LugarAlmacenamientoValue> lugaresAlmacenamiento, Map<Integer,BodegaValue> bodegas, Map<Integer,LocalValue> locales, Map<String,ProveedorValue> proveedores,Map<Integer,PedidoValue> pedidos,Map<String,UsuarioValue> provUsuarios,Map<Integer,BodegaValue> bodegasMantenimiento,Map<Integer,BodegaValue> bodegasFuncionamiento)
  {
    this.usuarios=usuarios;
    this.tiposProducto=tiposProducto;
    this.productos=productos;
    this.presentaciones=presentaciones;
    this.lugaresAlmacenamiento=lugaresAlmacenamiento;
    this.bodegas=bodegas;
    this.locales=locales;
    this.provUsuarios=provUsuarios;
    this.proveedores=proveedores;
    this.pedidos=pedidos;
    this.bodegasMantenimiento=bodegasMantenimiento;
    this.bodegasFuncionamiento=bodegasFuncionamiento;
  }

}
