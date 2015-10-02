package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

public class ModeloTablaUsuarios implements ModeloTabla<UsuarioValue>
{

  @Override
  public String[] darNombreColumnas()
  {
    String[] nombreColumnas={"DIRECCIÓN ELECTRÓNICA","NOMBRE","DOCUMENTO IDENTIFICACIÓN","PALABRA CLAVE","CIUDAD","DEPARTAMENTO","NACIONALIDAD","DIRECCIÓN FÍSICA","CÓDIGO POSTAL","TELÉFONO","ROL","TIPO"};
    return nombreColumnas;
  }

  @Override
  public Object darCelda(UsuarioValue item, int columna)
  {
    switch (columna)
    {
      case 0:
        return item.direccion_electronica;
      case 1:
        return item.nombre;
      case 2:
        return item.doc_identificacion;
      case 3:
        return item.palabra_clave;
      case 4:
        return item.ciudad;
      case 5:
        return item.departamento;
      case 6:
        return item.nacionalidad;
      case 7:
        return item.direccion_fisica;
      case 8:
        return item.codigo_postal;
      case 9:
        return item.telefono;
      case 10:
        return item.rol;
      case 11:
        return item.tipo;
      default:
        return null;
    }
  }

}
