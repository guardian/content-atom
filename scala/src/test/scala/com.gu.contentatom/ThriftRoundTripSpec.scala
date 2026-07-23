package com.gu.contentatom

import org.apache.thrift.transport._
import org.apache.thrift.protocol.{TProtocol, TBinaryProtocol, TCompactProtocol}
import java.nio.file.Files
import java.nio.file.Path
import com.twitter.scrooge.ThriftStructCodec
import org.scalatest.Assertion
import com.twitter.scrooge.ThriftStruct
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gu.contentatom.thrift.Atom
import com.gu.contentatom.thrift.AtomData

class ThriftRoundTripSpec extends AnyFlatSpec with Matchers {
  it should "round-trip a CTA atom" in {
    checkRoundTrip(
      Path.of("atom-cta-2bcdfd12-5e96-493c-8b18-a8d4c53df938-unwrapped.binary.thrift"),
      Atom,
      (atom: Atom) => (
        atom.data match { case AtomData.Cta(cta) => Some(cta); case _ => None }
      ).flatMap(_.label) shouldBe Some("Explore Amazon Freight for your shipments")
    )
  }

  it should "round-trip a quiz atom" in {
    checkRoundTrip(
      Path.of("atom-quiz-ed563bff-19cf-49f6-a5c3-a458559f432d-unwrapped.binary.thrift"),
      Atom,
      (atom: Atom) => (atom.data match {
        case AtomData.Quiz(quiz) => Some(quiz.content.questions(0).questionText)
        case _ => None
      }) shouldBe Some("Paul, 7, asks: who invented the game ‘rock, paper, scissors’?\n")
    )
  }

  it should "round-trip a guide atom" in {
    checkRoundTrip(
      Path.of("atom-guide-9c862998-6f26-42f1-9243-fcc5766486cf-unwrapped.binary.thrift"),
      Atom,
      (atom: Atom) => (atom.data match {
        case AtomData.Guide(guide) => Some(guide.items(0).body)
        case _ => None
      }) shouldBe Some("<p><b>Sandown </b>1.55 Arry Up 2.25 Jinman 2.55 Aperoll 3.30 Kamaway 4.07 Probation 4.42 Albertini Star 5.17 Sail On Sailor</p><p><b>Doncaster </b>2.10 Night Star 2.40 Round The Table 3.10 Ten Clarets 3.45 Sargent Dennis (nap) 4.15 Jenni 4.48 Palmarian 5.23 Brighlee</p><p><b>Southwell </b>4.53 Finn Ironside 5.28 Hulk Power 6.01 Dovecote 6.36 Beresford Gap 7.11 Little Mester 7.46 Koko Blue 8.21 Hansteen 8.56 My Mate Mackley</p><p><b>Yarmouth </b>5.04 Anchiano 5.39 Panelli 6.14 Hardy’s Hero 6.49 Campani 7.24 Maith Mar Or 7.59 Due Date 8.34 Roi De Coeur</p><p><b>Newbury </b>5.55 Duke Of Burgundy 6.30 Art Of Life 7.05 Always Perfect 7.40 The Craftymaster (nb) 8.15 Dancing Tiger 8.48 Port Louis</p>")
    )
  }

  it should "round-trip an explainer atom" in {
    checkRoundTrip(
      Path.of("atom-explainer-4d42b98e-1b9d-4f95-b256-e12acfd39f21-unwrapped.binary.thrift"),
      Atom,
      (atom: Atom) => (atom.data match {
        case AtomData.Explainer(explainer) => Some(explainer.title)
        case _ => None
      }) shouldBe Some("What is fracking? ")
    )
  }

