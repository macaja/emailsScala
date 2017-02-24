package com.example.emails.dominio

import akka.actor.ActorRef
import com.example.emails.actors.Usuario

/**
  * Created by janus on 24/02/17.
  */
case class Email(de:Cliente,para:Cliente, asunto: String,mensaje: String)
case class Cliente(direccion: String)
