namespace * contentatom.guide
namespace java com.gu.contentatom.thrift.atom.guide
#@namespace scala com.gu.contentatom.thrift.atom.guide

include "entity.thrift"
include "../shared.thrift"

struct Guide {
  1: optional string typeLabel
  2: optional list<string> tags
  3: optional Image eventImage
  4: required list<GuideItem> items
}

struct GuideItem {
  1: optional string title
  2: required string body
}
