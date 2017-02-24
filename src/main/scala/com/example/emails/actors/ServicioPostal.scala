package com.example.emails.actors

import akka.actor.{Actor, ActorRef, Props}
import com.example.emails.actors.Usuario.IngresarAlBuzon
import com.example.emails.dominio._
import akka.util.Timeout
/**
  * Created by janus on 23/02/17.
  */

class ServicioPostal extends Actor{
  import ServicioPostal._
  def receive = {
    case EnviarEmail(email) => {
      val destinatario: Option[ActorRef] = context.child(email.para.direccion)
      destinatario match {
        case Some(x:ActorRef) => destinatario.get ! IngresarAlBuzon(email);println("envió")
        case None => println("No existe el destinatario")
      }
    }
    case RegistrarCliente(cliente) => {
      if(context.child(cliente.direccion) == None){
        context.actorOf(Props[Usuario],cliente.direccion)
        val r = context.actorSelection("/user/gmail/maurico@gmail.com")
        val s = r.resolveOne()()
        println(s)
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


