namespace * contentatom.viewpoints
namespace java com.gu.contentatom.thrift.atom.viewpoints
# for some reason scrooge (the version we're using at least) overrides 'java'
# with '*', so we need to add the scala namespace. Apache Thrift will reject
# this so they have allowed this special format which appears as a comment to
# Thrift.
#@namespace scala com.gu.contentatom.thrift.atom.viewpoints

include "../shared.thrift"

struct Commenter {
  /** the name of the person or organisation */
  1: required string name

  /** a picture to display with the commenter, typically a cut out head shot */
  2: optional string imageUrl

  /** A longer description (e.g. "Former secretary of state Hillary Clinton") */
  3: optional string description

  /** an optional party */
  4: optional string party
}

struct Viewpoint {

  /** who this viewpoint is attributed to */
  1: required Commenter commenter

  /** the text */
  2: required string quote

  /** an optional url for reading more */
  3: optional string link

  /** an optional date */
  4: optional shared.DateTime date
}

struct ViewpointsAtom {

  /** the title / subject name */
  1: required string name

  /** an optional url that would be used when the atom is shared */
  2: optional string link

  /** a list of viewpoints from commenters on this subject */
  3: required list<Viewpoint> viewpoints
}
