namespace * contentatom.explainer
namespace java com.gu.contentatom.thrift.atom.explainer

enum DisplayType {
  FLAT,
  EXPANDABLE,
  CAROUSEL
}

struct ExplainerAtom {
  /* the unique ID will be stored in the `atom` data*/
  2: required string title
  3: required string body
  4: required DisplayType displayType
}
