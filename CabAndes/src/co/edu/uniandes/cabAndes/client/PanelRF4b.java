package co.edu.uniandes.cabAndes.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;


public class PanelRF4b extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

	  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	  public IntegerBox campoNumeroPedido=new IntegerBox();
	  public Button botonSatisfacerPedido=new Button("Satisfacer el pedido",this);

	  public PanelRF4b()
	  {
	    FlexTable formulario=new FlexTable();
	    formulario.setWidget(0,0,new Label("Número de pedido a satisfacer (entre 0 y 9999999 para CabAndes Grupo5):"));
	    formulario.setWidget(0,1,campoNumeroPedido);
	    super.add(formulario);
	    super.add(botonSatisfacerPedido);
	  }

	  @Override
	  public void onSuccess(Void result)
	  {
	    Window.alert("El pedido se intentó satisfacer. Si hizo falta algo, se le pidió al otro CabAndes.");
	  }

	  @Override
	  public void onFailure(Throwable caught)
	  {
	    Window.alert(caught.getMessage());
	  }

	  @Override
	  public void onClick(ClickEvent event)
	  {
	    Integer numeroPedido=campoNumeroPedido.getValue();
	    servlet.satisfacerPedidoRF23(numeroPedido,this);
	  }
	  
}
