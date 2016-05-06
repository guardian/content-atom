namespace * contentatom.video
namespace java com.gu.contentatom.thrift.atom.video
#@namespace scala com.gu.contentatom.thrift.atom.video

typedef i64 Version

enum AssetType {
  YOUTUBE,
  FACEBOOK,
  /* not sure about this one ... ? */
  SUBTITLES
}

struct Asset {
  1: required AssetType assetType
  2: required Version version
  3: required string id
}

struct VideoAtom {
  /* the unique ID will be stored in the `atom` data, and this should correspond to the pluto ID */
  2: required list<Asset> assets
  3: required Version latestVersion
}
