package co.edu.uniandes.cabAndes.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class PanelRF3b extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public PanelRF3b()
  {
    super.add(new HTML("<b>Enviar productos de una bodega a un local de ventas</b>"));
    super.add(new HTML("Falta"));
  }

  @Override
  public void onClick(ClickEvent event)
  {
  }

  @Override
  public void onSuccess(Void result)
  {
  }

  @Override
  public void onFailure(Throwable caught)
  {
  }

}
