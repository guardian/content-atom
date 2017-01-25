package com.gu.sangriascrooge

import scala.reflect.macros._

import sangria.macros.derive._

import com.twitter.scrooge.{ ThriftStruct, ThriftEnum, ThriftUnion }
import sangria.schema._

import scala.language.experimental.macros

trait TypeMapping[T] {
  def graphqlType: Type
}

object TypeMapping {
  implicit def fromGraphQLLookup[T](implicit gqll: GraphQLOutputTypeLookup[T]): TypeMapping[T] = new TypeMapping[T] { def graphqlType = gqll.graphqlType.asInstanceOf[Type] }
  implicit def optional[T](implicit tm: TypeMapping[T]): TypeMapping[Option[T]] = new TypeMapping[Option[T]] { def graphqlType = OptionType(tm.graphqlType.asInstanceOf[OutputType[T]]) }
  implicit def seq[T](implicit tm: TypeMapping[T]): TypeMapping[Seq[T]] = new TypeMapping[Seq[T]] { def graphqlType = ListType(tm.graphqlType.asInstanceOf[OutputType[T]]) }
}

object Macros {
  implicit def deriveThriftStruct[T <: ThriftStruct]: TypeMapping[T] = macro MacroImpl.deriveThriftStruct[T]
  implicit def deriveThriftEnum[T <: ThriftEnum]: TypeMapping[T] = macro MacroImpl.deriveThriftEnum[T]
}

class MacroImpl(val c: blackbox.Context) {
  import c.universe._

  def deriveThriftStruct[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val typeName = tType.typeSymbol.name.toTypeName
    val companion = tType.companion
    if(tType <:< typeOf[ThriftUnion] && !tType.typeSymbol.asClass.isCaseClass) {
      val unionTypes = companion
        .members
        .filter(m => m.isClass && m.asType.toType <:< tType && !m.name.toString.startsWith("UnknownUnionField"))
        .map { cl =>
          val clCompanion = cl.asType.toTypeIn(companion).companion
          val param = clCompanion.member(TermName("apply"))
            .asMethod.paramLists.head.head
          val paramName = param.name.toTermName
          val dealiased = cl.asType
          q"""_root_.scala.Predef.implicitly[_root_.shapeless.Lazy[_root_.com.gu.sangriascrooge.TypeMapping[${dealiased}]]].value.graphqlType.asInstanceOf[ObjectType[Unit, $dealiased]]"""
        }
      q"""new com.gu.sangriascrooge.TypeMapping[$tType] {
        def graphqlType =
          sangria.schema.UnionType(name = ${typeName.decodedName.toString},
            types = ${unionTypes.toList}
          )
      }"""
    } else {
      val applyMethod = companion.member(TermName("apply")).asMethod

      val fields = applyMethod.paramLists.head map { param =>
        val paramType = param.info
        val paramName = param.name.toTermName
        val fieldName = paramName.toString
        q"""sangria.schema.Field($fieldName,
              _root_.scala.Predef.implicitly[_root_.shapeless.Lazy[_root_.com.gu.sangriascrooge.TypeMapping[$paramType]]].value.graphqlType.asInstanceOf[OutputType[Any]],
              resolve = (_:Context[Unit, $tType]).value.${paramName})"""
      }
      q"""new com.gu.sangriascrooge.TypeMapping[$tType] {
        def graphqlType =
          sangria.schema.ObjectType(name = ${typeName.decodedName.toString}, fields = fields[Unit, $tType]($fields:_*))
      }"""
    }
  }

  def deriveThriftEnum[T: c.WeakTypeTag] = {
    val tType = weakTypeOf[T]
    val typeName = tType.typeSymbol.name.toTypeName
    val companion = tType.companion
    val classSymbol = tType.typeSymbol.asClass

    val enumValues = companion
      .members
      .filter{m => m.isTerm && m.info <:< tType }
      .map(subclass => q"""sangria.schema.EnumValue(${subclass.name.toString}, value = ${subclass.asTerm})""")
      .toList

    q"""new com.gu.sangriascrooge.TypeMapping[$tType] {
      def graphqlType = sangria.schema.EnumType(name = ${typeName.decodedName.toString}, None, ${enumValues})
    }"""
  }
}
