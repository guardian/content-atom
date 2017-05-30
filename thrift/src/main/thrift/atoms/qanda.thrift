namespace * contentatom.qanda
namespace java com.gu.contentatom.thrift.atom.qanda
#@namespace scala com.gu.contentatom.thrift.atom.qanda

include "entity.thrift"
include "../shared.thrift"

struct QAndA {
  1: optional string typeLabel
  2: optional list<string> tags
  3: optional Image eventImage
  4: required list<QAndAItem> items
  5: optional string sidenote
}

struct QAndAItem {
  1: optional string title
  2: required string body
}
