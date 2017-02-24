package com.example.emails.actors

import akka.actor.Actor
import com.example.emails.actors.ServicioPostal.EnviarEmail
import com.example.emails.dominio.Email

import scala.collection.mutable.ListBuffer
/**
  * Created by janus on 23/02/17.
  */

class Usuario extends Actor{
  import Usuario._
  val buzon: ListBuffer[Email] = ListBuffer()
  def receive = {
    case IngresarAlBuzon(email) => {
      buzon.append(email)
    }
    case "consultarMensajes" => {
      sender ! buzon
    }
  }
}

object Usuario{
  case class RecibirMensaje(email: Email)
  case class IngresarAlBuzon(email:Email)
  case object ConsultarMensajes
}
