package co.edu.uniandes.cabAndes.client;

import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF5 extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public DateBox campoFechaActualDeCorte=new DateBox();
  public Button botonConsultarLicitaciones=new Button("Consultar las licitaciones cerradas sin asignar",this);
  public Tabla<LicitacionValue> tablaLicitaciones=new Tabla<LicitacionValue>("LICITACIONES SIN ASIGNAR CON CIERRE ANTERIOR A LA FECHA DE CORTE",new ModeloTablaLicitaciones(),400);
  public Button botonConsultarOfertasProveedores=new Button("Consultar las propuestas presentadas por los proveedores a la licitación seleccionada",this);
  public Tabla<OfertaProveedorValue> tablaOfertasProveedores=new Tabla<OfertaProveedorValue>("PROPUESTAS DE PROVEEDORES A LA LICITACIÓN SELECCIONADA",new ModeloTablaOfertasProveedores(),300);
  public Button botonEscogerProveedor=new Button("Escoger el proveedor seleccionado para generar el pedido efectivo de la licitación",this);

  public PanelRF5()
  {
    campoFechaActualDeCorte.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Fecha actual de corte (límite fecha máxima propuestas):"));
    formulario.setWidget(0,1,campoFechaActualDeCorte);
    super.add(formulario);
    super.add(botonConsultarLicitaciones);
    super.add(tablaLicitaciones);
    super.add(botonConsultarOfertasProveedores);
    super.add(tablaOfertasProveedores);
    super.add(botonEscogerProveedor);
  }

  @Override
  public void onSuccess(Void result)
  {
    Window.alert("La propuesta seleccionada fue escogida para la licitación.");
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

  @Override
  public void onClick(ClickEvent event)
  {
    if (event.getSource()==botonConsultarLicitaciones)
    {
      Date fechaActualDeCorte=campoFechaActualDeCorte.getValue();
      servlet.darLicitacionesCerradasSinAsignar(fechaActualDeCorte,tablaLicitaciones);
    }
    else if (event.getSource()==botonConsultarOfertasProveedores)
    {
      LicitacionValue licitacion=tablaLicitaciones.darDatoSeleccionado();
      if (licitacion==null)
      {
        Window.alert("Debe seleccionar una licitación de la tabla");
      }
      else
      {
        servlet.darOfertasProveedores(licitacion.numero,tablaOfertasProveedores);
      }
    }
    else if (event.getSource()==botonEscogerProveedor)
    {
      OfertaProveedorValue ofertaProveedor=tablaOfertasProveedores.darDatoSeleccionado();
      if (ofertaProveedor==null)
      {
        Window.alert("Debe seleccionar una propuesta de la tabla");
      }
      else
      {
        servlet.escogerProveedorParaLicitacion(ofertaProveedor.cod_licitacion,ofertaProveedor.cod_proveedor,ofertaProveedor.fecha_esperada_entrega,this);
      }
    }
  }

}
