package com.gu.sangriascrooge

import scala.reflect.macros._

import sangria.macros.derive._

import com.twitter.scrooge.{ ThriftStruct, ThriftEnum, ThriftUnion }
import sangria.schema._

import scala.language.experimental.macros

object Macros {
  implicit def deriveThriftEnum[T <: ThriftEnum]: GraphQLOutputTypeLookup[T] = macro MacroImpl.deriveThriftEnum[T]
  implicit def deriveThriftStruct[T <: ThriftStruct]: GraphQLOutputTypeLookup[T] = macro MacroImpl.deriveThriftStruct[T]
  //implicit def deriveThriftUnion[T <: ThriftUnion]: GraphQLOutputTypeLookup[T] = macro MacroImpl.deriveThriftUnion[T]
}

class MacroImpl(val c: blackbox.Context) {
  import c.universe._

  def deriveThriftStruct[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val typeName = tType.typeSymbol.name.toTypeName
    val companion = tType.companion
    if(tType <:< typeOf[ThriftUnion]) {
      val unionTypes = companion
        .members
        .filter(m => m.isClass && m.asType.toType <:< tType && !m.name.toString.startsWith("UnknownUnionField"))
        .map { cl =>
          val clCompanion = cl.asType.toTypeIn(companion).companion
          val param = clCompanion.member(TermName("apply"))
            .asMethod.paramLists.head.head
          val dealiased = param.typeSignature.dealias.typeSymbol.asType.toTypeIn(companion)
          val res = q"""implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[${dealiased}]]"""
          res
        }
      //val classSymbol = tType.typeSymbol.asClass
      // hmm ... https://github.com/scala/scala/pull/5284
      //  produces error such as "knownDirectSubclasses of AtomData observed before subclass Quiz registered"
      //val unionTypes = classSymbol
        //.knownDirectSubclasses
        //.filterNot(_.name.toString.startsWith("UnknownUnionField"))
        //.map
      q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
        def graphqlType =
          sangria.schema.ObjectType(name = ${typeName.decodedName.toString},
            fields = fields[Unit, $typeName](${unionTypes.toList}:_*)
          )
      }"""
    } else {
      val applyMethod = companion.member(TermName("apply")).asMethod

      val fields = applyMethod.paramLists.head map { param =>
        val paramType = param.info
        val paramName = param.name.toTermName
        val fieldName = paramName.toString
        q"""sangria.schema.Field($fieldName,
              implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[$paramType]].graphqlType,
              resolve = (_:Context[Unit, $tType]).value.${paramName})"""
      }
      q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
        def graphqlType =
          sangria.schema.ObjectType(name = ${typeName.decodedName.toString}, fields = fields[Unit, $tType]($fields:_*))
      }"""
    }
  }

  def deriveThriftEnum[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val typeName = tType.typeSymbol.name.toTypeName
    val classSymbol = tType.typeSymbol.asClass

    val enumValues = classSymbol
      .knownDirectSubclasses
      .filterNot(_.name.toString.startsWith("EnumUnknown"))
      .map(subclass => q"""sangria.schema.EnumValue(${subclass.name.toString}, value = ${tType}.${subclass.name.toTermName})""")

 //${enumValues.toList})
    q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
      def graphqlType = sangria.schema.EnumType(name = ${typeName.decodedName.toString}, None, List())
    }"""

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