  it should "round-trip a timeline atom" in {
    checkRoundTrip(
      Path.of("atom-timeline-32b0d5c4-61cc-4306-847e-7f3b33f31e77-unwrapped.binary.thrift"),
      Atom,
      (atom: Atom) => (atom.data match {
        case AtomData.Timeline(timeline) => timeline.events(0).body
        case _ => None
      }) shouldBe Some("<p>Gina Rinehart and Pauline Hanson are seen dining together in Thailand, alongside the former Liberal vice-president Teena McQueen</p>")
    )
  }

  def checkRoundTrip[T <: ThriftStruct](
    resourcePath: Path,
    codec: ThriftStructCodec[T],
    assertion: T => Unit = (t: T) => ()
  ) = {
    for {
      protocol <- Seq(Compact, Binary)
    } yield {
      val (inputBytes, struct) = readProtocol(protocol, resourcePath, codec)
      val outputTransport = new TMemoryBuffer(inputBytes.length)
      val outputProtocol = protocol(outputTransport)
      struct.write(outputProtocol)
      outputTransport.getArray() shouldEqual inputBytes
      assertion(struct)
    }
  }

  /**
   * Helper for generating test files for a type.
   *
   * Start with a test like this:
   *
   * {{{
   * it should "round-trip a ProductSummaryElementFields" in {
   *   val fields = ProductSummaryElementFields(Some("Something"),ProductSummaryDisplayType.Carousel,Some(List(SummaryProductRef(Some("product-id"),Some(0)))),Some("An id"))
   *   val testPath = Path.of("productSummaryElementFields.binary.thrift")
   *   thriftToFile(testPath, fields)
   *   checkRoundTrip(testPath, ProductSummaryElementFields)
   * }
   * }}}
   *
   * Running `sbt test` will generate an appropriate resource file by writing
   * out the example value (`fields` here), and then you can remove the
   * `thriftToFile` call and simplify the test.
   */
  def thriftToFile[T <: ThriftStruct](
    resourcePath: Path,
    value: T,
  ) = {
    for {
      protocol <- Seq(Compact, Binary)
    } yield {
      writeProtocol(protocol, resourcePath, value)
    }
  }

  /**
   * Helper for producing the TBinaryProtocol encoding from the TCompactProtocol
   * one.
   *
   * This is useful if producing specs from concierge, because concierge only
   * uses TCompactProtocol.
   */
  def compactToBinary[T <: ThriftStruct](
    resourcePath: Path,
    codec: ThriftStructCodec[T],
  ) = {
    val (inputBytes, struct) = readProtocol(Compact, resourcePath, codec)
    writeProtocol(Binary, resourcePath, struct)
  }

  def readProtocol[T <: ThriftStruct](
    protocol: Protocol,
    resourcePath: Path,
    codec: ThriftStructCodec[T],
  ): (Array[Byte], T) = {
    val resourcesPath = Path.of("scala", "src", "test", "resources", protocol.name)
    val inputBytes: Array[Byte] = Files.readAllBytes(resourcesPath.resolve(resourcePath))
    val transport = new TMemoryBuffer(inputBytes.length)
    transport.write(inputBytes)
    val inputProtocol = protocol(transport)
    return (inputBytes, codec.decode(inputProtocol))
  }

  def writeProtocol[T <: ThriftStruct](
    protocol: Protocol,
    resourcePath: Path,
    value: T
  ): Unit = {
    val resourcesPath = Path.of("scala", "src", "test", "resources", protocol.name)
    val outputStream = Files.newOutputStream(resourcesPath.resolve(resourcePath))
    val transport = new TIOStreamTransport(outputStream)
    val outputProtocol = protocol(transport)
    value.write(outputProtocol)
  }

  sealed trait Protocol {
    val name: String
    def apply(transport: TTransport): TProtocol
  }
  case object Compact extends Protocol {
    val name = "TCompactProtocol"
    def apply(transport: TTransport) = new TCompactProtocol(transport)
  }
  case object Binary extends Protocol {
    val name = "TBinaryProtocol"
    def apply(transport: TTransport) = new TBinaryProtocol(transport)
  }
}
