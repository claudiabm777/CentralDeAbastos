package co.edu.uniandes.cabAndes.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class PanelRF3a extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public PanelRF3a()
  {
    super.add(new HTML("<b>Entregar producto por parte de un proveedor</b>"));
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
