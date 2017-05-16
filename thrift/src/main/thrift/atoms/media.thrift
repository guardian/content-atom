namespace * contentatom.media
namespace java com.gu.contentatom.thrift.atom.media
#@namespace scala com.gu.contentatom.thrift.atom.media

include "../shared.thrift"

typedef i64 Version

enum Platform {
  YOUTUBE = 0,
  FACEBOOK = 1,
  DAILYMOTION = 2,
  MAINSTREAM = 3,
  URL = 4
}

enum AssetType {
  AUDIO = 0,
  VIDEO = 1
}

enum Category {
  DOCUMENTARY = 0,
  EXPLAINER = 1,
  FEATURE = 2,
  NEWS = 3,
  HOSTED = 4// commercial content supplied by advertiser
}

enum PrivacyStatus {
   PRIVATE = 0,
   UNLISTED = 1,
   PUBLIC = 2
}

struct Asset {
  1: required AssetType assetType
  2: required Version version
  3: required string id
  4: required Platform platform
  5: optional string mimeType
}

struct Metadata {
  1: optional list<string> tags
  2: optional string categoryId
  3: optional string license
  4: optional bool commentsEnabled
  5: optional string channelId
  6: optional PrivacyStatus privacyStatus
  7: optional shared.DateTime expiryDate
}

struct MediaAtom {
  /* the unique ID will be stored in the `atom` data, and this should correspond to the pluto ID */
  2: required list<Asset> assets
  3: optional Version activeVersion
  4: required string title
  5: required Category category
  6: optional string plutoProjectId
  7: optional i64 duration // seconds
  8: optional string source
  9: optional string posterUrl
  10: optional string description
  11: optional Metadata metadata
  12: optional shared.Image posterImage
  14: optional string trailText
}
