package com.gu.contentatom.graphql

import com.gu.sangriascrooge.Macros._
import sangria.ast
import sangria.schema._
import sangria.validation.IntCoercionViolation
import sangria.macros.derive._
import com.gu.contentatom.thrift._
import com.gu.contentatom.thrift.atom._

import com.twitter.scrooge.{ ThriftStruct, ThriftEnum }

case class ContentAtomTest(name: String)

object ContentAtomSchema {

  val shortType = ScalarType[Short](
      name = "Short",
    coerceUserInput = {
      case s: Short => Right(s)
      case i: Int if i.isValidShort => Right(i.toShort)
      case x => Left(IntCoercionViolation)
    },
    coerceOutput = valueOutput,
    coerceInput = {
      case ast.IntValue(i, _, _) if i.isValidShort => Right(i.toShort)
      case _ => Left(IntCoercionViolation)
    }
  )

  implicit val lookupShort = new GraphQLOutputTypeLookup[Short] {
    def graphqlType = shortType
  }

//implicit def lookupThriftEnum[T <: ThriftEnum]= new GraphQLOutputTypeLookup[T] {
    //def graphqlType = deriveThriftEnum[T]
  //}

  // surely this is part of the sangria library?! I must be missing something...
  //implicit def lookupScalar[T](implicit scalarType: ScalarType[T]) = new GraphQLOutputTypeLookup[T] {
    //def graphqlType = scalarType
  //}

  //println(implicitly[GraphQLOutputTypeLookup[AtomData]].graphqlType)
  val atom = implicitly[GraphQLOutputTypeLookup[Atom]].graphqlType.asInstanceOf[ObjectType[Unit, Atom]]
  //val tmp = implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[com.gu.contentatom.thrift.atom.cta.CTAAtom]]
  //val schema = Schema(atom)
}

object TestMain extends App {
  //val x = implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[Boolean]].graphqlType
//  val x = implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[String]].graphqlType
  import sangria.renderer.SchemaRenderer
  //
  //println(
    //SchemaRenderer.renderSchema(ContentAtomSchema.schema)
  //)
}
