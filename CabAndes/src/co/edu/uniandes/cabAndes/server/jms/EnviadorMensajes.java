package co.edu.uniandes.cabAndes.server.jms;

import java.awt.*;
import java.awt.event.*;
import javax.jms.*;
import javax.swing.*;

public class EnviadorMensajes
{
  
  public EnviadorMensajes()
  {
  }

  public void enviarMensaje(ColaMensajes colaMensajes, String textoMensaje) throws JMSException
  {
    Queue cola=colaMensajes.getCola();
    QueueConnection conexion=colaMensajes.getConexion();
    QueueSession sesion=conexion.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
    MessageProducer productor=sesion.createProducer(cola);
    TextMessage mensaje=sesion.createTextMessage();
    mensaje.setText(textoMensaje);
    productor.send(mensaje);
    productor.close();
    sesion.close();
  }
  
  public static void main(String[] args) throws Exception
  {
    java.util.Vector<ColaMensajes> colas=new java.util.Vector<ColaMensajes>();
    colas.add(ColaMensajes.COLA_PETICIONES_GRUPO5);
    colas.add(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO5);
    colas.add(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO5);
    colas.add(ColaMensajes.COLA_PETICIONES_GRUPO9);
    colas.add(ColaMensajes.COLA_RESPUESTAS_RF25_GRUPO9);
    colas.add(ColaMensajes.COLA_RESPUESTAS_RF26_GRUPO9);
    final JFrame ventana=new JFrame();
    final JComboBox<ColaMensajes> comboCola=new JComboBox<ColaMensajes>(colas);
    final JTextArea campoMensaje=new JTextArea(20,20);
    final JButton botonEnviar=new JButton("Enviar");
    ventana.getContentPane().add(comboCola,BorderLayout.NORTH);
    ventana.getContentPane().add(campoMensaje,BorderLayout.CENTER);
    ventana.getContentPane().add(botonEnviar,BorderLayout.SOUTH);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana.pack();
    ventana.setVisible(true);
    botonEnviar.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          ColaMensajes colaMensajes=(ColaMensajes)(comboCola.getSelectedItem());
          String textoMensaje=campoMensaje.getText();
          EnviadorMensajes enviadorMensajes=new EnviadorMensajes();
          enviadorMensajes.enviarMensaje(colaMensajes,textoMensaje);
        }
        catch (Exception excepcion)
        {
          excepcion.printStackTrace();
        }
      }
    });
  }

}
