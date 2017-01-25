package com.gu.contentatom.graphql

import com.gu.sangriascrooge.TypeMapping
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
  val atom  = implicitly[TypeMapping[Atom]].graphqlType.asInstanceOf[ObjectType[Unit, Atom]]
  val schema = Schema(atom)
}

object TestMain extends App {
  import sangria.renderer.SchemaRenderer
  println(
    SchemaRenderer.renderSchema(ContentAtomSchema.schema)
  )
}
