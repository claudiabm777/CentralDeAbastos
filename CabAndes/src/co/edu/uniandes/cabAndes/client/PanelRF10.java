package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaPedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.PedidoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoLugarAlmacValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelRF10 extends VerticalPanel implements ClickHandler
{

	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public Tabla<BodegaValue> tablaBodegas=new Tabla<BodegaValue>("BODEGAS",new ModeloTablaBodegas(),200);
	public Button botonConsultarProductosBodega=new Button("Consultar los productos de la bodega seleccionada",this);
	public Tabla<ProductoLugarAlmacValue> tablaProductos=new Tabla<ProductoLugarAlmacValue>("PRODUCTOS LA BODEGA SELECCIONADA",new ModeloTablaProductosLugarAlmacenamiento(),200);
	public Button botonConsultarPedidosBodega=new Button("Consultar los pedidos de la bodega seleccionada",this);
	public Tabla<BodegaPedidoValue> tablaPedidosBodega=new Tabla<BodegaPedidoValue>("PEDIDOS DE LA BODEGA SELECCIONADA",new ModeloTablaBodegaPedido(),200);
	
	  public PanelRF10()
	  {
	    super.add(tablaBodegas);
	    super.add(botonConsultarProductosBodega);
	    super.add(tablaProductos);
	    super.add(botonConsultarPedidosBodega);
	    super.add(tablaPedidosBodega);
	  }
	public void onClick(ClickEvent event) 
	{
		if (event.getSource()==botonConsultarProductosBodega)
		{
			BodegaValue bodega=tablaBodegas.darDatoSeleccionado();
			if (bodega==null)
			{
				Window.alert("Debe seleccionar una bodega de la tabla");
			}
			else
			{
				servlet.darProductosBodega(bodega.id,tablaProductos);
			}
		}
		else
		{
			BodegaValue bodega=tablaBodegas.darDatoSeleccionado();
			if (bodega==null)
			{
				Window.alert("Debe seleccionar una bodega de la tabla");
			}
			else
			{
				servlet.darPedidosBodega(bodega.id,tablaPedidosBodega);
			}
		}
		
	}


}
