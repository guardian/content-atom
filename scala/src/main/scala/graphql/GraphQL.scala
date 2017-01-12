package com.gu.contentatom.graphql

import com.gu.sangriascrooge.Macros._
import sangria.schema._
import sangria.macros.derive._
import com.gu.contentatom.thrift._

import com.twitter.scrooge.{ ThriftStruct, ThriftEnum }

case class ContentAtomTest(name: String)

object ContentAtomSchema {

  //implicit def lookupThriftEnum[T <: ThriftEnum] = new GraphQLOutputTypeLookup[T] {
    //def graphqlType = deriveThriftEnum[T]
  //}

  // surely this is part of the sangria library?! I must be missing something...
  implicit def lookupScalar[T](implicit scalarType: ScalarType[T]) = new GraphQLOutputTypeLookup[T] {
    def graphqlType = scalarType
  }
  
  //val atom = deriveObjectType[Unit, ContentAtomTest]()
  lazy val atomType = deriveThriftEnum[AtomType]

  //lazy val atomType = sangria.schema.EnumType(name = "AtomType", None, scala.collection.immutable.List(sangria.schema.EnumValue("Quiz", value = Quiz.value), sangria.schema.EnumValue("Explainer", value = Explainer.value), sangria.schema.EnumValue("Recipe", value = Recipe.value), sangria.schema.EnumValue("Cta", value = Cta.value), sangria.schema.EnumValue("Interactive", value = Interactive.value), sangria.schema.EnumValue("Review", value = Review.value), sangria.schema.EnumValue("Media", value = Media.value)))
  
  //val atom = deriveThriftStruct[Atom]
  //val schema = Schema(atom)
}

object TestMain extends App {
  //val x = implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[Boolean]].graphqlType
//  val x = implicitly[sangria.macros.derive.GraphQLOutputTypeLookup[String]].graphqlType
  import sangria.renderer.SchemaRenderer
  println(ContentAtomSchema.atomType)
  //
  //println(
    //SchemaRenderer.renderSchema(ContentAtomSchema.schema)
  //)
}