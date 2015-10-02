package co.edu.uniandes.cabAndes.server.jms;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.jms.*;

import co.edu.uniandes.cabAndes.server.dao.AdminDAO;
import co.edu.uniandes.cabAndes.shared.vos.MovimientoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoLugarAlmacValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoMayorMovimientoValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoPedidoValue;

public class RecibidorMensajes
{


  public RecibidorMensajes()
  {
  }

  public String recibirMensajeYa(ColaMensajes colaMensajes) throws JMSException
  {
    Queue cola=colaMensajes.getCola();
    QueueConnection conexion=colaMensajes.getConexion();
    QueueSession sesion=conexion.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
    MessageConsumer consumidor=sesion.createConsumer(cola);
    TextMessage mensaje=(TextMessage)(consumidor.receive());
    String textoMensaje=mensaje.getText();
    consumidor.close();
    sesion.close();
    return textoMensaje;
  }

  public void recibirMensajes(final ColaMensajes colaMensajes) throws JMSException
  {
	  Thread threadRecibidor=new Thread()
	  {
		  public void run()
		  {
			 while (true)
			 {
				 try
				 {
					 String textoMensaje=recibirMensajeYa(colaMensajes);
					 procesarMensaje(colaMensajes,textoMensaje);
				 }
				 catch (Throwable excepcion)
				 {
				      excepcion.printStackTrace();
				 }
			 }
		  }
	  };
	  threadRecibidor.start();
  }
  
