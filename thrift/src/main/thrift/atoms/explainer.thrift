namespace * contentatom.explainer
namespace java com.gu.contentatom.thrift.atom.explainer
#@namespace scala com.gu.contentatom.thrift.atom.explainer

include "../shared.thrift"

enum DisplayType {
  FLAT = 0,
  EXPANDABLE = 1,
  CAROUSEL = 2
}

struct ExplainerAtom {
  1: optional string label
  /* the unique ID will be stored in the `atom` data*/
  2: required string title
  /* hopefully we can get rid of this one later: */
  3: required string body
  4: required DisplayType displayType
  5: optional list<string> tags
  6: optional ExplainerData explainerBody
}

union ExplainerData {
  1: QAndAExplainerData qa
  2: GuideExplainerData guide
  3: TimelineExplainerData timeline
  4: ProfileExplainerData profile
}

struct QAndAExplainerData {
  1: optional Image eventshot
  2: required list<ExplainerContentItem> tidbits
  3: optional string sidenote
}

struct GuideExplainerData {
  1: optional Image eventshot
  2: required list<ExplainerContentItem> tidbits
}

struct TimelineExplainerData {
  1: required list<ExplainerTimelineItem> events
  3: optional Entity entity
}

struct ProfileExplainerData {
  1: optional Image headshot
  2: required list<ExplainerContentItem> tidbits
  3: optional Entity entity
}

struct ExplainerContentItem {
  1: optional string title
  2: required string body
}

struct ExplainerTimelineItem {
  1: optional string title
  2: required DateTime date
  2: required string body
}
