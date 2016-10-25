namespace * contentatom.media
namespace java com.gu.contentatom.thrift.atom.media
#@namespace scala com.gu.contentatom.thrift.atom.media

typedef i64 Version

enum Platform {
  YOUTUBE = 1,
  FACEBOOK = 2,
  DAILYMOTION = 3,
  MAINSTREAM = 4,
  URL = 5
}

enum AssetType {
  AUDIO = 1,
  VIDEO = 2
}

enum Category {
  DOCUMENTARY = 1,
  EXPLAINER = 2,
  FEATURE = 3,
  NEWS = 4,
  HOSTED = 5// commercial content supplied by advertiser
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
}

struct MediaAtom {
  /* the unique ID will be stored in the `atom` data, and this should correspond to the pluto ID */
  2: required list<Asset> assets
  3: optional Version activeVersion
  4: required string title
  5: required Category category
  6: optional string plutoProjectId
  7: optional i64 duration // milliseconds
  8: optional string source
  9: optional string posterUrl
  10: optional string description
  11: optional Metadata metadata
}
