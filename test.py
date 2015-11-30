#!/usr/bin/env python

import sys
sys.path.append('src/main/thrift/atoms/gen-py')
sys.path.append('/usr/lib/python2.7/site-packages')
sys.path.append('')
#from contentatom.multimedia import MultimediaAtom
import contentatom.multimedia.ttypes as mm
import shared.ttypes as shared
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TCompactProtocol
#send first byte as 0=> uncompressed 4=>gzip compressed
#from boto import kinesis

# struct Rendition {
#     1: required string containerType
#     2: required MultimediaSubtype mediaType
#     3: optional FrameSize frameSize
#     4: required bool hasVideo
#     5: required bool hasAudio
#     6: required bool hasEmbeddedSubs
#     7: required bool hasExternalSubs
#     8: optional double videoBitRate
#     9: optional double audioBitRate
#     10: optional i16 audioChannels
#     20: required string url
#     21: optional string subsUrl
#     22: optional list<string> imageUrls
# }

rendition = mm.Rendition()
rendition.containerType = "mp4"
rendition.mediaType = mm.MultimediaSubtype.VIDEO
rendition.frameSize = mm.FrameSize(width=1920, height=1080, aspect=1.7777777777, nickName="HD 1080")
rendition.hasVideo = True
rendition.hasAudio = True
rendition.hasEmbeddedSubs = False
rendition.hasExternalSubs = False
rendition.videoBitRate = 2048   #kbit/s
rendition.audioBitRate = 128
rendition.audioChannels = 2
rendition.url = "http://cdn.theguardian.tv/not/a/real/path/to/an.mp4"
rendition.validate()

# struct Chapter {
#     1: required ChapterType type
#     2: required MultimediaSubtype mediaType
#     3: required EncodingStatus encodingStatus
#     4: required list<Rendition> renditions
#     5: required LegalStatus legalStatus
# }

chapter = mm.Chapter()
chapter.type = mm.ChapterType.ONDEMAND
chapter.mediaType = mm.MultimediaSubtype.VIDEO
chapter.encodingStatus = mm.EncodingStatus.NOT_READY
chapter.legalStatus = mm.LegalStatus.NOT_REQUIRED
chapter.renditions = [rendition]
chapter.validate()

# struct MultimediaAtom {
#   // do we need to store the ID, seeing as it is replicated(?) in the
#   // content-atom wrapping?
#   // should we store an embed count here or is this done higher up?
#   1  : required string id
#   2  : required string title
#   3  : required i16 contentVersion  //from __version field in Vidispine
#   7  : required PublicationStatus publicationStatus
#   8  : required MultimediaSubtype mediaType
#   9  : required LegalStatus legalStatus
#   10 : required list<Chapter> chapters
#   11 : required User creator        //we might not be able to get these easily but it would be good to get them
#   12 : required User commissioner   //we might not be able to get these easily but it would be good to get them
# }

atom = mm.MultimediaAtom()
atom.id="XX-999999"
atom.title = "first test atom"
atom.contentVersion = 1
atom.publicationStatus = mm.PublicationStatus.DRAFT
atom.mediaType = mm.MultimediaSubtype.VIDEO
atom.legalStatus = mm.LegalStatus.NOT_REQUIRED
atom.chapters = [chapter]
atom.creator = shared.User(email='andy.gallagher@theguardian.com',firstName='Andy',lastName='Gallagher')
atom.commissioner = shared.User(email='andy.gallagher@theguardian.com',firstName='Andy',lastName='Gallagher')
atom.validate()

transport = TTransport.TMemoryBuffer()
out = TCompactProtocol.TCompactProtocol(transport)
atom.write(out)

print transport.getvalue()