  public void procesarMensaje(ColaMensajes colaMensajes, String textoMensaje)
  {
    try
    {
      String nombreCola=colaMensajes.toString();
      System.out.println("Llego mensaje de la cola "+nombreCola);
      System.out.println("Texto recibido: "+textoMensaje);
      if (nombreCola.equals("ColaPeticionesGrupo5CabAndes"))
      {
        // Grupo 9 implementa
      }
      else if (nombreCola.equals("ColaPeticionesGrupo9CabAndes"))
      {
    	  try
    	  {
    		  BufferedReader reader=new BufferedReader(new StringReader(textoMensaje));
    		  String rf=reader.readLine();
    		  if (rf.equals("RF23"))
    		  {
    			  List<ProductoPedidoValue> cosas=new ArrayList<ProductoPedidoValue>();
        		  while (true)
        		  {
        		     String linea=reader.readLine();
        		     if (linea==null||linea.trim().isEmpty()) break;
        		     String[] pedazos=linea.split("\\;");
        		     Integer cod_pedido=null;
        		     Integer cod_producto=Formateador.leerEntero(pedazos[0]);
        		     Integer cod_presentacion=Formateador.leerEntero(pedazos[1]);
        		     Integer cantidad_solicitada=Formateador.leerEntero(pedazos[2]);
        		     Integer cantidad_entregada=0;
 System.out.println("RECIBI "+java.util.Arrays.toString(pedazos));
        		     cosas.add(new ProductoPedidoValue(cod_pedido,cod_producto,cod_presentacion,cantidad_solicitada,cantidad_entregada));
        		  }
        		  AdminDAO dao=new AdminDAO();
        		  try
        		  {
        		    dao.satisfacerPedido3(cosas);
        		  }
        		  catch (Throwable th)
        		  {
        		    th.printStackTrace();	    	
        		  }
    		  }
    		  else if (rf.equals("RF24"))
    		  {
    			  List<ProductoLugarAlmacValue> cosas=new ArrayList<ProductoLugarAlmacValue>();
     		      Integer cod_tipo_producto=Formateador.leerEntero(reader.readLine());
        		  while (true)
        		  {
        		     String linea=reader.readLine();
        		     if (linea==null||linea.trim().isEmpty()) break;
        		     String[] pedazos=linea.split("\\;");
 System.out.println("RECIBI "+java.util.Arrays.toString(pedazos));       		     
        		     Integer cod_lugar_almac=-1;
        		     Integer cod_producto=Formateador.leerEntero(pedazos[0]);
        		     Integer cod_presentacion=Formateador.leerEntero(pedazos[1]);
        		     Integer cantidad=Formateador.leerEntero(pedazos[2]);
        		     Integer numero_lote=Formateador.leerEntero(pedazos[3]);
        		     java.util.Date fecha_expiracion_lote=Formateador.leerFecha(pedazos[4]);
        		     Double promocion_lote=0.0;
        		     if (numero_lote==null) numero_lote=0;
        		     if (fecha_expiracion_lote==null) fecha_expiracion_lote=new java.util.Date();
        		     cosas.add(new ProductoLugarAlmacValue(cod_lugar_almac, cod_producto, cod_presentacion, cantidad, numero_lote, fecha_expiracion_lote, promocion_lote));
        		  }
        		  AdminDAO dao=new AdminDAO();
        		  try
        		  {
        		    dao.cerrarBodega3(cod_tipo_producto,cosas);
        		  }
        		  catch (Throwable th)
        		  {
        		    th.printStackTrace();	    	
        		  }
    		  }
    		  else if (rf.equals("RF25"))
    		  {
    			  Integer cantidad=Formateador.leerEntero(reader.readLine());
    			  Integer idProducto=Formateador.leerEntero(reader.readLine());
    			  Integer idPresentacion=Formateador.leerEntero(reader.readLine());
    			  Integer idBodegaOrigen=Formateador.leerEntero(reader.readLine());
    			  Integer idLugarDestino=Formateador.leerEntero(reader.readLine());
    			  Date fechaMinima=Formateador.leerFecha(reader.readLine());
    			  Date fechaMaxima=Formateador.leerFecha(reader.readLine());
    			  
System.out.println("RF25"+cantidad+" y "+idProducto+" y "+idPresentacion+" y "+idBodegaOrigen+" y "+idLugarDestino+" y "+fechaMinima+" y "+fechaMaxima);    			  
    			  String respuesta="RF25";
    			  List<MovimientoValue> cosas=new ArrayList<MovimientoValue>();
        		  AdminDAO dao=new AdminDAO();
        		  try
        		  {
        		    cosas=dao.darMovmientos(cantidad, idProducto, idPresentacion, idBodegaOrigen, idLugarDestino, fechaMinima, fechaMaxima);
        		  }
        		  catch (Throwable th)
        		  {
        		    th.printStackTrace();	    	
        		  }
        		  for (MovimientoValue cosa:cosas)
        		  {
        			 respuesta+="\r\n";
        			 respuesta+=cosa.numero+";";
        			 respuesta+=cosa.cod_lugar_origen+";";
        			 respuesta+=cosa.nombre_lugar_origen.replace(";","")+";";
        			 respuesta+=cosa.cod_lugar_destino+";";
        			 respuesta+=cosa.nombre_lugar_destino.replace(";","")+";";
        			 respuesta+=Formateador.formatearFecha(cosa.fecha_movimiento)+";";
        			 respuesta+=cosa.cod_producto+";";
        			 respuesta+=cosa.cod_presentacion+";";
        			 respuesta+=cosa.cantidad;
        		  }
System.out.println("RESPUESTA ENVIADA POR RF25: "+respuesta);        		  
        		  EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
        		  enviadorMensajes.enviarMensaje(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO5,respuesta);        		  
    		  }
    		  else if (rf.equals("RF26"))
    		  {
    			  java.util.Date fechaMinimaMovimiento=Formateador.leerFecha(reader.readLine());
    			  java.util.Date fechaMaximaMovimiento=Formateador.leerFecha(reader.readLine());
System.out.println("RF26"+fechaMinimaMovimiento+" y "+fechaMaximaMovimiento);    			  
    			  String respuesta="RF26";
    			  List<ProductoMayorMovimientoValue> cosas=new ArrayList<ProductoMayorMovimientoValue>();
        		  AdminDAO dao=new AdminDAO();
        		  try
        		  {
        		    cosas=dao.darProductosMayorMovimiento(fechaMinimaMovimiento, fechaMaximaMovimiento);
        		  }
        		  catch (Throwable th)
        		  {
        		    th.printStackTrace();	    	
        		  }
        		  for (ProductoMayorMovimientoValue cosa:cosas)
        		  {
        			 respuesta+="\r\n";
        			 respuesta+=cosa.cod_producto+";";
        			 respuesta+=cosa.cod_presentacion+";";
        			 respuesta+=cosa.promedio_peso+";";
        			 respuesta+=cosa.costo_promedio+";";
        			 respuesta+=cosa.veces_ha_sido_pedido+";";
        			 respuesta+=cosa.veces_ha_sido_vendido;
        		  }
System.out.println("RESPUESTA ENVIADA POR RF26: "+respuesta);        		  
        		  EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
        		  enviadorMensajes.enviarMensaje(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO5,respuesta);        		  
    		  }
    	  }
    	  catch (Throwable th)
    	  {
    		  th.printStackTrace();
    	  }
        // Grupo 5 implementa
      }
    }
    catch (Throwable excepcion)
    {
      excepcion.printStackTrace();
    }
  }
  

  public static void main(String[] args) throws Exception
  {
    RecibidorMensajes recibidorMensajes=new RecibidorMensajes();
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_PETICIONES_GRUPO5);
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO5);
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO5);
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_PETICIONES_GRUPO9);
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO9);
    recibidorMensajes.recibirMensajes(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO9);
  }

}
