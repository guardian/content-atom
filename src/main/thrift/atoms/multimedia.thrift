namespace * contentatom.quiz

namespace java com.gu.contentatom.thrift.atom.multimedia

include "../shared.thrift"

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
    DRAFT,          //This is still being edited
    UNPUBLISHED,    //It's ready to go but not been launched yet
    PUBLISHED       //It's published in CAPI
}

enum ChapterType {
    ONDEMAND,      //regular video composed of VOD encodings
    PRE_EVENT,      //VOD or image encodings to play in a loop before a live event starts
    PRE_ROLL,       //VOD or image encodings to play every time someone lands on the player
    LIVE_EVENT,     //Live streaming endpoints to play a live event
    POST_ROLL,      //VOD or image encodings to play (when the user stops the player?) or the live stream ends
    POST_EVENT      //VOR or image encodings as a holding pattern between when a live event ends and the VOD highlights package is available
}

struct Legals {
    1: required LegalStatus status
    2: User lawyer
}

struct FrameSize {
    1: required i16 width
    2: required i16 height
    3: required float aspect
    4: optional string nickName  //e.g., "small", "mobile", "HD", etc.
}

struct Rendition {
    1: required string containerType
    2: required MultimediaSubtype mediaType
    3: optional FrameSize frameSize
    4: required bool hasVideo
    5: required bool hasAudio
    6: optional float videoBitRate
    7: optional float audioBitRate

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
  7  : required PublicationStatus publicationStatus
  8  : required MultimediaSubtype mediaType
  10 : required list<Chapter> chapters
  11 : required User creator        //we might not be able to get these easily but it would be good to get them
  12 : required User commissioner   //we might not be able to get these easily but it would be good to get them
}

struct QuizContent {
  1: required list<Question> questions
  2: required ResultGroups resultGroups
}

struct ResultGroups {
  1: required list<ResultGroup> groups
  2: required bool revealAtEnd
}

struct ResultGroup {
  1: required string title
  2: required string share
  3: required i16 minScore
}

struct Question {
  1: required string questionText
  2: required list<Asset> assets
  3: required list<Answer> answers
}

struct Answer {
  1: required string answerText
  2: required list<Asset> assets
  3: required i16 weight
  4: optional string revealText
}

struct Asset {
  1: required string type
  /* what type is this? currently assuming opaque json */
  2: required shared.OpaqueJson data
}
