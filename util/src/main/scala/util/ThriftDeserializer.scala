package com.gu.contentatom.thrift.util

import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import java.io.ByteArrayOutputStream
import java.util.zip.{ GZIPOutputStream, GZIPInputStream }
import org.apache.commons.io.IOUtils
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.TIOStreamTransport
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream

import java.nio.ByteBuffer
import scala.util.Try

sealed trait CompressionType
case object NoneType extends CompressionType
case object GzipType extends CompressionType

object Compression {

  def gzip(data: Array[Byte]): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val out = new GZIPOutputStream(bos)
    out.write(data)
    out.close()
    bos.toByteArray
  }

  def gunzip(buffer: ByteBuffer): ByteBuffer = {
    val bos = new ByteArrayOutputStream(8192)
    val in = new GZIPInputStream(new ByteBufferBackedInputStream(buffer))
    IOUtils.copy(in, bos)
    in.close()
    bos.close()
    ByteBuffer.wrap(bos.toByteArray())
  }

  def gunzip(data: Array[Byte]): Array[Byte] = {
    val buffer = ByteBuffer.wrap(data)
    val byteBuffer = gunzip(buffer)
    val result = new Array[Byte](byteBuffer.remaining)
    byteBuffer.get(result)
    result
  }

}

trait ThriftDeserializer[T <: ThriftStruct] {

  val codec: ThriftStructCodec[T]

  def deserialize(buffer: ByteBuffer): Try[T] = {
    Try {
      val settings = buffer.get()
      val compressionType = compression(settings)
      compressionType match {
        case NoneType => payload(buffer)
        case GzipType => payload(Compression.gunzip(buffer))
      }
    }
  }

  private def compression(settings: Byte): CompressionType = {
    val compressionMask = 0x07.toByte
    val compressionType = (settings & compressionMask).toByte
    compressionType match {
      case 0 => NoneType
      case 1 => GzipType
      case x => throw new RuntimeException(s"The compression type: ${x} is not supported")
    }
  }

  private def payload(buffer: ByteBuffer): T = {
    val bbis = new ByteBufferBackedInputStream(buffer)
    val transport = new TIOStreamTransport(bbis)
    val protocol = new TCompactProtocol(transport)
    codec.decode(protocol)
  }
}
