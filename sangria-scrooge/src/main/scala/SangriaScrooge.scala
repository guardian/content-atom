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
    if(tType.typeSymbol.asClass.isCaseClass) { // if this is a case class we can just use the standard macros
      q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
        def graphqlType = sangria.macros.derive.deriveObjectType[Unit, $tType]()
      }"""
    } else if(tType <:< typeOf[ThriftUnion]) {
      val unionTypes = companion
        .members
        .filter(m => m.isClass && m.asType.toType <:< tType && !m.name.toString.startsWith("UnknownUnionField"))
        .map { cl =>
          val clCompanion = cl.asType.toTypeIn(companion).companion
          val param = clCompanion.member(TermName("apply"))
            .asMethod.paramLists.head.head
          val paramName = param.name.toTermName
          val dealiased = cl.asType
          //val dealiased = param.typeSignature.dealias.typeSymbol.asType.toTypeIn(companion)
          //println(s"[PMR] 1656 $cl ${cl.asType.toType <:< tType}")
          val res = q"""sangria.schema.Field(${paramName.toString},
            _root_.scala.Predef.implicitly[_root_.shapeless.Lazy[_root_.sangria.macros.derive.GraphQLOutputTypeLookup[${dealiased}]]].value.graphqlType
              .asInstanceOf[ObjectType[Unit, $tType]],
            resolve = (_:Context[Unit, $dealiased]).value).asInstanceOf[Field[Unit, $tType]]"""
//            resolve = (_:Context[Unit, $dealiased]).value match { case x @ (_: $dealiased) => x; case _ => ??? })"""
          res
        }
      //val classSymbol = tType.typeSymbol.asClass
      // hmm ... https://github.com/scala/scala/pull/5284
      //  produces error such as "knownDirectSubclasses of AtomData observed before subclass Quiz registered"
      //val unionTypes = classSymbol
        //.knownDirectSubclasses
        //.filterNot(_.name.toString.startsWith("UnknownUnionField"))
        //.map
      val res = q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
        def graphqlType =
          sangria.schema.ObjectType(name = ${typeName.decodedName.toString},
            fields = fields[Unit, $tType](${unionTypes.toList}:_*)
          )
      }"""
      print(s"[PMR] 2100 $tType => ${showCode(res)}")
      res
    } else {
      print(s"[PMR] 2123 $tType")
      val applyMethod = companion.member(TermName("apply")).asMethod

      val fields = applyMethod.paramLists.head map { param =>
        val paramType = param.info
        val paramName = param.name.toTermName
        val fieldName = paramName.toString
        q"""sangria.schema.Field($fieldName,
              _root_.scala.Predef.implicitly[_root_.shapeless.Lazy[_root_.sangria.macros.derive.GraphQLOutputTypeLookup[$paramType]]].value.graphqlType,
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
    val companion = tType.companion
    val classSymbol = tType.typeSymbol.asClass

    val enumValues = companion
      .members
      .filter{m => m.isTerm && m.info <:< tType }
      .map(subclass => q"""sangria.schema.EnumValue(${subclass.name.toString}, value = ${subclass.asTerm})""")
      //.map(subclass => q"""sangria.schema.EnumValue(${subclass.name.toString}, value = ${companion.termSymbol}.${subclass.name.toTermName})""")
      .toList

    val res = q"""new sangria.macros.derive.GraphQLOutputTypeLookup[$tType] {
      def graphqlType = sangria.schema.EnumType(name = ${typeName.decodedName.toString}, None, ${enumValues})
    }"""
    /*println(showCode(res))*/
    res

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
