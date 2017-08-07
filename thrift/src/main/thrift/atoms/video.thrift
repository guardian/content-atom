namespace * contentatom.video
namespace java com.gu.contentatom.thrift.atom.video

include "../shared.thrift"

typedef i64 Version

enum Platform {
  YOUTUBE = 0,
  URL = 1
}

enum Category {
  DOCUMENTARY   = 0,
  EXPLAINER     = 1,
  FEATURE       = 2,
  NEWS          = 3,
  HOSTED        = 4, // commercial content supplied by advertiser
  PAIDFOR       = 5
}

/** how a YouTube video can be watched **/
enum PrivacyStatus {
   PRIVATE      = 0, // requires login, not returned by search
   UNLISTED     = 1, // requires knowledge of URL, not returned by search
   PUBLIC       = 2 // can be viewed and found by search
}

struct Asset {
  1: required Version version
  2: required string id
  3: required Platform platform
  4: required i64 duration // seconds
  5: optional string mimeType
}

struct PlutoData {
  1: required string commissionId
  2: required string projectId
  3: optional string masterId
}

struct YouTubeMetadata {
  1: required string channelId
  2: required PrivacyStatus privacyStatus
  3: required string categoryId // https://developers.google.com/youtube/v3/docs/videos#snippet.categoryId
  4: optional shared.DateTime expiryDate // when the PrivacyStatus will change to Private
  5: optional list<string> tags // https://developers.google.com/youtube/v3/docs/videos#snippet.tags[]
}

struct CanonicalPageMetadata {
  1: string trailText
  2: list<string> byline
  3: list<string> commissioningDesks
  4: list<string> tags
  5: shared.Image trailImage
  6: bool optimisedForWeb
  7: bool commentsEnabled
  8: bool suppressRelatedContent
}

struct VideoAtom {
  1: required list<Asset> assets
  2: Version activeVersion
  3: required string title
  4: optional string description
  5: shared.Image posterImage
  6: optional string source
  7: optional YouTubeMetadata youtubeMetadata
  8: optional CanonicalPageMetadata canonicalPageMetadata
  9: optional PlutoData plutoData
}
