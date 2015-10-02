package co.edu.uniandes.cabAndes.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class PanelRF3d extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public PanelRF3d()
  {
    super.add(new HTML("<b>Vender productos de una bodega a un comprador mayorista</b>"));
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
