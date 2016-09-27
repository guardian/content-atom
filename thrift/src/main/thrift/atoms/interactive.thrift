namespace * contentatom.cta
namespace java com.gu.contentatom.thrift.atom.interactive
#@namespace scala com.gu.contentatom.thrift.atom.interactive

struct InteractiveAtom {
  /* the unique ID will be stored in the `atom` data*/
  2: required string type
  3: required string css
  4: required string html
  5: optional string inlineJS
  6: optional string mainJS
  7: optional string docData
}
