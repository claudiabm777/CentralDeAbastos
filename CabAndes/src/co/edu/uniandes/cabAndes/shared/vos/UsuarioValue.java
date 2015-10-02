package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase UsuarioValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class UsuarioValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=7729809594206185349L;

  public String direccion_electronica;
  public String nombre;
  public String doc_identificacion;
  public String palabra_clave;
  public String ciudad;
  public String departamento;
  public String nacionalidad;
  public String direccion_fisica;
  public Integer codigo_postal;
  public String telefono;
  public String rol;
  public String tipo;

  public UsuarioValue()
  {
  }

  public UsuarioValue(String direccion_electronica, String nombre, String doc_identificacion, String palabra_clave, String ciudad, String departamento, String nacionalidad, String direccion_fisica, Integer codigo_postal, String telefono, String rol, String tipo)
  {
    this.direccion_electronica=direccion_electronica;
    this.nombre=nombre;
    this.doc_identificacion=doc_identificacion;
    this.palabra_clave=palabra_clave;
    this.ciudad=ciudad;
    this.departamento=departamento;
    this.nacionalidad=nacionalidad;
    this.direccion_fisica=direccion_fisica;
    this.codigo_postal=codigo_postal;
    this.telefono=telefono;
    this.rol=rol;
    this.tipo=tipo;
  }

}
