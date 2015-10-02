package co.edu.uniandes.cabAndes.server.jms;

import java.util.*;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;

public class ColaMensajes
{

  public static final ColaMensajes COLA_PETICIONES_GRUPO5=new ColaMensajes("ColaPeticionesGrupo5CabAndes");
  public static final ColaMensajes COLA_PETICIONES_GRUPO9=new ColaMensajes("ColaPeticionesGrupo9CabAndes");
  public static final ColaMensajes COLA_RESPUESTAS_RF25_GRUPO5=new ColaMensajes("ColaRespuestasRF25Grupo5CabAndes");
  public static final ColaMensajes COLA_RESPUESTAS_RF25_GRUPO9=new ColaMensajes("ColaRespuestasRF25Grupo9CabAndes");
  public static final ColaMensajes COLA_RESPUESTAS_RF26_GRUPO5=new ColaMensajes("ColaRespuestasRF26Grupo5CabAndes");
  public static final ColaMensajes COLA_RESPUESTAS_RF26_GRUPO9=new ColaMensajes("ColaRespuestasRF26Grupo9CabAndes");

  private Queue cola;
  private QueueConnection conexion;

  // Funciona con JBoss 4.2.2
  // 1. Poner la línea
  //    <config-property name="Strict" type="java.lang.Boolean">false</config-property>
  //    en el archivo jboss-4.2.2/server/default/deploy/jms/jms-ds.xml encima de
  //    <max-pool-size>20</max-pool-size>
  //    dentro de la configuración de JmsXA
  // 2. Poner previamente al archivo jboss-4.2.2/server/default/deploy/jms/jbossmq-destinations-service.xml
  /*
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaPeticionesGrupo5CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaPeticionesGrupo9CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaRespuestasRF25Grupo5CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaRespuestasRF25Grupo9CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaRespuestasRF26Grupo5CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  <mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=ColaRespuestasRF26Grupo9CabAndes"> 
    <depends optional-attribute-name="DestinationManager">jboss.mq:service=DestinationManager</depends>
  </mbean>
  */

  private ColaMensajes(String nombreCola)
  {
    try
    {
      Hashtable<String,String> environment=new Hashtable<String,String>();
      environment.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
      environment.put(Context.PROVIDER_URL,"jnp://localhost:1099");
      environment.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
      Context contexto=new InitialContext(environment);
      QueueConnectionFactory fabrica=(QueueConnectionFactory)(contexto.lookup("QueueConnectionFactory"));
      cola=(Queue)(contexto.lookup("queue/"+nombreCola));
      conexion=fabrica.createQueueConnection();
      conexion.start();
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public Queue getCola()
  {
    return cola;
  }

  public QueueConnection getConexion()
  {
    return conexion;
  }

  public String toString()
  {
    try {
      return cola.getQueueName();
    }
    catch (Exception excepcion)
    {
      return "";
    }
  }

}
