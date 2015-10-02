package co.edu.uniandes.cabAndes.client;

public interface ModeloTabla<T>
{

  public String[] darNombreColumnas();

  public Object darCelda(T item, int columna);

}
