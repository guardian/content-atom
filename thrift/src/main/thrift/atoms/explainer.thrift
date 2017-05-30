namespace * contentatom.explainer
namespace java com.gu.contentatom.thrift.atom.explainer
#@namespace scala com.gu.contentatom.thrift.atom.explainer

include "entity.thrift"
include "../shared.thrift"

enum DisplayType {
  FLAT = 0,
  EXPANDABLE = 1,
  CAROUSEL = 2
}

enum ExplainerType {
  QA = 0,
  GUIDE = 1,
  TIMELINE = 2,
  PROFILE = 3
}

struct ExplainerAtom {
  1: optional string label
  /* the unique ID will be stored in the `atom` data*/
  2: required string title
  /* hopefully we can get rid of this one later: */
  3: required string body
  4: required DisplayType displayType
  5: optional list<string> tags

  6: optional ExplainerType explainerType
  7: optional QAndAExplainerData qa
  8: optional GuideExplainerData guide
  9: optional TimelineExplainerData timeline
  10: optional ProfileExplainerData profile
}

struct QAndAExplainerData {
  1: optional Image eventImage
  2: required list<ExplainerContentItem> items
  3: optional string sidenote
}

struct GuideExplainerData {
  1: optional Image eventImage
  2: required list<ExplainerContentItem> items
}

struct TimelineExplainerData {
  1: required list<ExplainerTimelineItem> events
  3: optional Entity entity
}

struct ProfileExplainerData {
  1: optional Image headshot
  2: required list<ExplainerContentItem> items
  3: optional Entity entity
}

struct ExplainerContentItem {
  1: optional string title
  2: required string body
}

struct ExplainerTimelineItem {
  1: required string title
  2: required DateTime date
  3: optional string body
}
