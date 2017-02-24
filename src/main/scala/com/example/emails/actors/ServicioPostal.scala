package com.example.emails.actors

import akka.actor.{Actor, ActorRef, Props}
import com.example.emails.actors.Usuario.IngresarAlBuzon
import com.example.emails.dominio._
import scala.concurrent.duration._
/**
  * Created by janus on 23/02/17.
  */
//usar resolveOne
class ServicioPostal extends Actor{
  import ServicioPostal._
  def receive = {
    case EnviarEmail(email) => {
      val destinatario: Option[ActorRef] = context.child(email.para.direccion)
      destinatario match {
        case Some(x:ActorRef) => destinatario.get ! IngresarAlBuzon(email)
        case None => println("No existe el destinatario")
      }
    }
    case RegistrarCliente(cliente) => {
      //val dominio = (cliente.direccion.split("@").tail.head).split(".").head
      //val servidor = context.actorSelection("user/"+dominio)
      if(context.child(cliente.direccion) == None){
        context.actorOf(Props[Usuario],cliente.direccion)
      }else{
       println("El usuario ya existe")
      }
    }
  }
}
object ServicioPostal{
  case class EnviarEmail(email:Email)
  case class RegistrarCliente(cliente: Cliente)
  case class LogIn(cliente:Cliente)
}


