namespace * contentatom.timeline
namespace java com.gu.contentatom.thrift.atom.timeline
#@namespace scala com.gu.contentatom.thrift.atom.timeline

include "entity.thrift"
include "../shared.thrift"

struct TimelineAtom {
  1: optional string typeLabel
  2: optional list<string> tags
  3: required list<TimelineItem> events
  4: optional Entity entity
}

struct TimelineItem {
  1: required string title
  2: required DateTime date
  3: optional string body
}
