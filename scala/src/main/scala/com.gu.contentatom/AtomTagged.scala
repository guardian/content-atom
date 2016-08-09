package com.gu.contentatom

import com.twitter.scrooge.ThriftStruct
import com.gu.contentatom.thrift._
import com.gu.contentatom.thrift.atom.cta.CTAAtom

object TestData {
  val cta = CTAAtom(url = "http://world.com", None, trackingCode = None)
  val a = Atom("1", AtomType.Cta, Nil, "", AtomData.Cta(cta), ContentChangeDetails(revision = 1), None)
}

sealed abstract class AtomTagged[A <: AtomData : Manifest] {
  type Data

  def extract: A => Data

  def data(atom: Atom): Option[Data] =
    (atom.data match {
       case a: A => Some(a)
       case _ => None
     }) map extract
}

object CtaTagged extends AtomTagged[AtomData.Cta] {
  type Data = CTAAtom
  def extract = _.cta
}

object AtomTagged {
  def apply(atom: Atom): AtomTagged[_] = apply(atom.atomType)
  def apply(atomType: AtomType): AtomTagged[_] = atomType match {
    //   case AtomType.Quiz => ???
    case AtomType.Cta  => CtaTagged
    case _ => ???
  }
}
