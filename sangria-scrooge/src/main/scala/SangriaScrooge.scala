package com.gu.sangriascrooge

import scala.reflect.macros._

import sangria.macros.derive._

import com.twitter.scrooge.{ ThriftStruct, ThriftEnum }
import sangria.schema._

import scala.language.experimental.macros

object Macros {
  implicit def deriveThriftEnum[T <: ThriftEnum]: EnumType[Int] = macro MacroImpl.deriveThriftEnum[T]

  implicit def deriveThriftStruct[T <: ThriftStruct]: ObjectType[Unit, T] = macro MacroImpl.deriveThriftStruct[T]
}

class MacroImpl(val c: blackbox.Context) {
  import c.universe._
  
  def deriveThriftStruct[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val companion = tType.companion
    val typeName = tType.typeSymbol.name.toTypeName
    val applyMethod = companion.member(TermName("apply")).asMethod
    val fields = applyMethod.paramLists.head map { param =>
      val paramType = param.info
      val paramName = param.name.toTermName
      val fieldName = paramName.toString
      q"""sangria.schema.Field($fieldName,
            implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[$paramType]].graphqlType,
            resolve = (_:Context[Unit, $tType]).value.${paramName})"""
    }
    val res = q"""ObjectType(name = ${typeName.decodedName.toString}, fields = fields[Unit, $typeName]($fields:_*))"""
    res
  }

  def deriveThriftEnum[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val typeName = tType.typeSymbol.name.toTypeName
    println(s"[PMR] 1328 ${weakTypeTag[T].tpe}")
    val classSymbol = tType.typeSymbol.asClass

    val enumValues = classSymbol
      .knownDirectSubclasses
      .filterNot(_.name.toString.startsWith("EnumUnknown"))
      .map(subclass => q"""sangria.schema.EnumValue(${subclass.name.toString}, value = ${typeName.toTermName}.${subclass.name.toTermName}.value)""")
    
    //val companion = tType.companion
    //val applyMethod = companion.member(TermName("apply")).asMethod

    q"""sangria.schema.EnumType(name = ${typeName.decodedName.toString}, None, ${enumValues.toList})"""

    //val fields = applyMethod.paramLists.head map { param =>
      //val paramType = param.info
      //val paramName = param.name.toTermName
      //val fieldName = paramName.toString
      //println(s"[PMR] 1658 $paramName, $paramType")
      //q"""sangria.schema.Field($fieldName,
            //implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[$paramType]].graphqlType,
            //resolve = (_:Context[Unit, $tType]).value.${paramName})"""
    //}
    //val res = q"""ObjectType(name = ${typeName.decodedName.toString}, fields = fields[Unit, $typeName]($fields:_*))"""
    //res
  }

}