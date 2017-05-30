namespace * contentatom.sidenote
namespace java com.gu.contentatom.thrift.atom.sidenote
#@namespace scala com.gu.contentatom.thrift.atom.sidenote

include "entity.thrift"
include "../shared.thrift"

enum SideNoteType {
  QA = 0,
  GUIDE = 1,
  TIMELINE = 2,
  PROFILE = 3
}

struct SideNoteAtom {
  1: optional string typeLabel
  2: optional list<string> tags

  3: required SideNoteType sidenoteType
  4: optional QAndA qa
  5: optional Guide guide
  6: optional Timeline timeline
  7: optional Profile profile
}

struct QAndA {
  1: optional Image eventImage
  2: required list<SideNoteContentItem> items
  3: optional string sidenote
}

struct Guide {
  1: optional Image eventImage
  2: required list<SideNoteContentItem> items
}

struct Timeline {
  1: required list<TimelineContentItem> events
  2: optional Entity entity
}

struct Profile {
  1: optional Image headshot
  2: required list<SideNoteContentItem> items
  3: optional Entity entity
}

struct SideNoteContentItem {
  1: optional string title
  2: required string body
}

struct TimelineContentItem {
  1: required string title
  2: required DateTime date
  3: optional string body
}
