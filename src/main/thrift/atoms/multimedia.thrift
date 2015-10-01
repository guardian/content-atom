namespace * contentatom.multimedia

namespace java com.gu.contentatom.thrift.atom.multimedia

include "../shared.thrift"

struct User {
    1: required string email;
    2: optional string firstName;
    3: optional string lastName;
    4: optional i32 googleID
}

enum LegalStatus {
    NOT_REQUIRED,
    PENDING,
    IN_PROGRESS,
    APPROVED,
    REJECTED
}

enum MultimediaSubtype {
    AUDIO,  //requires an audio-only player
    VIDEO,  //requires an audio-video player
    SILENT, //requires a video-only player
    IMAGE_SEQUENCE  //requires an audio player with ability to play (possibly) synced images
}

enum EncodingStatus {
    NOT_READY,  //encoding has not started
    PARTIAL_ENCODINGS,  //encoding is underway but not all assets have arrived
    ALL_ENCODINGS   //all assets have been delivered
}

enum PublicationStatus {
    NO_CONTENT,     //No media essence has been attached to this yet
    DRAFT,          //This is still being edited
    UNPUBLISHED,    //It's ready to go but not been launched yet
    PUBLISHED,       //It's published in CAPI
}

enum ChapterType {
    ONDEMAND,      //regular video composed of VOD encodings
    PRE_EVENT,      //VOD or image encodings to play in a loop before a live event starts
    PRE_ROLL,       //VOD or image encodings to play every time someone lands on the player
    LIVE_EVENT,     //Live streaming endpoints to play a live event
    POST_ROLL,      //VOD or image encodings to play (when the user stops the player?) or the live stream ends
    POST_EVENT      //VOD or image encodings as a holding pattern between when a live event ends and the VOD highlights package is available
}

enum AnnotationType {
    TEXT,
    IMAGE,
    LINK
}

struct Annotation {
    1: required AnnotationType type
    2: required string content
}

struct Legals {
    1: required LegalStatus status
    2: required User lawyer
}

struct FrameSize {
    1: required i16 width
    2: required i16 height
    3: required double aspect   //will normally be 1.777777777, i.e. 16:9
    4: optional string nickName  //e.g., "small", "mobile", "HD", etc.
}

struct Rendition {
    1: required string containerType
    2: required MultimediaSubtype mediaType
    3: optional FrameSize frameSize
    4: required bool hasVideo
    5: required bool hasAudio
    6: required bool hasEmbeddedSubs
    7: required bool hasExternalSubs
    8: optional double videoBitRate
    9: optional double audioBitRate
    10: optional i16 audioChannels
    20: required string url
    21: optional string subsUrl
    22: optional list<string> imageUrls
}

struct Chapter {
    1: required ChapterType type
    2: required MultimediaSubtype mediaType
    3: required EncodingStatus encodingStatus
    4: required list<Rendition> renditions
    5: required LegalStatus legalStatus
}

struct MultimediaAtom {
  // do we need to store the ID, seeing as it is replicated(?) in the
  // content-atom wrapping?
  // should we store an embed count here or is this done higher up?
  1  : required string id
  2  : required string title
  3  : required i16 contentVersion  //from __version field in Vidispine
  7  : required PublicationStatus publicationStatus
  8  : required MultimediaSubtype mediaType
  9  : required LegalStatus legalStatus
  10 : required list<Chapter> chapters
  11 : required User creator        //we might not be able to get these easily but it would be good to get them
  12 : required User commissioner   //we might not be able to get these easily but it would be good to get them
  13 : required string mediaTag
}
