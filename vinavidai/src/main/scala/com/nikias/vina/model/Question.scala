package com.nikias.vina.model

import net.liftweb._
import mapper._
import http._
import SHtml._
import util._

class Question extends LongKeyedMapper[Question] with IdPK {
  def getSingleton = Question
  object done extends MappedBoolean(this)
  object owner extends MappedLongForeignKey(this, User)
  
  object subject extends MappedPoliteString(this, 128) {
		override def validations = valMinLen(10, "Subject must be 10 characters") _ :: super.validations
  }
  
  object detail extends MappedTextarea(this, 128) {
		override def validations = valMinLen(15, "Description must be 15 characters") _ :: super.validations
  }
}

object Question extends Question with LongKeyedMetaMapper[Question] {

}