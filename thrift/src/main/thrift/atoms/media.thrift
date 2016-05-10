namespace * contentatom.video
namespace java com.gu.contentatom.thrift.atom.video
#@namespace scala com.gu.contentatom.thrift.atom.video

typedef i64 Version

enum AssetType {
  YOUTUBE,
  FACEBOOK,
  PODCAST,
  DAILYMOTION,
  MAINSTREAM,
  SUBTITLES
}

struct Asset {
  1: required AssetType assetType
  2: required Version version
  3: required string id
}

struct MediaAtom {
  /* the unique ID will be stored in the `atom` data, and this should correspond to the pluto ID */
  2: required list<Asset> assets
  3: required Version activeVersion
  4: required string pluto_project_id
}
