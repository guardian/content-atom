namespace * contentatom.explainer
namespace java com.gu.contentatom.thrift.atom.explainer
#@namespace scala com.gu.contentatom.thrift.atom.explainer

enum DisplayType {
  FLAT = 0,
  EXPANDABLE = 1,
  CAROUSEL = 2
}

struct ExplainerAtom {
  1: optional string label
  /* the unique ID will be stored in the `atom` data*/
  2: required string title
  3: required string body
  4: required DisplayType displayType
  5: optional list<string> tags
  6: optional ExplainerData explainerBody
}

union ExplainerData {
  1: QAExplainerData qa
  2: GuideExplainerData guide
  3: TimelineExplainerData timeline
  4: ProfileExplainerData profile
}

struct QAExplainerData {

}

struct GuideExplainerData {

}

struct TimelineExplainerData {

}

struct ProfileExplainerData {

}
