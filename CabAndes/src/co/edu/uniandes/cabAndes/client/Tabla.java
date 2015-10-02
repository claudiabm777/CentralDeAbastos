package co.edu.uniandes.cabAndes.client;

import java.util.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

/**
 * Clase provista por Alejandro Sotelo para el despliegue de tablas ordenadas por cualquier criterio. Se entrega esta clase al
 * grupo conformado por Claudia Bedoya y Johnathan Salamanca en el proyecto CabAndes.
 * 
 * @author Alejandro Sotelo
 */
public class Tabla<T> extends ScrollPanel implements AsyncCallback<List<T>>
{

  private static final NumberFormat FORMATO_NUMERO=NumberFormat.getFormat("0.00");
  private static final DateTimeFormat FORMATO_FECHA=DateTimeFormat.getFormat("yyyy-MM-dd");
  private static final DateTimeFormat FORMATO_FECHA_HORA=DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

  private CellTable<T> tabla;
  private ModeloTabla<T> modeloDatos;
  private SingleSelectionModel<T> modeloSeleccion=new SingleSelectionModel<T>();
  private ColumnSortEvent.ListHandler<T> manejadorOrdenamiento=new ColumnSortEvent.ListHandler<T>(new ArrayList<T>());

  public Tabla(String titulo, ModeloTabla<T> modeloDatos, int alto)
  {
    this.tabla=new CellTable<T>(1<<18);
    this.modeloDatos=modeloDatos;
    tabla.setSelectionModel(modeloSeleccion);
    tabla.addColumnSortHandler(manejadorOrdenamiento);
    String nombreColumnas[]=modeloDatos.darNombreColumnas();
    for (int indice=0; indice<nombreColumnas.length; indice++)
    {
      tabla.addColumn(new TablaColumna(indice),nombreColumnas[indice]);
    }
    cambiarDatos(new ArrayList<T>());
    VerticalPanel panel=new VerticalPanel();
    panel.add(new HTML("<b>"+titulo+"</b>"));
    panel.add(tabla);
    super.setWidget(panel);
    super.setWidth("880px");
    super.setHeight(alto+"px");
  }

  public T darDatoSeleccionado()
  {
    return modeloSeleccion.getSelectedObject();
  }

  public void cambiarDatos(Collection<T> datos)
  {
    ListDataProvider<T> proveedorDatos=new ListDataProvider<T>();
    proveedorDatos.setList(new ArrayList<T>(datos));
    proveedorDatos.addDataDisplay(tabla);
    manejadorOrdenamiento.setList(proveedorDatos.getList());
  }

  @Override
  public void onSuccess(List<T> result)
  {
    cambiarDatos(result);
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

  private class TablaColumna extends TextColumn<T> implements Comparator<T>
  {

    private int indice;

    public TablaColumna(int indice)
    {
      this.indice=indice;
      super.setSortable(true);
      super.setCellStyleNames("celda");
      manejadorOrdenamiento.setComparator(this,this);
    }

    @Override
    public String getValue(T item)
    {
      Object valor=modeloDatos.darCelda(item,indice);
      if (valor==null) return "";
      if (valor instanceof Boolean) return ((Boolean)valor)?"Si":"No";
      if (valor instanceof Double) return FORMATO_NUMERO.format((Double)valor).replace(',','.');
      if (valor instanceof java.sql.Timestamp) return FORMATO_FECHA_HORA.format((java.sql.Timestamp)valor);
      if (valor instanceof Date) return FORMATO_FECHA.format((Date)valor);
      return valor.toString();
    }

    @Override
    public int compare(T item1, T item2)
    {
      Object valor1=modeloDatos.darCelda(item1,indice);
      Object valor2=modeloDatos.darCelda(item2,indice);
      if (valor1==null&&valor2==null) return 0;
      if (valor1!=null&&valor2==null) return +1;
      if (valor1==null&&valor2!=null) return -1;
      if (valor1!=null&&valor2!=null) return ((Comparable)valor1).compareTo(valor2);
      return 0;
    }

  }

}
