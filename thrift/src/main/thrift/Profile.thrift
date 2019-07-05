namespace java com.gu.contentatom.thrift

/** Identifies an entity (human or not) who wrote an atom */
struct Profile {
  1: required string id

  2: optional string title

  3: required string firstName

  4: required string lastName

  5: optional string emailAddress

  6: optional string twitterHandle

  7: optional string imageUrl
}