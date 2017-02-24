package com.example.emails

import akka.actor.{ActorSystem, Props}
import com.example.emails.actors.ServicioPostal
import com.example.emails.actors.ServicioPostal._
import com.example.emails.actors.Usuario.{ConsultarMensajes, RecibirMensaje}
import com.example.emails.dominio._
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by janus on 23/02/17.
  */
object EmailMain extends App{
  val sistema = ActorSystem("SistemaDeEmail")

  val gmail = sistema.actorOf(Props[ServicioPostal],"gmail")
  val hotmail = sistema.actorOf(Props[ServicioPostal],"hotmail")
  val mauricio = Cliente("mauricio@gmail.com"); val juan = Cliente("juan"); val pedro = Cliente("pedro")

  gmail ! RegistrarCliente(mauricio)
  gmail ! RegistrarCliente(juan)

  hotmail ! RegistrarCliente(pedro)

  val correo = Email(mauricio,juan,"IMPORTANTE","Necesito que me llames")
  val correo2 = Email(mauricio,juan,"IMPORTANTE","Necesito que me llames, nuevamente")
  gmail ! EnviarEmail(correo)
  gmail ! EnviarEmail(correo2)

  val juanito = sistema.actorSelection("user/gmail/juan")
  val mensajes = (juanito ? "consultarMensajes")(1.second)
  for {
    a <- mensajes
  }yield println(a)
}
