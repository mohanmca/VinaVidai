package com.nikias.vina.snippet
import com.nikias.vina._
import com.nikias.vina.model._
import model._
import net.liftweb._
import http._

import SHtml._
import S._
import js._
import JsCmds._
import mapper._
import util._
import Helpers._
import scala.xml.{NodeSeq, Text}

class QS {
    object QueryNotDone extends SessionVar(false)
	
	private def toShow =
		ToDo.findAll(By(ToDo.owner, User.currentUser),
		if (QueryNotDone) By(ToDo.done, false)	else Ignore[ToDo],
		OrderBy(ToDo.done, Ascending),
		OrderBy(ToDo.priority, Descending),
		OrderBy(ToDo.desc, Ascending))
 
   private def desc(td: ToDo, reDraw: () => JsCmd) =	swappable(<span>{td.desc}</span>, <span>{ajaxText(td.desc,v => {td.desc(v).save; reDraw()})}</span>)
 
	private def doList(reDraw: () => JsCmd)(html: NodeSeq): NodeSeq =
		toShow.flatMap(td => bind("todo", html,
          "check" -> ajaxCheckbox(td.done, v => {td.done(v).save; reDraw()}),
          "priority" -> ajaxSelect(ToDo.priorityList, Full(td.priority.toString), v => {td.priority(v.toInt).save; reDraw()}),
          "desc" -> desc(td, reDraw)))

	def list(html: NodeSeq) = {
		val id = S.attr("all_id").open_!
		def inner(): NodeSeq = {
		def reDraw() = SetHtml(id, inner())
		bind("todo", html,	"exclude" -> ajaxCheckbox(QueryNotDone, v => {QueryNotDone(v); reDraw}),
        "list" -> doList(reDraw) _)
		}
		inner()
	}

    def add(form: NodeSeq)  : NodeSeq = {
    	var question: Question = new Question

		def checkAndSave(): Unit = {
		  if(question.save) {
			  println("Saved")
			  S.redirectTo("thank-you")
		  } 
		}
      
	    bind("question", form,
		    "subject" -> SHtml.text(question.subject, question.subject(_)),
		    "detail" -> SHtml.textarea(question.detail, question.detail(_),"cols" -> "80", "rows" -> "8"),
		    "submit" -> SHtml.submit("New", checkAndSave))
    
    }

    def add2(form: NodeSeq) = {
		val question = Question.create.owner(User.currentUser)
  
		def checkAndSave(): Unit = question.validate match {
			case Nil => question.save ; S.notice("Added "+question.detail)
			case xs => S.error(xs) ; S.mapSnippet("question.add", doBind)
		}

		def doBind(form: NodeSeq) =
			bind("question", form,
					"subject" -> question.subject.toForm,
					"detail" -> question.detail.toForm,
					"submit" -> submit("New", checkAndSave))
		doBind(form)
    }
}

