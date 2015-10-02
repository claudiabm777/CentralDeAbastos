package co.edu.uniandes.cabAndes.client;

import java.util.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF3e extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public DateBox campoFechaActualDeCorte=new DateBox();
  public Button botonEliminarProductosExpirados=new Button("Eliminar productos que cumplieron con la fecha de expiración",this);

  public PanelRF3e()
  {
    campoFechaActualDeCorte.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Fecha actual de corte (límite fecha máxima expiración):"));
    formulario.setWidget(0,1,campoFechaActualDeCorte);
    super.add(formulario);
    super.add(botonEliminarProductosExpirados);
  }

  @Override
  public void onSuccess(Void result)
  {
    Window.alert("Se eliminaron los productos con fecha de expiración menor o igual que la fecha dada.");
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

  @Override
  public void onClick(ClickEvent event)
  {
    Date fechaActualDeCorte=campoFechaActualDeCorte.getValue();
    servlet.eliminarProductosExpirados(fechaActualDeCorte,this);
  }

}
